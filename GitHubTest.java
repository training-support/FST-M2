package gitHubRestAssuredTesting;

import com.google.common.primitives.Bytes;
import io.restassured.authentication.OAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.maven.surefire.api.runorder.Priority;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GitHubTest {
    RequestSpecification reqSpec;
    String sshKey;
    int sshKeyId;

    @BeforeClass
    public void setUp() {
        reqSpec = new RequestSpecBuilder().setBaseUri("https://api.github.com")
                .addHeader("Authorization", "token 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")
                .setContentType(ContentType.JSON).build();
    }

    @Test(priority = 1, dataProvider = "sshKeyData")
    public void addKeys(String title, String key) throws IOException {

        File file= new File("src/test/resources/request.json");
        /*FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);
        String reqBody = new String(bytes, "UTF-8");*/
        Response res= given().spec(reqSpec)//.body(reqBody)
                .body(new gitHubPojo(title,key))
                .when()
                .post("/user/keys");
        sshKeyId = res.then().extract().path("id");
        System.out.println(res.asPrettyString());
        res.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getKeys() {
        Response res = given().spec(reqSpec).when().get("/user/keys");
        Reporter.log(res.asPrettyString());
        System.out.println(res.asPrettyString());
        res.then().statusCode(200);

    }

    @Test(priority = 3)
    public void deleteKeys() {
        Response res = given().spec(reqSpec).pathParam("sshKeyId",sshKeyId).when().
                delete("/user/keys/{sshKeyId}");
        Reporter.log(res.asPrettyString());
        System.out.println(res.asPrettyString());
        res.then().statusCode(204);

    }

    @DataProvider(name="sshKeyData")
    public Object[][] getData(){
        return new Object[][]{{"TestAPIKey","ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQDa/w7++tt8C75ulC5Sh0oHamd1IS5VmTiQRFQNqjNz0xpP42UgpRG1RBv14JjZcYIOvD2P2/mMSnC06DEjoRnhJlLZb8TvdmaZhuGL+kR3RS8NayJfWJGow/n0QpAN4AnKIIl5b9C8ejGVAxaJUWuxY4suaBYAZ9e0X93teH3sdQ=="},
                {"testKey2","ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQCB0fEFuC9lhlQUj08fP6yfQV9BK/5Wbh35sL9KVDz+c3XVJo/o1qqy7H3fYspfVab0ts1N4YjaB+idJN7878Y+23SRDiiyPrXlhUdbudoqSczgwy3OBQN9g8hL8Ljgrcqhp1kA2otC3Yitse8VKcEODiMplZutTjjrdC+zJiURHw=="}};
    }

}