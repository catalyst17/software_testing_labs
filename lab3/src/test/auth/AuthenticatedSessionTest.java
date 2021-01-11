package auth;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

public class AuthenticatedSessionTest {
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.gecko.driver","/opt/WebDriver/bin/geckodriver");
        System.setProperty("webdriver.chrome.driver","/opt/WebDriver/bin/chromedriver");

        driver = new FirefoxDriver();
        driver.manage().window().setSize(new Dimension(1920, 1054));
        driver.get("https://statcounter.com");

        WebDriverWait driverWait = new WebDriverWait(driver, 5);
        String logInLink = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//nav[@id='nav-main']//a[text()='Log In']"))).getAttribute("href");
        driver.navigate().to(logInLink);

        WebElement inputUsername = driver.findElement(By.xpath("//input[@id='username']"));
        WebElement inputPassword = driver.findElement(By.xpath("//input[@id='password']"));
        WebElement submitBtn = driver.findElement(By.xpath("//input[@value='Log In']"));

        inputUsername.sendKeys("arsen_itmo");
        inputPassword.sendKeys("ShjSXc8MCsVi5Cm");

        submitBtn.submit();

        driverWait.until((ExpectedCondition<Boolean>) d -> d.getTitle().toLowerCase().equals("statcounter projects"));
    }

    @BeforeEach
    void beforeEach() {
        driver.get("https://statcounter.com");
    }

    @Test
    void loggedIn() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);
        String username = driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@data-jq-dropdown='#user-menu']/strong"))).getText();

        assertEquals("arsen_itmo", username);
    }

    @Test
    void editProfileDateFormat() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        driver.findElement(By.xpath("//span[@data-jq-dropdown='#user-menu']")).click();
        driver.findElement(By.xpath("//li[@id='menu-edit-profile']/a")).click();

        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='datetype-3']/.."))).click();

        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Update Your Profile']"))).click();

        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='datetype-3']")));

        assertEquals("true", driver.findElement(By.xpath("//input[@id='datetype-3']")).getAttribute("checked"));
    }

    @Test
    void ifProjectsExistOpenOne() {
        WebDriverWait driverWait = new WebDriverWait(driver, 5);

        List<WebElement> projects = driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody/tr")));

        if (projects.size() > 0) {
            projects.get(0).findElement(By.xpath("./td/a")).click();
        }

        driverWait.until((ExpectedCondition<Boolean>) d -> d.getTitle().toLowerCase().startsWith("summary stats"));

        assertTrue(driver.getCurrentUrl().endsWith("summary/"));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
