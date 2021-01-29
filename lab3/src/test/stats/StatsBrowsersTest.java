package stats;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatsBrowsersTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.gecko.driver","/opt/WebDriver/bin/geckodriver");
        System.setProperty("webdriver.chrome.driver","/opt/WebDriver/bin/chromedriver");

        driver = new FirefoxDriver();
    }

    @BeforeEach
    void beforeEach() {
        driver.get("https://statcounter.com/demo/browser");
    }

    @Test
    void chromeHitsEqSumOfItsVersionsHits() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Chrome']//div[@class='hits']/a")).getText();
        int allHits = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)), sum = 0;

        driver.findElement(By.xpath("//div[text()='Chrome ']")).click();

        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='version Chrome']//div[@class='version-hits']/a")));

        for (WebElement version : driver.findElements(By.xpath("//div[@class='version Chrome']//div[@class='version-hits']/a"))) {
            sum += Integer.parseInt(version.getText().substring(0, version.getText().indexOf("Page") - 1));
        }

        assertEquals(allHits, sum);
    }

    @Test
    void firefoxHitsEqSumOfItsVersionsHits() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Firefox']//div[@class='hits']/a")).getText();
        int allHits = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)), sum = 0;

        driver.findElement(By.xpath("//div[text()='Firefox ']")).click();

        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='version Firefox']//div[@class='version-hits']/a")));

        for (WebElement version : driver.findElements(By.xpath("//div[@class='version Firefox']//div[@class='version-hits']/a"))) {
            sum += Integer.parseInt(version.getText().substring(0, version.getText().indexOf("Page") - 1));
        }

        assertEquals(allHits, sum);
    }

    @Test
    void mobileBrowsersHitsEqSumOfItsVersionsHits() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Mobile_Browsers']//div[@class='hits']/a")).getText();
        int allHits = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)), sum = 0;

        driver.findElement(By.xpath("//div[text()='Mobile Browsers ']")).click();

        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='version Mobile_Browsers']//div[@class='version-hits']/a")));

        for (WebElement version : driver.findElements(By.xpath("//div[@class='version Mobile_Browsers']//div[@class='version-hits']/a"))) {
            sum += Integer.parseInt(version.getText().substring(0, version.getText().indexOf("Page") - 1));
        }

        assertEquals(allHits, sum);
    }

    @Test
    void chromeHitsIncreaseWithDateRangeIncreasing() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Chrome']//div[@class='hits']/a")).getText();
        int defaultPeriodHitsQuantity = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1));

        driver.findElement(By.xpath("//div[@id='date-range-trigger']")).click();
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ranges']//li[@data-range-key='Last 30 Days']"))).click();
        driver.findElement(By.xpath("//div[@class='ranges']//button[text()='Apply']")).click();

        hits = driver.findElement(By.xpath("//div[@class='main-stat Chrome']//div[@class='hits']/a")).getText();

        assertTrue(defaultPeriodHitsQuantity <= Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)));
    }

    @Test
    void firefoxHitsIncreaseWithDateRangeIncreasing() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Firefox']//div[@class='hits']/a")).getText();
        int defaultPeriodHitsQuantity = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1));

        driver.findElement(By.xpath("//div[@id='date-range-trigger']")).click();
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ranges']//li[@data-range-key='Last 30 Days']"))).click();
        driver.findElement(By.xpath("//div[@class='ranges']//button[text()='Apply']")).click();

        hits = driver.findElement(By.xpath("//div[@class='main-stat Firefox']//div[@class='hits']/a")).getText();
        assertTrue(defaultPeriodHitsQuantity <= Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)));
    }

    @Test
    void mobileBrowsersHitsIncreaseWithDateRangeIncreasing() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        String hits = driver.findElement(By.xpath("//div[@class='main-stat Mobile_Browsers']//div[@class='hits']/a")).getText();
        int defaultPeriodHitsQuantity = Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1));

        driver.findElement(By.xpath("//div[@id='date-range-trigger']")).click();
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ranges']//li[@data-range-key='Last 30 Days']"))).click();
        driver.findElement(By.xpath("//div[@class='ranges']//button[text()='Apply']")).click();

        hits = driver.findElement(By.xpath("//div[@class='main-stat Mobile_Browsers']//div[@class='hits']/a")).getText();
        assertTrue(defaultPeriodHitsQuantity <= Integer.parseInt(hits.substring(0, hits.indexOf("Page") - 1)));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
