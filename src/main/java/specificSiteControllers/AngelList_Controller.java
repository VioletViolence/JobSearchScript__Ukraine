package specificSiteControllers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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

        //Getting all of the startups

    }
}
