package Contract;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class GitHubProject {

    RequestSpecification reqSpec;

    String sshKey;
    int sshKeyId;

    @BeforeClass
    public void SetUp()
    {
       // reqSpec= new RequestSpecBuilder().setContentType(ContentType.JSON)





    }
}






