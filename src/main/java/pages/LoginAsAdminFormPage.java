package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;
import utils.Utils;

import java.time.Duration;

public class LoginAsAdminFormPage  extends BasePage {
    private final By emailLoginBy;
    private final By contraseniaLoginBy;
    private final By botonIniciarSesionAdminBy;
    private final By botonOKinicioSesionBy;


    public LoginAsAdminFormPage(WebDriver webDriver) {
        super(webDriver);

        emailLoginBy = By.name("inputEmail");
        contraseniaLoginBy = By.name("inputPassword");
        botonIniciarSesionAdminBy = By.xpath("/html/body/div/div/div/div/div/div/div/div/div/div/div[2]/div/form/div[3]/div[2]/button");
        botonOKinicioSesionBy = By.cssSelector("body > div.swal2-container.swal2-center.swal2-backdrop-show > div > div.swal2-actions > button.swal2-confirm.swal2-styled.swal2-default-outline");

    }

    public void ingresarEmailLogin(String emailLogin) {
        sendKeys(emailLoginBy, emailLogin);
    }
    public void ingresarContraseniaLogin(String contraseniaLogin) {
        sendKeys(contraseniaLoginBy, contraseniaLogin);
    }
    public void clickBotonIniciarSesion(){click(botonIniciarSesionAdminBy);}
    public void clickBotonOK(){click(botonOKinicioSesionBy);}
    public String tituloPagina() {
        return title();
    }

    public AdminLoggedInMainPage irAdminMainPage() throws InterruptedException {
        Utils.sleep(5);
        //get(PropertyReader.getEnviroment("environment"));
        return new AdminLoggedInMainPage(webDriver);
    }

}


