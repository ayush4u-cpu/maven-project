package com.sunbeam;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT )
public class LoginTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginSuccess() {
        // Dynamic port prevents hardcoded localhost conflicts
        driver.get("http://localhost:" + port + "/index.html");

        System.out.println(driver.getTitle());
        
        // Fill form
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("admin");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("admin");

        // Click login button
        driver.findElement(By.cssSelector("button")).click();

        // Explicitly wait for status text instead of forcing thread sleep
        WebElement statusElement = driver.findElement(By.id("status"));
        
        String statusText = statusElement.getText();
        System.out.println("Login Result: " + statusText);

        // Assert condition replaces standard console logs and manual errors
        assertEquals("Login successful", statusText);
        System.out.println("Selenium Test Passed");
    }
}
