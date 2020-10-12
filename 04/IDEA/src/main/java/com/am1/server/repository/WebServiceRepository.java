package com.am1.server.repository;

import com.am1._04.Payment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebServiceRepository {

    /**
     * Initialization of HashMap payments
     */
    @PostConstruct
    public void initialize( ){

    }

    /**
     * This function return all payments
     * @return List with all Payment classes
     */
    public List<Payment> getPayments( ) {
        List<Payment> allPayments = new ArrayList<>();
        payments.forEach( ( k, v ) -> allPayments.add( v ) );
        return allPayments;
    }

    /**
     * This function adds new payment to HashMap payments
     * @param payment - new payment
     * @return - true (success) or false (payment already exists)
     */
    public boolean addPayment( Payment payment ) {
        if ( payments.get( payment.getOrderId( ) ) == null ) {
            payments.put( payment.getOrderId( ), payment );
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This function returns total count of payments in hashmap
     * @return - count of payments
     */
    public int getCountOfPayments( ) {
        return payments.size();
    }

    private static final Map<Integer, Payment> payments = new HashMap<>();
}
