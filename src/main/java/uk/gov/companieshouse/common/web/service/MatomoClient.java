package uk.gov.companieshouse.common.web.service;

import jakarta.servlet.http.Cookie;
import org.matomo.java.tracking.MatomoRequest;
import org.matomo.java.tracking.MatomoRequests;
import org.matomo.java.tracking.MatomoTracker;
import org.matomo.java.tracking.TrackerConfiguration;
import org.matomo.java.tracking.parameters.VisitorId;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Component
public class MatomoClient {
    private final String piwikUrl;
    private final int piwikSiteId;
    private final String serviceName;
    private final MatomoTracker tracker;
    private final Logger logger;

    public MatomoClient(String piwikUrl, int piwikSiteId, String serviceName) {
        this.piwikUrl = piwikUrl;
        this.piwikSiteId = piwikSiteId;
        this.serviceName = serviceName;

        // Create the tracker configuration
        var configuration = TrackerConfiguration.builder()
                .apiEndpoint(URI.create(piwikUrl + "/matomo.php"))
                .defaultSiteId(piwikSiteId)
                .build();
        // Prepare the tracker (stateless - can be used for multiple requests)
        tracker = new MatomoTracker(configuration);

        this.logger = LoggerFactory.getLogger(serviceName);
    }

    public void sendEvent(Cookie[] cookies, String page, String eventAction, String context) {
        track(cookies, 0, page, eventAction, context);
    }

    public void sendGoal(Cookie[] cookies, int goal, String context) {
        track(cookies, goal, null, null, context);
    }

    private void track(Cookie[] cookies, int goal, String page, String eventAction, String context) {

        // Look for the piwik ID cookie
        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(c ->c.getName().startsWith("_pk_id"))
                .findFirst();
        var logMap = new HashMap<String, Object>();

        if (cookie.isPresent()) {
            // Only send matomo tracking event if cookie present

            MatomoRequest matomoRequest;
            if (goal > 0) {
                matomoRequest = MatomoRequests
                        .goal(goal, null)
                        .build();
            } else {
                // Create the event request - (category, action, name, value)
                // Category naming is "service page"
                // Action is equivalent of data-event-id in html
                matomoRequest = MatomoRequests
                        .event( serviceName + " - " + page, eventAction, null, null)
                        .build();
            }

            // Get the visitor ID from the cookie
            var cookieContents = cookie.get().getValue().split("\\.");
            matomoRequest.setVisitorId(VisitorId.fromHex(cookieContents[0]));

            if (goal > 0) {
                logger.traceContext(context, String.format("tracking matomo goal %d for site %d to %s", goal, piwikSiteId, piwikUrl), logMap);
            } else {
                logger.traceContext(context, String.format("tracking matomo event %s - %s, action=%s for site %d to %s",
                        serviceName, page, eventAction, piwikSiteId, piwikUrl), logMap);
            }

            // Send the request asynchronously (non-blocking) as an HTTP POST request (payload is JSON and contains the
            // tracking parameters)
            tracker.sendRequestAsync(matomoRequest);

        } else {
            logger.traceContext(context, "tracking matomo - cookie not found", logMap);
        }
    }
}
