package example;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class FirstClass {
    @Test
    public void GetPetDetails() {

        final String baseURI = "https://petstore.swagger.io/v2/pet";

        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().get(baseURI + "/findByStatus?status=sold"); // Run GET request
        System.out.println(response.getBody().toString());

        response.then().statusCode(200);
        response.then().body("[0].status", equalTo("sold"));
    }
}
