package com.epam.training.eduard_andrei_iliuta.practical_task_3.task3;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private static final String HOME_PAGE_URL = "https://www.epam.com/";
    private static final int WAIT_TIMEOUT_SECONDS = 10;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    @FindBy(css = "[role='dialog'][aria-label='Privacy']")
    private WebElement cookiesPrivacyDialog;

    @FindBy(css = "a.fat-links[href*='code-of-conduct']")
    private WebElement downloadLink;

    private final WebDriver webDriver;
    private final WebDriverWait wait;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
        PageFactory.initElements(webDriver, this);
    }

    public HomePage openPage() {
        webDriver.get(HOME_PAGE_URL);
        return this;
    }

    public HomePage tryAcceptCookies() {
        try {
            wait.until(ExpectedConditions.visibilityOf(acceptCookiesButton));
        } catch (TimeoutException e) {
            return this;
        }

        wait.until(driver -> {
            try {
                if (!acceptCookiesButton.isDisplayed() || !acceptCookiesButton.isEnabled()) {
                    return false;
                }

                acceptCookiesButton.click();
                return true;
            } catch (Exception e) {
                return false;
            }
        });

        wait.until(ExpectedConditions.invisibilityOf(cookiesPrivacyDialog));
        return this;
    }

    public void clickCodeOfEthicalConduct() {
        Actions actions = new Actions(webDriver);

        actions.scrollToElement(downloadLink).perform();
        wait.until(ExpectedConditions.elementToBeClickable(downloadLink)).click();
    }


}
