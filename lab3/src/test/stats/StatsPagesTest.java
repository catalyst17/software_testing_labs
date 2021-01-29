package stats;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public class StatsPagesTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.gecko.driver","/opt/WebDriver/bin/geckodriver");
        System.setProperty("webdriver.chrome.driver","/opt/WebDriver/bin/chromedriver");

        driver = new FirefoxDriver();
    }

    @BeforeEach
    void beforeEach() {
        driver.get("https://statcounter.com/demo/pages");
    }

    @Test
    void showsOnlyMatchingURLs() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        driver.findElement(By.xpath("//div[@id='add-filter']")).click();
        WebElement filtersPanel = driver.findElement(By.xpath("//div[@id='filters']"));

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='available-filters']//a[@id='more-filters-link']"))).click();

        WebElement filterByURL = filtersPanel.findElement(By.xpath("./div[@id='available-filters']//a[@data-value='url_match']/.."));
        driverWait.until(ExpectedConditions.elementToBeClickable(filterByURL)).click();

        WebElement inputURL = filtersPanel.findElement((By.xpath("./div[@id='active-filters']//input[@id='url_match']")));

        driverWait.until(ExpectedConditions.elementToBeClickable(inputURL)).sendKeys("guitar-online.com/en/tag");

        driverWait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//div[@id='paging-and-table']")).getAttribute("class").equals("reloading")
                    || driver.findElements(By.xpath("//div[@id='paging-and-table']/div[@class='loading-icon']")).size() != 0);

        driverWait.until((ExpectedCondition<Boolean>) driver -> !driver.findElement(By.xpath("//div[@id='paging-and-table']")).getAttribute("class").equals("reloading")
                    && driver.findElements(By.xpath("//div[@id='paging-and-table']/div[@class='loading-icon']")).size() == 0);

        List<WebElement> pagesURLs = driver.findElements(By
                .xpath("//div[@id='paging-and-table']//tbody//div[@class='iw iwurl']/span/wbr/.."));


        boolean onlyMatchingURLs = true;

        for (WebElement pageURL :
                pagesURLs) {
            if (!pageURL.getText().startsWith("guitar-online.com/en/tag")) {
                onlyMatchingURLs = false;
                break;
            }
        }

        assertTrue(onlyMatchingURLs);
    }

    @Test
    void upgradeIsDeniedInDemo() {
        driver.findElement(By.xpath("//div[@data-jq-dropdown='#upgrade-to-track-conversion-rate']")).click();
        driver.findElement(By.xpath("//div/a[text()='Upgrade to Unlock']")).click();

        assertEquals("Permission Denied", driver.findElement(By.xpath("//div[@data-type='warning']/../h2")).getText());
    }

    @Test
    void pageAnalysisShows6Charts() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        driver.findElement(By.xpath("//tbody//a[@class='page-analysis-link']")).click();

        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@id='time_series']//div[@class='chart-area']")));

        assertEquals(6, driver.findElements(By.xpath("//div[@id='time_series']//div[@class='chart-area']")).size());
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
