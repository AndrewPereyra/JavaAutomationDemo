package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class CreateAdminAccountPage  extends BasePage {
    private final By nombreBy;
    private final By apellidoBy;
    private final By emailBy;
    private final By contraseniaBy;
    private final By repetirContraseniaBy;
    private final By paisNacimientoBy;
    private final By botonRegistrarseBy;
    private final By botonOKBy;


    public CreateAdminAccountPage(WebDriver webDriver) {
        super(webDriver);


        nombreBy = By.cssSelector("div.form-group:nth-child(1) > div:nth-child(1) > input:nth-child(1)");
        apellidoBy = By.cssSelector("div.form-group:nth-child(1) > div:nth-child(2) > input:nth-child(1)");
        emailBy = By.cssSelector("div.form-group:nth-child(2) > input:nth-child(1)");
        contraseniaBy = By.cssSelector("div.form-group:nth-child(3) > div:nth-child(1) > input:nth-child(1)");
        repetirContraseniaBy = By.cssSelector("div.form-group:nth-child(3) > div:nth-child(2) > input:nth-child(1)");
        paisNacimientoBy = By.cssSelector(".mx-auto > input:nth-child(1)");
        botonRegistrarseBy = By.cssSelector("#btnRegister");
        botonOKBy = By.cssSelector(".swal2-confirm");

    }
    public void ingresarNombre(String nombre) {
        sendKeys(nombreBy, nombre);
    }
    public void ingresarApellido(String apellido) {
        sendKeys(apellidoBy, apellido);
    }
    public void ingresarEmail(String email) {
        sendKeys(emailBy, email);
    }
    public void ingresarContrasenia(String contrasenia) {
        sendKeys(contraseniaBy, contrasenia);
    }
    public void ingresarRepetirContrasenia(String repetirContrasenia) { sendKeys(repetirContraseniaBy, repetirContrasenia);}
    public void ingresarPaisNacimiento(String paisNacimiento) {
        sendKeys(paisNacimientoBy, paisNacimiento);
    }
    public void clickBotonRegistrarse(){click(botonRegistrarseBy);}
    public void clickBotonOK(){click(botonOKBy);}
    public String tituloPagina() {
        return title();
    }
}
