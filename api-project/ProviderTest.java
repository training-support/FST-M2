package liveProject;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("UserProvider")
    @PactFolder("target/pacts")
    public class ProviderTest {
        @BeforeEach
        public void setUp(PactVerificationContext context) {
            // Set target for provider to send request to
            HttpTestTarget target =new HttpTestTarget("localhost", 8585);
            context.setTarget(target);
        }
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void testMethod(PactVerificationContext context) {
        // Verify the interaction between Consumer and Provider
        // using the contract generated in target/pacts
        context.verifyInteraction();
    }


    // State to send the call to consumer
    @State("A request to create a user")
    public void providerState() {}
    
}

