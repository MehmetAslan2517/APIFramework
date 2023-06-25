package com.spartan;

import com.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class testGet extends SpartanTestBase {
    /*
    Given Accept type application/json
    When user send GET request to api/spartans end point
    Then status code must 200
    And response Content Type must be application/json
    And response body should include spartan result
    */

    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .get("http://54.173.46.213:8000/api/spartans");

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        //assertTrue(response.body().asString().contains("Dorelle"));

        // System.out.println("response.body().asString() = " + response.body().asString());
        assertTrue(response.prettyPrint().contains("Dorelle"));
    }

    /*
        Given accept header is application/json
        When users sends a get request to /api/spartans/3
        Then status code should be 200
        And content type should be application/json
        and json body should contain Fidole
     */
    @DisplayName("path param")
    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id", 3)
                .get("http://54.173.46.213:8000/api/spartans/{id}");

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.body().asString().contains("Fidole"));

        // System.out.println("response.body().asString() = " + response.body().asString());
        //assertTrue(response.prettyPrint().contains("Dorelle"));
    }

      /*
        Given no headers provided
        When Users sends GET request to /api/hello
        Then response status code should be 200
        And Content type header should be “text/plain;charset=UTF-8”
        And header should contain date
        And Content-Length should be 17
        And body should be “Hello from Sparta"
        */

    @DisplayName("api/hello test")
    @Test
    public void test34() {
        Response response = given().get("http://54.173.46.213:8000/api/hello");
//.accept(ContentType.TEXT)
        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(200, response.statusCode());

        assertEquals("text/plain;charset=UTF-8", response.contentType());

        assertTrue(response.headers().hasHeaderWithName("date"));

        int contentLenght = Integer.parseInt(response.header("Content-Length"));
        assertEquals(17,contentLenght);
        //assertEquals("17",response.header("Content-Length"));
        response.prettyPrint();

        System.out.println("response.header(\"date\") = " + response.header("date"));
    }

/*TASK
Given Accept type application/xml
When user send GET request to /api/spartans/10 end point
Then status code must be 406
And response Content Type must be application/xml;charset=UTF-8
*/


/*   Given accept type is Json
 And Id parameter value is 5
 When user sends GET request to /api/spartans/{id}
 Then response status code should be 200
 And response content-type: application/json
 And "Blythe" should be in response payload
*/

/*
 TASK
 Given accept type is Json
 And Id parameter value is 500
 When user sends GET request to /api/spartans/{id}
 Then response status code should be 404
 And response content-type: application/json
 And "Not Found" message should be in response payload
*/

/*
        Given accept type is Json
        And query parameter values are:
        gender|Female
        nameContains|e
        When user sends GET request to /api/spartans/search
        Then response status code should be 200
        And response content-type: application/json
        And "Female" should be in response payload
        And "Janette" should be in response payload
     */
    @DisplayName("path method with query param and Gpath")
    @Test
    public void test3() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("nameContains", "k")
                .queryParam("gender","Female")
                .get("http://54.173.46.213:8000/api/spartans/search");

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(200, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertEquals(response.path("content[0].name"), "Karmen");
        List<String> genders = response.path("content.gender");
        System.out.println(genders);
        for (String gender : genders) {
            assertEquals(gender,"Female");
        }

        //assertEquals(response.path("content.gender"), "Female");
        //assertTrue(response.body().asString().contains("Female"));
        //assertTrue(response.body().asString().contains("Janette"));
        // System.out.println("response.body().asString() = " + response.body().asString());
        //assertTrue(response.prettyPrint().contains("Dorelle"));
    }

    /*
     Given accept type is json
     And path param id is 10
     When user sends a get request to "api/spartans/{id}"
     Then status code is 200
     And content-type is "application/json"
     And response payload values match the following:
          id is 10,
          name is "Lorenza",
          gender is "Female",
          phone is 3312820936
   */
    @DisplayName("jsonPath method with path param")
    @Test
    public void test12() {

        Response response = given().accept(ContentType.JSON).pathParam("id", 10).
                when().get("http://54.173.46.213:8000/api/spartans/{id}");

        JsonPath json = response.jsonPath();

        assertEquals(200,response.statusCode());
        assertEquals("application/json", response.contentType());

        //int id = json.getInt("id");
        assertEquals(10,json.getInt("id"));

        //String name = json.getString("name");
        assertEquals("Lorenza",json.getString("name"));

        assertEquals(3312820936L,json.getLong("phone"));

    }

    @DisplayName("jsonPath method with findAll")
    @Test
    public void test13() {

        Response response = given().accept(ContentType.JSON).
                when().get("http://54.173.46.213:8000/api/spartans");

        JsonPath json = response.jsonPath();
        System.out.println(json.getString("findAll{it.gender=='Female'}.name"));
                          //response.path("findAll{it.gender=='Female'}.name"));
    }
    @DisplayName("path method with path param")
    @Test
    public void test4() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .pathParam("id",10)
                .when().get("http://54.173.46.213:8000/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        int id = response.path("id");
        assertEquals(id,10);
        assertEquals(response.path("name"),"Lorenza");
        assertEquals(response.path("gender"),"Female");

        long phone = response.path("phone");
        assertEquals(phone,3312820936L);
    }

