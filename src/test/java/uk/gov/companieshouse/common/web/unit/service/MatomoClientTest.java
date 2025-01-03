package uk.gov.companieshouse.common.web.unit.service;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.matomo.java.tracking.MatomoRequest;
import org.matomo.java.tracking.MatomoTracker;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import uk.gov.companieshouse.common.web.service.MatomoClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatomoClientTest {

    private static final MatomoClient matomoClient=new MatomoClient("https://matomo.platform.aws.chdev.org", 24, "common-web-java");

    private MockHttpServletRequest servletRequest;

    @Mock
    MatomoTracker mockTracker;
    @Mock
    Cookie cookie;
    @Captor
    private ArgumentCaptor<MatomoRequest> matomoRequestCaptor;
    @BeforeEach
    public void setUp() {
        servletRequest = new MockHttpServletRequest();
        Cookie[] cookies = new Cookie[]{ cookie };
        servletRequest.setCookies(cookies);
        ReflectionTestUtils.setField(matomoClient, "tracker", mockTracker);
    }

    @Test
    @DisplayName("send goal")
    void sendGoalSuccess() {
        ReflectionTestUtils.setField(matomoClient, "tracker", mockTracker);
        when(cookie.getName()).thenReturn("_pk_id.24.ca6b");
        when(cookie.getValue()).thenReturn("d98f63f0c6f700cd.1732096477.");

        matomoClient.sendGoal(servletRequest.getCookies(), 42);

        verify(mockTracker).sendRequestAsync(matomoRequestCaptor.capture());
        var matomoRequest = matomoRequestCaptor.getValue();
        assertEquals(42, matomoRequest.getGoalId());
        assertEquals("d98f63f0c6f700cd", matomoRequest.getVisitorId().toString());
    }

    @Test
    @DisplayName("send event")
    void sendEventSuccess() {
        when(cookie.getName()).thenReturn("_pk_id.24.ca6b");
        when(cookie.getValue()).thenReturn("d98f63f0c6f700cd.1732096477.");

        matomoClient.sendEvent(servletRequest.getCookies(), "page", "event-action");

        verify(mockTracker).sendRequestAsync(matomoRequestCaptor.capture());
        var matomoRequest = matomoRequestCaptor.getValue();
        assertEquals("event-action", matomoRequest.getEventAction());
        assertEquals("common-web-java - page", matomoRequest.getEventCategory());
        assertEquals("d98f63f0c6f700cd", matomoRequest.getVisitorId().toString());
    }

    @Test
    @DisplayName("send goal")
    void noCookie() {
        ReflectionTestUtils.setField(matomoClient, "tracker", mockTracker);
        when(cookie.getName()).thenReturn("other-cookie");

        matomoClient.sendGoal(servletRequest.getCookies(), 42);

        verify(mockTracker, times(0)).sendRequestAsync(any());
    }
}
