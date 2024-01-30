import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;

public class AuthorizationTest {

    private Logger logger = (Logger) LogManager.getLogger(AuthorizationTest.class);

    private String authUrl = System.getProperty("auth.url");
    private String login = System.getProperty("login");
    private String password = System.getProperty("password");
    private WebDriver driver;
    private WaitTools waitTools;


    @BeforeEach
    public void init() {
        driver = new DriverFactory("--start-fullscreen").create();
        waitTools = new WaitTools(driver);
        driver.get(authUrl);
        logger.info("Start driver");
    }

    @AfterEach
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Stop driver");
    }

    @Test
    public void authViewCookie() {
        String signInBtnLocator = "//button[text()='Войти']";

        waitTools.waitForCondition(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section > a[href*='tel']")));
        logger.info("Button sign in is visibility. Continue the test");
        waitTools.waitForCondition(ExpectedConditions
                .presenceOfElementLocated(By.xpath(signInBtnLocator)));
        waitTools.waitForCondition(ExpectedConditions
                .elementToBeClickable(By.xpath(signInBtnLocator)));

        WebElement signInBtn = driver.findElement(By.xpath(signInBtnLocator));

        logger.info("Check authorization popup status. Continue the test");
        String authPopupSelector = "#__PORTAL__ > div";
        Assertions.assertTrue(waitTools
                .waitForCondition(ExpectedConditions.not(ExpectedConditions
                        .visibilityOfElementLocated(By.cssSelector(authPopupSelector)))), "Authorization popup status error");

        signInBtn.click();

        WebElement authPopupEl = driver.findElement(By.cssSelector("#__PORTAL__ > div"));
        Assertions.assertTrue(waitTools
                .waitForCondition(ExpectedConditions
                        .visibilityOf(authPopupEl)), "Authorization popup status error");

        driver.findElement(By.xpath("//div[./input[@name='email']]")).click();

        WebElement emailInputField = driver.findElement(By.cssSelector("input[name='email']"));
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(emailInputField));
        emailInputField.sendKeys(login);

        WebElement passwordInputField = driver.findElement(By.cssSelector("input[type='password']"));
        driver.findElement(By.xpath("//div[./input[@type='password']]")).click();
        waitTools.waitForCondition(ExpectedConditions.stalenessOf(passwordInputField));
        passwordInputField.sendKeys(password);
        logger.info("Enter authorization data. Continue the test");

        driver.findElement(By.cssSelector("#__PORTAL__ button")).click();

        logger.info("Check successful auth. Continue the test");
        Assertions.assertTrue(waitTools
                .waitForCondition(ExpectedConditions
                        .presenceOfElementLocated(By.cssSelector("img[src*='blue-owl']"))));

        String cookies = driver.manage().getCookies().toString();
        logger.info("Cookies" + cookies);
    }
}
