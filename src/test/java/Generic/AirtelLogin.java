package Generic;

import org.testng.annotations.BeforeClass;

public class AirtelLogin {
    public static String bearerToken;

    @BeforeClass
    public void Login() {
        bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyX2lkIiwiZXhwIjoyMDg0MTY1Njg4fQ.B8eG385AekH9eSSBrYo_PrTBb77Ai0boZGfXgwOquo8";

        System.out.println("Bearer Token: " + bearerToken);
        System.out.println("Login method is executed");

        try {
            Thread.sleep(3000); // Add a wait time of 3000 milliseconds after executing login method
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getBearerToken() {
        return bearerToken;
    }

}
