package com.epam.training.eduard_andrei_iliuta.practical_task_3.task1refactored;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PracticalTask1RefactoredTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        webDriver = new ChromeDriver(options);
    }

    @ParameterizedTest
    @CsvSource("Hello World!")
    public void displayHelloWorld(String expectedText) {
        String actualText = new DynamicLoadingPage(webDriver)
                .openPage()
                .clickStart()
                .getResultText();

        assertEquals(expectedText, actualText, "Incorrect text");
    }

    @AfterEach
    public final void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
