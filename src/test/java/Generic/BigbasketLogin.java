package Generic;

import FileUtility.FileLib;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static Generic.BigbasketRoutes.*;

public class BigbasketLogin {
    public Cookies cookies;

    @BeforeClass
    public void Login() {
        String username = "kaptureqa";
        String password = "Testing@1234";

        Response response = RestAssured.given()
                .queryParam("username", username)
                .queryParam("password", password)
                .get(BigBasketLogin);
        response.then().log().all();

        cookies = response.getDetailedCookies(); // Store the cookies
        System.out.println("*****************************");
        System.out.println(cookies);
        System.out.println("Login method is executed");
        FileLib.writeDataIntoPropertyFileBigBasket(String.valueOf(cookies));

        try {
            Thread.sleep(3000); // Add a wait time of 300 milliseconds after executing login method
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Cookies getCookies() {
        return cookies;

    }

}
