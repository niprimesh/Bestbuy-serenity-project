package com.bestbuy.crudtest;

import com.bestbuy.steps.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;
import static org.hamcrest.Matchers.hasValue;

;

@RunWith(SerenityRunner.class)
public class ProductCrudTest  extends TestBase {
    static String name = TestUtils.getRandomValue() + "Duracell";
    static String type = TestUtils.getRandomValue() + "Housewares";
    static double price = 5.60;
    static String upc = TestUtils.getRandomValue();
    static int shipping = Integer.parseInt(TestUtils.getRandomValue());
    static String description = TestUtils.getRandomValue();
    static String manufacturer = TestUtils.getRandomValue();
    static String model = TestUtils.getRandomValue() + "MN1400R4Z";
    static String url = "http://" + TestUtils.getRandomValue();
    static String image = "http://" + TestUtils.getRandomValue();
    static int productID;

    @Steps
    ProductSteps steps;

    @Title("This will create a new Product")
    @Test
    public void test001() {
        ValidatableResponse response = steps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image).statusCode(201);
        productID = response.log().all().extract().path("id");

    }

    @Title("Verify if the product was added to application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = steps.getProductInfoByProductName(productID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003() {
        ValidatableResponse response = steps.updateProduct(productID, name, type, price, upc, shipping, description, manufacturer, model, url, image).statusCode(200);
        productID = response.log().all().extract().path("id");

        HashMap<String, Object> productMap = steps.getProductInfoByProductName(productID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Delete the product and verify if the product has been deleted")
    @Test
    public void test004() {
        steps.deleteProduct(productID).statusCode(200);
        steps.getProductById(productID).statusCode(404);
    }

}
