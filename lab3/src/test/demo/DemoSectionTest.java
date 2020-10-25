package demo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class DemoSectionTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.gecko.driver","/opt/WebDriver/bin/geckodriver");

        driver = new FirefoxDriver();

        driver.get("https://statcounter.com/");
        driver.manage().window().setSize(new Dimension(1920, 1054));
        driver.findElement(By.xpath("//li[@id='nav-demo']/a")).click();
    }


    @Test
    void savingDefaultChartSettingsWorks() {
        WebElement returningVisitsCheckbox = driver.findElement(By.xpath("//input[@id='show_returningvisits']"));
        WebElement uniqueVisitsCheckbox = driver.findElement(By.xpath("//input[@id='show_uniquevisits']"));
        WebElement pageViewsCheckbox = driver.findElement(By.xpath("//input[@id='show_pageviews']"));

        if (returningVisitsCheckbox.isSelected())
            returningVisitsCheckbox.click();
        if (uniqueVisitsCheckbox.isSelected())
            uniqueVisitsCheckbox.click();
        if (pageViewsCheckbox.isSelected())
            pageViewsCheckbox.click();

        WebDriverWait driverWait = new WebDriverWait(driver, 3);

        WebElement saveAsDefaultCheckbox = driver.findElement(By.xpath("//input[@id='save_as_default']"));

        driverWait.until((ExpectedCondition<Boolean>) driver -> saveAsDefaultCheckbox.getAttribute("disabled") == null);

        saveAsDefaultCheckbox.click();

        driverWait.until((ExpectedCondition<Boolean>) driver -> saveAsDefaultCheckbox.getAttribute("disabled") != null);

        driver.get("https://statcounter.com/demo/summary/");

        boolean nothingIsSelected = !driver.findElement(By.xpath("//input[@id='show_uniquevisits']")).isSelected() &&
                !driver.findElement(By.xpath("//input[@id='show_returningvisits']")).isSelected() &&
                !driver.findElement(By.xpath("//input[@id='show_pageviews']")).isSelected();

        driver.findElement(By.xpath("//input[@id='show_pageviews']")).click();
        driver.findElement(By.xpath("//input[@id='show_uniquevisits']")).click();
        driver.findElement(By.xpath("//input[@id='show_returningvisits']")).click();
        driver.findElement(By.xpath("//input[@id='save_as_default']")).click();

        assertTrue(nothingIsSelected);
    }

    @Test
    void yAxisOnChartWorks() {
        List<WebElement> originalStatsContainer = driver.findElements(By.xpath("//div[@id='stats-graph']/*"));

        if (driver.findElements(By.xpath("//div[@class='summary-chart-options']")).size() == 0)
            driver.findElement(By.xpath("//div[@id='summary-chart-options-trigger']")).click();

        if (driver.findElement(By.xpath("//input[@id='direct_labels']")).isSelected())
            driver.findElement(By.xpath("//input[@id='direct_labels']")).click();

        List<WebElement> newStatsContainer = driver.findElements(By.xpath("//div[@id='stats-graph']/*"));

        driver.findElement(By.xpath("//input[@id='direct_labels']")).click();
        driver.findElement(By.xpath("//div[@id='summary-chart-options-trigger']")).click();

        assertNotSame(newStatsContainer.get(1), originalStatsContainer.get(1));
//        assertFalse(driver.findElements(By.xpath("//g[@class='position-translate']/g[@class='y axis']")).size() > 0);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
