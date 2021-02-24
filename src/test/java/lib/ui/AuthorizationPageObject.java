package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject{

    private static final String
            LOGIN_BUTTON = "xpath:/html/body/div[4]/div[2]/a",
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "css:button#wpLoginAttempt";
    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton(){
        this.waitForElementAndClick(LOGIN_BUTTON,"Cannot find Login Button", 5);
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click Login Button", 5);
    }

    public void enterLoginData(String login, String password){
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find Login Input",5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password,"Cannot find Password Input",5);
    }

    public void submitForm(){
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find Submit Button", 5);
    }
}
