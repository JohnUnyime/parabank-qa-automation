package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By registerLink = By.linkText("Register");
    private By firstName = By.id("customer.firstName");
    private By lastName = By.id("customer.lastName");
    private By address = By.id("customer.address.street");
    private By city = By.id("customer.address.city");
    private By state = By.id("customer.address.state");
    private By zipCode = By.id("customer.address.zipCode");
    private By phone = By.id("customer.phoneNumber");
    private By ssn = By.id("customer.ssn");
    private By username = By.id("customer.username");
    private By password = By.id("customer.password");
    private By confirmPassword = By.id("repeatedPassword");
    private By registerButton = By.cssSelector("input[value='Register']");
    private By errorMessage = By.cssSelector("span.error, p.error");
    private By pageBody = By.tagName("body");

    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToRegisterPage() {
        driver.findElement(registerLink).click();
    }

    public void fillRegistrationForm(String fname, String lname, String addr, String cty,
                                      String st, String zip, String ph, String socialSN,
                                      String uname, String pwd, String confirmPwd) {
        WebElement fn = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        fn.clear();
        fn.sendKeys(fname);

        WebElement ln = driver.findElement(lastName);
        ln.clear();
        ln.sendKeys(lname);

        WebElement addr1 = driver.findElement(address);
        addr1.clear();
        addr1.sendKeys(addr);

        WebElement cty1 = driver.findElement(city);
        cty1.clear();
        cty1.sendKeys(cty);

        WebElement st1 = driver.findElement(state);
        st1.clear();
        st1.sendKeys(st);

        WebElement zip1 = driver.findElement(zipCode);
        zip1.clear();
        zip1.sendKeys(zip);

        WebElement ph1 = driver.findElement(phone);
        ph1.clear();
        ph1.sendKeys(ph);

        WebElement ssn1 = driver.findElement(ssn);
        ssn1.clear();
        ssn1.sendKeys(socialSN);

        WebElement un1 = driver.findElement(username);
        un1.clear();
        un1.sendKeys(uname);

        WebElement pw1 = driver.findElement(password);
        pw1.clear();
        pw1.sendKeys(pwd);

        WebElement cpw1 = driver.findElement(confirmPassword);
        cpw1.clear();
        cpw1.sendKeys(confirmPwd);
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public String getErrorMessage() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return error.getText();
    }

    public boolean isRegistrationSuccessful() {
        try {
            wait.until(d -> d.findElement(pageBody)
                    .getText()
                    .contains("Your account was created successfully"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}