package uk.gov.companieshouse.common.web.domain.components;

import java.util.List;

public record CheckBoxes(
    String name,
    String legendText,
    boolean hasLegendTitle,
    String hintText,
    String checkBoxIdPrefix,
    boolean required,
    boolean disabled,
    String errorText,
    List<CheckBox> checkBoxes
) {
    /**
     * Builder for {@link CheckBoxes}.
     * <p>
     * Mandatory fields: name, checkBoxes.<br>
     * If hasLegendTitle is true, legendText is also required.<br>
     * String fields default to empty strings.
     * </p>
     */
    public static class Builder {
        private String name = "";
        private String legendText = "";
        private boolean hasLegendTitle = false;
        private String hintText = "";
        private String checkBoxIdPrefix = "";
        private boolean required = false;
        private boolean disabled = false;
        private String errorText = "";
        private List<CheckBox> checkBoxes;

        /**
         * Sets the name (mandatory).
         * @param name the name (cannot be null or empty)
         * @return this builder
         */
        public Builder withName(String name) {
            this.name = name != null ? name : "";
            return this;
        }

        /**
         * Sets the legend text.
         * @param legendText the legend text (defaults to empty string if null)
         * @return this builder
         */
        public Builder withLegendText(String legendText) {
            this.legendText = legendText != null ? legendText : "";
            return this;
        }

        /**
         * Sets whether the legend has a title.
         * @param hasLegendTitle true if legend has a title
         * @return this builder
         */
        public Builder withHasLegendTitle(boolean hasLegendTitle) {
            this.hasLegendTitle = hasLegendTitle;
            return this;
        }

        /**
         * Sets the hint text.
         * @param hintText the hint text (defaults to empty string if null)
         * @return this builder
         */
        public Builder withHintText(String hintText) {
            this.hintText = hintText != null ? hintText : "";
            return this;
        }

        /**
         * Sets the checkbox ID prefix.
         * @param checkBoxIdPrefix the prefix (defaults to empty string if null)
         * @return this builder
         */
        public Builder withCheckBoxIdPrefix(String checkBoxIdPrefix) {
            this.checkBoxIdPrefix = checkBoxIdPrefix != null ? checkBoxIdPrefix : "";
            return this;
        }

        /**
         * Sets whether the checkboxes are required.
         * @param required true if required
         * @return this builder
         */
        public Builder withRequired(boolean required) {
            this.required = required;
            return this;
        }

        /**
         * Sets whether the checkboxes are disabled.
         * @param disabled true if disabled
         * @return this builder
         */
        public Builder withDisabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        /**
         * Sets the error text.
         * @param errorText the error text (defaults to empty string if null)
         * @return this builder
         */
        public Builder withErrorText(String errorText) {
            this.errorText = errorText != null ? errorText : "";
            return this;
        }

        /**
         * Sets the list of checkboxes (mandatory).
         * @param checkBoxes the list of checkboxes (cannot be null or empty)
         * @return this builder
         */
        public Builder withCheckBoxes(List<CheckBox> checkBoxes) {
            this.checkBoxes = checkBoxes;
            return this;
        }

        /**
         * Builds the CheckBoxes instance, validating mandatory fields.
         * @throws IllegalArgumentException if mandatory fields are missing
         */
        public CheckBoxes build() {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("CheckBoxes 'name' is required and cannot be empty.");
            }
            if (checkBoxes == null || checkBoxes.isEmpty()) {
                throw new IllegalArgumentException("CheckBoxes 'checkBoxes' is required and cannot be null or empty.");
            }
            if (hasLegendTitle && (legendText == null || legendText.isEmpty())) {
                throw new IllegalArgumentException("CheckBoxes 'legendText' is required when 'hasLegendTitle' is true.");
            }
            return new CheckBoxes(name, legendText, hasLegendTitle, hintText, checkBoxIdPrefix, required, disabled, errorText, checkBoxes);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
