package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netolody.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCard {

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
    @DisplayName("Покупка тура через оплату картой,позитивный тест")
    void shouldSuccessfullyPayWithValidUser() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankAccept();
        var expected = DataHelper.getStatusValidCard;
        var actual = SQLHelper.getPaymentInfo();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Оплата картой, невалидный номер карты, негативный тест")
    void shouldPayWithInvalidCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getInvalidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankReject();
        var expected = DataHelper.getStatusInvalidCard;
        var actual = SQLHelper.getPaymentInfo();
        assertEquals(expected, actual);
    }



    @Test
    @DisplayName("Оплата картой, пустое поле карты, негативный тест")
    void shouldPayWithEmptyCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getEmptySpace();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }

    @Test
    @DisplayName("Оплата картой, неверный номер карты[менее 16 цифр], негативный тест")
    void shouldPayWithErrorLessCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getErrorLessCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }



    @Test
    @DisplayName("Оплата картой, неверный номер карты, негативный тест")
    void shouldPayWithRandomCard() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getRandomCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.bankReject();
    }



    @Test
    @DisplayName("Пустое поле месяца срока действия карты, негативный тест")
    void shouldPayWithEmptyMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getEmptySpace();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }




    @Test
    @DisplayName("Оплата картой, неверный месяц срока действия карты, негативный тест")
    void shouldPayWithMoreMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getMonthMorePossible();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorCard();
    }



    @Test
    @DisplayName("Неверный формат месяца срока действия карты, негативный тест")
    void shouldPayWithErrorMonth() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getInvalidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }



    @Test
    @DisplayName("Пустое поле года срока действия карты, негативный тест")
    void shouldPayWithEmptyYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmptySpace();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }




    @Test
    @DisplayName("Неверный формат года срока действия карты, негативный тест")
    void shouldPayWithInvalidYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getInvalidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }



    @Test
    @DisplayName("Неверный год срока действия карты, ранее текущей даты, негативный тест")
    void shouldPayWithBeforeYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearPast();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorYearCard();
    }



    @Test
    @DisplayName("Неверный год срока действия карты, более 6 лет, негативный тест")
    void shouldPayWithMoreYear() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getYearFuture();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorCard();
    }



    @Test
    @DisplayName("Пустое поле владельца карты, негативный тест")
    void shouldPayWithEmptyOwner() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmptySpace();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }





    @Test
    @DisplayName("Неверное имя владельца карты, негативный тест")
    void shouldPayWithErrorOwner() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getInvalidOwner();
        var codeCard = DataHelper.getValidCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }



    @Test
    @DisplayName("Незаполненное поле кода безопасности карты")
    void shouldPayWithEmptyCode() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getEmptySpace();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.emptyField();
    }




    @Test
    @DisplayName("Неверный код безопасности карты менее 3 цифр, негативный тест")
    void shouldPayWithLittleCode() {
        var paymentPage = new PaymentPage();
        paymentPage.cardPayment();
        var cardNumber = DataHelper.getValidCard();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var codeCard = DataHelper.getInvalidLittleCode();
        paymentPage.fillForm(cardNumber, month, year, owner, codeCard);
        paymentPage.errorField();
    }




}