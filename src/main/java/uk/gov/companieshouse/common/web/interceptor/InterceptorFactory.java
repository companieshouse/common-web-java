package uk.gov.companieshouse.common.web.interceptor;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.gov.companieshouse.common.web.configuration.ChsPageBackingInterceptorConfig;
import uk.gov.companieshouse.logging.Logger;

public class InterceptorFactory {

    private InterceptorFactory() {}

    public static ChsPageBackingInterceptor createChsPageBackingInterceptor(final Logger logger,
                                                                            final String internalApiUrl) {
        final var beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerSingleton("logger", logger);
        beanFactory.registerSingleton("internalApiUrl", internalApiUrl);
        var ctx = new AnnotationConfigApplicationContext(beanFactory);
        ctx.registerBean(ChsPageBackingInterceptorConfig.class);
        ctx.refresh();
        return ctx.getBean(ChsPageBackingInterceptor.class);
    }

}
