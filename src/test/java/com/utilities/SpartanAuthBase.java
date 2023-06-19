package com.utilities;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class SpartanAuthBase {

    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;
    public static RequestSpecification userSpec;
    public static RequestSpecification adminSpec;

    @BeforeAll
    public static void init(){

        baseURI = ConfigReader.getProperty("spartanBase");
        port = Integer.parseInt(ConfigReader.getProperty("spartanPort"));
        basePath =ConfigReader.getProperty("spartanBasePath");

        DBUtils.createConnection(ConfigReader.getProperty("spartanDbUrl"),
                ConfigReader.getProperty("spartanDbUsername"),
                ConfigReader.getProperty("spartanDbPassword"));

        requestSpec = given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .log().all();

        userSpec =given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("user", "user")
                .log().all();

        responseSpec = expect().statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .logDetail(LogDetail.ALL);  //logging with response specification

    }

    @AfterAll
    public static void teardown(){
        DBUtils.destroy();
    }

}
