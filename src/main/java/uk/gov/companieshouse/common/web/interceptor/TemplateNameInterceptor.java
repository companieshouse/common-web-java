package uk.gov.companieshouse.common.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/*
 * Interceptor to add name of template in get requests to model for use by matomo events
 * Infers name of template from final part of request uri
 */

@Component
public class TemplateNameInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, ModelAndView modelAndView) {

        // Ensure that a model/view exists
        if ((request.getMethod().equalsIgnoreCase("GET") || request.getMethod().equalsIgnoreCase("POST"))
                && modelAndView != null) {

            // Exclude redirect responses
            var viewName = modelAndView.getViewName();
            if (modelAndView.getView() instanceof RedirectView ||
                    (viewName != null && viewName.startsWith("redirect:"))) {
                return;
            }

            // Extract the request URI and remove leading '/'
            var requestURI = request.getRequestURI().substring(1);

            // Get the last part of the URI (assuming it matches the HTML file name)
            String[] uriParts = requestURI.split("/");
            String templateName = uriParts[uriParts.length - 1];

            // Add the template name to the model
            modelAndView.addObject("templateName", templateName);
        }
    }
}
