package com.epam.training.eduard_andrei_iliuta.practical_task_1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PracticalTask1Test {
    private static final String PAGE_URL = "https://the-internet.herokuapp.com/dynamic_loading/1";
    private static final By START_BUTTON = By.cssSelector("#start > button");
    private static final By LOADING_INDICATOR = By.cssSelector("#loading");
    private static final By RESULT_MESSAGE = By.cssSelector("#finish > h4");

    private WebDriver webDriver;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        webDriver = new ChromeDriver(options);
        webDriver.get(PAGE_URL);
    }

    @Test
    public void displayHelloWorld() {
        WebElement startButton = webDriver.findElement(START_BUTTON);
        startButton.click();

        Wait<WebDriver> wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADING_INDICATOR));
        WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(RESULT_MESSAGE));

        String actualText = resultElement.getText();
        assertEquals("Hello World!", actualText);
    }

    @AfterEach
    public final void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
