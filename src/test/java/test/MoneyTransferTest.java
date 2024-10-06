package test;

import data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
        VerificationPage verificationPage = loginPage.validlogin(authInfo);
        DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards1() {
        DashboardPage dashboardPage = new DashboardPage();
        DataHelper.CardInfo cardOneInfo = DataHelper.getFirstCard();
        DataHelper.CardInfo cardTwoInfo = DataHelper.getSecondCard();
        var cardOneBalance = dashboardPage.getCardBalance(cardOneInfo);
        var cardTwoBalance = dashboardPage.getCardBalance(cardTwoInfo);
        var sum = DataHelper.getValidSum(cardOneBalance);
        var transfer = dashboardPage.selectCard(cardOneInfo);
        transfer.getValidTransfer(String.valueOf(sum), cardTwoInfo);

        Assertions.assertEquals(cardTwoBalance - sum, dashboardPage.getCardBalance(cardTwoInfo));
        Assertions.assertEquals(cardOneBalance + sum, dashboardPage.getCardBalance(cardOneInfo));
    }

    @Test
    public void shouldTransferMoneyFromCardOneToCard2() {
        DashboardPage dashboardPage = new DashboardPage();
        var cardOneInfo = DataHelper.getFirstCard();
        var cardTwoInfo = DataHelper.getSecondCard();
        var cardOneBalance = dashboardPage.getCardBalance(cardOneInfo);
        var cardTwoBalance = dashboardPage.getCardBalance(cardTwoInfo);
        var sum = DataHelper.getValidSum(cardOneBalance);
        var transfer = dashboardPage.selectCard(cardTwoInfo);
        transfer.getValidTransfer(String.valueOf(sum), cardOneInfo);

        Assertions.assertEquals(cardOneBalance - sum, dashboardPage.getCardBalance(cardOneInfo));
        Assertions.assertEquals(cardTwoBalance + sum, dashboardPage.getCardBalance(cardTwoInfo));

    }

    @Test
    public void shouldCancellationOfMoneyTransfer() {
        DashboardPage dashboardPage = new DashboardPage();
        var cardOneInfo = DataHelper.getFirstCard();
        var cardTwoInfo = DataHelper.getSecondCard();
        var cardOneBalance = dashboardPage.getCardBalance(cardOneInfo);
        var cardTwoBalance = dashboardPage.getCardBalance(cardTwoInfo);
        var transfer = dashboardPage.selectCard(cardOneInfo);
        transfer.cancelRefill();

        Assertions.assertEquals(cardTwoBalance, dashboardPage.getCardBalance(cardTwoInfo));
        Assertions.assertEquals(cardOneBalance, dashboardPage.getCardBalance(cardOneInfo));

    }

    @Test
    public void ShouldTransferMoreMoneyThanYourBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        var cardOneInfo = DataHelper.getFirstCard();
        var cardTwoInfo = DataHelper.getSecondCard();
        var cardTwoBalance = dashboardPage.getCardBalance(cardTwoInfo);
        var sum = DataHelper.getInvalidSum(cardTwoBalance);
        var transfer = dashboardPage.selectCard(cardOneInfo);

        transfer.refillCard(String.valueOf(sum), cardTwoInfo);
        transfer.getInvalidTransfer("Ошибка! Недостаточно средств на карте!");
    }
}