package hooks;

import configs.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private WebDriver webDriver;

    @Before
    public void setUp(){
        webDriver = DriverFactory.initBrowser("chrome");
        webDriver.manage().window().maximize();
        webDriver.get("https://automationexercise.com/");
    }

    @After
    public void tearDown(){
        DriverFactory.quitDriver();
    }
}
