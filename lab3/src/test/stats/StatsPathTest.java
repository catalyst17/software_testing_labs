package stats;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatsPathTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.gecko.driver","/opt/WebDriver/bin/geckodriver");
        System.setProperty("webdriver.chrome.driver","/opt/WebDriver/bin/chromedriver");

        driver = new FirefoxDriver();

        driver.get("https://statcounter.com/demo/path");
    }

    @Test
    void defaultVisitorQuantityPerPageEqTwenty() {
        List<WebElement> visitors = driver.findElements(By.xpath("//div[@class='results visible-tags']/table"));

        assertTrue(visitors.size() <= 20);
    }

    @Test
    void tenVisitorsPerPage() {
        WebElement managementPanel = driver.findElement(By.xpath("//div[@id='results-per-page-trigger']/.."));
        managementPanel.findElement(By.xpath("./div[@id='results-per-page-trigger']")).click();

        managementPanel.findElement(By.xpath(".//a[text()='10 Per Page']")).click();

        List<WebElement> visitors = driver.findElements(By.xpath("//div[@class='results visible-tags']/table"));

        assertTrue(visitors.size() <= 10);
    }

    @Test
    void thirtyVisitorsPerPage() {
        WebElement managementPanel = driver.findElement(By.xpath("//div[@id='results-per-page-trigger']/.."));
        managementPanel.findElement(By.xpath("./div[@id='results-per-page-trigger']")).click();

        managementPanel.findElement(By.xpath(".//a[text()='30 Per Page']")).click();

        List<WebElement> visitors = driver.findElements(By.xpath("//div[@class='results visible-tags']/table"));

        assertTrue(visitors.size() <= 30);
    }

    @Test
    void fortyVisitorsPerPage() {
        WebElement managementPanel = driver.findElement(By.xpath("//div[@id='results-per-page-trigger']/.."));
        managementPanel.findElement(By.xpath("./div[@id='results-per-page-trigger']")).click();

        managementPanel.findElement(By.xpath(".//a[text()='40 Per Page']")).click();

        List<WebElement> visitors = driver.findElements(By.xpath("//div[@class='results visible-tags']/table"));

        assertTrue(visitors.size() <= 40);
    }

    @Test
    void fiftyVisitorsPerPage() {
        WebElement managementPanel = driver.findElement(By.xpath("//div[@id='results-per-page-trigger']/.."));
        managementPanel.findElement(By.xpath("./div[@id='results-per-page-trigger']")).click();

        managementPanel.findElement(By.xpath(".//a[text()='50 Per Page']")).click();

        List<WebElement> visitors = driver.findElements(By.xpath("//div[@class='results visible-tags']/table"));

        assertTrue(visitors.size() <= 50);
    }

    @Test
    void showsOnlyLinuxVisitors() {
        WebDriverWait driverWait = new WebDriverWait(driver, 10);

        driver.findElement(By.xpath("//div[@id='add-filter']")).click();
        WebElement filtersPanel = driver.findElement(By.xpath("//div[@id='filters']"));

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='available-filters']//a[@id='more-filters-link']"))).click();

        WebElement filterByOS = filtersPanel.findElement(By.xpath("./div[@id='available-filters']//a[@data-value='os']/.."));
        driverWait.until(ExpectedConditions.elementToBeClickable(filterByOS)).click();

        WebElement inputOS = filtersPanel.findElement((By.xpath("./div[@id='active-filters']//input[@id='os-filter']")));

        inputOS.sendKeys("Linux");

        driverWait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//div[@id='paging-and-table']")).getAttribute("class").equals("reloading")
                    || driver.findElements(By.xpath("//div[@id='paging-and-table']/div[@class='loading-icon']")).size() != 0);

        driverWait.until((ExpectedCondition<Boolean>) driver -> !driver.findElement(By.xpath("//div[@id='paging-and-table']")).getAttribute("class").equals("reloading")
                    && driver.findElements(By.xpath("//div[@id='paging-and-table']/div[@class='loading-icon']")).size() == 0);

        List<WebElement> visitors = driver.findElements(By.xpath("//div[@class='results visible-tags']/table"));

        boolean onlyLinux = true;

        for (WebElement visitor :
                visitors) {
            if (!visitor.findElement(By.xpath("./thead//span[@class='system-desc']")).getText().startsWith("Linux")) {
                onlyLinux = false;
                break;
            }
        }

        driver.get("https://statcounter.com/demo/path");

        assertTrue(onlyLinux);
    }

    @Test
    void showsOnlyRussianLinuxVisitors() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        driver.findElement(By.xpath("//div[@id='add-filter']")).click();
        WebElement filtersPanel = driver.findElement(By.xpath("//div[@id='filters']"));

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='available-filters']//a[@id='more-filters-link']"))).click();

        WebElement filterByOS = filtersPanel.findElement(By.xpath("./div[@id='available-filters']//a[@data-value='os']/.."));
        driverWait.until(ExpectedConditions.elementToBeClickable(filterByOS)).click();

        WebElement filterByCountry = filtersPanel.findElement(By.xpath("./div[@id='available-filters']//a[@data-value='country']/.."));
        driverWait.until(ExpectedConditions.elementToBeClickable(filterByCountry)).click();

        WebElement inputOS = filtersPanel.findElement((By.xpath("./div[@id='active-filters']//input[@id='os-filter']")));
        WebElement inputCountry = filtersPanel.findElement((By.xpath("./div[@id='active-filters']//input[@id='country-filter']")));

        inputOS.sendKeys("Linux");
        driverWait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//div[@id='paging-and-table']")).getAttribute("class").equals("reloading")
                || driver.findElements(By.xpath("//div[@id='paging-and-table']/div[@class='loading-icon']")).size() != 0);
        driverWait.until((ExpectedCondition<Boolean>) driver -> !driver.findElement(By.xpath("//div[@id='paging-and-table']")).getAttribute("class").equals("reloading")
                && driver.findElements(By.xpath("//div[@id='paging-and-table']/div[@class='loading-icon']")).size() == 0);

        inputCountry.sendKeys("Russian Federation");
        driverWait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(By.xpath("//div[@id='paging-and-table']")).getAttribute("class").equals("reloading")
                || driver.findElements(By.xpath("//div[@id='paging-and-table']/div[@class='loading-icon']")).size() != 0);
        driverWait.until((ExpectedCondition<Boolean>) driver -> !driver.findElement(By.xpath("//div[@id='paging-and-table']")).getAttribute("class").equals("reloading")
                && driver.findElements(By.xpath("//div[@id='paging-and-table']/div[@class='loading-icon']")).size() == 0);

        List<WebElement> visitors = driver.findElements(By.xpath("//div[@class='results visible-tags']/table"));

        boolean onlyLinux = true;

        for (WebElement visitor : visitors) {
            if (!(visitor.findElement(By.xpath("./thead//span[@class='system-desc']")).getText().startsWith("Linux")
                    && visitor.findElement(By.xpath("./thead//span[@class='ip-isp']")).getText().contains("Russian Federation"))) {
                onlyLinux = false;
                break;
            }
        }

        driver.get("https://statcounter.com/demo/path");

        assertTrue(onlyLinux);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
