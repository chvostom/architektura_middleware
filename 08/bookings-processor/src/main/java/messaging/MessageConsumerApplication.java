package messaging;

import entities.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

import java.util.Map;


@SpringBootApplication
@EnableJms
public class MessageConsumerApplication {

    private final Logger logger = LoggerFactory.getLogger(MessageConsumerApplication.class);


    @JmsListener(destination = "bookingsQueue")
    public void readMessage(Map<String,?> orderMap) throws InterruptedException {
        Booking booking = this.convertMapToBooking( orderMap );
        logger.info( "Received: " + booking.getSummary() );
    }

    private Booking convertMapToBooking(Map<String,?> orderMap ){
        return new Booking(
                Integer.parseInt((String) orderMap.get("id")),
                orderMap.get("description"),
                orderMap.get("customer"),
                Integer.parseInt((String) orderMap.get("price"))
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageConsumerApplication.class, args);
    }

}
