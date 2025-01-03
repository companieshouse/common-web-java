package uk.gov.companieshouse.common.web.service;

import jakarta.servlet.http.Cookie;
import org.matomo.java.tracking.MatomoRequest;
import org.matomo.java.tracking.MatomoRequests;
import org.matomo.java.tracking.MatomoTracker;
import org.matomo.java.tracking.TrackerConfiguration;
import org.matomo.java.tracking.parameters.VisitorId;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@Component
public class MatomoClient {
    private final String serviceName;
    private final MatomoTracker tracker;

    public MatomoClient(String piwikUrl, int piwikSiteId, String serviceName) {
        // Create the tracker configuration
        var configuration = TrackerConfiguration.builder()
                .apiEndpoint(URI.create(piwikUrl + "/matomo.php"))
                .defaultSiteId(piwikSiteId)
                .build();
        // Prepare the tracker (stateless - can be used for multiple requests)
        tracker = new MatomoTracker(configuration);

        this.serviceName = serviceName;
    }

    public void sendEvent(Cookie[] cookies, String page, String eventAction) {
        track(cookies, 0, page, eventAction);
    }

    public void sendGoal(Cookie[] cookies, int goal) {
        track(cookies, goal, null, null);
    }

    private void track(Cookie[] cookies, int goal, String page, String eventAction ) {

        // Look for the piwik ID cookie
        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(c ->c.getName().startsWith("_pk_id"))
                .findFirst();

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

            // Send the request asynchronously (non-blocking) as an HTTP POST request (payload is JSON and contains the
            // tracking parameters)
            tracker.sendRequestAsync(matomoRequest);
        }
    }
}
