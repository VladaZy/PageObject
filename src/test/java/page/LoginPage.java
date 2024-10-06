package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {


    private SelenideElement LoginField = $("[data-test-id=login] input");
    private SelenideElement PasswordField = $("[data-test-id=password] input");
    private SelenideElement LoginButton = $("[data-test-id=action-login]");

    public VerificationPage validlogin(DataHelper.AuthInfo info) {
        LoginField.setValue(info.getLogin());
        PasswordField.setValue(info.getPassword());
        LoginButton.click();
        return new VerificationPage();
    }

}