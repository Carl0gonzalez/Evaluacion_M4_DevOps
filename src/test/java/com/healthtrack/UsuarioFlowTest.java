// src/test/java/com/healthtrack/UsuarioFlowTest.java
package com.healthtrack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class UsuarioFlowTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // 1) Apunta directamente a tu chromedriver local (versión 138.x)
        System.setProperty(
            "webdriver.chrome.driver",
            System.getProperty("user.dir") + "/chromedriver"
        );

        // 2) Opciones de Chrome en modo headless
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--headless",
            "--no-sandbox",
            "--disable-dev-shm-usage"
        );
        // (Opcional) si tienes varios binarios de Chrome:
        // options.setBinary("/usr/bin/google-chrome");

        driver = new ChromeDriver(options);
    }

    @Test
    void testActualizarPeso_EnUI() {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("user")).sendKeys("ana");
        driver.findElement(By.id("pass")).sendKeys("secret");
        driver.findElement(By.id("btnLogin")).click();
        driver.findElement(By.id("navPeso")).click();

        WebElement pesoInput = driver.findElement(By.id("peso"));
        pesoInput.clear();
        pesoInput.sendKeys("75.3");
        driver.findElement(By.id("btnActualizar")).click();

        String displayed = driver.findElement(By.id("mostrarPeso")).getText();
        Assertions.assertTrue(displayed.contains("75.3"), "Debe mostrar 75.3 kg");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
