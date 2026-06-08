package uk.gov.companieshouse.common.web.domain;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

/**
 * Represents a page in the application.
 * This interface is used to define the contract for rendering pages.
 * 
 * A Page knows about the underlying ModelAndView and is responsible for rendering itself.
 * The Thymeleaf template will reference particular variables in the ModelAndView
 * to render the page correctly. This allows for a clean separation of concerns
 * between the render logic from the business logic around loading the page.
 */
public interface Page {
    
    /**
     * Renders the page using the provided ModelAndView.
     * This method is called to populate the ModelAndView with the necessary data
     * and to set the view name for the page.
     * @param modelAndView The ModelAndView to be populated with data and view name.
     * @param userMessages A list of user messages to be included in the page.
     *                     These messages can be used to display notifications or alerts to the user.
     */
    void render(final ModelAndView modelAndView, final List<UserMessage> userMessages);
}
