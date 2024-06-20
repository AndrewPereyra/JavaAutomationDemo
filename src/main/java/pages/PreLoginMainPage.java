package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.PropertyReader;

import java.time.Duration;

public class PreLoginMainPage extends BasePage {
    private final By reiniciarContraseniaBy;
    private final By registrarseBy;
    private final By iniciarSesionBy;
    private final By gestionSistemaBy;
    private final By h4MainPageBy;
    private final By ingresarHashBy;
    private final By ingresarSitioPostHash;
    public PreLoginMainPage(WebDriver webDriver)  {
        super(webDriver);

        get(PropertyReader.getEnviroment("environment"));
        // Ingresar hash
        ingresarHashBy = By.cssSelector("#pass");
        ingresarSitioPostHash= By.cssSelector("#loginForm > div.btns > button");

        // Secciones de la main page ( pre-login ) //
        ////////////////////////////////////////////

        // Iniciar sesion
        iniciarSesionBy = By.cssSelector("li.nav-item:nth-child(3) > div:nth-child(1) > a:nth-child(1) > span:nth-child(2)");

        // Registrarse ( como admin )
        registrarseBy= By.cssSelector("li.nav-item:nth-child(3) > div:nth-child(2) > a:nth-child(1) > span:nth-child(2)");

        // Reiniciar contraseña
        reiniciarContraseniaBy = By.cssSelector("li.nav-item:nth-child(3) > div:nth-child(3) > a:nth-child(1) > span:nth-child(2)");

        // Gestión sistema
        gestionSistemaBy= By.cssSelector("li.nav-item:nth-child(3) > div:nth-child(4) > a:nth-child(1) > span:nth-child(2)");

        // Obtengo header ACCESOS de MainPage
        h4MainPageBy = By.xpath("//h4[contains(text(),'Accesos')]");
    }
    public void ingresarSeccionIniciarSesion(){click(iniciarSesionBy);}
    public void ingresarSeccionRegistrarse(){click(registrarseBy);}
    public void ingresarSeccionReinicarContrasenia(){click(reiniciarContraseniaBy);}
    public void ingresarSeccionGestionSistema(){click(gestionSistemaBy);}
    public WebElement h4Displayed (){ return findElement(h4MainPageBy);}

    public void ingresarHash(String hash) {
        sendKeys(ingresarHashBy, hash);
    }
    public void ingresarSitioPostHash(){click(ingresarSitioPostHash);}
    public void cerrarSesion(){
        quit();
    }

    public SystemManagementPage irGestionSistema() {
        ingresarSeccionGestionSistema();
        // get(PropertyReader.getEnviroment("environment")+PropertyReader.getEnviroment("gestionSistemaForm"));
        return new SystemManagementPage(webDriver);
    }

    public ResetPasswordPage irReiniciarContrasenia() {
        ingresarSeccionReinicarContrasenia();
        // get(PropertyReader.getEnviroment("environment")+PropertyReader.getEnviroment("gestionSistemaForm"));

        return new ResetPasswordPage(webDriver);
    }
    public LoginAsAdminFormPage irIniciarSesion() {
        ingresarSeccionIniciarSesion();
        // get(PropertyReader.getEnviroment("environment")+PropertyReader.getEnviroment("gestionSistemaForm"));
        return new LoginAsAdminFormPage(webDriver);

    }

    public CreateAdminAccountPage irRegistrarse() {
        ingresarSeccionRegistrarse();
        // get(PropertyReader.getEnviroment("environment")+PropertyReader.getEnviroment("gestionSistemaForm"));
        return new CreateAdminAccountPage(webDriver);
    }

}

