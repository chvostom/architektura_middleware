package com.am1.repository;

import com.am1._03.Booking;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebServiceRepository {

    /**
     * Initialization of HashMap bookings
     * Adding two bookings
     */
    @PostConstruct
    public void initialize( ){
        Booking booking1 = new Booking( );
        booking1.setId( 9901 );
        booking1.setFirstName( "Rafael" );
        booking1.setLastName( "Nadal" );
        booking1.setPassportNumber( 10203040 );
        booking1.setDepartureDate( "10/10/2020 16:20" );
        booking1.setArrivalDate( "10/10/2020 17:40" );
        booking1.setDepartureAirport( "Madrid SPA" );
        booking1.setArrivalAirport( "Prague CZE");

        Booking booking2 = new Booking( );
        booking2.setId( 9902 );
        booking2.setFirstName( "Novak" );
        booking2.setLastName( "Djokovic" );
        booking2.setPassportNumber( 80706050 );
        booking2.setDepartureDate( "11/10/2020 10:00" );
        booking2.setArrivalDate( "12/10/2020 02:45" );
        booking2.setDepartureAirport( "Amsterdam NL" );
        booking2.setArrivalAirport( "Peking CHN");

        bookings.put( booking1.getId( ), booking1 );
        bookings.put( booking2.getId( ), booking2 );
    }

    /**
     * This function returns Booking with given id
     * @param id - given id
     * @return Booking class from HashMap bookings
     */
    public Booking getBooking( int id ){
        return bookings.get( id );
    }

    /**
     * This function return all bookings
     * @return List with all Booking classes
     */
    public List<Booking> getBookings( ) {
        List<Booking> allBookings = new ArrayList<>();
        bookings.forEach( ( k, v ) -> allBookings.add( v ) );
        return allBookings;
    }

    /**
     * This function adds new booking to HashMap booking
     * @param booking - new booking
     * @return - true (success) or false (booking already exists)
     */
    public boolean addBooking( Booking booking ) {
        if ( bookings.get( booking.getId( ) ) == null ) {
            bookings.put( booking.getId( ), booking );
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This function updates booking in HashMap booking
     * @param booking - booking with some updates
     * @return - 2 (success), 1 (booking already exists with same atributes) or 0 (booking doesn't exist)
     */
    public int updateBooking( Booking booking ) {
        if ( bookings.get( booking.getId( ) ) == null ) {
            return 0 ;
        }
        else {
            Booking bookingToUpdate = bookings.get( booking.getId( ) ) ;
            if( bookingMatch( booking, bookingToUpdate ) ) {
                return 1;
            }
            else {
                bookings.put( booking.getId( ), booking );
                return 2;
            }
        }
    }

    /**
     * This function deletes booking from HashMap booking
     * @param id - given id of booking
     * @return - true (success) or false (booking doesn't exist)
     */
    public boolean deleteBooking( int id ) {
        if ( bookings.get( id ) == null ) {
            return false;
        }
        else {
            bookings.remove( id );
            return true;
        }
    }

    /**
     * Function for comparing booking classes
     * @param booking1
     * @param booking2
     * @return - true (given bookings are same), false( given bookings aren't same)
     */
    private boolean bookingMatch( Booking booking1, Booking booking2 ) {
        if (  booking1.getId() != booking2.getId( ) ) {
            return false;
        }
        if (  booking1.getPassportNumber() != booking2.getPassportNumber( ) ) {
            return false;
        }
        if ( !booking1.getFirstName( ).equals( booking2.getFirstName( ) ) ) {
            return false;
        }
        if ( !booking1.getLastName( ).equals( booking2.getLastName( ) ) ) {
            return false;
        }
        if ( !booking1.getDepartureDate( ).equals( booking2.getDepartureDate( ) ) ) {
            return false;
        }
        if ( !booking1.getArrivalDate( ).equals( booking2.getArrivalDate( ) ) ) {
            return false;
        }
        if ( !booking1.getDepartureAirport( ).equals( booking2.getDepartureAirport( ) ) ) {
            return false;
        }
        if ( !booking1.getArrivalAirport( ).equals( booking2.getArrivalAirport( ) ) ) {
            return false;
        }
        return true;
    }

    private static final Map<Integer, Booking> bookings = new HashMap<>();

}
