package com.bestbuy.steps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class ProductSteps extends ProductPojo {

    @Step("Creating product with name : {0}, type : {1}, price : {2}, upc : {3}, shipping : {4}," +
            " description : {5}, manufacturer : {6}, model : {7}, url : {8}, image : {9}  ")
    public ValidatableResponse createProduct(
            String name, String type, Double price, String upc, int shipping, String description,
            String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, upc, shipping, description, manufacturer, model, url, image);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)// replace payload to product pojo here
                .post(Path.PRODUCTS)
                .then().log().all().statusCode(201);
    }


    @Step("Verifying product is added with productID : {0}")
    public HashMap<String, Object> getProductInfoByProductName(int productID) {
        return SerenityRest.given().log().all()
                .when()
                .pathParam("productID", productID)
                .get(Path.PRODUCTS + EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");
    }


    @Step("Updating product with productID : {0}, name : {1}, type : {2}, price : {3}, upc : {4}, shipping : {5}," +
            " description : {6}, manufacturer : {7}, model : {8}, url : {9}, image : {10}")
    public ValidatableResponse updateProduct(
            int productID, String name, String type, Double price, String upc, int shipping, String description,
            String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = ProductPojo.getProductPojo
                (name, type, price, upc, shipping, description, manufacturer, model, url, image);

        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .patch(Path.PRODUCTS + EndPoints.UPDATE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("Delete product with productID")
    public ValidatableResponse deleteProduct(int productID) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .delete(Path.PRODUCTS + EndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("Getting Product information with productID : {0}")
    public ValidatableResponse getProductById(int productID) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .get(Path.PRODUCTS + EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().all()
                .statusCode(404);
    }
}
