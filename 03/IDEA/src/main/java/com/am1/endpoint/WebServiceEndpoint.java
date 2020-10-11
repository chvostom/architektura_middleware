package com.am1.endpoint;

import com.am1._03.*;
import com.am1.repository.WebServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {
    

    @PayloadRoot( namespace = NAMESPACE_URI, localPart = "getBookingRequest" )
    @ResponsePayload
    public GetBookingResponse getBookingRequest(@RequestPayload GetBookingRequest request){
        GetBookingResponse response = new GetBookingResponse( );
        ServiceStatus serviceStatus = new ServiceStatus();
        response.setBooking( bookingRepository.getBooking( request.getId() ) );
        if ( response.getBooking( ) == null ) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while finding Entity: No booking with id: " + request.getId() + "!");
        }
        else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Found Successfully");
        }
        response.setServiceStatus( serviceStatus );
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllBookingsRequest")
    @ResponsePayload
    public GetAllBookingsResponse getAllBookings(@RequestPayload GetAllBookingsRequest request) {
        GetAllBookingsResponse response = new GetAllBookingsResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Content Found Successfully");
        response.getBooking().addAll( bookingRepository.getBookings( ) );
        response.setServiceStatus( serviceStatus );
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addBookingRequest")
    @ResponsePayload
    public AddBookingResponse addBooking(@RequestPayload AddBookingRequest request) {
        AddBookingResponse response = new AddBookingResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        if ( request.getBooking().getId() == 0 ) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while adding Entity: Booking with id 0 forbidden!");
        }
        else {
            if ( !bookingRepository.addBooking( request.getBooking( ) ) ) {
                serviceStatus.setStatusCode("CONFLICT");
                serviceStatus.setMessage("Exception while adding Entity: Booking with id: " + request.getBooking().getId() + " already exists!");
            } else {
                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("Content Added Successfully");
            }
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateBookingRequest")
    @ResponsePayload
    public UpdateBookingResponse updateBooking(@RequestPayload UpdateBookingRequest request) {
        UpdateBookingResponse response = new UpdateBookingResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        int updateCode = bookingRepository.updateBooking( request.getBooking() );
        if ( updateCode == 0 ) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while updating Entity: No booking with id: " + request.getBooking().getId() + "!");
        } else if ( updateCode == 1 ) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while updating Entity: Booking with same attributes already exists!");
        }
        else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Updated Successfully");
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBookingRequest")
    @ResponsePayload
    public DeleteBookingResponse deleteBooking(@RequestPayload DeleteBookingRequest request) {
        DeleteBookingResponse response = new DeleteBookingResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        if ( !bookingRepository.deleteBooking( request.getId() ) ) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Exception while deleting Entity: No booking with id: " + request.getId() + "!");
        } else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Deleted Successfully");
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    private static final String NAMESPACE_URI = "http://am1.com/03";
    
    @Autowired
    private WebServiceRepository bookingRepository;

}
