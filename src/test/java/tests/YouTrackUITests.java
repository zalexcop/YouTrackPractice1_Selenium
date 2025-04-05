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
        WebElement element = waitForElementByXPath("/html/body/div[1]/div[1]/div[2]/div[2]/div[5]/span/div/button");
        assertTrue(element.isDisplayed());
    }

    void loginWithPassword() {
        waitForElementByXPath("//input[@id='username']").sendKeys(username);
        waitForElementByXPath(("//input[@id='password']")).sendKeys(password);
        waitForElementByXPath("/html/body/div[2]/div[1]/div/div[2]/form/div[2]/div[2]/button/span/ng-transclude/span").click();
    }

    @Test
    void logout() {
        loginWithPassword();
        waitForElementByXPath("/html/body/div[1]/div[1]/div[2]/div[2]/div[5]/span/div/button/span").click();
        waitForElementByXPath("/html/body/div[3]/div/div/div/div/div/div/div/div[5]/div/button").click();
        WebElement element = waitForElementByXPath("//input[@id='username']");
        assertTrue(element.isDisplayed());
    }

    @Test
    void switchBoard() {
        loginWithPassword();
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete")
        );
        waitForElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div/div[1]/div[1]/div/div[2]/span[2]/span[1]/button").click();
        waitForElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]/div/div/div/div[4]/div/span/div/div[1]/a").click();
        WebElement element = waitForElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div/div[2]/main/div[1]/div[2]/h1");
        assertTrue(element.isDisplayed());
    }
    @Test
    void openCreateWidgetMenu() {
        loginWithPassword();
        wait.until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete")
        );
        waitForElementByXPath("/html/body/div[1]/div[2]/div[3]/div/div/div[1]/div[2]/div[2]/div/span/button").click();
        waitForElementByXPath("/html/body/div[4]/div/div/div[2]/div[1]/div/div/div/div[2]/div/div/button/div/span[1]").click();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("c_widgetDialog__a84")));
        assertTrue(element.isDisplayed());
    }

    @Test
    void openNotifications() {
        loginWithPassword();
        wait.until(
                webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete")
        );
        waitForElementByXPath("/html/body/div[1]/div[1]/div[2]/div[2]/div[4]/button/span/span[1]/span").click();
        WebElement element = waitForElementByXPath("/html/body/div[2]/div/div/yt-notifications-feed/div/div[1]/h2/span[1]");
        assertTrue(element.isDisplayed());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
