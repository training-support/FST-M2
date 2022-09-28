package GitHub_RestAssured_Project;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTest {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    //String ssh = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCBJAHLWjogB5La5qH8DQf2isBnhXwXibl1uqJs/gYm9Ssjm8p3XAMMlZ/xJMBB6kaAS23vfs+w5WKJDdHFPqCVuL0HDJq+9iKBOPN7xkHEnFjwpnNbh65tnA7yY3nu9fPVoNVtvmEWkAi2L+XQLWSbUh7fsvV8eq9iQLlPuaT2YbUhcjlhKjcX6B5GoiJRGKpMk3VLuSKr7Ez9/KxxLpjSRYme99CKk7S+zA3x+e+ZI4GtS6JcuW0gBGVPT8PFkAQCpydaYI16VcwCgFwqLrL532Q0lEOtMZp2uDdz05soTJOtaV3hwcSSvZ0eZJA4s+hsYXNbhPPqnk1IpyMtKOQD";
    int num;

    @BeforeClass
    public void Setup(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .setContentType(ContentType.JSON)
                .addHeader("token","token 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")
                .build();

    }

    @Test(priority = 1)
    public void postRequestTest() {
        //Request body
        String reqBody = "{\"title\": TestAPIKey, \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCBJAHLWjogB5La5qH8DQf2isBnhXwXibl1uqJs/gYm9Ssjm8p3XAMMlZ/xJMBB6kaAS23vfs+w5WKJDdHFPqCVuL0HDJq+9iKBOPN7xkHEnFjwpnNbh65tnA7yY3nu9fPVoNVtvmEWkAi2L+XQLWSbUh7fsvV8eq9iQLlPuaT2YbUhcjlhKjcX6B5GoiJRGKpMk3VLuSKr7Ez9/KxxLpjSRYme99CKk7S+zA3x+e+ZI4GtS6JcuW0gBGVPT8PFkAQCpydaYI16VcwCgFwqLrL532Q0lEOtMZp2uDdz05soTJOtaV3hwcSSvZ0eZJA4s+hsYXNbhPPqnk1IpyMtKOQD\"}";

        //Generate Response
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys/");

        System.out.println(response.getBody().asPrettyString());
        num = response.then().extract().body().path("id");

        //Assertions
        response.then().statusCode(201);
    }

        @Test(priority = 2)
        public void getRequestTest(){
            Response response = given().spec(requestSpec)
                    .when().get("/user/keys/" + num);
            System.out.println(response.getBody().asPrettyString());
            response.then().statusCode(200);


        }

        @Test(priority= 3)
        public void getDeleteTest(){
            Response response = given().spec(requestSpec)
                    .when().delete("/user/keys/" + num);

            System.out.println(response.getBody().asPrettyString());
            response.then().statusCode(204);
        }

}


