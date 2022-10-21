import mycompany.lraytestprj.Mix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import dataProvider.ConfigFileReader;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import static java.lang.invoke.MethodHandles.lookup;
import org.junit.Ignore;
import org.openqa.selenium.support.Color;

public class FormJUnitTest {
    
    private WebDriver driver;
    static final Logger log = getLogger(lookup().lookupClass());
    
    
    public FormJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        ConfigFileReader configFileReader = new ConfigFileReader();
        System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath()); 
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(configFileReader.getImplWait(), TimeUnit.SECONDS);
        String url =configFileReader.getApplicationUrl();
        driver.get(url);
        String title = driver.getTitle();
        log.info("The title of {} is {}", url, title);

    }
    
    @After
    public void tearDown() {
         
         driver.quit();
         System.out.println("The driver has been closed.");
    }

    //@Ignore("")
    @Test
    public void positive_fill_in_all_required_fields_and_submit() {
        //positive test case - happy flow
        WebElement soccerplayerTxt = driver.findElement(By.cssSelector("input.ddm-field-text"));
        soccerplayerTxt.sendKeys("Zlatan Ibrahimovic");
         
        WebElement testingTxtarea = driver.findElement(By.cssSelector("textarea.ddm-field-text"));
        testingTxtarea.sendKeys("Because QA world is terrific!");
         
        WebElement datefoundedTxt = driver.findElement(By.cssSelector(".input-group-inset"));
        datefoundedTxt.sendKeys("01021999");  //MMDDYYYY
         
        try {
	  	
          WebElement submitBtn = driver.findElement(By.id("ddm-form-submit"));
	  
          if(submitBtn.isDisplayed()){
              Mix.highLight(submitBtn, driver);
              submitBtn.click();
              System.out.println("Submit Successful!");
              Thread.sleep(3000);	
          }
        } 
        catch (InterruptedException e) {
              System.out.println("Error after submitting...");
        }
         
        // after successful submit,  we need to wait until page is completely loaded 
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        
        WebElement submitagainBtn = driver.findElement(By.cssSelector(".btn"));
        Mix.highLight(submitagainBtn, driver);
        System.out.print(submitagainBtn.getText() + "\n");
        Assert.assertEquals("Submit Again", submitagainBtn.getText());
                        
        WebElement pageTitle = driver.findElement(By.cssSelector(".lfr-ddm__default-page-header-title"));
        Mix.highLight(pageTitle, driver);
        Assert.assertEquals( "This is a Liferay Forms", pageTitle.getText());
        
        WebElement thankyouTxt = driver.findElement(By.cssSelector(".lfr-ddm__default-page-title"));
        Mix.highLight(thankyouTxt, driver);
        Assert.assertEquals("Thank you.", thankyouTxt.getText());
        
        WebElement pagedescriptionTxt= driver.findElement(By.cssSelector(".lfr-ddm__default-page-description"));
        Mix.highLight(pagedescriptionTxt, driver);
        Assert.assertEquals("Your information was successfully received. Thank you for filling out the form.", pagedescriptionTxt.getText());
        
    } //end positive_fill_in_all_required_fields_and_submit()
    
    //@Ignore("")
    @Test  
    public void negative_not_all_required_fields_filled_and_submit() { 
        //negative test case 
        
        //Scenario 1: soccer player is filled in. The text area and date field are not filled in
        WebElement soccerplayerTxt = driver.findElement(By.cssSelector("input.ddm-field-text"));
        soccerplayerTxt.sendKeys("Cristiano Ronaldo");
        Mix.highLight(soccerplayerTxt, driver);
        	  	
        WebElement submitBtn = driver.findElement(By.id("ddm-form-submit"));
	Mix.highLight(submitBtn, driver);
        submitBtn.click(); 
                
        // Fill in date field:
        WebElement datefield_requiredTxt = driver.findElement(By.cssSelector("span.form-feedback-group:nth-child(7) > div:nth-child(1)"));
        Mix.highLight(datefield_requiredTxt, driver);
        Assert.assertEquals("This field is required.", datefield_requiredTxt.getText());
        String rgbformat = datefield_requiredTxt.getCssValue("color");
        System.out.println(rgbformat);  //rgba(218, 20, 20, 1)
        String redColor = Color.fromString(rgbformat).asHex();
        System.out.println("Color is :" + redColor);
        Assert.assertTrue(redColor.equals("#da1414"));        
        //Fill in text area:
        WebElement textarea_requiredTxt = driver.findElement(By.cssSelector("span.form-feedback-group:nth-child(6) > div:nth-child(1)"));
        Mix.highLight(textarea_requiredTxt, driver);
        Assert.assertEquals("This field is required.", textarea_requiredTxt.getText());
        rgbformat = textarea_requiredTxt.getCssValue("color");
        System.out.println(rgbformat);
        redColor = Color.fromString(rgbformat).asHex();
        Assert.assertTrue(redColor.equals("#da1414"));
        
        driver.navigate().refresh();   
        // after refresh,  we need to wait until page is completely loaded 
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        
        //Scenario 2: soccer player is not filled in. The text area and date fields are filled in
        soccerplayerTxt = driver.findElement(By.cssSelector("input.ddm-field-text"));
        Mix.highLight(soccerplayerTxt, driver);
        
        WebElement testingTxtarea = driver.findElement(By.cssSelector("textarea.ddm-field-text"));
        testingTxtarea.sendKeys("Because of the prevention of future issues and improvement in the quality of the product.");
         
        WebElement datefoundedTxt = driver.findElement(By.cssSelector(".input-group-inset"));
        datefoundedTxt.sendKeys("11232001");   //MMDDYYYY
        
        submitBtn = driver.findElement(By.id("ddm-form-submit"));
        submitBtn.click();
        WebElement soccer_requiredTxt = driver.findElement(By.cssSelector(".form-feedback-item"));
        Mix.highLight(soccer_requiredTxt, driver);
        Assert.assertEquals("This field is required.", soccer_requiredTxt.getText());
        rgbformat = soccer_requiredTxt.getCssValue("color");
        System.out.println(rgbformat);
        redColor = Color.fromString(rgbformat).asHex();
        Assert.assertTrue(redColor.equals("#da1414"));
        String bkrgbformat = soccerplayerTxt.getCssValue("background-color");   //#feefef
        System.out.println(bkrgbformat);
        String bkColor = Color.fromString(bkrgbformat).asHex();
        Assert.assertTrue(bkColor.equals("#feefef")); 
         
    } //end negative_not_all_required_fields_filled_and_submit()
}
