package githubproject;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class PostRequestTest {
    RequestSpecification  requestSpecification;
    String ShhKey ;
    int id;
    @BeforeClass
    public  void setUp()
    {
         ShhKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCpWdC+YAJieFbbo3swBK5VBLxdmSDW+cU/3a39rLgsCipjrETlHhi9UAvlEaXKGQmSkMpARvd5hCll6QOvAQNWl4BG/KAoFb02qb/4OJQPU/phtR5OdYav5wbTCzLZPzM95XS3knO0rrZGKezdnB1zJClfBm8x1lYJ7Aljm+Ljxw5i2T/Jz/V5/VNysR3AwG5+HhmRMrfMO7bJ2udo1vEpV8V6LC5mrII9ZtMh/eKcb4dIbofndDDXxIMB45F1EeIaXlnO6s4eBQDfj3eLPopxLxM70CqmaE/3tEr+sskFTxYTxqXg26UOltPvRlQ9gPTW0sT1kH2ShSpDjrLWd2RJ";

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://api.github.com");
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Authorization", "token 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");
        requestSpecification = builder.build();
    }

    @Test(priority = 1)
    public void createPostRequest()
    {
        String requestBody = "{\"title\":\"TestAPIKey\", \"key\":\"" + ShhKey + "\"}";
        Response response = given().spec(requestSpecification)
                .body(requestBody)
                .when().post("/user/keys");

        String responseBody = response.getBody().asString();
        System.out.println("Response of Post request: " +responseBody);
         id = response.then().extract().path("id");
        // Assertions for status code
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void createGetRequest(){
        Response response = given().spec(requestSpecification)
                .when().get("/user/keys/" +id);

        String responseBody = response.getBody().asString();
        System.out.println("Response of Get request: " +responseBody);
        JsonPath js = new JsonPath(responseBody);
        String id1= js.getString("id");
        System.out.println("id of user key is : " +id1);
        // Assertions for status code
        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteRequestTest()
    {
        Response response = given().spec(requestSpecification)
                .when().delete("/user/keys/"+ id);

        String responseBody = response.getBody().asString();
        System.out.println("Response of Delete request: " +responseBody);

        // Assertions for status code
        response.then().statusCode(204);
    }
}
