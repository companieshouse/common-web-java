package uk.gov.companieshouse.common.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for common-web-java library.
 * Registered via META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
 * so that consuming services automatically pick up common beans (CommonGlobalModelAttributes etc.)
 * without needing explicit @ComponentScan.
 * This is the standard Spring Boot auto-configuration mechanism used by all Spring starters.
 */
@Configuration
@ComponentScan("uk.gov.companieshouse.common.web")
public class CommonWebAutoConfiguration {
}