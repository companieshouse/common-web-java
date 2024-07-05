package uk.gov.companieshouse.common.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.companieshouse.common.web.interceptor.ChsPageBackingInterceptor;
import uk.gov.companieshouse.common.web.interceptor.InterceptorFactory;
import uk.gov.companieshouse.logging.Logger;

@Configuration
public class InterceptorFactoryConfig {

    private final String internalApiUrl;

    public InterceptorFactoryConfig(@Value("${internal.api.url}") final String internalApiUrl) {
        this.internalApiUrl = internalApiUrl;
    }

    @Bean
    public ChsPageBackingInterceptor chsPageBackingInterceptor(final Logger logger) {
        return InterceptorFactory.createChsPageBackingInterceptor(logger, internalApiUrl);
    }

}
