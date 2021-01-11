package stats;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatsPlatformsTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.gecko.driver","/opt/WebDriver/bin/geckodriver");
        System.setProperty("webdriver.chrome.driver","/opt/WebDriver/bin/chromedriver");

        driver = new FirefoxDriver();
    }

    @BeforeEach
    void beforeEach() {
        driver.get("https://statcounter.com/demo/platform");
    }

    @Test
    void desktopHitsIncreaseWithDateRangeIncreasing() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Desktop']//div[@class='hits']/a")).getText();
        int defaultPeriodHitsQuantity = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1));

        driver.findElement(By.xpath("//div[@id='date-range-trigger']")).click();
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ranges']//li[@data-range-key='Last 30 Days']"))).click();
        driver.findElement(By.xpath("//div[@class='ranges']//button[text()='Apply']")).click();

        hits = driver.findElement(By.xpath("//div[@class='main-stat Desktop']//div[@class='hits']/a")).getText();

        assertTrue(defaultPeriodHitsQuantity <= Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)));
    }

    @Test
    void mobileHitsIncreaseWithDateRangeIncreasing() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Mobile']//div[@class='hits']/a")).getText();
        int defaultPeriodHitsQuantity = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1));

        driver.findElement(By.xpath("//div[@id='date-range-trigger']")).click();
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ranges']//li[@data-range-key='Last 30 Days']"))).click();
        driver.findElement(By.xpath("//div[@class='ranges']//button[text()='Apply']")).click();

        hits = driver.findElement(By.xpath("//div[@class='main-stat Mobile']//div[@class='hits']/a")).getText();
        assertTrue(defaultPeriodHitsQuantity <= Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)));
    }

    @Test
    void unknownPlatformHitsIncreaseWithDateRangeIncreasing() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Unknown']//div[@class='hits']/a")).getText();
        int defaultPeriodHitsQuantity = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1));

        driver.findElement(By.xpath("//div[@id='date-range-trigger']")).click();
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ranges']//li[@data-range-key='Last 30 Days']"))).click();
        driver.findElement(By.xpath("//div[@class='ranges']//button[text()='Apply']")).click();

        hits = driver.findElement(By.xpath("//div[@class='main-stat Unknown']//div[@class='hits']/a")).getText();
        assertTrue(defaultPeriodHitsQuantity <= Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
