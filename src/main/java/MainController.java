import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class MainController {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();

        driver.get("http://rabota.ua/jobsearch");

        WebElement input = driver.findElement(By.xpath("//input[@placeholder='Введите ключевые слова']"));
        input.sendKeys("Junior Java Developer");

        Actions builder = new Actions(driver);

        builder.sendKeys(Keys.ENTER).build().perform();

        WebElement foundDriver = driver.findElement(By.className("fd-fat-merchant"));

        if(Integer.parseInt(foundDriver.getText()) >= 1){
            System.out.println("Ama big boi and I do automatization");

            List<WebElement> vacancyTable = driver.findElements(By.className("f-vacancylist-vacancyblock"));
            for (WebElement vacancy: vacancyTable) {
                WebElement clickable = vacancy.findElement(By.className("f-visited-enable ga_listing");
                System.out.println(clickable.getAttribute("title"));
            }
            System.out.println(vacancyTable.size());
        }

    }
}
