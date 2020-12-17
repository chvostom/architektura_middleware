package services;

/**
 * Třída reprezentující jednotlivé služby
 */
public class Service {

    /**
     * Konstruktor třídy s parametry
     * @param url - url služby
     * @param healthy - zda služba běží
     * @param rrc - koeficient pro prioritní frontu představující aktuální nevyužitost služby
     */
    public Service(String url, boolean healthy, int rrc) {
        this.url = url;
        this.healthy = healthy;
        this.robinRoundCoef = rrc;
    }

    /**
     * Getter
     * @return url služby
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Getter
     * @return dostupnost služby
     */
    public boolean isHealthy() {
        return this.healthy;
    }

    /**
     * Getter
     * @return vrací koeficient nevyužitosti služby
     */
    public int getRobinRoundCoef() {
        return this.robinRoundCoef;
    }

    /**
     * Metoda sníží dočasně koeficient u právě použité služby
     */
    public void temporaryRobinRoundCoefChange( ){
        this.robinRoundCoef--;
    }


    private final String url;
    private final boolean healthy;
    private int robinRoundCoef;
}
