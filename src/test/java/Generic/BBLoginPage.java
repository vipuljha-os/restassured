package Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BBLoginPage {

    public BBLoginPage(WebDriver driver) {
        super();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//p[contains(text(), 'Oops. Something went wrong here. ')]")
    private WebElement errorMessage;

    @FindBy(xpath = "//input[@id='username_']")
    private WebElement userNameTextFiled;
    @FindBy(xpath = "//*[@id=\"login-form-submit\"]/div[2]/div/input")
    private WebElement passwordTextFiled;
    @FindBy(xpath = "//button[@value='Login']")
    private WebElement loginButton;

    public void doLogin() {
        try {
            userNameTextFiled.sendKeys("kaptureqa");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            passwordTextFiled.sendKeys("Testing@1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
           Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginButton.click();
    }
}
