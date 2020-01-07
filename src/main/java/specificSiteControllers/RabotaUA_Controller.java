package specificSiteControllers;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.*;
import java.util.*;

public class RabotaUA_Controller {
    public static void searchOnRabota(String keyword, String emailValue,String firstNameValue, String surnameValue, String englishLevelRawValue) throws InterruptedException {
        //Setting things up and getting a window open
        System.setProperty("webdriver.chrome.driver", "C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();

        driver.get("http://rabota.ua/jobsearch");

        //Entering the search words and going to the page
        WebElement input = driver.findElement(By.xpath("//input[@placeholder='Введите ключевые слова']"));
        input.sendKeys(keyword);

        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.ENTER).build().perform();

        //Ensuring that search is in Kiev
        String mainWindow = driver.getCurrentUrl();
        String newMain = mainWindow.substring(0, mainWindow.lastIndexOf("/")) + "/киев";
        driver.get(newMain);
        boolean done = false;

        //Getting usable englishValue from raw value
        String usableEnglishValue;
        switch (englishLevelRawValue){
            case "0" : usableEnglishValue = "1";break;      //не владею
            case "A1" : usableEnglishValue = "2";break;     //базовый
            case "A2" : usableEnglishValue = "3";break;     //ниже среднего
            case "B1" : usableEnglishValue = "4";break;     //средний
            case "B2" : usableEnglishValue = "5";break;     //выше среднего
            case "C1" : usableEnglishValue = "6";break;     //продвинутый
            case "C2" : usableEnglishValue = "7";break;     //свободно
            case "Native" : usableEnglishValue = "8";break; //родной
            default: usableEnglishValue = "0";
        }

        //Setting up good job list and count
        HashMap<String, ArrayList<String>> goodJobList = new HashMap<>();
        int goodJobCount = 0;
        //Perform while not at the end of the list
        while (!done) {
            WebElement foundDriver = driver.findElement(By.className("fd-fat-merchant"));

            //Finding all vacancy elements on the page and iterating over them
            if (Integer.parseInt(foundDriver.getText().trim()) >= 1) {
                System.out.println("Ama big boi and I do automatization");

                List<WebElement> vacancyTable = driver.findElements(By.xpath("/html/body/form/div[4]/div/section/div[2]/table/tbody/tr"));
                for (WebElement vacancy : vacancyTable) {
                    String idVacancy = vacancy.getAttribute("id");
                    try {
                        //Finding a clickable button to go to the vacancy page
                        WebElement clickable = vacancy.findElement(By.xpath("//*[@id=" + idVacancy + "]/td/article/div[1]/div[1]/h3/a"));

                        //Opening it in new tab
                        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
                        clickable.sendKeys(selectLinkOpeninNewTab);

                        //Switching to the newly opened tab
                        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
                        driver.switchTo().window(tabs2.get(1));

                        //Find out if the vacancy is suiting the needed parameters
                        int hiringGoodnessIndeex = 0;
                        //Extract stats
                        List<WebElement> statsNeeded = driver.findElements(By.xpath("//*[@id='ctl00_content_vcVwPopup_VacancyViewInner1_Clusters_ClustersContainer']/div/div/div/ul/li"));
                        ArrayList<String> statsNeededList = new ArrayList<>();
                        for (WebElement stat : statsNeeded) {
                            statsNeededList.add(stat.getText());
                            String statText = stat.getText();
                            System.out.println(statText);
                            //Analyzing a match
                            switch (stat.getText()) {
                                case "Java": hiringGoodnessIndeex += 15; break;
                                case "Spring": hiringGoodnessIndeex += 10; break;
                                case "Junior/Младший специалист": hiringGoodnessIndeex += 7; break;
                                case "Работа для студента": hiringGoodnessIndeex += 5; break;
                                case "Middle/Специалист":
                                    hiringGoodnessIndeex -= 3;
                                    break;
                                case "Senior/Старший специалист": hiringGoodnessIndeex -= 20;
                                case "Android":
                                    hiringGoodnessIndeex -= 5;
                                    break;
                                case "Web development":
                                    hiringGoodnessIndeex -= 10;
                                    break;
                            }
                        }

                        System.out.println(driver.getTitle());
                        System.out.println(hiringGoodnessIndeex);

                        if (hiringGoodnessIndeex >= 15) {
                            goodJobList.put(driver.getTitle(), statsNeededList);
                            goodJobCount++;
                            WebElement apply = driver.findElement(By.id("ctl00_content_vcVwPopup_linkApply"));
                            apply.click();

                            //Getting all the elements that we need to interact with
                            WebElement file = driver.findElement(By.id("ctl00_content_vcVwPopup_vacancyApplyForm_flUplResume"));
                            WebElement email = driver.findElement(By.name("ctl00$content$vcVwPopup$vacancyApplyForm$txFromEMail"));
                            WebElement surname = driver.findElement(By.name("ctl00$content$vcVwPopup$vacancyApplyForm$txtSurName"));
                            WebElement firstName = driver.findElement(By.name("ctl00$content$vcVwPopup$vacancyApplyForm$txtName"));
                            WebElement englishLevel = driver.findElement(By.name("ctl00$content$vcVwPopup$vacancyApplyForm$applyControlQuestion$rptLanguages$ctl00$ddlSkills"));
                            WebElement emailListCheckbox = driver.findElement(By.xpath("/html/body/form/div[4]/div[2]/div[1]/div[1]/div/div/div/div[3]/div[1]/div[3]/div[1]/div/div[2]/div[3]/div/div[1]"));
                            WebElement confirmButton = driver.findElement(By.id("ctl00_content_vcVwPopup_vacancyApplyForm_hpLnkSendResumeToEmployer"));
                            Select selectEnglishLevel = new Select(englishLevel);

                            //Sending the values and confirming
                            email.sendKeys(emailValue);
                            surname.sendKeys(firstNameValue);
                            firstName.sendKeys(surnameValue);
                            selectEnglishLevel.selectByValue(usableEnglishValue);
                            emailListCheckbox.click();
                            if(statsNeededList.contains("Java")){
                                file.sendKeys("C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/Bogdan_Rosinskiy_resume(JD).pdf");
                            }else{
                                file.sendKeys("C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/Bogdan_Rosinskiy_resume(SD).pdf");
                            }
                            confirmButton.click();
                            System.out.println("File sent");
                        }

                        //Closing the new tab and switching to the main one
                        driver.close();
                        driver.switchTo().window(tabs2.get(0));
                    } catch (InvalidSelectorException e) {
                        System.out.println(e);
                    }
                }
                try {
                    List<WebElement> nextPage = driver.findElements(By.xpath("/html/body/form/div[4]/div/section/div[2]/table/tbody/tr[21]/td/dl/dd[9]/a"));
                    File file = new File("C:/Users/brosi/IdeaProjects/JobSearchScript(Maven)/src/main/java/resumeSent.txt");
                    if (nextPage.size() >= 1) {
                        nextPage.get(0).click();
                    } else {
                        done = true;

                        if (file.createNewFile()){
                            System.out.println("File is created!");
                        } else {
                            System.out.println("File already exists.");
                        }

                        Iterator it = goodJobList.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            System.out.println(pair.getKey() + " = " + pair.getValue());
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                            bw.write(pair.getKey() + " = " + pair.getValue() + "_______Resume sent");
                            bw.newLine();
                            bw.close();
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                        System.out.println(goodJobCount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
