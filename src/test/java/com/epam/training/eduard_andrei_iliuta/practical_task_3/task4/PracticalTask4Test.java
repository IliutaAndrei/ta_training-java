package com.epam.training.eduard_andrei_iliuta.practical_task_3.task4;

import com.epam.training.eduard_andrei_iliuta.practical_task_3.task4.pages.HomePage;
import com.epam.training.eduard_andrei_iliuta.practical_task_3.task4.pages.InsightsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PracticalTask4Test {
    private WebDriver webDriver;


    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        webDriver = new ChromeDriver(options);
    }

    @ParameterizedTest
    @CsvSource("2")
    public void matchTitles(int times) {
        InsightsPage insightsPage = new HomePage(webDriver)
                .openPage()
                .tryAcceptCookies()
                .clickInsights()
                .swipeCarouselRight(times);

        String articleTitle = insightsPage.getArticleTitle();

        String openedArticleTitle = insightsPage
                .clickReadMore()
                .getArticleTitle();

        System.out.println(articleTitle);
        System.out.println();
        System.out.println(openedArticleTitle);

        assertEquals(articleTitle, openedArticleTitle,
                "Titles doesn't match");
    }

    @AfterEach
    public final void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

}
