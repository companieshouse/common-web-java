package uk.gov.companieshouse.common.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "commonweb")
public class CommonWebProperties {
    private String govukFrontendVersion = "5.11.0";
    private String govukFrontendPathname = "/rebrand";
    private String useChHeader = "false";
    private String cdnUrl;
    private String chsUrl;
    private String piwikUrl;
    private String piwikSiteId;
    private String contactUsUrl;
    private String developerUrl;

    // Getters and setters
    public String getGovukFrontendVersion() { return govukFrontendVersion; }
    public void setGovukFrontendVersion(String govukFrontendVersion) { this.govukFrontendVersion = govukFrontendVersion; }
    public String getGovukFrontendPathname() { return govukFrontendPathname; }
    public void setGovukFrontendPathname(String govukFrontendPathname) { this.govukFrontendPathname = govukFrontendPathname; }
    public String getUseChHeader() { return useChHeader; }
    public void setUseChHeader(String useChHeader) { this.useChHeader = useChHeader; }
    public String getCdnUrl() { return cdnUrl; }
    public void setCdnUrl(String cdnUrl) { this.cdnUrl = cdnUrl; }
    public String getChsUrl() { return chsUrl; }
    public void setChsUrl(String chsUrl) { this.chsUrl = chsUrl; }
    public String getPiwikUrl() { return piwikUrl; }
    public void setPiwikUrl(String piwikUrl) { this.piwikUrl = piwikUrl; }
    public String getPiwikSiteId() { return piwikSiteId; }
    public void setPiwikSiteId(String piwikSiteId) { this.piwikSiteId = piwikSiteId; }
    public String getContactUsUrl() { return contactUsUrl; }
    public void setContactUsUrl(String contactUsUrl) { this.contactUsUrl = contactUsUrl; }
    public String getDeveloperUrl() { return developerUrl; }
    public void setDeveloperUrl(String developerUrl) { this.developerUrl = developerUrl; }
}

