//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.12 at 10:58:34 PM CEST 
//


package com.am1._04;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.am1._04 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.am1._04
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllPaymentsRequest }
     * 
     */
    public GetAllPaymentsRequest createGetAllPaymentsRequest() {
        return new GetAllPaymentsRequest();
    }

    /**
     * Create an instance of {@link GetAllPaymentsResponse }
     * 
     */
    public GetAllPaymentsResponse createGetAllPaymentsResponse() {
        return new GetAllPaymentsResponse();
    }

    /**
     * Create an instance of {@link ServiceStatus }
     * 
     */
    public ServiceStatus createServiceStatus() {
        return new ServiceStatus();
    }

    /**
     * Create an instance of {@link Payment }
     * 
     */
    public Payment createPayment() {
        return new Payment();
    }

    /**
     * Create an instance of {@link AddPaymentRequest }
     * 
     */
    public AddPaymentRequest createAddPaymentRequest() {
        return new AddPaymentRequest();
    }

    /**
     * Create an instance of {@link AddPaymentResponse }
     * 
     */
    public AddPaymentResponse createAddPaymentResponse() {
        return new AddPaymentResponse();
    }

}
