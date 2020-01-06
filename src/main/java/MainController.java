import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

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
        }

    }
}
