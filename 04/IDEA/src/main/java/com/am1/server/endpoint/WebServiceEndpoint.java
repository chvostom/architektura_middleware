package com.am1.server.endpoint;

import com.am1._04.*;
import com.am1.client.WebServiceClient;
import com.am1.server.repository.WebServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPaymentsRequest")
    @ResponsePayload
    public GetAllPaymentsResponse getAllPayments(@RequestPayload GetAllPaymentsRequest request) {
        GetAllPaymentsResponse response = new GetAllPaymentsResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        if ( paymentRepository.getCountOfPayments( ) == 0 ){
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("No payments found!");
        }
        else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Found Successfully");
            response.getPayment().addAll(paymentRepository.getPayments());
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPaymentRequest")
    @ResponsePayload
    public AddPaymentResponse addBooking(@RequestPayload AddPaymentRequest request) {
        AddPaymentResponse response = new AddPaymentResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        if ( request.getPayment().getOrderId() == 0 ) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity: Payment with order id 0 forbidden!");
        }
        else if ( !webServiceClient.validateCard( request.getPayment( ) ) ){
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity: Validation of card failed!");
        }
        else {
            if ( !paymentRepository.addPayment(request.getPayment()) ) {
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while adding Entity: Payment with order id: " + request.getPayment().getOrderId() + " already exists!");
            } else {
                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content Added Successfully");
            }
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    private static final String NAMESPACE_URI = "http://am1.com/04";

    @Autowired
    private WebServiceRepository paymentRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WebServiceClient webServiceClient;

}
