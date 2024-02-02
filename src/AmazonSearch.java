import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AmazonSearch {
    public static void main(String[] args) throws InterruptedException, IOException {
    	
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\eclipse-workspace\\AmazonAutomation\\src\\Utilities\\chrome.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.in");

        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("mobile phone under 15000");
        searchBox.submit();

        Thread.sleep(2000);

        FileWriter fileWriter = new FileWriter("mobile_phones_under_15000.txt");
        for (int i = 1; i <= 20; i++) { 
            List<WebElement> results = driver.findElements(By.cssSelector("div[data-component-type='s-search-result']"));

            for (WebElement result : results) {
                WebElement nameElement = result.findElement(By.cssSelector("span[class='a-size-medium a-color-base a-text-normal']"));
                WebElement priceElement = result.findElement(By.cssSelector("span[class='a-price-whole']"));
                String name = nameElement.getText();
                String price = priceElement.getText();
                System.out.println(name + " - " + price);
                fileWriter.write(name + " - " + price + "\n");
            }
            WebElement nextPage = driver.findElement(By.linkText("Next"));
            if (nextPage.isEnabled()) {
                nextPage.click();
                Thread.sleep(2000); 
            } else {
                break; 
            }
        }

        fileWriter.close();
        driver.quit();
    }
}
