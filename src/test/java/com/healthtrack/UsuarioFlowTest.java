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

import io.github.bonigarcia.wdm.WebDriverManager;

class UsuarioFlowTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Descarga y configura automáticamente la versión correcta de ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Configuración de Chrome en modo headless
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--headless",
            "--no-sandbox",
            "--disable-dev-shm-usage"
        );
        driver = new ChromeDriver(options);
    }

    @Test
    void testActualizarPeso_EnUI() {
        // 1. Accede al login
        driver.get("http://localhost:8080/login");

        // 2. Realiza login
        driver.findElement(By.id("user")).sendKeys("ana");
        driver.findElement(By.id("pass")).sendKeys("secret");
        driver.findElement(By.id("btnLogin")).click();

        // 3. Navega a la sección de peso
        driver.findElement(By.id("navPeso")).click();

        // 4. Actualiza el peso
        WebElement pesoInput = driver.findElement(By.id("peso"));
        pesoInput.clear();
        pesoInput.sendKeys("75.3");
        driver.findElement(By.id("btnActualizar")).click();

        // 5. Verifica que se muestra el nuevo peso
        String displayed = driver.findElement(By.id("mostrarPeso")).getText();
        Assertions.assertTrue(displayed.contains("75.3"), "Debe mostrar 75.3 kg");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
