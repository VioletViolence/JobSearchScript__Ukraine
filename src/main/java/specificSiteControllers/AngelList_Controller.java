package specificSiteControllers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AngelList_Controller {
    public static void searchOnAngelList(String emailValue,String passwordValue) {
        //Setting things up and getting a window open
        System.setProperty("webdriver.chrome.driver", "C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://angel.co/login");


        //Logging in
        WebElement emailInput = driver.findElement(By.id("user_email"));
        WebElement passwordInput = driver.findElement(By.id("user_password"));
        WebElement loginButton = driver.findElement(By.name("commit"));

        emailInput.sendKeys(emailValue);
        passwordInput.sendKeys(passwordValue);
        loginButton.click();

        //TODO: Setting up the job parameters
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


//        //Getting all of the startups
//        WebElement startupHolder = driver.findElement(By.xpath("//*[@id=\"startups_content\"]/div[1]/div[5]"));
//        String startupIDs = startupHolder.getAttribute("data-startup_ids");
//        startupIDs =  startupIDs.substring(1, startupIDs.length()-2);
//        String[] idList = startupIDs.trim().split(",");
//        System.out.println(Arrays.toString(idList));
        List<WebElement> startupList = driver.findElements(By.xpath("/html/body/div[1]/div[4]/div[2]/div[1]/div[2]/div/div/div[1]/div[5]/div[1]/div/div[2]/div[1]/div[6]/div/div/div[3]/div[2]/div/div[1]/div[1]/a  "));
        startupList.remove(0);
        for (WebElement startup : startupList) {
            String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
            startup.sendKeys(selectLinkOpeninNewTab);
            String url = startup.getAttribute("data-click-promotion-event-url");

        }
    }
}
