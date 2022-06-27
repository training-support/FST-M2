package activity;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ExtendWith(PactConsumerTestExt.class)
public class PactSpringConsumerTest {
	// Create Map for the headers
	Map<String, String> headers = new HashMap<String, String>();

	// Set resource URI
	String createTutorial = "/api/tutorials";
	
	// Create Pact contract
	@Pact(provider = "MY_PROVIDER", consumer = "MY_CONSUMER")
	public RequestResponsePact createPact(PactDslWithProvider builder) throws ParseException {
		// Add headers
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");

		// Create request JSON
		DslPart bodySentCreateTutorial = new PactDslJsonBody()
				.stringType("title")
				.stringType("description")
				.booleanType("published");

		// Create response JSON
		DslPart bodyReceivedCreateTutorial = new PactDslJsonBody()
				.numberType("id")
				.stringType("title")
				.stringType("description")
				.booleanType("published");

		// Create rules for request and response
		return builder.given("A request to create a tutorial")
			.uponReceiving("A request to create a tutorial")
				.path(createTutorial)
				.method("POST")
				.headers(headers)
				.body(bodySentCreateTutorial)
			.willRespondWith()
				.status(201)
				.body(bodyReceivedCreateTutorial)
			.toPact();
	}

	@Test
	@PactTestFor(providerName = "MY_PROVIDER", port = "8080")
	public void runTest() {
		// Mock url
		RestAssured.baseURI = "http://localhost:8080";
		// Create request specification
		RequestSpecification rq = RestAssured.given().headers(headers).when();

		// Create request body
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "Tutorial title");
		map.put("description", "This is a Pact test");
		map.put("published", true);

		// Send POST request
		Response response = rq.body(map).post(createTutorial);

		// Assertion
		assert (response.getStatusCode() == 201);
	}

}