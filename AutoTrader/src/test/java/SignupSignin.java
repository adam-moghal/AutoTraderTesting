import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignupSignin {

    @FindBy(css = "#js-social__signup-tab")
    private WebElement signUpTab;

    @FindBy(css = "#email")
    private WebElement signupEmail;
    @FindBy(css = "#password")
    private WebElement signupPassword;

    @FindBy(css = "#signin-email")
    private WebElement signinEmail;
    @FindBy(css = "#signin-password")
    private WebElement signinPassword;


    @FindBy(css = "#social--signup--submit")
    private WebElement completeSignUpButton;
    @FindBy(css = "#signInSubmit")
    private WebElement signinButton;


    public void signUp(String email, String password) {

        signUpTab.click();
        signupEmail.sendKeys(email);
        signupPassword.sendKeys(password);
        completeSignUpButton.click();

    }

    public void signIn(String email, String password) {

        signinEmail.sendKeys(email);
        signinPassword.sendKeys(password);
        signinButton.click();

    }
}
