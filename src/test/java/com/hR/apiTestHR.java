package com.hR;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class apiTestHR {

    /*
        Given accept type is application/json
        When user sends get request to /regions/2
        Then response status code must be 200
        and content type equals to application/json
        and response body contains   Americas
     */

    @DisplayName("Get request with query param by path method")
    @Test
    public void test1() {

    }

    /*
        Given accept type is Json
        And parameters: q = {"region_id":2}
        When users sends a GET request to "/countries"
        Then status code is 200
        And Content type is application/json
        And Payload should contain "United States of America"
     */

    @DisplayName("Get request with query param by jsonPath method")
    @Test
    public void test2() {

    }

    /*
    send a get request to employees endpoint with query parameter job_id IT_PROG
verify each job_id is IT_PROG
verify first names are .... (find proper method to check list against list)
verify emails without checking order (provide emails in different order,
just make sure it has same emails)
   expected names
   */
    @DisplayName("GET request to Employees IT_PROG endpoint and chaining")
    @Test
    public void test3() {

    }

    /*
    GET request to countries
    get the second country name with jsonpath
    get all country ids
    get all country names where their region id is equal to 2
     */

    /*
    GET request to employees with query param
    We added limit query param to get 107 employees
    get me all email of employees who is working as IT_PROG
    get me first name of employees who is making 10000
    get the max salary first name
     */


    /* send a get request to regions
verify that region ids are 1,2,3,4
verify that regions names Europe ,Americas , Asia, Middle East and Africa
verify that count is 4
try to use pojo as much as possible
ignore non used fields
*/





}
