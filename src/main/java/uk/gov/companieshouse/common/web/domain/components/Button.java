package uk.gov.companieshouse.common.web.domain.components;

/**
 * Provides the options for a button component in the web application.
 * This includes properties such as text, CSS classes, href link, and button state.
 */
public record Button(
    String text,
    String classes,
    String href,
    boolean disabled,
    boolean isStartButton,
    boolean preventDoubleClick
) {

    public static final String SECONDARY_BUTTON_CLASS = "govuk-button--secondary";
    public static final String WARNING_BUTTON_CLASS = "govuk-button--warning";
    public static final String INVERSE_BUTTON_CLASS = "govuk-button--inverse";

    /**
     * Builder for {@link Button}.
     * <p>
     * Allows step-by-step construction of a Button instance with sensible defaults:
     * <ul>
     *   <li>String fields default to empty strings</li>
     *   <li>Boolean fields default to false, except <code>preventDoubleClick</code> (true)</li>
     * </ul>
     * Use the <code>withX</code> methods to set properties, then call {@link #build()} to create the Button.
     * </p>
     */
    public static class Builder {
        private String text = "";
        private String classes = "";
        private String href = "";
        private boolean disabled = false;
        private boolean isStartButton = false;
        private boolean preventDoubleClick = true;

        /**
         * Sets the button text.
         * @param text the button text (defaults to empty string if null)
         * @return this builder instance
         */
        public Builder withText(String text) {
            this.text = text != null ? text : "";
            return this;
        }

        /**
         * Sets the CSS classes for the button.
         * @param classes the CSS classes (defaults to empty string if null)
         * @return this builder instance
         */
        public Builder withClasses(String classes) {
            this.classes = classes != null ? classes : "";
            return this;
        }

        /**
         * Sets the href for the button.
         * @param href the href value (defaults to empty string if null)
         * @return this builder instance
         */
        public Builder withHref(String href) {
            this.href = href != null ? href : "";
            return this;
        }

        /**
         * Sets whether the button is disabled.
         * @param disabled true to disable, false otherwise
         * @return this builder instance
         */
        public Builder withDisabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        /**
         * Sets whether the button is a start button.
         * @param isStartButton true if start button, false otherwise
         * @return this builder instance
         */
        public Builder withIsStartButton(boolean isStartButton) {
            this.isStartButton = isStartButton;
            return this;
        }

        /**
         * Sets whether to prevent double click on the button.
         * @param preventDoubleClick true to prevent double click, false otherwise
         * @return this builder instance
         */
        public Builder withPreventDoubleClick(boolean preventDoubleClick) {
            this.preventDoubleClick = preventDoubleClick;
            return this;
        }

        /**
         * Builds the Button instance.
         * @return the constructed Button
         */
        public Button build() {
            return new Button(text, classes, href, disabled, isStartButton, preventDoubleClick);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
