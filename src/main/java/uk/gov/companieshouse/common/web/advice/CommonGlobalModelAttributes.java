package uk.gov.companieshouse.common.web.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import uk.gov.companieshouse.common.web.config.CommonWebProperties;

@ControllerAdvice
public class CommonGlobalModelAttributes {

    private final CommonWebProperties props;

    public CommonGlobalModelAttributes(CommonWebProperties props) {
        this.props = props;
    }

    @ModelAttribute("govukFrontendVersion")
    public String govukFrontendVersion() {
        return props.getGovukFrontendVersion();
    }

    @ModelAttribute("govukFrontendPathname")
    public String govukFrontendPathname() {
        return props.getGovukFrontendPathname();
    }

    @ModelAttribute("useChHeader")
    public String useChHeader() {
        return props.getUseChHeader();
    }

    @ModelAttribute("cdnUrl")
    public String cdnUrl() {
        return props.getCdnUrl();
    }

    @ModelAttribute("chsUrl")
    public String chsUrl() {
        return props.getChsUrl();
    }

    @ModelAttribute("piwikUrl")
    public String piwikUrl() {
        return props.getPiwikUrl();
    }

    @ModelAttribute("piwikSiteId")
    public String piwikSiteId() {
        return props.getPiwikSiteId();
    }

    @ModelAttribute("contactUsUrl")
    public String contactUsUrl() {
        return props.getContactUsUrl();
    }

    @ModelAttribute("developerUrl")
    public String developerUrl() {
        return props.getDeveloperUrl();
    }
}