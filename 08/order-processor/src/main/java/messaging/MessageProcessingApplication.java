package messaging;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import java.util.Map;

@SpringBootApplication
@EnableJms
public class MessageProcessingApplication {

    private final Logger logger = LoggerFactory.getLogger(MessageProcessingApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;


    @JmsListener(destination = "allOrdersQueue")
    public void readMessage(Map<String,?> orderMap) throws InterruptedException {
        logger.info("Received order with id: {}", orderMap.get("id"));
        if ( orderMap.get("type").equals("booking") ){
            jmsTemplate.convertAndSend(new ActiveMQQueue("bookingsQueue"), orderMap);
            logger.info("Booking with id: " + orderMap.get("id") + " send to processing." );
        }
        else{
            jmsTemplate.convertAndSend(new ActiveMQQueue("newTripsQueue"), orderMap);
            logger.info("New trip with id: " + orderMap.get("id") + " send to processing." );
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(MessageProcessingApplication.class, args);
    }

}
