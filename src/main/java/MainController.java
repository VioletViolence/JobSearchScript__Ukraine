import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    public static void main(String[] args) throws InterruptedException {
        //Setting things up and getting a window open
        System.setProperty("webdriver.chrome.driver", "C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();

        driver.get("http://rabota.ua/jobsearch");

        //Entering the search words and going to the page
        WebElement input = driver.findElement(By.xpath("//input[@placeholder='Введите ключевые слова']"));
        input.sendKeys("Junior Java Developer");

        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.ENTER).build().perform();

        //Ensuring that search is in Kiev
        String mainWindow = driver.getCurrentUrl();
        String newMain = mainWindow.substring(0,mainWindow.lastIndexOf("/")) + "/киев";
        driver.get(newMain);

        WebElement foundDriver = driver.findElement(By.className("fd-fat-merchant"));

        //Finding all vacancy elements on the page and iterating over them
        if(Integer.parseInt(foundDriver.getText()) >= 1){
            System.out.println("Ama big boi and I do automatization");

            List<WebElement> vacancyTable = driver.findElements(By.xpath("/html/body/form/div[4]/div/section/div[2]/table/tbody/tr"));
            for (WebElement vacancy: vacancyTable) {
                String idVacancy = vacancy.getAttribute("id");
                try {
                    //Finding a clickable button to go to the vacancy page
                    WebElement clickable = vacancy.findElement(By.xpath("//*[@id=" + idVacancy + "]/td/article/div[1]/div[1]/h3/a"));

                    //Opening it in new tab
                    String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
                    clickable.sendKeys(selectLinkOpeninNewTab);

                    //Switching to the newly opened tab
                    ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());


                    //Closing the new tab and switching to the main one
                    driver.switchTo().window(tabs2.get(1));
                    driver.close();
                    driver.switchTo().window(tabs2.get(0));
                }catch (InvalidSelectorException e){
                    System.out.println(e);
                }
            }
            System.out.println(vacancyTable.size());
        }

    }
}
