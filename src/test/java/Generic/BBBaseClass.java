package Generic;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BBBaseClass {
    public static WebDriver driver;
    public static Cookies cookies;
    private static boolean isFirstLogin = true;

    @BeforeClass
    public void setUp() {
        // Define ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1680x1050");

        try {
            // Define the URL of the Selenium Grid hub
            String gridUrl = "https://selenium-hub-client-qaw43rtxc.infra.kapturecrm.com/wd/hub";

            // Create a RemoteWebDriver instance with the specified grid URL and ChromeOptions
            driver = new RemoteWebDriver(new URL(gridUrl), options);
            driver.manage().window().maximize();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to initialize the browser. Exception: " + e.getMessage());
        }
    }

    @BeforeMethod
    public void login() {
        if (isFirstLogin) {
            normalLogin();
            isFirstLogin = false;
        } else {
            System.out.println("1111");
            sessionIdLogin();
        }

        // Navigate to the target page
        driver.navigate().to("https://bigbasket.kapdesk.com/nui/tickets/assigned_to_me/5/-1/0");
        String URL = driver.getCurrentUrl();
        System.out.println("******************************");
        System.out.println("Current URL is :- " + URL);
        Assert.assertEquals(URL, "https://bigbasket.kapdesk.com/nui/tickets/assigned_to_me/5/-1/0");
    }

    private void normalLogin() {
        try {
            // Navigate to the login page
            driver.get("https://bigbasket.kapdesk.com/employee/index.html");
            BBLoginPage loginpage = new BBLoginPage((RemoteWebDriver) driver);
            loginpage.doLogin();

            // Capture session cookies after login
            cookies = captureSessionCookies();
            System.out.println("***************");
            System.out.println(cookies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sessionIdLogin() {
        try {
            // Navigate to the login page
            driver.get("https://bigbasket.kapdesk.com/employee/index.html");

            // Add cookies to Selenium WebDriver
            for (Cookie restAssuredCookie : cookies.asList()) {
                org.openqa.selenium.Cookie seleniumCookie = new org.openqa.selenium.Cookie.Builder(restAssuredCookie.getName(), restAssuredCookie.getValue())
                        .domain(restAssuredCookie.getDomain())
                        .path(restAssuredCookie.getPath())
                        .expiresOn(restAssuredCookie.getExpiryDate())
                        .isHttpOnly(restAssuredCookie.isHttpOnly())
                        .isSecure(restAssuredCookie.isSecured())
                        .build();
                driver.manage().addCookie(seleniumCookie);
            }

            // Refresh the page to apply cookies
            driver.navigate().refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Cookies captureSessionCookies() {
        Set<org.openqa.selenium.Cookie> seleniumCookies = driver.manage().getCookies();
        List<Cookie> restAssuredCookies = new ArrayList<>();

        for (org.openqa.selenium.Cookie seleniumCookie : seleniumCookies) {
            Cookie restAssuredCookie = new Cookie.Builder(seleniumCookie.getName(), seleniumCookie.getValue())
                    .setDomain(seleniumCookie.getDomain())
                    .setPath(seleniumCookie.getPath())
                    .setHttpOnly(seleniumCookie.isHttpOnly())
                    .setSecured(seleniumCookie.isSecure())
                    .build();
            restAssuredCookies.add(restAssuredCookie);
        }

        return new Cookies(restAssuredCookies);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
