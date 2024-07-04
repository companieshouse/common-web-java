package uk.gov.companieshouse.common.web.sdk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.sdk.manager.ApiClientManager;
import uk.gov.companieshouse.sdk.manager.ApiSdkManager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApiClientServiceTest {

    private static final String CONFIGURED_INTERNAL_API_URL = "http://api.chs.local:4001";

    private ApiClientService service;

    @Mock
    private ApiClient apiClient;

    @Mock
    private InternalApiClient internalApiClient;

    @BeforeEach
    void setUp() {
        service = new ApiClientService(CONFIGURED_INTERNAL_API_URL);
    }

    @Test
    @DisplayName("getApiClient returns API client obtained from API client manager")
    void getApiClientGetsSdk() {

        try (final MockedStatic<ApiClientManager> clientManager = Mockito.mockStatic(ApiClientManager.class)) {
            // Given
            clientManager.when(ApiClientManager::getSDK).thenReturn(apiClient);

            // When
            final ApiClient apiClientReturned = service.getApiClient();

            // Then
            clientManager.verify(ApiClientManager::getSDK);
            assertThat(apiClientReturned, is(apiClient));
        }
    }

    @Test
    @DisplayName("getInternalApiClient returns internal API client with an overridden internal API URL value")
    void getInternalApiClientOverridesInternalApiUrlUsed() {

        try (final MockedStatic<ApiSdkManager> sdkManager = Mockito.mockStatic(ApiSdkManager.class)) {
            // Given
            sdkManager.when(ApiSdkManager::getInternalSDK).thenReturn(internalApiClient);

            // When
            final InternalApiClient internalApiClientReturned = service.getInternalApiClient();

            // Then
            sdkManager.verify(ApiSdkManager::getInternalSDK);
            verify(internalApiClient).setInternalBasePath(CONFIGURED_INTERNAL_API_URL);
            assertThat(internalApiClientReturned, is(internalApiClient));
        }
    }

}