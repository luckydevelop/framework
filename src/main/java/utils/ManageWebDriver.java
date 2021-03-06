package utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;


@Component
public class ManageWebDriver {

    private static WebDriver driver;
    private final static Logger log = LogManager.getLogger(ManageWebDriver.class);
    private static Environment env;


    @Autowired
    ManageWebDriver(Environment env) {
        ManageWebDriver.env = env;
    }


    public static WebDriver getWebdriver() {
        driver = GlobalVariables.getWebDriver();
        if (driver == null) {
            driver = initializeWebDriver();
            driver.manage().window().setSize(new Dimension(Integer.valueOf(env.getProperty("browser.dimensions.width")), Integer.valueOf(env.getProperty("browser.dimensions.height"))));
        }
        return driver;
    }

    public static WebDriver getDriverVariable() {
        return driver;
    }

    private static WebDriver initializeWebDriver() {
        String driverType = env.getProperty("browser.default");

        switch (driverType) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", env.getProperty("driver.chrome.path"));
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("driver type is unknown");
        }

        log.info(String.format("New %s driver is created", driverType));

        GlobalVariables.setWebdriver(driver);

        return driver;
    }

    public static void closeWebDriver() {
        driver = GlobalVariables.getWebDriver();
        if (driver == null) {
            log.info("Driver is NULL. Nothing to close");
        } else {
            driver.close();
            log.info("Close driver");
            GlobalVariables.setWebdriver(null);
        }
    }

}
