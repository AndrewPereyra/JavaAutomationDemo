package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected final WebDriver webDriver;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected void click(By by) {
        webDriver.findElement(by).click();
    }
    protected void clear(By by) {
        webDriver.findElement(by).clear();
    }
    protected void sendKeys(By by, String text) {
        webDriver.findElement(by).sendKeys(text);
    }


    protected String text(By by) {
        return webDriver.findElement(by).getText();
    }
    protected String getAttribute(By by, String attribute) {
        return webDriver.findElement(by).getAttribute(attribute);
    }
    protected String getAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }


    protected void selectByIndex(By by, int index) {
        new Select(findElement(by)).selectByIndex(index);
    }
    protected void selectByText(By by, String text) {
        new Select(findElement(by)).selectByVisibleText(text);
    }
    protected void selectByValue(By by, String value) {
        new Select(findElement(by)).selectByValue(value);
    }

    protected String title() {
        return webDriver.getTitle();
    }

    protected void quit() {webDriver.quit();}

    protected void get(String url) {
        webDriver.get(url);
    }

    protected WebElement findElement(By by) {
        return webDriver.findElement(by);
    }
    protected List<WebElement> findElements(By by) {
        return webDriver.findElements(by);
    }

    public void tiempoEspera(){
        new WebDriverWait(webDriver, Duration.ofSeconds(5));
    }


}
