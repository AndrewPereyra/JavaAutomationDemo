package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;

public class UpdateAdminProfilePage extends BasePage {
    private final By nombreBy;
    private final By apellidoBy;
    private final By emailBy;
    private final By paisBy;
    private final By botonConfirmarCambiosPerfilBy;
    private final By botonOKBy;
    private final By botonMenuInicioBy;
    private final By botonNombreAdministradorPerfilBy;
    private final By accedoPerfilAdminBy;

    public UpdateAdminProfilePage (WebDriver webDriver) {
        super(webDriver);
        botonNombreAdministradorPerfilBy = By.xpath("//*[@id=\"content\"]/nav/ul/li/div/div/a");
        accedoPerfilAdminBy = By.xpath("//*[@id=\"content\"]/nav/ul/li/div/div/ul/li[1]/a");

        nombreBy = By.cssSelector("div.mb-3:nth-child(1) > div:nth-child(1) > input:nth-child(2)");
        apellidoBy = By.cssSelector("div.mb-3:nth-child(2) > div:nth-child(1) > input:nth-child(2)");
        emailBy = By.cssSelector("div.mb-3:nth-child(3) > div:nth-child(1) > input:nth-child(2)");
        paisBy = By.cssSelector("div.mb-3:nth-child(4) > div:nth-child(1) > input:nth-child(2)");
        botonConfirmarCambiosPerfilBy = By.xpath("//*[@id=\"detailProfile\"]/div[2]/button");
        botonOKBy = By.cssSelector(".swal2-confirm");
        botonMenuInicioBy = By.xpath("/html/body/div/div/ul/a/div[2]");

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
    public void ingresarPaisNacimiento(String pais) {
        sendKeys(paisBy, pais);
    }
    public void clickBotonConfirmarCambiosPerfil(){click(botonConfirmarCambiosPerfilBy);}
    public void clickBotonOK(){click(botonOKBy);}

    public void vaciarCampoNombre() {
        clear(nombreBy);
    }
    public void vaciarCampoApellido() {
        clear(apellidoBy);
    }
    public void vaciarCampoEmail() {
        clear(emailBy);
    }
    public void vaciarCampoPaisNacimiento() {
        clear(paisBy);
    }

    public AdminLoggedInMainPage irAdminMainPage() {
        //get(PropertyReader.getEnviroment("environment"));
        //click(botonMenuInicioBy);
        return new AdminLoggedInMainPage(webDriver);
    }

    public void reAccederPerfil(){
        click(botonNombreAdministradorPerfilBy);
        click(accedoPerfilAdminBy);
    }
    public String tituloPagina() {
        return title();
    }
}


