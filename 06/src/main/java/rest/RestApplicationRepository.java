package rest;

import entities.Tour;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestApplicationRepository {

    @PostConstruct
    public void initRepo() {
        tours.put(1, new Tour(1, "France - Paris", "01/01/2021", "03/01/2021", "bus" ) );
        tours.put(2, new Tour(2, "USA - New York", "05/06/2021", "10/06/2021", "plane" ) );
        tours.put(3, new Tour(3, "Japan - Tokio", "12/12/2021", "23/12/2021", "plane" ) );
    }

    public List<Tour> getTours( ) {
        List<Tour> allTours = new ArrayList<>();
        tours.forEach( ( k, v ) -> allTours.add( v ) );
        return allTours;

    }

    public int addToDeleteList( int id ){
        if ( tours.get( id ) == null ) {
            return 404;
        }
        if ( toursForDeletion.get( id ) == null ){
            toursForDeletion.put(id, tours.get( id ) );
            return 2021;
        }
        else {
            return 2022;
        }
    }

    public int deleteTour(int id) {
        if ( tours.get( id ) == null ){
            return 404;
        }
        if ( toursForDeletion.get( id ) == null ){
            return 400;
        }
        else {
            tours.remove( id );
            toursForDeletion.remove( id );
            return 200;
        }
    }

    private static final Map<Integer,Tour> tours = new HashMap<>();
    private static final Map<Integer,Tour> toursForDeletion = new HashMap<>();

}
