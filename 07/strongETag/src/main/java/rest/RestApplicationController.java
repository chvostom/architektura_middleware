package rest;

import entities.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


@RestController
public class RestApplicationController {

    @Autowired
    RestApplicationRepository repository;


    @GetMapping(value = "/tour")
    public ResponseEntity getTours(WebRequest request){
        if ( request.checkNotModified( repository.getETag( ) ) ) {
            return ResponseEntity
                    .status(HttpStatus.NOT_MODIFIED)
                    .body("Not Modified!");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .eTag(repository.getETag())
                .body(repository.getTours( ));
    }


    @PutMapping(value = "/tour")
    public ResponseEntity updateTour(@RequestBody Tour tour) {
        int returnedValue = repository.updateTour( tour );
        if ( returnedValue == 200 ) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .eTag(repository.getETag())
                    .body("Tour successfully updated!");
        }
        else if ( returnedValue == 201 ){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .eTag(repository.getETag())
                    .body("Tour was created!");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No updates! Entities are same!");
        }
    }
}
