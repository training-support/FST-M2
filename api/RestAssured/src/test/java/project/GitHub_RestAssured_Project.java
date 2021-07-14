package project;

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
import org.testng.annotations.Test;


public class GitHub_RestAssured_Project {
	
	
RequestSpecification requestSpec;
int id;

	@BeforeClass
	public void requestSpec()
	{
			requestSpec = new RequestSpecBuilder()
						.setContentType(ContentType.JSON)
						.setBaseUri("https://api.github.com")
						.addHeader("Authorization","token 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")
						.build();
			          
	}
	
	@Test(priority=2)
	public void getRequest()
		{
		Response response=given()
					.spec(requestSpec)
					.get("/user/keys");
		
		ResponseSpecification responseSpec=new ResponseSpecBuilder()
							.expectStatusCode(200)
							.build();
		
		response.then().log().all();
		String response_body=response.then().log().body().extract().asPrettyString();
		
		Reporter.log(response_body);
		
				
	}
	
	@Test(priority=1)
	public void postRequest()
	{
		
		System.out.println("Post Request is");
		String ssh_key="{\"title\": \"TestAPIKey1\",\"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCpQSr19s2QEND+HkiJPDM1F+bcoSfa/1tIjmhUGbzmTw7MmO4IFipCm470lJCAPRLjbnqtT4ydWmpsLU/JiolzrzZRKk5YcxoBVq1PhfxKgkBzC6jvaBmLKhiDus2LJNmBuANzuImXXkLCdgit+RIxBrWD5Tk2YTrbyie4OCR+AzdtDgBRF8os94uNpinVqYKM/OSUhugBvrQ70TwdL0bx1YO7BeGZnTxWltPpb33T+sSbgzz8W2hFitu/qLW/HIULIpv9Bpec1+QRK/BaUKIOEEclq7a5rMcu4ypXIN2FLp2Rw3inM0UIfDhiJAlU28XMcYeTXthKyRw+gf5IfYCv\"";
					
		Response response=given()
				.spec(requestSpec)
				.body(ssh_key)
				.post("/user/keys");
		
		ResponseSpecification responseSpec=new ResponseSpecBuilder()
				.expectStatusCode(201)
				.build();
		
		response.then().log().body();
		id=response.then().extract().path("id");
		System.out.println("id value is"+id);
		
		String response_body=response.then().log().body().extract().asPrettyString();
		
		Reporter.log(response_body);
		
	}
	
	@Test(priority=3)
	public void deleteRequest()
	{
		//String ssh_key="{\"title\": \"TestAPIKey1\",\"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCLlnKVD0+tQssqZHo7lC8hBFkyGG5okhCAlOWC+D1+8VeOkQIrAkQRYTILIBoUKW6LAFBiy4uIPnR6wzKEfChavbdT6Kk+aVA0rmkE11vjc4+jIafgYNvtgLfzZJQMiuWYPVDfCjyMNmcx/0fJ8lgw2niP9FSWSWtwcqzKbj2QbO02oKYDEcj2aNoN3lWzpbjqlOUDkP2Yp7PpoytK0A9QkTURM1wJwRnu7q/SojEJtkmZtMQKCWJHuLMIa7oREaAqlB/W6tpbIaTNpPeodU27rpfl5HZ4ruPlQYww2pSOUg1DOCr9f/K7KZVfIzXRHTQj37noyevTy9K46vFtZOOR\"";
					
		Response response=given()
				.spec(requestSpec)
				.pathParam("keyid",id)
				.delete("/user/keys/{keyid}");
		
		ResponseSpecification responseSpec=new ResponseSpecBuilder()
				.expectStatusCode(204)
				.build();
		
		String response_body=response.then().log().body().extract().asPrettyString();
		
		Reporter.log(response_body);
		
	}
	
	

}
