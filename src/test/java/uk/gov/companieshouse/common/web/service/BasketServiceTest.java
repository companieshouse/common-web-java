package uk.gov.companieshouse.common.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpStatusCodes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.order.BasketResourceHandler;
import uk.gov.companieshouse.api.handler.order.request.BasketGet;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.order.BasketLinksApi;
import uk.gov.companieshouse.api.model.order.BasketLinksDataApi;
import uk.gov.companieshouse.common.web.sdk.ApiClientService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.service.ServiceException;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ApiClientService apiClientService;

    @Mock
    private ApiClient apiClient;

    @Mock
    private BasketResourceHandler basketResourceHandler;

    @Mock
    private BasketGet basketGet;

    @Mock
    private ApiResponse<BasketLinksApi> responseWithData;

    @Mock
    private BasketLinksApi basketLinksApi;
    @Mock
    private BasketLinksDataApi basketLinksDataApi;

    @Mock
    private Logger logger;

    @InjectMocks
    private BasketService basketService;

    @BeforeEach
    void initApiClient() {
        when(apiClientService.getApiClient()).thenReturn(apiClient);
        when(apiClient.basket()).thenReturn(basketResourceHandler);
        when(basketResourceHandler.get(any())).thenReturn(basketGet);
    }

    @Test
    @DisplayName("Get Basket Links - Success")
    void getBasketLinksSuccess() throws ServiceException, ApiErrorResponseException, URIValidationException {
        String[] items = {"link 1", "link 2"};
        when(basketGet.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(basketLinksApi);
        when(basketLinksApi.getData()).thenReturn(basketLinksDataApi);
        when(basketLinksDataApi.isEnrolled()).thenReturn(true);
        when(basketLinksDataApi.getItems()).thenReturn(items);
        String[] basketLinks = basketService.getBasketLinks();
        assertEquals(items[0], basketLinks[0]);
        assertEquals(items[1], basketLinks[1]);
    }

    @Test
    @DisplayName("Get Basket Links - not enrolled")
    void getBasketLinksNotEnrolled() throws ServiceException, ApiErrorResponseException, URIValidationException {
        when(basketGet.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(basketLinksApi);
        when(basketLinksApi.getData()).thenReturn(basketLinksDataApi);
        when(basketLinksDataApi.isEnrolled()).thenReturn(false);
        String[] basketLinks = basketService.getBasketLinks();
        assertEquals(0, basketLinks.length);
    }

    @Test
    @DisplayName("Get Basket Links - no links found")
    void getBasketLinksNoLinks() throws ServiceException, ApiErrorResponseException, URIValidationException {
        var builder = new HttpResponseException.Builder(HttpStatusCodes.STATUS_CODE_NOT_FOUND, "not found", new HttpHeaders());
        when(basketGet.execute()).thenThrow(new ApiErrorResponseException(builder));
        String[] basketLinks = basketService.getBasketLinks();
        assertEquals(0, basketLinks.length);
    }

    @Test
    @DisplayName("Get Basket Links - other ApiErrorResponseException")
    void getBasketLinksException1() throws ApiErrorResponseException, URIValidationException {
        var builder = new HttpResponseException.Builder(HttpStatusCodes.STATUS_CODE_BAD_GATEWAY, "not found", new HttpHeaders());
        when(basketGet.execute()).thenThrow(new ApiErrorResponseException(builder));
        assertThrows(ServiceException.class, () ->
                basketService.getBasketLinks());
    }

    @Test
    @DisplayName("Get Basket Links - Throws URIValidationException")
    void getBasketLinksThrowsURIValidationException() throws ApiErrorResponseException, URIValidationException {
        when(basketGet.execute()).thenThrow(new URIValidationException("bad url"));
        assertThrows(ServiceException.class, () ->
                basketService.getBasketLinks());
    }
}
