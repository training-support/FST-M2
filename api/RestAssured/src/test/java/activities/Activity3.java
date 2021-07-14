package activities;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Activity3 {
	
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	
	@BeforeClass
	public void setup()
	{
		requestSpec=new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri("https://api.github.com")
				.addHeader("Authorization","token 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")
				.build();
		
		
	}
	
	
	@DataProvider
	public Object getUser()
	{
		Object obj[][]=new Object[][]
				{
					{ 77232, "Riley", "alive" }, 
        	
					{ 77233, "Hansel", "alive" }
				};
            return obj;
	}
				
	
	
	@Test(dataProvider="getUser",priority=1)
	public void postRequest(int id, String name, String status)
	{
			String reqBody="{ 77232, \"Riley\", \"alive\" }";
			Response response=given().spec(requestSpec)
					.body(reqBody)
					.when().post();
			
			
			String reqBody1="{ 77233, \"Hansel\", \"alive\" }";
			response=given().spec(requestSpec)
					.body(reqBody1)
					.when().post();
						
			response.then().spec(responseSpec);
			
	}
	@Test
	public void getRequest()
	{
			Response response=given().spec(requestSpec)
							.get("/user/keys");
			
			responseSpec=new ResponseSpecBuilder()
					.expectStatusCode(200)
					.expectContentType("application/json")
					.build();
			
			response.then().log().all().spec(responseSpec);
		
			Reporter.log(response.asPrettyString());
	

	}
	
    @Test(dataProvider = "petInfoProvider", priority=3)
	
    public void deletePets(int id, String name, String status) {
	
        Response response = given().spec(requestSpec) // Use requestSpec
	
                .pathParam("petId", id) // Add path parameter
	
                .when().delete("/{petId}"); // Send GET request
	
 
	
        // Assertions
	
        response.then().body("code", equalTo(200));
	
    }
	
}
