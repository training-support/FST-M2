package example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Session2Activity3 {
       RequestSpecification reqSpec;
       ResponseSpecification resSpec;

    @BeforeClass
    public void setUp() {

        reqSpec = new RequestSpecBuilder()
                          .setContentType(ContentType.JSON)

                .setBaseUri("https://petstore.swagger.io/v2/pet")
                 .build();

        resSpec = new ResponseSpecBuilder()

                .expectStatusCode(200)

                .expectContentType("application/json")

                .expectBody("status", equalTo("alive"))

                .build();
    }

    @DataProvider
    public Object[][] PInfoProvider() {

        Object[][] data = new Object[][] {
                { 56233, "IBM", "alive" },
                { 56234, "IBM1", "alive" }
        };
        return data;
    }

    @Test(priority=1)

    public void addPets() {
        String reqBody = "{\"id\": 56233, \"name\": \"IBM\", \"status\": \"alive\"}";
        Response res = given().spec(reqSpec)
                .body(reqBody)
                .when().post();

        reqBody = "{\"id\": 56234, \"name\": \"IBM1\", \"status\": \"alive\"}";
        res = given().spec(reqSpec)
                .body(reqBody)
                .when().post();


        res.then().spec(resSpec);
    }


    @Test(dataProvider = "PInfoProvider", priority=2)
    public void getPets(int id, String name, String status) {
        Response response = given().spec(reqSpec)
                .pathParam("petId", id)
                .when().get("/{petId}");


        System.out.println(response.asPrettyString());

        response.then()
                .spec(resSpec)
                .body("name", equalTo(name));
    }


    @Test(dataProvider = "PInfoProvider", priority=3)
    public void deletePets(int id, String name, String status) {
        Response response = given().spec(reqSpec)
                .pathParam("petId", id)
                .when().delete("/{petId}");


        response.then().body("code", equalTo(200));
    }

}
