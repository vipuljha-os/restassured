package Generic;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static Generic.Routes.login;

public class MeeshoSXLogin {
    public Cookies cookies;
 @BeforeClass
    public void Login() {
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
       // FileLib.writeDataIntoPropertyFile(String.valueOf(cookies));
    }
    public static void hardWait(){
        try {
            Thread.sleep(700);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
