package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Objects;

public class YouTrackUITests {
    WebDriver driver;
    WebDriverWait wait;

    String url = "http://193.233.193.42:9091/";
    String username = "ivanov_vitaliy";
    String password = "v3~mpvfps/$LQ43";

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(url);
    }
    public WebElement waitForElementByXPath(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
    public WebElement waitForElementByClassName(String className) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(className)));
    }

    @Test
    void login() {
        loginWithPassword();
        WebElement element = waitForElementByXPath("//img[@alt='User avatar']");
        assertTrue(element.isDisplayed());
    }

    void loginWithPassword() {
        waitForElementByXPath("//input[@id='username']").sendKeys(username);
        waitForElementByXPath(("//input[@id='password']")).sendKeys(password);
        waitForElementByXPath("//button[@type='submit']").click();
    }

    @Test
    void logout() {
        loginWithPassword();
        waitForElementByXPath(String.format("//div[@title='%s']//button", username)).click();
        waitForElementByXPath("//button[@data-test='ring-link ring-list-link ring-list-item' and text()='Выйти']").click();
        WebElement element = waitForElementByXPath("//input[@id='username']");
        assertTrue(element.isDisplayed());
    }

    @Test
    void switchBoard() {
        loginWithPassword();
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete")
        );
        waitForElementByXPath("//div[@id='breadcrumbs-portal-target']//button[span[span[text()='…']]]").click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("list-item-17-60xp"))).click();
        WebElement input = waitForElementByXPath("//input");
        input.click();
        input.clear();
        input.sendKeys(username);
    }
    @Test
    void openCreateWidgetMenu() {
        loginWithPassword();
        wait.until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete")
        );
        waitForElementByXPath("//button[@data-test='add-widget-button']").click();
        waitForElementByXPath("//span[@title='Issue List']").click();
        WebElement element = waitForElementByXPath("//span[@aria-label='Список задач']");
        assertTrue(element.isDisplayed());
    }

    @Test
    void openNotifications() {
        loginWithPassword();
        wait.until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete")
        );
        waitForElementByXPath("//button[@data-test='ring-link navigation-notifications-unread-button']").click();
        WebElement element = waitForElementByXPath("//span[@data-test='notifications-feed-title']");
        assertTrue(element.isDisplayed());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
