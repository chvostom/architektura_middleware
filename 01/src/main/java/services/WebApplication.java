package services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import parser.TextToJSONParser;

@RestController
@EnableAutoConfiguration
public class WebApplication {

    @PostMapping(value = "/transformation", consumes = "text/plain")
    String transformation(@RequestBody(required = false) String message){
        if ( message != null ) {
            TextToJSONParser textToJSONParser = new TextToJSONParser( message );
            return textToJSONParser.constructJSON();
        }
        else {
            return "Text je prázdný!\n";
        }
    }

    public static void main( String[] args ) throws HttpMessageNotReadableException {
        SpringApplication.run( WebApplication.class, args);
    }
}
