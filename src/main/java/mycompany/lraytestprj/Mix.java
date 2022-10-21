package mycompany.lraytestprj;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Mix {
    public static void highLight(WebElement element, WebDriver driver)
    {
        for (int i = 0; i <2; i++) 
        {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black; border: 4px solid yellow;");
                Thread.sleep(400);
                js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
