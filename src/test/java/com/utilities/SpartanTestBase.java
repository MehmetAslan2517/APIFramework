package com.utilities;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class SpartanTestBase {

    @BeforeAll
    public static void init(){

        baseURI = ConfigReader.getProperty("spartanBase");
        port = Integer.parseInt(ConfigReader.getProperty("spartanPort"));
        basePath =ConfigReader.getProperty("spartanBasePath");

        DBUtils.createConnection(ConfigReader.getProperty("spartanDbUrl"),
                ConfigReader.getProperty("spartanDbUsername"),
                ConfigReader.getProperty("spartanDbPassword"));

    }

    @AfterAll
    public static void teardown(){
        DBUtils.destroy();
    }

}
