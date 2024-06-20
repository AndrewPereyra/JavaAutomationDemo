package pages;

import org.openqa.selenium.WebDriver;
import utils.PropertyReader;

public class SeleniumEasyPage extends BasePage {

    public SeleniumEasyPage(WebDriver webDriver) {
        super(webDriver);
        get(PropertyReader.getEnviroment("environment"));
    }

    public InputFormSubmitPage irInputFormSubmit() {
        get(PropertyReader.getEnviroment("environment")+PropertyReader.getEnviroment("inputForm"));
        return new InputFormSubmitPage(webDriver);
    }

    public void cerrarSesion(){
        quit();
    }
}
