package activities;


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
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity_3 {
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	@BeforeClass
	public void setup()
	{
		
		
		requestSpec=new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri("https://petstore.swagger.io/v2/pet")
				.build();
		
		responseSpec=new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType("application/json")
				.build();
	}
	
	
	@DataProvider
	public Object[][] petProvider() {
		
		Object[][] testData = new Object[][] { 
		    { 77282, "Riley", "alive" }, 
		    { 77283, "Hansel", "alive" } 
		
		};
		return testData;
	}
	
	@Test(dataProvider="petProvider",priority=2)
	public void getRequest(int id,String name,String status) throws InterruptedException
	{
		 Thread.sleep(5000);
		System.out.println("Id value is"+id);
		Response response=given().spec(requestSpec)
				.pathParam("petId",id)
				.when().get("/{petId}");
		System.out.println("get request"+response.asPrettyString());
	
        response.then()
    	        .spec(responseSpec) // Use responseSpec
    	        .body("name", equalTo(name));
		
	}
	
	@Test(dataProvider="petProvider",priority=3)
	public void deleteRequest(int id,String name,String status)
	{
		Response response=given().spec(requestSpec)
				.pathParam("petId",id)
				.when().delete("/{petId}");
		
		System.out.println("Delete request"+response.asPrettyString());
		
		response.then()
				.body("code", equalTo(200));
	}
	
	
    @Test(priority=1)
	
    public void addPets() {
	
        String reqBody = "{\"id\": 77282, \"name\": \"Riley\", \"status\": \"alive\"}";
	
        Response response = given().spec(requestSpec) 
        	                .body(reqBody) 
        	                .when().post(); 
        System.out.println("Post request"+response.asPrettyString());
        
        response.then().spec(responseSpec);
	
        reqBody = "{\"id\": 77283, \"name\": \"Hansel\", \"status\": \"alive\"}";
	
        response = given().spec(requestSpec)
                .body(reqBody)	
                .when().post();
        System.out.println("Post request"+response.asPrettyString());
        
        response.then().spec(responseSpec);
	
    }
    
    
	
	}


