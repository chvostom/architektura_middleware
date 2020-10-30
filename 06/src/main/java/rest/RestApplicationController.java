package rest;

import entities.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RestApplicationController {

    @Autowired
    RestApplicationRepository repository;

    @GetMapping(value = "/tour")
    @ResponseStatus(HttpStatus.OK)
    public List<Tour> getTours( ){
        return repository.getTours( );
    }


    @DeleteMapping(value = "/tour/{id}")
    public ResponseEntity setTourForDeletion(@PathVariable int id) {
        int returnedValue = repository.addToDeleteList( id );
        if ( returnedValue == 2021 ) {
            return ResponseEntity.status(HttpStatus.OK).body("Tour has been set for deletion. Confirm the deletion by sending DELETE request to /confirmTourDeletion/" + id);
        }
        else if ( returnedValue == 2022 ){
            return ResponseEntity.status(HttpStatus.OK).body("Tour is already setup for deletion. Confirm the deletion by sending DELETE request to /confirmTourDeletion/" + id);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour doesn't exist and cannot be deleted.");
        }
    }

    @DeleteMapping(value = "/confirmTourDeletion/{id}")
    public ResponseEntity deleteTour(@PathVariable int id) {
        int returnedValue = repository.deleteTour( id );
        if ( returnedValue == 200 ) {
            return ResponseEntity.status(HttpStatus.OK).body("Deletion was confirmed. Tour successfully deleted!");
        }
        else if ( returnedValue == 400 ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tour isn't set to be deleted!");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This tour either doesn't exist!");
        }
    }
}
