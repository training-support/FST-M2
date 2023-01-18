package pactTesting;


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
public class PactConsumerTest {
    Map<String, String> headers = new HashMap<String, String>();
    String createUser = "/api/users";

    @Pact(provider = "UserProvider", consumer = "UserConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) throws ParseException {
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        DslPart bodySentCreateUser = new PactDslJsonBody()
                .numberType("id", 1)
                .stringType("firstName", "string")
                .stringType("lastName", "string")
                .stringType("email", "string");

        DslPart bodyReceivedCreateUser = new PactDslJsonBody()
                .numberType("id", 1)
                .stringType("firstName", "string")
                .stringType("lastName", "string")
                .stringType("email", "string");

        return builder.given("a request to create user")
                .uponReceiving("a request to create user")
                .path(createUser)
                .method("POST")
                .headers(headers)
                .body(bodySentCreateUser)
                .willRespondWith()
                .status(201)
                .body(bodyReceivedCreateUser)
                .toPact();

    }

    @Test
    @PactTestFor(providerName = "UserProvider", port = "8080")
    public void runTest() {
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification rq = RestAssured
                .given().headers(headers)
                .when();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        map.put("firstName", "Justin");
        map.put("lastName", "Case");
        map.put("email", "justincase@mail.com");
        Response rs = rq.body(map).post(createUser);
        System.out.println(rs.getBody().asPrettyString());
        rs.then().statusCode(201);
        //assert (rs.getStatusCode()==201);


    }


}