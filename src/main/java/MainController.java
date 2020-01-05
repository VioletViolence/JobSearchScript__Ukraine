import org.openqa.selenium.chrome.ChromeDriver;

public class MainController {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();

        driver.get("http://rabota.ua/jobsearch");


    }
}
