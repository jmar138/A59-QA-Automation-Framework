import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class BaseTest {

    WebDriver driver = null;
    ChromeOptions options = new ChromeOptions();
    String url = "https://qa.koel.app/";
    String expectedAddMessage = "Added 1 song into \"TestPro Playlist.\"";
    String expectedDeleteMessage = "Deleted playlist \"TestPro Playlist.\"";
    String expectedCreatedMessage = "Created playlist \"TestPro Playlist.\"";
    String playListName = "TestPro Playlist";
    String songName = "grav";
    String returnedString = null;

//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebDriverWait wait;
    WebDriverWait waitForIt;

    @BeforeSuite
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    @Parameters({"BaseURL"})
    public void launchBrowser(String BaseURL) throws InterruptedException {

        // Pre-condition
        // Added ChromeOptions argument below to fix websocket error
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       // waitForIt = new WebDriverWait(driver, Duration.ofSeconds(10));




        url = BaseURL;
        navigateToPage();
        enterEmail("demo@testpro.io");
        enterPassword("te$t$tudent");
        submit();
        //Thread.sleep(3000);

    //    wait = new WebDriverWait();
    //    driver.manage().window().maximize();
    }

    @AfterMethod
    public void closeBrowser(){
    //    driver.quit();
    }

    protected void submit() throws InterruptedException {
     //   WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        WebElement submit = wait.until
                (ExpectedConditions.visibilityOfElementLocated
                        (By.cssSelector("button[type='submit']")));
        submit.click();
    }

    protected void enterPassword(String password) {
     //   WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        WebElement passwordField = wait.until
                (ExpectedConditions.visibilityOfElementLocated
                        (By.cssSelector("input[type='password']")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    protected void enterEmail(String email) {
    //    WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        WebElement emailField = wait.until
                (ExpectedConditions.visibilityOfElementLocated
                        (By.cssSelector("input[type='email']")));

        emailField.clear();
        emailField.sendKeys(email);
    }

    protected void navigateToPage() {
        driver.get(url);
    }
}