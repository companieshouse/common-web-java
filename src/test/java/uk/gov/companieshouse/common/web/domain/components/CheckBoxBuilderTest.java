package uk.gov.companieshouse.common.web.domain.components;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CheckBoxBuilderTest {
    @Test
    void builderSetsDefaults() {
        String value = "myValue";
        CheckBox cb = new CheckBox.Builder()
                .withValue(value)
                .build();
        assertThat(cb.value()).isEqualTo(value);
        assertThat(cb.label()).isEmpty();
        assertThat(cb.checked()).isFalse();
        assertThat(cb.isOption()).isTrue();
    }

    @Test
    void builderSetsAllFields() {
        String value = "myValue";
        String label = "My Label";
        boolean checked = true;
        boolean isOption = false;
        CheckBox cb = new CheckBox.Builder()
                .withValue(value)
                .withLabel(label)
                .withChecked(checked)
                .withIsOption(isOption)
                .build();
        assertThat(cb.value()).isEqualTo(value);
        assertThat(cb.label()).isEqualTo(label);
        assertThat(cb.checked()).isEqualTo(checked);
        assertThat(cb.isOption()).isEqualTo(isOption);
    }

    @Test
    void builderThrowsIfValueMissing() {
        CheckBox.Builder builder = new CheckBox.Builder();
        assertThatThrownBy(builder::build)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("value");
    }

    @Test
    void builderHandlesNullLabel() {
        String value = "myValue";
        CheckBox cb = new CheckBox.Builder()
                .withValue(value)
                .withLabel(null)
                .build();
        assertThat(cb.label()).isEmpty();
    }

    @Test
    void builderBuildsNonOptionCheckBox() {
        boolean isOption = false;
        CheckBox cb = new CheckBox.Builder()
                .withIsOption(isOption)
                .build();
        assertThat(cb.value()).isNull();
        assertThat(cb.isOption()).isFalse();
    }
}
