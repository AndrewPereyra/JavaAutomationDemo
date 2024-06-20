package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;

import java.time.Duration;

public class SystemManagementPage extends BasePage {
    private final By botonCargarDatosBy;
    private final By confirmarCargaDatosBy;
    private final By botonCargaDatosOKBy;

    public SystemManagementPage (WebDriver webDriver) {
        super(webDriver);
        botonCargarDatosBy = By.cssSelector("div.col-sm-12:nth-child(1) > div:nth-child(1) > div:nth-child(2) > button:nth-child(3)");
        confirmarCargaDatosBy = By.cssSelector("body > div.swal2-container.swal2-center.swal2-backdrop-show > div > div.swal2-actions > button.swal2-confirm.swal2-styled.swal2-default-outline");
        botonCargaDatosOKBy = By.cssSelector("body > div.swal2-container.swal2-center.swal2-backdrop-show > div > div.swal2-actions > button.swal2-confirm.swal2-styled.swal2-default-outline");
    }
    public void cargarDatos(){click(botonCargarDatosBy);}
    public void confirmarCargaDatos(){click(confirmarCargaDatosBy);}
    public void botonCargarDatosOK(){click(botonCargaDatosOKBy);}

    public PreLoginMainPage irPreLoginMain() {
        get(PropertyReader.getEnviroment("environment"));
        return new PreLoginMainPage(webDriver);
    }


}

