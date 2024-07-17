package uk.gov.companieshouse.common.web.interceptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.companieshouse.common.web.service.BasketService;
import uk.gov.companieshouse.environment.exception.EnvironmentVariableException;
import uk.gov.companieshouse.logging.Logger;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;

import java.util.List;

@SpringBootTest
class AbstractChsPageBackingInterceptorExceptionsTest {

    private static final String POST_REQUEST = "POST";
    private static final String USER_EMAIL_ADDRESS = "user@email.com";
    private static final String TOKEN_VALUE = "Token value";

    private static final List<String> REQUIRED_ENVIRONMENT_VARIABLES = List.of("COOKIE_NAME",
            "COOKIE_DOMAIN",
            "CHS_API_KEY",
            "API_URL",
            "PAYMENTS_API_URL",
            "DOCUMENT_API_LOCAL_URL");

    // This excludes COOKIE_NAME and COOKIE_DOMAIN because their absence causes an EnvironmentVariableException
    // to arise as part of the static initialisation of SessionHandler, making it hard test the impact of the
    // absence of each in a robust, reproducible manner.
    private static final List<String> TESTABLE_REQUIRED_ENVIRONMENT_VARIABLES = List.of(
            "CHS_API_KEY",
            "API_URL",
            "PAYMENTS_API_URL",
            "DOCUMENT_API_LOCAL_URL");

    @Configuration
    static class TestConfiguration {

        @Bean
        BasketService basketService(final Logger logger) {
            return new BasketService(logger);
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

    @MockBean
    private Logger logger;

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

    @Test
    @DisplayName("Suppresses non-EnvironmentVariableException exception when setting up basket link")
    void postHandleSuppressesOtherExceptionWhenSettingUpBasketLink() throws Exception {

        // Given
        var env = new EnvironmentVariables();
        REQUIRED_ENVIRONMENT_VARIABLES.forEach(e -> env.set(e, TOKEN_VALUE));
        when(httpServletRequest.getMethod()).thenReturn(POST_REQUEST);

        env.execute(() -> {

            // When
            abstractChsPageBackingInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

            // Then
            verify(logger).errorRequest(any(HttpServletRequest.class),
                eq("Exception getting basket links: java.net.MalformedURLException: no protocol: Token value"),
                any(IllegalArgumentException.class));

        });

    }

    @Test
    @DisplayName("Requires environment variables when setting up basket link")
    void postHandleRequiresEnvironmentVariablesWhenSettingUpBasketLink() {
        TESTABLE_REQUIRED_ENVIRONMENT_VARIABLES.forEach(this::postHandleRequiresEnvironmentVariable);
    }

    private void postHandleRequiresEnvironmentVariable(final String environmentVariableName)  {

        // Given
        var env = new EnvironmentVariables();
        REQUIRED_ENVIRONMENT_VARIABLES.forEach(e -> {
            if(!e.equals(environmentVariableName)) {
                env.set(e, TOKEN_VALUE);
            }
        });
        when(httpServletRequest.getMethod()).thenReturn(POST_REQUEST);

        try {
            env.execute(() -> {
                // When and then
                final EnvironmentVariableException exception = assertThrows(EnvironmentVariableException.class,
                        () -> abstractChsPageBackingInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView));
                assertThat(exception.getMessage(), containsString("Environment variable missing: " + environmentVariableName));
            });
            env.teardown();
        } catch (Exception ex) {
            // Will not occur
        }
    }

}