package com.am1.client;

import com.am1._04.Payment;
import https.courses_fit_cvut_cz.ni_am1.hw.web_services.ValidateCardRequest;
import https.courses_fit_cvut_cz.ni_am1.hw.web_services.ValidateCardResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class WebServiceClient extends WebServiceGatewaySupport {

    public boolean validateCard(Payment payment){
        ValidateCardRequest request = new ValidateCardRequest();
        request.setCardNumber(payment.getCreditCardNumber());
        request.setCardOwner(payment.getCreditCardOwner());
        ValidateCardResponse response = (ValidateCardResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response.isResult();
    }
}
