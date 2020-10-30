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
        updateLastModified();
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
            updateLastModified();
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
                updateLastModified();
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
     * Method updates Last-Modified value
     * Time is in milliseconds
     */
    private void updateLastModified( ){
        ZonedDateTime time = ZonedDateTime.now();
        currentLastModifiedValue =  time.toInstant().toEpochMilli();
    }

    /**
     * Method returns Last-Modified value
     * @return time of last modified
     */
    public long getLastModified(){
        return currentLastModifiedValue;
    }



    private static final Map<Integer,Tour> tours = new HashMap<>();
    private static final Map<Integer,Tour> toursForDeletion = new HashMap<>();
    private long currentLastModifiedValue;

}
