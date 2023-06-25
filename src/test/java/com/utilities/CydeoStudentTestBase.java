package com.utilities;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class CydeoStudentTestBase {

    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = ConfigReader.getProperty("cydeoBaseURL");
    }
}
