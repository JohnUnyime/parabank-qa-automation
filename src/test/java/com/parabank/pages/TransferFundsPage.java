package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransferFundsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By transferLink = By.linkText("Transfer Funds");
    private By amountField = By.id("amount");
    private By fromAccountDropdown = By.id("fromAccountId");
    private By toAccountDropdown = By.id("toAccountId");
    private By transferButton = By.cssSelector("input[value='Transfer']");
    private By resultDiv = By.id("showResult");

    public TransferFundsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToTransferFunds() {
        wait.until(ExpectedConditions.elementToBeClickable(transferLink)).click();
    }

    public void selectFromAccountByIndex(int index) {
        wait.until(d -> new Select(d.findElement(fromAccountDropdown)).getOptions().size() > 1);
        new Select(driver.findElement(fromAccountDropdown)).selectByIndex(index);
    }

    public void selectToAccountByIndex(int index) {
        wait.until(d -> new Select(d.findElement(toAccountDropdown)).getOptions().size() > 1);
        new Select(driver.findElement(toAccountDropdown)).selectByIndex(index);
    }

    public void enterAmount(String amount) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(amountField));
        field.clear();
        field.sendKeys(amount);
    }

    public void clickTransfer() {
        driver.findElement(transferButton).click();
    }

    public boolean isTransferSuccessful() {
        try {
            WebElement result = new WebDriverWait(driver, Duration.ofSeconds(25))
                    .until(ExpectedConditions.visibilityOfElementLocated(resultDiv));
            return result.getText().contains("Transfer Complete");
        } catch (Exception e) {
            return false;
        }
    }
}