package uk.gov.companieshouse.common.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.companieshouse.common.web.interceptor.ChsPageBackingInterceptor;
import uk.gov.companieshouse.common.web.sdk.ApiClientService;
import uk.gov.companieshouse.common.web.service.BasketService;
import uk.gov.companieshouse.logging.Logger;

@Configuration
public class ChsPageBackingInterceptorConfig {

    private final String internalApiUrl;
    private final Logger logger;

    public ChsPageBackingInterceptorConfig(final String internalApiUrl, final Logger logger) {
        this.internalApiUrl = internalApiUrl;
        this.logger = logger;
    }

    @Bean
    public ChsPageBackingInterceptor chsPageBackingInterceptor() {
        var apiClientService = new ApiClientService(internalApiUrl);
        var basketService = new BasketService(apiClientService, logger);
        return new ChsPageBackingInterceptor(basketService, logger);
    }

}
