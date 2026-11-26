package com.epam.training.eduard_andrei_iliuta.practical_task_3.task1refactored;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DynamicLoadingPage {

    private static final String HOMEPAGE_URL = "https://the-internet.herokuapp.com/dynamic_loading/1";
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "#start > button")
    private WebElement startButton;

    @FindBy(css = "#loading")
    private WebElement loadingIndicator;

    @FindBy(css = "#finish > h4")
    private WebElement resultMessage;


    public DynamicLoadingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public DynamicLoadingPage openPage() {
        driver.get(HOMEPAGE_URL);

        return this;
    }

    public DynamicLoadingPage clickStart() {
        startButton.click();

        return this;
    }


    public String getResultText() {
        wait.until(ExpectedConditions.invisibilityOf(loadingIndicator));

        return wait
                .until(ExpectedConditions.visibilityOf(resultMessage))
                .getText();
    }
}
