package uk.gov.companieshouse.common.web.advice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.common.web.config.CommonWebProperties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommonGlobalModelAttributesTest {
    private CommonWebProperties props;
    private CommonGlobalModelAttributes attributes;

    @BeforeEach
    void setUp() {
        props = mock(CommonWebProperties.class);
        when(props.getGovukFrontendVersion()).thenReturn("5.11.0");
        when(props.getGovukFrontendPathname()).thenReturn("/rebrand");
        when(props.getUseChHeader()).thenReturn("true");
        when(props.getCdnUrl()).thenReturn("http://cdn");
        when(props.getChsUrl()).thenReturn("http://chs");
        when(props.getPiwikUrl()).thenReturn("http://piwik");
        when(props.getPiwikSiteId()).thenReturn("1");
        when(props.getContactUsUrl()).thenReturn("http://contact");
        when(props.getDeveloperUrl()).thenReturn("http://dev");
        attributes = new CommonGlobalModelAttributes(props);
    }

    @Test
    void testGovukFrontendVersion() {
        assertEquals("5.11.0", attributes.govukFrontendVersion());
    }

    @Test
    void testGovukFrontendPathname() {
        assertEquals("/rebrand", attributes.govukFrontendPathname());
    }

    @Test
    void testUseChHeader() {
        assertEquals("true", attributes.useChHeader());
    }

    @Test
    void testCdnUrl() {
        assertEquals("http://cdn", attributes.cdnUrl());
    }

    @Test
    void testChsUrl() {
        assertEquals("http://chs", attributes.chsUrl());
    }

    @Test
    void testPiwikUrl() {
        assertEquals("http://piwik", attributes.piwikUrl());
    }

    @Test
    void testPiwikSiteId() {
        assertEquals("1", attributes.piwikSiteId());
    }

    @Test
    void testContactUsUrl() {
        assertEquals("http://contact", attributes.contactUsUrl());
    }

    @Test
    void testDeveloperUrl() {
        assertEquals("http://dev", attributes.developerUrl());
    }
}