/*
Given accept type is json
And path param id is 11
When user sends GET request to /api/spartans
Then response status code should be 200
And response content-type: application/json;charset=UTF-8
And response payload values match with the followings;
Id is 11,
Name is “Nona”,
Gender is “Female”,
Phone is 7959094216
*/

    @DisplayName("chaining method with path param")
    @Test
    public void test41() {
        given()
                .pathParam("id",11)
                .when().get("http://54.173.46.213:8000/api/spartans/{id}")
                .then()
                .and().assertThat().statusCode(200)
                .contentType("application/json")
                .and()
                .body("id",is(equalTo(11)),"name",equalTo("Nona"),
                        "gender",is("Female"),"phone",equalTo(7959094216L));

    }

    @DisplayName("chaining method")
    @Test
    public void test42() {
       /*This is another way of extract
       and deserialization
        */
       ValidatableResponse r = given()
                .get("/spartans")
                .then()
                .and().assertThat().statusCode(200)
                .contentType("application/json");

        List<Map<String,Object>> jsonList = r.extract().as(List.class);
        System.out.println(jsonList);

    }

    /*
given accept type is Json
And path param id is 15
When user sends a get request to spartans/{id}
Then status code is 200
And content type is Json
And json data has following
   "id": 15,
   "name": "Meta",
   "gender": "Female",
   "phone": 1938695106
*/

    @DisplayName("deserialization with path param")
    @Test
    public void oneSpartanToMap() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("/spartans/{id}")
                .then()
                .statusCode(200).contentType("application/json").extract().response();

        response.prettyPrint();
        //System.out.println(response);

        Map<String,Object> spartan = response.as(Map.class);

        assertEquals(15,spartan.get("id"));
        System.out.println(spartan.get("id"));
        assertEquals("Female",spartan.get("gender"));
        System.out.println(spartan.get("gender"));
    }

    @DisplayName("Get all spartans to Java data structure")
    @Test
    public void getAllSpartanToListOfMap() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/spartans")
                .then().statusCode(200).contentType("application/json").extract().response();

        List<Map<String,String>> listOfSpartans = response.as(List.class);

        //response.prettyPrint();

        assertEquals("Meade",listOfSpartans.get(0).get("name"));

        String gender = listOfSpartans.get(0).get("gender");
        assertEquals("Male",gender);

        assertEquals(1,listOfSpartans.get(0).get("id"));

        String id = listOfSpartans.get(0).get("id");
        String phone = listOfSpartans.get(0).get("phone");
        assertEquals(3584128232L,phone);
    }

    /*
    given accept type is Json
    And query parameter values are (send this query as Map):
        gender|Male
        nameContains|j
     When user sends GET request to /api/spartans/search
     Then response status code should be 200
     And response content-type: application/json
     verify total element greater than or equal to 3
     and add the names inside the Lİst
     then print all names
     */
    @DisplayName("GET spartan/search and chaining together")
    @Test
    public void test100(){

        Map<String, String> queryMap= new HashMap<>();
        queryMap.put("gender", "Male");
        queryMap.put("nameContains", "j");

        List<Object> names = given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("/spartans/search")
                .then().statusCode(200).contentType("application/json")
                .body("totalElement", greaterThanOrEqualTo(3))
                .extract().path("content.name");
                         //jsonPath().getList("content.name")

        System.out.println(names);
        //response.path("totalElement")

    }

    /*
    given accept type is Json
And path param id is 15
When user sends a get request to spartans/{id}
Then status code is 200
convert json response to spartan object with the help of jackson
do it 2 different way
1.using as() method
2.using JsonPath to deserialize to custom class
     */
    @DisplayName("GET one spartan and convert it to Spartan Object")
    @Test
    public void oneSpartanPojo(){

    }

    @DisplayName("Get one spartan from search endpoint result and use POJO")
    @Test
    public void spartanSearchWithPojo(){

    }

/*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"Severus",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */

    @DisplayName("post method")
    @Test
    public void test112() {


    }


}


