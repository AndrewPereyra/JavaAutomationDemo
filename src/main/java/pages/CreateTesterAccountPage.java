package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateTesterAccountPage extends BasePage {
    private final By nombreBy;
    private final By apellidoBy;
    private final By emailBy;
    private final By contraseniaTesterBy;
    private final By tipoTesterBy;

    private final By botonConfirmarCreacionCuentaTesterBy;
    private final By botonOKBy;


    public CreateTesterAccountPage(WebDriver webDriver) {
        super(webDriver);

        nombreBy = By.cssSelector("div.form-group:nth-child(1) > div:nth-child(1) > input:nth-child(1)");
        apellidoBy = By.cssSelector("div.form-group:nth-child(1) > div:nth-child(2) > input:nth-child(1)");
        emailBy = By.cssSelector("div.form-group:nth-child(2) > input:nth-child(1)");
        contraseniaTesterBy = By.cssSelector("#formCreateUser > div:nth-child(3) > div.col-sm-6.mb-3.mb-sm-0 > input");
        botonConfirmarCreacionCuentaTesterBy = By.cssSelector("#btnRegister");
        botonOKBy = By.cssSelector(".swal2-confirm");
        //tipo tester hardcodeado
        tipoTesterBy = By.id("testerSenior");
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
    public void ingresarTipoTester() {
       click(tipoTesterBy);
    }

    public void ingresarContrasenia(String contraseniaTester) {
        sendKeys(contraseniaTesterBy, contraseniaTester);
    }
    public void ingresarPais(String pais){
        Select drpPaisNacimiento = new Select(webDriver.findElement(By.name("inputCountry")));
        drpPaisNacimiento.selectByVisibleText(pais);
    }


    public void clickBotonCrearCuenta(){click(botonConfirmarCreacionCuentaTesterBy);}
    public void clickBotonOK(){click(botonOKBy);}

    public AdminLoggedInMainPage irAdminMainPage() {
        //get(PropertyReader.getEnviroment("environment"));
        return new AdminLoggedInMainPage(webDriver);
    }

    public String tituloPagina() {
        return title();
    }
}

