package Generic;

import FileUtility.FileLib;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static Generic.Routes.login;

public class MeeshoSXLogin {
    public Cookies cookies;

    @BeforeClass
    public void Login() throws InterruptedException {
        String username = "meeshosxtest1";
        String password = "Kapture@1";

        Response response = RestAssured.given()
                .queryParam("username", username)
                .queryParam("password", password)
                .get(login);
        response.then().log().all();

        cookies = response.getDetailedCookies();
        System.out.println("*****************************");
        System.out.println(cookies);
        System.out.println("Login method is executed");
        FileLib.writeDataIntoPropertyFile(String.valueOf(cookies));

        try {
            Thread.sleep(300); // Add a wait time of 300 milliseconds after executing login method
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
