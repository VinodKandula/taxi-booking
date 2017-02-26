package com.taxibooking.booking.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vinodkandula
 */
public class URLTokenParser {

    String result = "";
    String token = "";
    Map<String, String> tokens;
    URLTokenParserState state;

    public URLTokenParser(Map<String, String> tokens) {
        this.tokens = tokens;
    }

    public void changeState(URLTokenParserState newState) {
        state = newState;
    }

    public String tokenize(String line) {
        
        this.state = new URLTokenParserNoBracesState(this);

        for (int i = 0; i < line.length(); i++) {
            char read = line.charAt(i);

            if (read == '{') {
                state.addLeftBracket();
            } else if (read == '}') {
                state.addRightBracket();
            } else {
                state.addChar(read);
            }
        }
        
        // add final part to url
        this.addToURL();
        
        return this.result;
    }

    public String getTemp(){
        return this.token;
    }
    
    public void setToken(String token){
        this.token = token;
    }
    
    public String getKey(String key){
        return this.tokens.get(key);
    }
    
    public void addToTemp(char c) {
        token += c;
    }

    public void addToURL() {
        if (token != null && !token.equals("")) {
            result += token;
            token = "";
        }
    }
    
    public static void main(String[] args){
        
        Map<String,String> tokens = new HashMap<>();
        tokens.put("longitude", "1");
        tokens.put("latitude", "100");
        
        String t = new URLTokenParser(tokens).tokenize("https://maps.googleapis.com/maps/api/geocode/json?latlng={latitude},{longitude}&key=AIzaSyDY6IVa8pcYJD4lMDbFaCayQr6327cyqMc");
        System.out.println(t);
    }
}
