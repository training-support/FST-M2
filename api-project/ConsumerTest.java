package Liveproject;

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


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //headers
    Map<String, String> reqHeaders = new HashMap<>();
    String resourcePath = "/api/users";

    // Creating a pact
    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        reqHeaders.put("Content-Type", "application/json");

        // Create Request and response body
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");

        return builder.given("Request to create a user")
                .uponReceiving("Request to create a user")
                .method("POST")
                .path(resourcePath)
                .headers(reqHeaders).body(reqResBody).willRespondWith()
                .status(201).body(reqResBody).toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "9999")
    public void consumerTest() {
        // Set Base URI
        String baseURI = "http://localhost:9999";
        // Define Request Body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 123);
        reqBody.put("firstName", "Ravi");
        reqBody.put("lastName", "Kumar");
        reqBody.put("email", "ravikumar@test.com");

        Response response = given().headers(reqHeaders).body(reqBody)
                .when().post(baseURI + resourcePath);

        //Print the data
        System.out.println(response.getBody().asPrettyString());

        //Assertion
        response.then().statusCode(201);

    }
}
