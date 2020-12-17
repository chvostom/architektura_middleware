package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.sql.Timestamp;
import java.util.*;

/**
 * Třída pro rest controller
 * Kvůli úloze na pozadí implementuje rozhraní CommandLineRunner
 */
@RestController
public class RestApplicationController implements CommandLineRunner {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * GET metoda
     * Zkopíruje hlavičky z příchozího požadavku a vytvoří nový požadavek, kterému nastaví tyto hlavičky
     * Z prioritní fronty získá služby, která je s vysokou pravděpodobností dostupná a nebyla nejdéle použita
     * Službu vrátí do fronty s aktualizovaným koeficientem pro případ, že by se do příštího požadavku fronta nebyla aktualizována
     * Odešle na službu požadavek a vrací získanou odpověď
     * Je možné, že změna v dostupnosti služeb se projeví dříve, než jí úloha na pozadí dokáže zpracovat nebo že
       žádná služba není dostupná, v takových případech je vrácena odpověď se status kódem INTERNAL_SERVER_ERROR
     * @param request - požadavek
     * @return odpověď služby
     * @throws Exception - výjimka v případě nedostupnosti služby, případně NullPointerException (nepravděpodobné)
     */
    @GetMapping(value = "/proxy")
    public ResponseEntity test(HttpServletRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(head -> headers.add(head, request.getHeader(head)));
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        try{
            Service service = this.servicesQueue.poll();
            service.temporaryRobinRoundCoefChange();
            this.servicesQueue.add(service);
            this.updateRobinRoundMap( service.getUrl() );
            logger.info("Request redirected to " + service.getUrl() );
            ResponseEntity responseEntity = restTemplate.exchange( new URI( service.getUrl() ), HttpMethod.GET, requestEntity, String.class );
            return responseEntity;
        }
        catch (Exception exception){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No server available! Please try again later.");
        }
    }

    /**
     * Úloha na pozadí
     * Vytvoří prioritní frontu, ve které je prioritou dostupnost služby a poté aktuální nevyužitost služby
     * V případě, že došlo ke změně v dostupnosti služeb, vypíše do logu aktuální stav
     * Časový interval aktualizace je možné nastavit v atributu třídy backgroundTaskInterval
     * @param args - případné vstupní parametry
     * @throws Exception - případné vyjímky
     */
    @Override
    public void run(String... args) throws Exception {
        while (true) {
            StringBuilder stateString = new StringBuilder("");
            PriorityQueue<Service> servicesPriorityQueue = new PriorityQueue<Service>(this.serviceHealthComparator);
            for ( String url : this.serviceURLs ){
                URLHealthCheck urlHealthCheck = new URLHealthCheck(url);
                boolean healthy = urlHealthCheck.getHealth(false).getStatus().getCode().equals("UP");
                stateString.append(healthy);
                servicesPriorityQueue.add( new Service( url, healthy, robinRoundMap.get( url ) ) );
            }
            this.setServicesQueue( servicesPriorityQueue );
            if( this.serviceStateCode != stateString.toString( ).hashCode( ) ){
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logger.info("Actual state ( no changes since " + timestamp.toString( ) + " ):");
                this.serviceStateCode = stateString.toString( ).hashCode( );
                for ( Service service : this.servicesQueue ){
                    if ( service.isHealthy( ) ){
                        logger.info("Url " + service.getUrl( ) + " is available!");
                    }
                    else {
                        logger.info("Url " + service.getUrl( ) + " is not available!");
                    }
                }
            }
            Thread.sleep(this.backgroundTaskInterval);
        }
    }

    /**
     * Synchronizovaná metoda která přiřadí sdílené proměnné novou prioritní frontu
     * @param queue - prioritní fronta
     */
    public synchronized void setServicesQueue( PriorityQueue<Service> queue){
        this.servicesQueue = queue;
    }

    /**
     * Metoda aktualizuje u každé služby koeficient nevyužití
     * @param url - právě použitá služba
     */
    private void updateRobinRoundMap( String url ){
        for ( String key : this.serviceURLs){
            this.robinRoundMap.put( key,  this.robinRoundMap.get( key ) + 1 );
        }
        this.robinRoundMap.put( url,  0 );
    }

    /**
     * Komparátor pro prioritní frontu
     * Nejvyšší prioritu má dostupnost služby
     * Střední prioritu má nevyužitost služby
     * Nejnižší prioritu má název služby
     */
    private final Comparator<Service> serviceHealthComparator = new Comparator<Service>() {
        @Override
        public int compare(Service service1, Service service2) {
            if ( service1.isHealthy( ) != service2.isHealthy( ) ){
                return Boolean.compare(service2.isHealthy(), service1.isHealthy() );
            }
            else{
                if (service1.getRobinRoundCoef() != service2.getRobinRoundCoef()) {
                    return service2.getRobinRoundCoef() - service1.getRobinRoundCoef();
                }
                else{
                    return service1.getUrl( ).compareTo( service2.getUrl( ) );
                }

            }

        }
    };

    private final Logger logger = LoggerFactory.getLogger(RestApplicationController.class);
    private volatile PriorityQueue<Service> servicesQueue;
    private int serviceStateCode = 0;
    private final List<String> serviceURLs = new ArrayList<String>( Arrays. asList(
            "http://147.32.233.18:8888/MI-MDW-LastMinute1/list",
            "http://147.32.233.18:8888/MI-MDW-LastMinute2/list",
            "http://147.32.233.18:8888/MI-MDW-LastMinute3/list"
    ));
    private final Map<String, Integer> robinRoundMap  = new HashMap<String, Integer>() {{
        put("http://147.32.233.18:8888/MI-MDW-LastMinute1/list", 0);
        put("http://147.32.233.18:8888/MI-MDW-LastMinute2/list", 0);
        put("http://147.32.233.18:8888/MI-MDW-LastMinute3/list", 0);
    }};
    private final int backgroundTaskInterval = 1000;
}
