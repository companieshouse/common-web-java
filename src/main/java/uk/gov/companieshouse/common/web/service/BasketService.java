package uk.gov.companieshouse.common.web.service;

import com.google.api.client.http.HttpStatusCodes;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.order.BasketLinksApi;
import uk.gov.companieshouse.common.web.sdk.ApiClientService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.service.ServiceException;

/**
 * Service that gets basket link information from the basket links API.
 */
@Service
public class BasketService {

    private final ApiClientService apiClientService;
    private final Logger logger;

    private static final UriTemplate GET_BASKET_LINKS_URI = new UriTemplate("/basket/links");

    public BasketService(ApiClientService apiClientService, Logger logger) {
        this.apiClientService = apiClientService;
        this.logger = logger;
    }

    /**
    * <p>getBasketLinks() - retrieves basket links data and returns array of links.
    * </p>
    *
    * @return BasketLinksApi.getData.getItems
    */
    public String[] getBasketLinks() throws ServiceException {
        var apiClient = apiClientService.getApiClient();
        BasketLinksApi basketLinksApi;
        var uri = GET_BASKET_LINKS_URI.toString();
        try {
            basketLinksApi = apiClient.basket().get(uri).execute().getData();
        } catch (ApiErrorResponseException ex) {
            if (ex.getStatusCode() == HttpStatusCodes.STATUS_CODE_NOT_FOUND) {
                return new String[]{};
            } else {
                throw new ServiceException("Error making request to retrieve basket links ", ex);
            }
        } catch (URIValidationException ex) {
            throw new ServiceException("Error making request to retrieve basket links ", ex);
        }

        if (basketLinksApi.getData().isEnrolled()) {
            logger.debug("User enrolled for multi-item basket; displaying basket link.");
            return basketLinksApi.getData().getItems();
        } else {
            logger.debug("User not enrolled for multi-item basket; not displaying basket link.");
            return new String[]{};
        }
    }
}
