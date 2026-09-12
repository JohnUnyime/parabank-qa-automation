package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RequestLoanPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By requestLoanLink = By.linkText("Request Loan");
    private By loanAmountField = By.id("amount");
    private By downPaymentField = By.id("downPayment");
    private By fromAccountDropdown = By.id("fromAccountId");
    private By applyNowButton = By.cssSelector("input[value='Apply Now']");
    private By loanStatus = By.id("loanStatus");
    private By approvedDiv = By.id("loanRequestApproved");
    private By deniedDiv = By.id("loanRequestDenied");

    public RequestLoanPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToRequestLoan() {
        wait.until(ExpectedConditions.elementToBeClickable(requestLoanLink)).click();
    }

    public void applyForLoan(String loanAmount, String downPayment) {
        WebElement amt = wait.until(ExpectedConditions.visibilityOfElementLocated(loanAmountField));
        amt.clear();
        amt.sendKeys(loanAmount);

        WebElement dp = driver.findElement(downPaymentField);
        dp.clear();
        dp.sendKeys(downPayment);

        driver.findElement(applyNowButton).click();
    }

    public String getLoanStatus() {
        WebElement status = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(loanStatus));
        return status.getText().trim();
    }

    public boolean isApproved() {
        return "Approved".equalsIgnoreCase(getLoanStatus());
    }
}