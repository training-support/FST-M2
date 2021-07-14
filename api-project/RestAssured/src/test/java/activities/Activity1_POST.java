package activities;



import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;
public class Activity1_POST {
	
	final String baseURI="https://petstore.swagger.io/v2/pet";
	
	@Test(priority=1)
	public void postRequest()
	{
		String reqBody="{"+ 
				"\"id\": 77232," + 
				"\"name\": \"Riley\"," + 
				"\"status\": \"alive\"" + 
				"}";
		

		
		Response response=given().contentType(ContentType.JSON)
				.body(reqBody)
				.when()
				.post(baseURI);
		
		response.then().body("id",equalTo(77232));
		response.then().body("name",equalTo("Riley"));
		response.then().body("status",equalTo("alive"));
		
	}
	
	@Test(priority=2)
	public void getRequest()
	{
		Response response=given().contentType(ContentType.JSON)
				.when().pathParam("id","77232")
				.get(baseURI+"/{id}");
		

		response.then().body("id",equalTo(77232));
		response.then().body("name",equalTo("Riley"));
		response.then().body("status",equalTo("alive"));
	}
	
	@Test(priority=3)
	public void deleteRequest()
	{
		Response response=given().contentType(ContentType.JSON)
				.when().pathParam("id","77232")
				.delete(baseURI+"/{id}");
		
		System.out.println(response.asPrettyString());
		response.then().body("code",equalTo(200));
		response.then().body("message",equalTo("77232"));
		
	}

}
