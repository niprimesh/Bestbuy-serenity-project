package com.bestbuy.testbase;

import com.bestbuy.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class TestBase {
   @BeforeClass
    public static void inIt() {
       RestAssured.baseURI = PropertyReader.getInstance().getProperty("baseUrl");
       RestAssured.port = Integer.parseInt(PropertyReader.getInstance().getProperty("port"));
    }
}
