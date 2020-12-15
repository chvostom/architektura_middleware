package messaging;

import entities.NewTrip;
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


    @JmsListener(destination = "newTripsQueue")
    public void readMessage(Map<String,?> orderMap) throws InterruptedException {
        NewTrip newTrip = this.convertMapToNewTrip( orderMap );
        logger.info( "Received: " + newTrip.getSummary() );
    }

    private NewTrip convertMapToNewTrip(Map<String,?> orderMap ){
        return new NewTrip(
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
