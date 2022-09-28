package liveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    // Create Map for the headers
    Map<String, String> headers = new HashMap<>();
    // Set resource URI
    String resourcePath = "/api/users";

    // Create Pact contract
    @Pact(provider = "UserProvider", consumer = "UserConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder){

        // Add headers
        headers.put("Content-Type", "application/json");

        // Create body (request JSON)
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");


        // Create rules for request and response (PACT)
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .path(resourcePath)
                .method("POST")
                .headers(headers)
                .body(requestResponseBody)
                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();


    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8282")
    public void consumerTest() {

        // Set the base url
        final String baseURI = "http://localhost:8282";
        // Create request body
        Map<String,Object> reqBody = new HashMap<>();
        reqBody.put("id",3);
        reqBody.put("firstName","Avinash");
        reqBody.put("lastName","Nagesh");
        reqBody.put("email","abc@gmail.com");

        //Generate Repsonse
        Response response = given().headers(headers).body(reqBody)
                .when().post(baseURI + resourcePath);

    }


}
