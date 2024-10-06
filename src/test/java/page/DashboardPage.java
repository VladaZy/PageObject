package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private SelenideElement deposit = $("[data-test-id='action-deposit']");
    private SelenideElement reloadButton = $("[data-test-id='action-reload']");


    public DashboardPage() {
        heading.shouldBe(visible);

    }


    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int end = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), end);
        return Integer.parseInt(value);

    }

    public RefillCardPage selectCard(DataHelper.CardInfo cardInfo) {
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getCardId()))
                .$("[data-test-id='action-deposit']").click();
        return new RefillCardPage();

    }


    public SelenideElement findCard(DataHelper.CardInfo card) {
        return cards.findBy(Condition.attribute("data-test-id", card.getCardId()));
    }

    public int getCardBalance(DataHelper.CardInfo card) {
        String text = findCard(card).text();
        return extractBalance(text);
    }
}