package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BillPayPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By billPayLink = By.linkText("Bill Pay");
    private By payeeName = By.name("payee.name");
    private By address = By.name("payee.address.street");
    private By city = By.name("payee.address.city");
    private By state = By.name("payee.address.state");
    private By zipCode = By.name("payee.address.zipCode");
    private By phone = By.name("payee.phoneNumber");
    private By accountNumber = By.name("payee.accountNumber");
    private By verifyAccountNumber = By.name("verifyAccount");
    private By amount = By.name("amount");
    private By fromAccountDropdown = By.name("fromAccountId");
    private By sendPaymentButton = By.cssSelector("input[value='Send Payment']");
    private By resultDiv = By.id("billpayResult");
    private By mismatchError = By.id("validationModel-verifyAccount-mismatch");

    public BillPayPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToBillPay() {
        wait.until(ExpectedConditions.elementToBeClickable(billPayLink)).click();
    }

    public void fillBillPayForm(String payeeNameVal, String addr, String cty, String st,
                                 String zip, String ph, String acctNum, String verifyAcctNum,
                                 String amt) {
        WebElement pn = wait.until(ExpectedConditions.visibilityOfElementLocated(payeeName));
        pn.clear(); pn.sendKeys(payeeNameVal);

        WebElement a = driver.findElement(address);
        a.clear(); a.sendKeys(addr);

        WebElement c = driver.findElement(city);
        c.clear(); c.sendKeys(cty);

        WebElement s = driver.findElement(state);
        s.clear(); s.sendKeys(st);

        WebElement z = driver.findElement(zipCode);
        z.clear(); z.sendKeys(zip);

        WebElement p = driver.findElement(phone);
        p.clear(); p.sendKeys(ph);

        WebElement an = driver.findElement(accountNumber);
        an.clear(); an.sendKeys(acctNum);

        WebElement van = driver.findElement(verifyAccountNumber);
        van.clear(); van.sendKeys(verifyAcctNum);

        WebElement amt1 = driver.findElement(amount);
        amt1.clear(); amt1.sendKeys(amt);
    }

    public void clickSendPayment() {
        driver.findElement(sendPaymentButton).click();
    }

    public boolean isPaymentSuccessful() {
        try {
            WebElement result = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(resultDiv));
            return result.getText().contains("was successful");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAccountMismatchErrorShown() {
        try {
            WebElement error = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(mismatchError));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}