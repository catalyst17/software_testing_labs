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

public class StatsSummaryTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.gecko.driver","/opt/WebDriver/bin/geckodriver");
        System.setProperty("webdriver.chrome.driver","/opt/WebDriver/bin/chromedriver");

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

    @Test
    void summaryStatsHelpWorks() {
        WebElement helpIcon = driver.findElement(By.xpath("//img[@src='/images/help_icon.svg']"));

        if (helpIcon.getAttribute("data-jq-dropdown") == null)
            helpIcon.click();

        boolean helpInDropdown = driver.findElement(By.xpath("//img[@src='/images/help_icon.svg']")).getAttribute("data-jq-dropdown").equals("#summary-chart-help");

        helpIcon.click();

        assertTrue(helpInDropdown);
    }

    @Test
    void otherLanguagesStillNotReady() {
        String oldH1 = driver.findElement(By.xpath("//body//h1/span")).getText();

        WebElement langChooser = driver.findElement(By.xpath("//footer//div[@id='language-select']"));
        langChooser.click();

        driver.findElement(By.xpath("//footer//div[@id='language-select-panel']//a")).click();

        String newH1 = driver.findElement(By.xpath("//body//h1/span")).getText();

        boolean notTranslated = oldH1.equals(newH1);

        driver.get("https://statcounter.com/demo/summary/");

        assertTrue(notTranslated);
    }

    @Test
    void editProfileIsDeniedInDemo() {
        driver.findElement(By.xpath("//span[@data-jq-dropdown='#user-menu']")).click();
        driver.findElement(By.xpath("//li[@id='menu-edit-profile']/a")).click();

        String message = driver.findElement(By.xpath("//div[@data-type='warning']/../h2")).getText();

        driver.get("https://statcounter.com/demo/summary/");

        assertEquals("Permission Denied", message);
    }

    @Test
    void viewAllUsersIsDeniedInDemo() {
        driver.findElement(By.xpath("//span[@data-jq-dropdown='#user-menu']")).click();
        driver.findElement(By.xpath("//li[@id='menu-view-all-users']/a")).click();

        String message = driver.findElement(By.xpath("//div[@data-type='warning']/../h2")).getText();

        driver.get("https://statcounter.com/demo/summary/");

        assertEquals("Permission Denied", message);
    }

    @Test
    void addUserIsDeniedInDemo() {
        driver.findElement(By.xpath("//span[@data-jq-dropdown='#user-menu']")).click();
        driver.findElement(By.xpath("//li[@id='menu-add-user']/a")).click();

        String message = driver.findElement(By.xpath("//div[@data-type='warning']/../h2")).getText();

        driver.get("https://statcounter.com/demo/summary/");

        assertEquals("Permission Denied", message);
    }

    @Test
    void logOutSendsToSeeYouSoonPage() {
        WebDriverWait driverWait = new WebDriverWait(driver, 3);

        driver.findElement(By.xpath("//span[@data-jq-dropdown='#user-menu']")).click();
        driver.findElement(By.xpath("//li[@id='menu-log-out']/a")).click();

        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form[@id='login-form']")));

        String byePhrase = driver.findElement(By.xpath("//header/div[@id='header-content']//h3")).getText();

        driver.get("https://statcounter.com/demo/summary/");

        assertEquals("See you soon!", byePhrase);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
