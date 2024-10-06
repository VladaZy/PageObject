package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
public class RefillCardPage {

    private final SelenideElement heading = $(withText("Пополнение карты"));
    private final SelenideElement amount = $("[data-test-id=amount] input");
    private final SelenideElement from = $("[data-test-id=from] input");
    private final SelenideElement button = $("[data-test-id=action-transfer]");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private final SelenideElement error = $("[data-test-id=error-notification]");

    public RefillCardPage() {
        heading.shouldBe(visible);

    }

    public DashboardPage getValidTransfer(String sum, DataHelper.CardInfo cardInfo) {
        refillCard(sum, cardInfo);
        return new DashboardPage();
    }

    public void refillCard(String sum, DataHelper.CardInfo cardInfo) {
        amount.sendKeys(sum);
        from.sendKeys(cardInfo.getCardNumber());
        button.click();

    }


    public void getInvalidTransfer(String expectedText) {
        error.shouldHave(text(expectedText),
                Duration.ofSeconds(10)).shouldBe(Condition.visible);

    }

    public DashboardPage cancelRefill() {
        cancelButton.click();
        return new DashboardPage();
    }
}