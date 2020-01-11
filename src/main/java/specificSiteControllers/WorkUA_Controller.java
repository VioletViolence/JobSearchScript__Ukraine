package specificSiteControllers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class WorkUA_Controller {
    public static void searchOnWork(String searchKeyword, String emailValue,String nameValue) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        String[] keywords = {"Java", "Junior", "Developer", "Повна зайнятість"};
        driver.get("https://www.work.ua/");

        WebElement cityChanger = driver.findElement(By.xpath("/html/body/header/section/div/form/div/div[2]/input[1]"));
        WebElement searchInput = driver.findElement(By.xpath("/html/body/header/section/div/form/div/div[1]/input"));
        WebElement searchButton = driver.findElement(By.id("sm-but"));


        cityChanger.clear();
        cityChanger.sendKeys("Київ");
        //cityChanger.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        searchInput.sendKeys(searchKeyword);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        searchButton.click();


        List<WebElement> jobElements = driver.findElements(By.xpath("//*[@id=\"pjax-job-list\"]/div/h2/a"));
        for (WebElement jobelement : jobElements) {
            int relevancyCounter = 0;
            String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
            jobelement.sendKeys(selectLinkOpeninNewTab);

            ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs2.get(1));

            String html = driver.getPageSource();

            for (String keyword : keywords) {
                if(html.contains(keyword)) relevancyCounter++;
            }
            if(relevancyCounter >= keywords.length - (keywords.length/3) && html.contains("Java")){
                WebElement applyButton = driver.findElement(By.xpath("//*[@id=\"fix-block\"]/div/div/div/div/div/ul/li[2]/a"));
                applyButton.click();

                System.out.println("Apply button clicked");

                WebDriverWait wait = new WebDriverWait(driver,30);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"setupload\"]/span[1]")));

                WebElement applyWithResumeOption = driver.findElement(By.xpath("//*[@id=\"setupload\"]/span[1]"));
                applyWithResumeOption.click();


                WebElement fullNameInput = driver.findElement(By.xpath("//*[@id=\"personal\"]/div[1]/input"));
                WebElement emailInput = driver.findElement(By.xpath("//*[@id=\"email\"]"));
                WebElement mobilePhone = driver.findElement(By.xpath("//*[@id=\"user_phone\"]"));
                WebElement fileInput = driver.findElement(By.xpath("//*[@id=\"resume_file\"]"));

                fullNameInput.sendKeys(nameValue);
                emailInput.sendKeys(emailValue);
                mobilePhone.sendKeys("0955243788");
                fileInput.sendKeys("C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/Bogdan_Rosinskiy_resume(SD).pdf");

                //Stopper. To be commented out
                wait.until(ExpectedConditions.urlToBe("www.lol.com"));

                WebElement sendButton = driver.findElement(By.xpath("//*[@id=\"submitbtn\"]"));
                sendButton.click();
            }

            driver.close();
            driver.switchTo().window(tabs2.get(0));
        }
    }
}
