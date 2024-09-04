package uk.gov.companieshouse.common.web.unit.interceptor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import uk.gov.companieshouse.common.web.interceptor.TemplateNameInterceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class TemplateNameInterceptorUnitTest {
    @InjectMocks
    private TemplateNameInterceptor interceptor;


    @Test
    void postHandle_GetSuccess() {

        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var modelAndView = new ModelAndView();

        request.setMethod("GET");
        request.setRequestURI("/route-name/page-name");

        interceptor.postHandle(request, response, new Object(), modelAndView);

        assertEquals("page-name", modelAndView.getModel().get("templateName"));
    }

    @Test
    void postHandle_NotGet() {

        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var modelAndView = new ModelAndView();

        request.setMethod("POST");
        request.setRequestURI("/route-name/page-name");

        interceptor.postHandle(request, response, new Object(), modelAndView);

        assertNull(modelAndView.getModel().get("templateName"));
    }
}
