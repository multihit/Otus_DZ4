import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeHeadless {

    static Logger logger = LogManager.getLogger(ChromeHeadless.class);
    WebDriver driver;

    @BeforeAll
    public static void driverInstall() {
        WebDriverManager.chromedriver().setup();
        logger.info("Установка драйвера");
    }

    @BeforeEach
    public void driverStart() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
//        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        logger.info("Запуск браузера");
    }


    @AfterEach
    public void driverStop() {
        if (driver != null) {
            logger.info("Закрываю браузер");
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void ChromeHeadless() {
        driver.get("https://duckduckgo.com/");
        driver.findElement(By.xpath("//input[@placeholder='Онлайн-поиск без слежки']")).
                sendKeys("отус" + Keys.ENTER);
        logger.info("Зашел на сайт");
        driver.manage().window().setSize(new Dimension(1920, 1080));

        WebElement otusLink = driver.findElement(By.xpath("//span[text()='Онлайн‑курсы для профессионалов, дистанционное обучение современным ...']"));
        logger.info("Ввел ОТУС в поисковую строку");
        String textFromOtusLink = otusLink.getText();
        Assertions.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение...", textFromOtusLink);
        logger.info("Текст 1ой ссылки совпадает с ожидаемым");

    }
}
