package activities;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

public class Activity2 {

	final String baseURI="https://petstore.swagger.io/v2/user";
	
	@Test(priority=1)
	public void externalJSON() throws IOException
	{
		//FileInputStream input1=new FileInputStream("src/test/resource/jsonInput1.json");
		 String file = "src/test/resource/jsonInput1.json";
	     String reqBody=new String(Files.readAllBytes(Paths.get(file)));
	        
		
		System.out.println(reqBody);
	
		Response response=given().contentType(ContentType.JSON)
				.body(reqBody)
				.when()
				.post(baseURI);
		
		System.out.println(response.asPrettyString());
		response.then().body("message",equalTo("990122"));
		response.then().body("code",equalTo(200));
		
	}
	
	@Test(priority=2)
	public void getRequest() throws IOException
	{
		Response response=given().contentType(ContentType.JSON)
				.when().pathParam("username","saranya")
				.get(baseURI+"/{username}");
		
		File file=new File("src/test/resource/output1.json");
		file.createNewFile();
		FileWriter writer=new FileWriter(file);
		writer.write(response.asPrettyString());
		writer.close();
		
		response.then().body("id",equalTo(990122));
		response.then().body("username",equalTo("saranya"));
		response.then().body("firstName",equalTo("Justin"));
		response.then().body("lastName",equalTo("Case"));
		response.then().body("email",equalTo("justincase@mail.com"));
		response.then().body("password",equalTo("password123"));
		response.then().body("phone",equalTo("9812763450"));
		
	}
	
	@Test(priority=3,enabled=false)
	public void deleteRequest()
	{
		
		Response response=given().contentType(ContentType.JSON)
					.when().pathParam("username","saranya")
				.delete(baseURI+"/{username}");
		
		response.then().body("code",equalTo(200));
	
	}
	
	
}
