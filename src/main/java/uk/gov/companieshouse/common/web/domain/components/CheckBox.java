package uk.gov.companieshouse.common.web.domain.components;

public record CheckBox(
    String label,
    String value,
    boolean checked,
    boolean isOption
) {
    /**
     * Builder for {@link CheckBox}.
     * <p>
     * Mandatory field: value.<br>
     * label defaults to empty string, checked to false, isOption to true.
     * </p>
     */
    public static class Builder {
        private String label = "";
        private String value;
        private boolean checked = false;
        private boolean isOption = true;

        /**
         * Sets the label (defaults to empty string).
         * @param label the label
         * @return this builder
         */
        public Builder withLabel(String label) {
            this.label = label != null ? label : "";
            return this;
        }

        /**
         * Sets the value (mandatory).
         * @param value the value (cannot be null or empty)
         * @return this builder
         */
        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets whether the checkbox is checked (defaults to false).
         * @param checked true if checked
         * @return this builder
         */
        public Builder withChecked(boolean checked) {
            this.checked = checked;
            return this;
        }

        /**
         * Sets whether the checkbox is an option (defaults to true).
         * @param isOption true if option
         * @return this builder
         */
        public Builder withIsOption(boolean isOption) {
            this.isOption = isOption;
            return this;
        }

        /**
         * Builds the CheckBox instance, validating mandatory fields.
         * @throws IllegalArgumentException if value is missing
         */
        public CheckBox build() {
            if (this.isOption && (value == null || value.isEmpty())) {
                throw new IllegalArgumentException("CheckBox 'value' is required and cannot be null or empty.");
            }
            return new CheckBox(label, value, checked, isOption);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
