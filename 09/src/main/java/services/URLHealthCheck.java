package services;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Třída pro kontrolu dostupnosti služby
 * Implementuje rozhraní HealthIndicator
 */
public class URLHealthCheck implements HealthIndicator {

    /**
     * Konstruktor třídy s parametrem
     * @param url - url kontrolované služby
     */
    public URLHealthCheck(String url){
        this.URL = url;
    }

    /**
     * Metoda ve které je definována kontrola dostupnosti serveru
     * @return instance třídy Health
     */
    @Override
    public Health health() {
        try {
            ResponseEntity responseEntity = new RestTemplate().exchange(new URI(URL), HttpMethod.GET, null, String.class);
        } catch (Exception e) {
            return Health.down().withDetail("error", e.getMessage()).build();
        }
        return Health.up().build();
    }

    private final String URL;

}
