package rest;

import entities.Tour;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class RestApplicationRepository {

    @PostConstruct
    public void initRepo() {
        ArrayList<String> customers1 = new ArrayList<>(Arrays.asList("Jan Novak", "Tereza Stehlikova"));
        ArrayList<String> customers2 = new ArrayList<>(Arrays.asList("Josef Dolezal", "Josef Sedlacek"));
        tours.put(1, new Tour(1, "Tour to Prague", customers1));
        tours.put(2, new Tour(2, "Tour to New York", customers2));
    }

    public boolean addTour(Tour tour) {
        if ( tours.get( tour.getId( ) ) == null ) {
            tours.put( tour.getId( ), tour );
            return true;
        }
        else {
            return false;
        }

    }

    public List<Tour> getTours( ) {
        List<Tour> allTours = new ArrayList<>();
        tours.forEach( ( k, v ) -> allTours.add( v ) );
        return allTours;

    }

    public int updateTour( Tour tour ) {
        if ( tours.get( tour.getId( ) ) == null ) {
            this.addTour(tour);
            return 201 ;
        }
        else {
            Tour tourToUpdate = tours.get( tour.getId( ) ) ;
            if( toursMatch( tour, tourToUpdate ) ) {
                return 400;
            }
            else {
                tours.put( tour.getId( ), tour );
                if ( toursForDeletion.get( tour.getId( ) ) != null ) {
                    toursForDeletion.remove( tour.getId( ) );
                }
                return 200;
            }
        }
    }

    private boolean toursMatch( Tour tour1, Tour tour2 ) {
        if ( tour1.getId( ) != tour2.getId( ) ) {
            return false;
        }
        if ( !tour1.getName( ).equals( tour2.getName( ) ) ) {
            return false;
        }
        if ( !tour1.getCustomers( ).equals( tour2.getCustomers( ) ) ){
            return false;
        }
        return true;
    }

    /**
     * Method computes weak ETag from tour id values and tour name values
     * First character is added for better string representation
     * @return hash code for ETag
     */
    public String getETag(){
        long hashValue = 0;
        for ( Tour tour : tours.values( ) ){
            hashValue += (tour.getId() + tour.getName()).hashCode();
        }
        return getFirstChar(hashValue) + Long.valueOf( Math.abs(hashValue) ).toString( );
    }

    /**
     * First alpha character of etag
     * @return first character of etag
     */
    public String getFirstChar( long number ) {
        if ( number % 6 == 0 ){
            return "a";
        }
        else if ( number % 6 == 1 ){
            return "b";
        }
        else if ( number % 6 == 2 ){
            return "c";
        }
        else if ( number % 6 == 3 ){
            return "d";
        }
        else if ( number % 6 == 4 ){
            return "e";
        }
        else {
            return "f";
        }
    }



    private static final Map<Integer,Tour> tours = new HashMap<>();
    private static final Map<Integer,Tour> toursForDeletion = new HashMap<>();

}
