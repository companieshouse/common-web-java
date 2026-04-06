package uk.gov.companieshouse.common.web.domain;

import java.util.Optional;

/**
 * Stub interface for pages to represent the type of user messages.
 */
public interface UserMessageType {
    Optional<String> getFieldId();

    String getMessageKey();
}
