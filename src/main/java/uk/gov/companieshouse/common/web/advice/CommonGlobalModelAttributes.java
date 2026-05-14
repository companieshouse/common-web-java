package uk.gov.companieshouse.common.web.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CommonGlobalModelAttributes {

    private final String govukFrontendVersion;
    private final String govukFrontendPathname;
    private final String useChHeader;
    private final String cdnUrl;
    private final String chsUrl;
    private final String piwikUrl;
    private final String piwikSiteId;
    private final String contactUsUrl;
    private final String developerUrl;

    public CommonGlobalModelAttributes(
            @Value("${govuk.frontend.version:5.11.0}") String govukFrontendVersion,
            @Value("${govuk.frontend.pathname:/rebrand}") String govukFrontendPathname,
            @Value("${use.ch.header:false}") String useChHeader,
            @Value("${cdn.url}") String cdnUrl,
            @Value("${chs.url}") String chsUrl,
            @Value("${piwik.url}") String piwikUrl,
            @Value("${piwik.siteId}") String piwikSiteId,
            @Value("${contactUsUrl}") String contactUsUrl,
            @Value("${developer.url}") String developerUrl
    ) {
        this.govukFrontendVersion = govukFrontendVersion;
        this.govukFrontendPathname = govukFrontendPathname;
        this.useChHeader = useChHeader;
        this.cdnUrl = cdnUrl;
        this.chsUrl = chsUrl;
        this.piwikUrl = piwikUrl;
        this.piwikSiteId = piwikSiteId;
        this.contactUsUrl = contactUsUrl;
        this.developerUrl = developerUrl;
    }

    @ModelAttribute("govukFrontendVersion")
    public String govukFrontendVersion() {
        return govukFrontendVersion;
    }

    @ModelAttribute("govukFrontendPathname")
    public String govukFrontendPathname() {
        return govukFrontendPathname;
    }

    @ModelAttribute("useChHeader")
    public String useChHeader() {
        return useChHeader;
    }

    @ModelAttribute("cdnUrl")
    public String cdnUrl() {
        return cdnUrl;
    }

    @ModelAttribute("chsUrl")
    public String chsUrl() {
        return chsUrl;
    }

    @ModelAttribute("piwikUrl")
    public String piwikUrl() {
        return piwikUrl;
    }

    @ModelAttribute("piwikSiteId")
    public String piwikSiteId() {
        return piwikSiteId;
    }

    @ModelAttribute("contactUsUrl")
    public String contactUsUrl() {
        return contactUsUrl;
    }

    @ModelAttribute("developerUrl")
    public String developerUrl() {
        return developerUrl;
    }
}