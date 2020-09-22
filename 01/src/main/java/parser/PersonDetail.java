package parser;

/**
 * Třída pro uložení jednotlivých údajů
 */
public class PersonDetail {

    /**
     * Konstruktor třídy
     * @param key - typ údaje (klíč)
     * @param value - konkrétní hodnota
     */
    public PersonDetail(String key, String value) {
        this.key = this.toCamelCase(key.toLowerCase());
        this.value = value;
    }

    /**
     * Metoda vrací typ údaje
     * @return key - typ údaje (klíč)
     */
    public String getKey() {
        return key;
    }

    /**
     * Metoda vrací hodnotu údaje
     * @return value - konkrétní hodnota
     */
    public String getValue() {
        return value;
    }

    /**
     * Funkce pro převod řetězce do CamelCase
     * @param text - text, který chceme převést
     * @return String - text v CamelCase formátu
     */
    private String toCamelCase(String text){
        String[] parts = text.split("\\s+");
        String camelCaseString = "";
        for (String part : parts){
            camelCaseString += toProperCase(part);
        }
        return camelCaseString.substring(0, 1).toLowerCase() + camelCaseString.substring(1);
    }

    /**
     * Pomocná funkce pro funkci toCamelCase
     * Funkce změní první symbol v textu na velký
     * @param text - zadaný text
     * @return String - původní text, kde pvní písmeno je velké
     */
    private static String toProperCase(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    private String key;
    private String value;

}

