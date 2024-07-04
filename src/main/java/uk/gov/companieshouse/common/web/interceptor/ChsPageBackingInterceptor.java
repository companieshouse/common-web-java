package uk.gov.companieshouse.common.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.companieshouse.common.web.service.BasketService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.session.Session;
import uk.gov.companieshouse.session.handler.SessionHandler;
import uk.gov.companieshouse.session.model.UserProfile;

import java.util.Objects;

/**
 * Interceptor that provides basic "backing" state required to render CHS pages.
 */
@Component
public class ChsPageBackingInterceptor implements HandlerInterceptor {

    static final String MODEL_ATTR_USER_BAR = "userBar";
    static final String MODEL_ATTR_YOUR_DETAILS_URL = "yourDetailsUrl";
    static final String MODEL_ATTR_RECENT_FILINGS_URL = "recentFilingsUrl";
    static final String MODEL_ATTR_COMPANIES_YOU_FOLLOW_URL = "companiesYouFollowUrl";
    static final String MODEL_ATTR_USER_SIGNOUT_URL = "userSignoutUrl";
    static final String MODEL_ATTR_BASKET_URL = "basketWebUrl";
    static final String MODEL_ATTR_BASKET_ITEMS = "basketItemsNum";
    static final String MODEL_ATTR_USER_EMAIL = "userEmail";
    static final String GET_REQUEST = "GET";
    static final String YOUR_DETAILS_URL = "/user/account";
    static final String YOUR_FILINGS_URL = "/user/transactions";
    static final String USER_SIGNOUT_URL = "/signout";
    static final String BASKET_URL = "/basket";

    static final String TRUE = "1";

    private final BasketService basketService;
    private final Logger logger;

    @Value("${chs.url}")
    private String chsUrl;
    @Value("${disable.follow}")
    private String disableFollow;
    @Value("${monitor.gui-url}")
    private String monitorGuiUrl;

    public ChsPageBackingInterceptor(BasketService basketService,
                                     Logger logger) {
        this.basketService = basketService;
        this.logger = logger;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                           @NotNull Object handler,
                           @Nullable ModelAndView modelAndView) {
        if (modelAndView == null) {
            logger.info("Warning: postHandle(): modelAndView is null.");
            return;
        }
        setUpUserBar(request, modelAndView);
    }

    private void setUpUserBar(@NotNull HttpServletRequest request, @NotNull ModelAndView modelAndView) {
        modelAndView.addObject(MODEL_ATTR_USER_BAR, TRUE);
        modelAndView.addObject(MODEL_ATTR_YOUR_DETAILS_URL, YOUR_DETAILS_URL);
        modelAndView.addObject(MODEL_ATTR_RECENT_FILINGS_URL, chsUrl + YOUR_FILINGS_URL);

        if ((disableFollow == null || !disableFollow.equals("1")) && monitorGuiUrl != null) {
            modelAndView.addObject(MODEL_ATTR_COMPANIES_YOU_FOLLOW_URL, monitorGuiUrl);
        }

        setUpBasketLink(request, modelAndView);

        modelAndView.addObject(MODEL_ATTR_USER_SIGNOUT_URL, USER_SIGNOUT_URL);

        var userEmail = getEmailAddressFromUserProfile();
        modelAndView.addObject(MODEL_ATTR_USER_EMAIL, userEmail);
    }

    private void setUpBasketLink(@NotNull HttpServletRequest request, @NotNull ModelAndView modelAndView) {
        String[] basketItems = null;
        try {
            basketItems = basketService.getBasketLinks();
        } catch(Exception e) {
            logger.errorRequest(request, "Exception getting basket links: " + e.getMessage(), e);
        }

        if (basketItems != null) {
            modelAndView.addObject(MODEL_ATTR_BASKET_URL, chsUrl + BASKET_URL);
            modelAndView.addObject(MODEL_ATTR_BASKET_ITEMS, basketItems.length);
        }
    }

    // TODO CC-1415 Clarify whether we should be getting the user email address this way going forward.
    // Private beta work : get email address from session of a signed-in user.
    protected String getEmailAddressFromUserProfile(){
        UserProfile userProfile;
        var signInInfo = getSession().getSignInInfo();
        if (signInInfo != null) {
            userProfile = signInInfo.getUserProfile();
        } else {
            userProfile = new UserProfile();
        }
        // Return an empty String rather than null
        return Objects.requireNonNullElse( userProfile.getEmail(), "");
    }

    private Session getSession() {
        return SessionHandler.getSessionFromContext();
    }

}