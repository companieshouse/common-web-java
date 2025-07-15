package uk.gov.companieshouse.common.web.domain.components;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BreadcrumbsBuilderTest {
    @Test
    void testWithCollapseOnMobile() {
        Breadcrumbs breadcrumbs = new Breadcrumbs.Builder()
                .withCollapseOnMobile(true)
                .withBreadcrumbs(new BreadcrumbLink("Home", "/"))
                .build();
        assertThat(breadcrumbs.collapseOnMobile()).isTrue();
    }

    @Test
    void testWithInverseColour() {
        Breadcrumbs breadcrumbs = new Breadcrumbs.Builder()
                .withInverseColour(true)
                .withBreadcrumbs(new BreadcrumbLink("Home", "/"))
                .build();
        assertThat(breadcrumbs.inverseColour()).isTrue();
    }

    @Test
    void testWithBreadcrumbs() {
        BreadcrumbLink link1 = new BreadcrumbLink("Home", "/");
        BreadcrumbLink link2 = new BreadcrumbLink("About", "/about");
        Breadcrumbs breadcrumbs = new Breadcrumbs.Builder()
                .withBreadcrumbs(link1, link2)
                .build();
        assertThat(breadcrumbs.breadcrumbs()).containsExactly(link1, link2);
    }

    @Test
    void testDefaultValues() {
        BreadcrumbLink link = new BreadcrumbLink("Home", "/");
        Breadcrumbs breadcrumbs = new Breadcrumbs.Builder()
                .withBreadcrumbs(link)
                .build();
        assertThat(breadcrumbs.collapseOnMobile()).isFalse();
        assertThat(breadcrumbs.inverseColour()).isFalse();
    }

    @Test
    void testBuildWithoutBreadcrumbsThrowsException() {
        Breadcrumbs.Builder builder = new Breadcrumbs.Builder();
        assertThatThrownBy(builder::build)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("breadcrumb");
    }
}
