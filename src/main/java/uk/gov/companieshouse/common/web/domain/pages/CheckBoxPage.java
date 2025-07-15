package uk.gov.companieshouse.common.web.domain.pages;

import org.springframework.web.servlet.ModelAndView;
import uk.gov.companieshouse.common.web.domain.Page;
import uk.gov.companieshouse.common.web.domain.UserMessage;
import uk.gov.companieshouse.common.web.domain.components.Button;
import uk.gov.companieshouse.common.web.domain.components.CheckBoxes;

import java.util.List;

/**
 * Defines an implementation of a page that displays a set of checkboxes.
 *
 * Typically, a CheckBoxPage is used to present a list of options
 * and would have no other content on the page other than the checkboxes.
 */
public class CheckBoxPage implements Page {

    private static final String TEMPLATE_NAME = "commonPages/checkBoxes";

    private final CheckBoxes checkBoxes;
    private final String actionUrl;
    private final String previousUrl;

    /**
     * Constructs a CheckBoxPage with the specified checkboxes, action URL, and previous URL.
     *
     * @param checkBoxes The checkboxes to be displayed on the page.
     * @param actionUrl The URL to which the form will be submitted.
     * @param previousUrl The URL to navigate back to the previous page.
     */
    public CheckBoxPage(final CheckBoxes checkBoxes, final String actionUrl, final String previousUrl) {
        this.checkBoxes = checkBoxes;
        this.actionUrl = actionUrl;
        this.previousUrl = previousUrl;
    }

    /**
     * Renders the CheckBoxPage using the provided ModelAndView.
     * This method populates the ModelAndView with the necessary data
     * and sets the view name for the page.
     *
     * @param modelAndView The ModelAndView to be populated with data and view name.
     * @param userMessages A list of user messages to be included in the page.
     */
    public void render(final ModelAndView modelAndView, final List<UserMessage> userMessages) {
        modelAndView.setViewName(TEMPLATE_NAME);

        modelAndView.addObject("checkBoxOptions", checkBoxes);

        modelAndView.addObject("userMessages", userMessages);

        modelAndView.addObject("actionUrl", actionUrl);

        modelAndView.addObject("submitButtonOptions", Button.builder()
                .build());

        modelAndView.addObject("cancelButtonOptions", Button.builder()
                .withText("Cancel")
                .withHref(previousUrl)
                .withClasses(Button.SECONDARY_BUTTON_CLASS)
                .build());

        if (previousUrl != null) {
            modelAndView.addObject("previousUrl", previousUrl);
        } else {
            modelAndView.addObject("previousUrl", "");
        }

    }
}
