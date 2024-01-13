import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ChromeKiosk {

        static Logger logger = LogManager.getLogger(ChromeKiosk.class);
        WebDriver driver;

        @BeforeAll
        public static void driverInstall() {
            WebDriverManager.chromedriver().setup();
            logger.info("Установка драйвера");
        }

        @BeforeEach
        public void driverStart() {

//        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
            logger.info("Запуск браузера");
        }


        @AfterEach
        public void driverStop(){
            if(driver!=null){
                logger.info("Закрываю браузер");
                driver.quit();
            }
        }


        @Test
        public void ChromeKiosk(){
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
           driver.get("https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/" +
                   "685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
            driver.manage().window().maximize();
            logger.info("Браузер открылся в режиме киоска");
            logger.info("Перешел по ссылке");
//            driver.manage().window().setSize(new Dimension(1920,1080));
            WebElement image = driver.findElement(By.xpath("//span[@class='image-block']/."));
            image.click();
            logger.info("Нажал на 1ую картинку");
            WebElement modalWindow = driver.findElement(By.cssSelector("//div[@class='pp_hoverContainer']"));
            Assertions.assertEquals("pp_hoverContainer", modalWindow.getAttribute("class"));
            logger.info("Появилось модальное окно");
        }

        }

