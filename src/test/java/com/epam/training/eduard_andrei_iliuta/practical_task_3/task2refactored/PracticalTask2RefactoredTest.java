package com.epam.training.eduard_andrei_iliuta.practical_task_3.task2refactored;

import com.epam.training.eduard_andrei_iliuta.practical_task_3.task2refactored.pages.HomePage;
import com.epam.training.eduard_andrei_iliuta.practical_task_3.task2refactored.pages.JobsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PracticalTask2RefactoredTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        webDriver = new ChromeDriver(options);
    }

    @ParameterizedTest
    @CsvSource("All available countries, Java")
    public void containsSearchedKeyword(String location, String keyword) {

        JobsPage jobsPage = new HomePage(webDriver)
                .openPage()
                .tryAcceptCookies()
                .clickCareers()
                .clickStartSearch()
                .tryAcceptCookiesOnJobsPage()
                .clickRemoteFilter()
                .selectLocation(location)
                .searchByKeyword(keyword)
                .clickAndExpandFirstResult();

        String resultDescription = jobsPage.getResultDescription();

        assertTrue(resultDescription.contains(keyword),
                "Description doesn't contain searched keyword");

        jobsPage.clickApply();
    }

    @AfterEach
    public final void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
