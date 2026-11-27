package com.epam.training.eduard_andrei_iliuta.practical_task_3.task3;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class PracticalTask3Test {
    private WebDriver webDriver;
    private String downloadDir;

    @BeforeEach
    public void setup() {
        downloadDir = new File(System.getProperty("user.dir"), "downloads")
                .getAbsolutePath();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setExperimentalOption("prefs", Map.of(
                "download.default_directory", downloadDir,
                "download.prompt_for_download", false,
                "plugins.always_open_pdf_externally", true
        ));

        webDriver = new ChromeDriver(options);
    }

    @ParameterizedTest(name = "Check complete download of pdf")
    @CsvSource("Code_of_Ethical_Conduct.pdf")
    public void downloadCompleted(String fileName) {
        File previousFile = new File(downloadDir, fileName);

        assertTrue(!previousFile.exists() || previousFile.delete(),
                "Couldn't delete existing file");

        new HomePage(webDriver)
                .openPage()
                .tryAcceptCookies()
                .clickCodeOfEthicalConduct();

        assertTrue(waitForDownload(fileName),
                "File download didn't complete");
    }

    @AfterEach
    public final void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private boolean waitForDownload(String fileName) {
        File targetFile = new File(downloadDir, fileName);

        try {
            new FluentWait<>(targetFile)
                    .withTimeout(Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(file -> {
                        File[] partialFiles = new File(downloadDir).listFiles(
                                (folder, name) ->
                                        name.endsWith(".crdownload") || name.endsWith(".tmp")
                        );

                        return file.isFile() &&
                               file.length() > 0 &&
                               partialFiles != null &&
                               partialFiles.length == 0;
                    });

            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
