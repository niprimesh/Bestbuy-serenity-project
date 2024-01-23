package com.bestbuy.steps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class StoreSteps extends StorePojo {
    @Step("Creating Store with name : {0}, type : {1}, address : {2}, address2 : {3}, city : {4}," +
            " state : {5}, zip : {6}, lat : {7}, lng : {8}, hours : {9}  ")
    public ValidatableResponse createStore(
            String name, String type, String address, String address2, String city, String state,
            String zip, double lat, double lng, String hours) {

        StorePojo storePojo = StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)// replace payload to store pojo here
                .post(Path.STORES)
                .then().log().all().statusCode(201);
    }


    @Step("Verifying store is added with storeID : {0}")
    public HashMap<String, Object> getStoreInfoByStoreName(int storeID) {
        return SerenityRest.given().log().all()
                .when()
                .pathParam("storeID", storeID)
                .get(Path.STORES + EndPoints.GET_SINGLE_STORE_BY_ID)
                .then()
                .statusCode(200)
                .extract().path("");
    }


    @Step("Creating Store with storeID : {0}, name : {1}, type : {2}, address : {3}, address2 : {4}, city : {5}," +
            " state : {6}, zip : {7}, lat : {8}, lng : {9}, hours : {10}  ")
    public ValidatableResponse updateStore(
            int storeID, String name, String type, String address, String address2, String city, String state,
            String zip, double lat, double lng, String hours) {

        StorePojo storePojo = StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given().log().all()
                .pathParam("storeID", storeID)
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .patch(Path.STORES + EndPoints.UPDATE_STORE_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("Delete store with storeID : {0}")
    public ValidatableResponse deleteStore(int storeID) {
        return SerenityRest.given().log().all()
                .pathParam("storeID", storeID)
                .when()
                .delete(Path.STORES + EndPoints.DELETE_STORE_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("Getting store information with storeID : {0}")
    public ValidatableResponse getStoreById(int storeID) {
        return SerenityRest.given().log().all()
                .pathParam("storeID", storeID)
                .when()
                .get(Path.STORES + EndPoints.GET_SINGLE_STORE_BY_ID)
                .then().log().all()
                .statusCode(404);
    }
}

