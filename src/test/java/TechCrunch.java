import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TechCrunch {

    public WebDriver driver;

    @BeforeTest
    public void OpenBrowser() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        System.setProperty(DriverParameters.CHROME_DRIVER_TYPE, DriverParameters.CHROME_DRIVER_PATH);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get(CaseUrls.PAGE_URL);
    }

    @Test
    public void CheckAuthor() throws Exception{
        List<WebElement> AuthorList = driver.findElements(By.xpath(Elements.XPATH_AUTHOR));
        for(WebElement element : AuthorList) {
            if(element.getText().equals(Elements.EMPTY_CONTROL)){
                System.out.println(Messages.NO_AUTHOR_MESSAGE);
            }System.out.println(Messages.AUTHOR_MESSAGE);
        }
    }

    @Test
    public void CheckImage() throws Exception {
        List<WebElement> PhotoList = driver.findElements(By.xpath(Elements.XPATH_PHOTO));
        for (WebElement element : PhotoList) {
            if (element.getAttribute(Elements.PHOTO_ATTRIBUTE).equals(Elements.EMPTY_CONTROL)) {
                System.out.println(Messages.NO_PHOTO_MESSAGE);
            }System.out.println(Messages.PHOTO_MESSAGE);
        }
    }

    @Test
    public void CompareTitlesAndCheckLinks() throws Exception {
        driver.findElement(By.xpath(Elements.XPATH_BROWSER_TITLE)).click();
        if(driver.getTitle().equals(driver.findElement(By.xpath(Elements.XPATH_PAGE_TITLE)).getText())){
            System.out.println(Messages.SAME_TITLE_MESSAGE);
        }else System.out.println(Messages.DIFFERENT_TITLE_MESSAGE);

        List<WebElement> allLinks = driver.findElements(By.xpath(Elements.XPATH_LINKS));
        for(WebElement link:allLinks){
            try {
                String urlLink = link.getAttribute(Elements.LINKS_ATTRIBUTE);
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
                httpURLConnect.setConnectTimeout(5000);
                httpURLConnect.connect();
                    if(httpURLConnect.getResponseCode()>=400)
                    {
                        System.out.println(urlLink+" - "+httpURLConnect.getResponseMessage()+"is a broken link");
                    }
                    else{
                        System.out.println(urlLink+" - "+httpURLConnect.getResponseMessage());
                    }
                }catch (Exception e) {
            }
        }
    }

    @AfterClass
    public void CloseBrowser() throws Exception{
        driver.quit();
    }
}
