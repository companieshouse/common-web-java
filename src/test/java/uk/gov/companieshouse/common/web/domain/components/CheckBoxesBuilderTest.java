package uk.gov.companieshouse.common.web.domain.components;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CheckBoxesBuilderTest {
    @Test
    void builderSetsDefaults() {
        List<CheckBox> checkBoxes = List.of(new CheckBox("label", "value", false, false));
        String name = "test";
        CheckBoxes cb = new CheckBoxes.Builder()
                .withName(name)
                .withCheckBoxes(checkBoxes)
                .build();
        assertThat(cb.name()).isEqualTo(name);
        assertThat(cb.legendText()).isEmpty();
        assertThat(cb.hasLegendTitle()).isFalse();
        assertThat(cb.hintText()).isEmpty();
        assertThat(cb.checkBoxIdPrefix()).isEmpty();
        assertThat(cb.required()).isFalse();
        assertThat(cb.disabled()).isFalse();
        assertThat(cb.errorText()).isEmpty();
        assertThat(cb.checkBoxes()).containsExactlyElementsOf(checkBoxes);
    }

    @Test
    void builderThrowsIfNameMissing() {
        List<CheckBox> checkBoxes = List.of(new CheckBox("label", "value", false, false));
        CheckBoxes.Builder builder = new CheckBoxes.Builder().withCheckBoxes(checkBoxes);
        assertThatThrownBy(builder::build)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("name");
    }

    @Test
    void builderThrowsIfCheckBoxesMissing() {
        CheckBoxes.Builder builder = new CheckBoxes.Builder().withName("test");
        assertThatThrownBy(builder::build)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("checkBoxes");
    }

    @Test
    void builderThrowsIfCheckBoxesEmpty() {
        CheckBoxes.Builder builder = new CheckBoxes.Builder().withName("test").withCheckBoxes(Collections.emptyList());
        assertThatThrownBy(builder::build)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("checkBoxes");
    }

    @Test
    void builderThrowsIfLegendTextMissingWhenHasLegendTitleTrue() {
        List<CheckBox> checkBoxes = List.of(new CheckBox("label", "value", false, false));
        CheckBoxes.Builder builder = new CheckBoxes.Builder()
                .withName("test")
                .withCheckBoxes(checkBoxes)
                .withHasLegendTitle(true);
        assertThatThrownBy(builder::build)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("legendText");
    }

    @Test
    void builderAllowsLegendTextWhenHasLegendTitleTrue() {
        List<CheckBox> checkBoxes = List.of(new CheckBox("label", "value", false, false));
        String legendText = "Legend";
        String name = "test";
        CheckBoxes cb = new CheckBoxes.Builder()
                .withName(name)
                .withCheckBoxes(checkBoxes)
                .withHasLegendTitle(true)
                .withLegendText(legendText)
                .build();
        assertThat(cb.legendText()).isEqualTo(legendText);
        assertThat(cb.hasLegendTitle()).isTrue();
        assertThat(cb.name()).isEqualTo(name);
        assertThat(cb.checkBoxes()).containsExactlyElementsOf(checkBoxes);
    }
}
