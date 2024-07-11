package uk.gov.companieshouse.common.web.interceptor;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.BASKET_URL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.GET_REQUEST;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.MODEL_ATTR_BASKET_ITEMS;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.MODEL_ATTR_BASKET_URL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.MODEL_ATTR_COMPANIES_YOU_FOLLOW_URL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.MODEL_ATTR_RECENT_FILINGS_URL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.MODEL_ATTR_USER_BAR;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.MODEL_ATTR_USER_EMAIL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.MODEL_ATTR_USER_SIGNOUT_URL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.MODEL_ATTR_YOUR_DETAILS_URL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.TRUE;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.USER_SIGNOUT_URL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.YOUR_DETAILS_URL;
import static uk.gov.companieshouse.common.web.interceptor.AbstractChsPageBackingInterceptor.YOUR_FILINGS_URL;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.companieshouse.common.web.service.BasketService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import uk.gov.companieshouse.service.ServiceException;

@SpringBootTest
class AbstractChsPageBackingInterceptorTest {

    private static final String POST_REQUEST = "POST";
    private static final String USER_EMAIL_ADDRESS = "user@email.com";

    @Configuration
    static class TestConfiguration {

        @Bean
        Logger getLogger() {
            return LoggerFactory.getLogger("test-logger");
        }

        @Bean
        AbstractChsPageBackingInterceptor chsPageBackingInterceptor(final BasketService basketService,
                                                                    final Logger logger) {
            return new AbstractChsPageBackingInterceptor(basketService, logger) {
                @Override
                protected String getUserEmailAddress() {
                    return USER_EMAIL_ADDRESS;
                }
            };
        }

    }

    @Autowired
    private AbstractChsPageBackingInterceptor abstractChsPageBackingInterceptor;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private Object handler;

    @Mock
    private ModelAndView modelAndView;

    @MockBean
    private BasketService basketService;

    @Value("${chs.url}")
    private String chsUrl;

    @Value("${monitor.gui-url}")
    private String monitorGuiUrl;

    @Test
    @DisplayName("Handles null ModelAndView object gracefully")
    @SuppressWarnings("squid:S2699") // at least one assertion
    void postHandleHandlesNullModelAndViewGracefully() {
        abstractChsPageBackingInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, null);
    }

    @Test
    @DisplayName("Sets up the user bar")
    void postHandleSetsUpBasicUserBar() {

        // Given
        when(httpServletRequest.getMethod()).thenReturn(POST_REQUEST);

        // When
        abstractChsPageBackingInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

        // Then
        verify(modelAndView).addObject(MODEL_ATTR_USER_BAR, TRUE);
        verify(modelAndView).addObject(MODEL_ATTR_YOUR_DETAILS_URL, YOUR_DETAILS_URL);
        verify(modelAndView).addObject(MODEL_ATTR_RECENT_FILINGS_URL, chsUrl + YOUR_FILINGS_URL);
        verify(modelAndView).addObject(MODEL_ATTR_USER_SIGNOUT_URL, USER_SIGNOUT_URL);
    }

    @Test
    @DisplayName("Populates the companies you follow URL")
    void postHandlePopulatesCompaniesYouFollowUrl() {

        // Given
        when(httpServletRequest.getMethod()).thenReturn(POST_REQUEST);

        // When
        abstractChsPageBackingInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

        // Then
        verify(modelAndView).addObject(MODEL_ATTR_COMPANIES_YOU_FOLLOW_URL, monitorGuiUrl);
    }

    @Test
    @DisplayName("Populates the user email")
    void postHandlePopulatesUserEmail() {

        // Given
        when(httpServletRequest.getMethod()).thenReturn(GET_REQUEST);

        // When
        abstractChsPageBackingInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

        // Then
        verify(modelAndView).addObject(MODEL_ATTR_USER_EMAIL, USER_EMAIL_ADDRESS);
    }

    @Test
    @DisplayName("Sets up the basket link")
    void postHandleSetsUpBasketLink() throws ServiceException {

        // Given
        when(httpServletRequest.getMethod()).thenReturn(POST_REQUEST);
        when(basketService.getBasketLinks()).thenReturn(new String[] {"CRT-919416-521817"});

        // When
        abstractChsPageBackingInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

        // Then
        verify(modelAndView).addObject(MODEL_ATTR_BASKET_URL, chsUrl + BASKET_URL);
        verify(modelAndView).addObject(MODEL_ATTR_BASKET_ITEMS, 1);
    }

}