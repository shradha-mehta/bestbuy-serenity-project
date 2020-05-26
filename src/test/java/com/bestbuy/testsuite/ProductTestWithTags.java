package com.bestbuy.testsuite;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.pojo.ProductsPojo;
import com.bestbuy.steps.ProductsSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SerenityRunner.class)
public class ProductTestWithTags extends TestBase {

    static String name = "APPLE New Mobile" + TestUtils.getRandomValue();
    static String type = "iOS 5009" + TestUtils.getRandomValue();
    static String upc = "ABC" + TestUtils.getRandomValue();
    static double price = 87.99;
    static String description = "This is a placeholder request for creating a new product.";
    static String model = "NP12345" + TestUtils.getRandomValue();
    static long productId;

    @Steps
    ProductsSteps productsSteps;

    @WithTags({@WithTag("bestbuyfeature:POSITIVE"),
            @WithTag("bestbuyfeature:REGRESSION")
    })

    @Title("This test will create a new products and verify its generated")
    @Test

    public void test001() {
        productsSteps.createNewProduct(name, type, upc, price, description, model);

    }

    @WithTags({
            @WithTag("bestbuyfeature:SANITY"),
            @WithTag("bestbuyfeature:REGRESSION")
    })

    @Title("This test will get the product information by ID")
    @Test

    public void test002() {
        SerenityRest.rest()
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID + "/" + 309062)
                .then().log().all();
    }
    @WithTags({
    @WithTag("bestbuyfeature:POSITIVE"),
    @WithTag("bestbuyfeature:REGRESSION")
    })
    @Title("This test will update the product information and verify the updated information")
    @Test

    public void test003(){
        name = name+"_Changed";
        price = 89.99;
        upc = upc + "_added";
        productsSteps.updateProduct(name,type,upc,price,description,model).statusCode(200);
        productsSteps.getProductById().body("name",equalTo(name));

    }

    @WithTags({
            @WithTag("bestbuyfeature:NEGATIVE"),
            @WithTag("bestbuyfeature:REGRESSION")
    })

    @Title("This test will delete the product information and verify the product is deleted ")
    @Test

    public void test004(){
        SerenityRest.rest()
                .given()
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID + "/" + 9999682)
                .then()
                .statusCode(404);
    }

    @WithTags({
            @WithTag("bestbuyfeature:SMOKE"),
            @WithTag("bestbuyfeature:REGRESSION")
    })

    @Title("This test will get all product information ")
    @Test
    public void test005(){
        productsSteps.getAllProduct().statusCode(200);
    }



}
