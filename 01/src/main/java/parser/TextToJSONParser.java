package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída realizující převod zadaného textu do JSON formátu
 */
public class TextToJSONParser {

    /**
     * Konstruktor třídy
     * @param content - text, který má být převeden
     */
    public TextToJSONParser( String content ) {
        wrongFormat = false;
        personDetails = new ArrayList<>();
        if ( content.split("===").length > 2 ) {
            content = content.split("===")[1];
            String[] elements = content.split("\"");
            if ( elements.length > 1 ) {
                for ( int i = 0 ; i <= ( elements.length - 1 ) / 2 ; i++ ){
                    try {
                        personDetails.add( new PersonDetail(elements[2*i].replaceAll(":\\s", ""), elements[2*i+1]));
                    }
                    catch( ArrayIndexOutOfBoundsException e ){
                        wrongFormat = true;
                    }
                }
            }
            for ( PersonDetail personDetail : personDetails ) {
                if ( personDetail.getValue().contains(":") ) {
                    wrongFormat = true;
                }
            }
        }
        else {
            wrongFormat = true;
        }
    }

    /**
     * Metoda vytvoří a vrátí text v JSON formátu
     * Případně pokud text není ve správném formátu, vrátí chybovou hlášku
     * @return jsonFormat - text v JSON formátu
     */
    public String constructJSON( ) {
        if ( wrongFormat ){
            return "Text není v požadovaném formátu!\n";
        }
        else {
            String jsonFormat = "{\n";
            for ( PersonDetail personDetail : personDetails ){
                if ( personDetail.getKey( ).toLowerCase().contains("id") ) {
                    jsonFormat += "\t\"id\": \"" + personDetail.getValue( ) + "\",\n";
                }
                else if ( personDetail.getKey().equals("person") ) {
                    jsonFormat += "\t\"person\": {\n";
                    String[] names = personDetail.getValue().split("\\s+");
                    if ( names.length == 1 ) {
                        jsonFormat += "\t\t\"name\": \"" + names[0] + "\"\n";
                    }
                    else if ( names.length == 2 ) {
                        jsonFormat += "\t\t\"name\": \"" + names[0] + "\",\n";
                        jsonFormat += "\t\t\"surname\": \"" + names[1] + "\"\n";
                    }
                    else if ( names.length == 3 ) {
                        jsonFormat += "\t\t\"name\": \"" + names[0] + "\",\n";
                        jsonFormat += "\t\t\"middlename\": \"" + names[1] + "\",\n";
                        jsonFormat += "\t\t\"surname\": \"" + names[2] + "\"\n";
                    }
                    else {
                        jsonFormat += "\t\t\"name\": \"" + names[0] + "\",\n";
                        for ( int i = 1 ; i < names.length-1 ; i++ ) {
                            jsonFormat += "\t\t\"middlename" + i + "\": \"" + names[i] + "\",\n";
                        }
                        jsonFormat += "\t\t\"surname\": \"" + names[names.length-1] + "\"\n";
                    }
                    jsonFormat += "\t}\n";
                }
                else {
                    jsonFormat += "\t\"" + personDetail.getKey() + "\": \"" + personDetail.getValue( ) + "\",\n";
                }
            }
            jsonFormat += "}";
            return jsonFormat + "\n";
        }
    }

    private List<PersonDetail> personDetails;
    boolean wrongFormat;

}

