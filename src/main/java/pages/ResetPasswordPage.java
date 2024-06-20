package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class ResetPasswordPage extends BasePage {
    private final By contraseniaBy;
    private final By emailBy;
    private final By confirmarContraseniaBy;
    private final By botonReiniciarContraseniaBy;
    private final By botonConfirmarReinicioBy;


    public ResetPasswordPage(WebDriver webDriver) {
        super(webDriver);

        // Secciones de la reset password page //
        ////////////////////////////////////////

        emailBy = By.cssSelector("input.form-control:nth-child(1)");
        contraseniaBy = By.cssSelector("input.form-control:nth-child(2)");
        confirmarContraseniaBy = By.cssSelector("input.form-control:nth-child(3)");
        botonReiniciarContraseniaBy = By.cssSelector("#btnReset");
        botonConfirmarReinicioBy = By.cssSelector(".swal2-confirm");

    }

    public void ingresarEmail(String email) {
        sendKeys(emailBy, email);
    }
    public void ingresarContrasenia(String contrasenia) {
        sendKeys(contraseniaBy, contrasenia);
    }
    public void confirmarContrasenia(String confirmarContrasenia) { sendKeys(confirmarContraseniaBy, confirmarContrasenia);
    }
    public void clickReiniciarContraseniaSend() {
        click(botonReiniciarContraseniaBy);
    }
    public void clickConfirmarReinicioContraseniaSend() {
        click(botonConfirmarReinicioBy);
    }
    //public String urlSitioReiniciarContrasenia() {return get(url);}


}


