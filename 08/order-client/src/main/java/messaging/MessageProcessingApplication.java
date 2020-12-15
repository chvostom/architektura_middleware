package messaging;

import entities.Order;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableJms
@RestController
@RequestMapping("/")
public class MessageProcessingApplication {

    private final Logger logger = LoggerFactory.getLogger(MessageProcessingApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("allOrdersQueue");
    }

    @PostMapping(path = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> publish(@RequestBody Order order){
        if ( !order.getType().equals("booking") && !order.getType().equals("new trip")){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        else {
            Map<String, String> orderMap = this.convertOrderToMap( order );
            jmsTemplate.convertAndSend(this.queue, orderMap);
            logger.info("Order with id: " + order.getId() + " send to proccesing.");
            return new ResponseEntity(order, HttpStatus.CREATED);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MessageProcessingApplication.class, args);
    }

    private Map<String,String> convertOrderToMap( Order order){
        Map<String, String> result = new HashMap<>();
        result.put("id", String.valueOf(order.getId()));
        result.put("type", order.getType());
        result.put("description", order.getDescription());
        result.put("customer", order.getCustomer());
        result.put("price", String.valueOf(order.getPrice()));
        return result;
    }

    @Bean
    public OpenAPI customOpenAPI( ){
        return new OpenAPI()
                .info(new Info()
                        .title("Rest Application")
                        .version("1.0")
                        .description("Rest Application")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
