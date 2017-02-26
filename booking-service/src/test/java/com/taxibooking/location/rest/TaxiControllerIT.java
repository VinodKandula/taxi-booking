package com.taxibooking.location.rest;

import static com.jayway.restassured.RestAssured.expect;

import org.junit.Test;

/**
 *
 * @author vinodkandula
 */
public class TaxiControllerIT {
    

    /**
     * Test method find taxi of TaxiController. 
     * Test: unauthorised.
     */
    @Test
    public void testFindTaxi1() {
        expect()
                .statusCode(403)
                .request()
                .and()
                .response()
                .when()
                .get("http://127.0.0.1:8000/api/v1/taxi/1000");
    }
    
    /**
     * Test method find taxi of TaxiController. 
     * Test: taxi does not exist.
     */
    @Test
    public void testFindTaxi2() {
        expect()
                .statusCode(404)
                .request()
                .header("Authorization", "Basic am9obi5zbWl0aDpwYXNzd29yZA==")
                .and()
                .response()
                .when()
                .get("http://127.0.0.1:8000/api/v1/taxi/1000");
    }
    
    /**
     * Test method find taxi of TaxiController. 
     * Test: valid taxi.
     */
    @Test
    public void testFindTaxi3() {
        expect()
                .statusCode(200)
                .request()
                .header("Authorization", "Basic am9obi5zbWl0aDpwYXNzd29yZA==")
                .and()
                .response()
                .when()
                .get("http://127.0.0.1:8000/api/v1/taxi/101");
    }
    
    /**
     * Test method updateTaxiLocation of TaxiController. 
     * Test: bad request as no location provided in payload.
     */
    @Test
    public void testUpdateTaxiLocation1() {
        expect()
                .statusCode(400)
                .request()
                .header("Content-Type","application/json")
                .header("Authorization", "Basic am9obi5zbWl0aDpwYXNzd29yZA==")
                .and()
                .response()
                .when()
                .post("http://127.0.0.1:8000/api/v1/taxi/101/location");
    }
    
    /**
     * Test method updateTaxiLocation of TaxiController. 
     * Test: taxi does not exist.
     */
    @Test
    public void testUpdateTaxiLocation2() {
        expect()
                .statusCode(400)
                .request()
                .header("Content-Type","application/json")
                .header("Authorization", "Basic am9obi5zbWl0aDpwYXNzd29yZA==")
                .and()
                .response()
                .when()
                .post("http://127.0.0.1:8000/api/v1/taxi/10000/location");
    }  
    
    /**
     * Test method updateTaxiLocation of TaxiController. 
     * Test: valid update transaction.
     */
    @Test
    public void testUpdateTaxiLocation3() {
        expect()
                .statusCode(200)
                .request()
                .header("Content-Type","application/json")
                .header("Authorization", "Basic am9obi5zbWl0aDpwYXNzd29yZA==")
                .and()
               .body("{\n" +
                        "\"latitude\":51.753106,\n" +
                        "\"longitude\":-0.24\n" +
                        "}\n")
                .response()
                .when()
                .post("http://127.0.0.1:8000/api/v1/taxi/101/location");
    } 
}



