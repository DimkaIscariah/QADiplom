package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import org.junit.jupiter.api.*;
import ru.netolody.page.PaymentPage;
import io.qameta.allure.selenide.AllureSelenide;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class TestCredit {

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080");
    }



    @AfterEach
    void teardown() {
        SQLHelper.cleanDatabase();
    }


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }



    @Test
    @DisplayName("Покупка тура в кредит, позитивный тест")
    void shouldSuccessfullyCreditWithValidUser() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankAccept();
        var expected = DataHelper.getStatusValidCard;
        var actual = SQLHelper.getCreditRequestInfo();
        assertEquals(expected, actual);
    }



    @Test
    @DisplayName("Оплата в кредит, невалидный номер карты, негативный тест")
    void shouldCreditWithInvalidCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getInvalidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankReject();
        var expected = DataHelper.getStatusInvalidCard;
        var actual = SQLHelper.getCreditRequestInfo();
        assertEquals(expected, actual);
    }



    @Test
    @DisplayName("Оплата в кредит, незаполненное поле номера карты, негативный тест")
    void shouldCreditWithEmptyCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getEmptySpace();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }



    @Test
    @DisplayName("Оплата в кредит, некорректный номер карты, менее 16 цифр, негативный тест")
    void shouldCreditWithErrorLessCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getErrorLessCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }



    @Test
    @DisplayName("Оплата в кредит, неверный номер карты, негативный тест")
    void shouldCreditWithRandomCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getRandomCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankReject();
    }



    @Test
    @DisplayName("Оплата в кредит, незаполненный месяц срока действия карты, негативный тест")
    void shouldCreditWithEmptyMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getEmptySpace();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }



    @Test
    @DisplayName("Оплата в кредит, неверный месяц окончания срока действия карты, негативный тест")
    void shouldCreditWithMoreMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getMonthMorePossible();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorCard();
    }



    @Test
    @DisplayName("Оплата в кредит, некорректный формат месяца окончания срока действия карты, негативный тест")
    void shouldCreditWithErrorMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getInvalidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }



    @Test
    @DisplayName("Оплата в кредит, незаполненный год окончания срока действия карты, негативный тест")
    void shouldCreditWithEmptyYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmptySpace();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }



    @Test
    @DisplayName("Оплата в кредит, неверный формат года окончания срока действия карты, негативный тест")
    void shouldCreditWithInvalidYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getInvalidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }



    @Test
    @DisplayName("Оплата в кредит, неверный год окончания срока действия карты, ранее текущей даты, негативный тест")
    void shouldCreditWithBeforeYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearPast();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorYearCard();
    }



    @Test
    @DisplayName("Оплата в кредит, неверный год окончания срока действия карты, более 6 лет, негативный тест")
    void shouldCreditWithMoreYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearFuture();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorCard();
    }



    @Test
    @DisplayName("Оплата в кредит, незаполненное имя владельца карты, негативный тест")
    void shouldCreditWithEmptyOwner() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmptySpace();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }



    @Test
    @DisplayName("Оплата в кредит, неверное имя владельца карты, негативный тест")
    void shouldCreditWithErrorOwner() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getInvalidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }



    @Test
    @DisplayName("Оплата в кредит, незаполненный код безопасности карты, негативный тест")
    void shouldCreditWithEmptyCode() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getEmptySpace();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }



    @Test
    @DisplayName("Оплата в кредит, некорретный код безопасности карты, менее 3 цифр, негативный тест")
    void shouldCreditWithLittleCode() {
        var paymentPage = new PaymentPage();
        paymentPage.cardCredit();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getInvalidLittleCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }


}
