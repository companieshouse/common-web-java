package uk.gov.companieshouse.common.web.domain;

public record UserMessage(
    UserMessageType id,
    String message
) {
}
