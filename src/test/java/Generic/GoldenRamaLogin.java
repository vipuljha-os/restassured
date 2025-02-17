package Generic;

import FileUtility.FileLib;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Generic.GoldenRamaRoutes.GoldenRamalogin;

public class GoldenRamaLogin {
    public Cookies cookies;

    //@BeforeClass
    @Test
    public void GoldenRamalogin() {
        String username = "Test@kaptue.cx";
        String password = "Test@1234";

        Response response = RestAssured.given()
                .queryParam("username", username)
                .queryParam("password", password)
                .get(GoldenRamalogin);
        response.then().log().all();

        try {
            cookies = response.getDetailedCookies();
            System.out.println("*****************************");
            System.out.println(cookies);
            System.out.println("Login method is executed");
            FileLib.writeDataIntoPropertyFile(String.valueOf(cookies));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing cookies or writing to the property file.");
        }

        try {
            Thread.sleep(300); // Add a wait time of 300 milliseconds after executing login method
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
  }
}
