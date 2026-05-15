package uk.gov.companieshouse.common.web.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonWebPropertiesTest {
    @Test
    void testDefaultValues() {
        CommonWebProperties props = new CommonWebProperties();
        assertEquals("5.11.0", props.getGovukFrontendVersion());
        assertEquals("/rebrand", props.getGovukFrontendPathname());
        assertEquals("false", props.getUseChHeader());
    }

    @Test
    void testSettersAndGetters() {
        CommonWebProperties props = new CommonWebProperties();
        props.setGovukFrontendVersion("6.0.0");
        props.setGovukFrontendPathname("/custom");
        props.setUseChHeader("true");
        props.setCdnUrl("http://cdn.example.com");
        props.setChsUrl("http://chs.example.com");
        props.setPiwikUrl("http://piwik.example.com");
        props.setPiwikSiteId("123");
        props.setContactUsUrl("http://contact.example.com");
        props.setDeveloperUrl("http://dev.example.com");

        assertEquals("6.0.0", props.getGovukFrontendVersion());
        assertEquals("/custom", props.getGovukFrontendPathname());
        assertEquals("true", props.getUseChHeader());
        assertEquals("http://cdn.example.com", props.getCdnUrl());
        assertEquals("http://chs.example.com", props.getChsUrl());
        assertEquals("http://piwik.example.com", props.getPiwikUrl());
        assertEquals("123", props.getPiwikSiteId());
        assertEquals("http://contact.example.com", props.getContactUsUrl());
        assertEquals("http://dev.example.com", props.getDeveloperUrl());
    }
}

