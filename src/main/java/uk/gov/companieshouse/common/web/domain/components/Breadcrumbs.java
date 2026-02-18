package uk.gov.companieshouse.common.web.domain.components;

public record Breadcrumbs(
    boolean collapseOnMobile,
    boolean inverseColour,
    BreadcrumbLink[] breadcrumbs
) {
    /**
     * Builder for {@link Breadcrumbs}.
     * <p>
     * Mandatory field: breadcrumbs.<br>
     * collapseOnMobile defaults to false, inverseColour to false.
     * </p>
     */
    public static class Builder {
        private boolean collapseOnMobile = false;
        private boolean inverseColour = false;
        private BreadcrumbLink[] breadcrumbs;

        /**
         * Sets whether the breadcrumbs should collapse on mobile (defaults to false).
         * @param collapseOnMobile true if breadcrumbs should collapse on mobile
         * @return this builder instance
         */
        public Builder withCollapseOnMobile(boolean collapseOnMobile) {
            this.collapseOnMobile = collapseOnMobile;
            return this;
        }

        /**
         * Sets whether the breadcrumbs should use inverse colour (defaults to false).
         * @param inverseColour true if breadcrumbs should use inverse colour
         * @return this builder instance
         */
        public Builder withInverseColour(boolean inverseColour) {
            this.inverseColour = inverseColour;
            return this;
        }

        /**
         * Sets the breadcrumbs links.
         * @param breadcrumbs the breadcrumb links (cannot be null or empty)
         * @return this builder instance
         */
        public Builder withBreadcrumbs(BreadcrumbLink... breadcrumbs) {
            this.breadcrumbs = breadcrumbs;
            return this;
        }

        /**
         * Builds the Breadcrumbs instance.
         * @return a new Breadcrumbs instance
         */
        public Breadcrumbs build() {
            if (breadcrumbs == null || breadcrumbs.length == 0) {
                throw new IllegalArgumentException("Breadcrumbs must contain at least one breadcrumb link.");
            }
            return new Breadcrumbs(collapseOnMobile, inverseColour, breadcrumbs);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
