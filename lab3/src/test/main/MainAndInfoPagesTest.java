package main;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainAndInfoPagesTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.gecko.driver","/opt/WebDriver/bin/geckodriver");

        driver = new FirefoxDriver();

        driver.get("https://statcounter.com/");
    }

    @Test
    void numberOfTIFFEquals3() {
        List<WebElement> elements = driver.findElements(By.xpath("//*[text()='Try it for FREE!']"));

        assertEquals(3, elements.size());
    }

    @Test
    void afterTIFFClickThereIsSignUpPage() {
        WebElement element = driver.findElement(By.xpath("//*[text()='Try it for FREE!']"));
        element.click();

        String title = driver.getTitle();
        driver.navigate().back();

        assertTrue(title.contains("Sign Up"));
    }

    @Test
    void afterPricingClickThereIsTwoPlansAvailable() {
        WebElement element = driver.findElement(By.xpath("//*[text()='Pricing']"));
        driver.navigate().to(element.getAttribute("href"));

        List<WebElement> pricingPlans = driver.findElements(By.xpath("//*[@id='plans-outer']/div"));

        driver.navigate().back();

        assertEquals(2, pricingPlans.size());
    }

    @Test
    void afterFeaturesClickThereIsOnlyStaticInfoAnd3ExternalLinks() {
        WebElement element = driver.findElement(By.xpath("//*[text()='Features']"));
        driver.navigate().to(element.getAttribute("href"));

        List<WebElement> bodyElements = driver.findElements(By.xpath("//body/*"));
        List<String> allLinks = new ArrayList<>();

        int i = 0;
        while (!bodyElements.get(i).getTagName().equals("footer")) {
            if (!bodyElements.get(i).getTagName().equals("header")) {
                for (WebElement aTag : bodyElements.get(i).findElements(By.xpath(".//a")))
                    if (!aTag.getAttribute("href").startsWith("https://statcounter.com"))
                        allLinks.add(aTag.getAttribute("href"));
            }
            i++;
        }

        driver.navigate().back();

        assertEquals(3, allLinks.size());
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
