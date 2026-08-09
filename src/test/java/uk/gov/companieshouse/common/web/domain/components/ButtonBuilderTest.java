package uk.gov.companieshouse.common.web.domain.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ButtonBuilderTest {
    @Test
    void builderSetsDefaultValues() {
        Button button = new Button.Builder().build();
        assertEquals("", button.text());
        assertEquals("", button.classes());
        assertEquals("", button.href());
        assertFalse(button.disabled());
        assertFalse(button.isStartButton());
        assertTrue(button.preventDoubleClick());
    }

    @Test
    void builderSetsCustomValues() {
        Button button = new Button.Builder()
                .withText("Submit")
                .withClasses("custom-class")
                .withHref("/submit")
                .withDisabled(true)
                .withIsStartButton(true)
                .withPreventDoubleClick(false)
                .build();
        assertEquals("Submit", button.text());
        assertEquals("custom-class", button.classes());
        assertEquals("/submit", button.href());
        assertTrue(button.disabled());
        assertTrue(button.isStartButton());
        assertFalse(button.preventDoubleClick());
    }

    @Test
    void builderHandlesNullStringsAsEmpty() {
        Button button = new Button.Builder()
                .withText(null)
                .withClasses(null)
                .withHref(null)
                .build();
        assertEquals("", button.text());
        assertEquals("", button.classes());
        assertEquals("", button.href());
    }
}
