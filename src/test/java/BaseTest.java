import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.LoginPage;

import java.time.Duration;

public class BaseTest {

    public WebDriver driver = null;
    public ChromeOptions options = new ChromeOptions();
    public WebDriverWait wait;
    public Wait<WebDriver> fluentWait;
    public Actions actions = null;

    protected String urlStartPoint =        "https://qa.koel.app/";
    protected String songName =             "grav";
    protected String playlistName =         "TestPro Playlist";
    protected String userName =             "demo@testpro.io";
    protected String userPassword =         "te$t$tudent";
    protected String userEmail =            "demo@testpro.io";
    protected String expectedCreatedMsg =   "Created playlist \""  + playlistName + ".\"";
    protected String expectedDeletedMsg =   "Deleted playlist \""  + playlistName + ".\"";
    protected String expectedAddedMsg =     "Added 1 song into \"" + playlistName + ".\"";

    @BeforeSuite
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    @Parameters({"BaseURL"})
    public void launchBrowser(String baseURL) {

        // Pre-condition
        // Added ChromeOptions argument below to fix websocket error
        options.addArguments("--remote-allow-origins=*");   // allowing remote origins
        options.addArguments("--disable-notifications");    // disabling notifications
        options.addArguments("--incognito");                // launching in incognito mode
        options.addArguments("--window-position=250,0");    // move the window over to the right
//      options.addArguments("--start-maximized");          // launching in maximized mode

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//      driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(200));
        navigateToPage(baseURL);
    }

    protected void navigateToPage(String url) {

        driver.get(url);
    }

    protected void enterEmail(String email) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.provideEmail(email);
    }

    protected void enterPassword(String password) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.providePassword(password);
    }

    protected void submit() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.clickSubmit();
    }

    @AfterMethod
    public void closeBrowser() {

  driver.quit();
    }
}