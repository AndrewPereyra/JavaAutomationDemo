package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;
import utils.Utils;

public class AdminLoggedInMainPage  extends BasePage {
    private final By reiniciarContraseniaBy;
    private final By verUsuariosBy;
    private final By crearUsuarioBy;
    private final By gestionSistemaBy;
    private final By botonNombreAdministradorPerfilBy;
    private final By accedoPerfilAdminBy;
    private final By cerrarSesionBy;
    private final By confirmarCierreBy;


    public AdminLoggedInMainPage(WebDriver webDriver) {
        super(webDriver);

        botonNombreAdministradorPerfilBy = By.xpath("//*[@id=\"content\"]/nav/ul/li/div/div/a");
        accedoPerfilAdminBy = By.xpath("//*[@id=\"content\"]/nav/ul/li/div/div/ul/li[1]/a");

        // Cerrar sesion
        cerrarSesionBy = By.xpath("//*[@id=\"content\"]/nav/ul/li/div/div/ul/li[2]/a");
        confirmarCierreBy = By.cssSelector(".swal2-confirm");

        // Secciones de la main page ( post-AdminLogin ) //
        ////////////////////////////////////////////

        // Crear Usuario
        crearUsuarioBy= By.cssSelector("li.nav-item:nth-child(3) > div:nth-child(1) > a:nth-child(1) > span:nth-child(2)");
        // Reiniciar contraseña
        reiniciarContraseniaBy = By.cssSelector("li.nav-item:nth-child(3) > div:nth-child(3) > a:nth-child(1) > span:nth-child(2)");
        // Ver Usuarios
        verUsuariosBy = By.cssSelector("li.nav-item:nth-child(3) > div:nth-child(3) > a:nth-child(1) > span:nth-child(2)");
        // Gestión sistema
        gestionSistemaBy= By.cssSelector("li.nav-item:nth-child(3) > div:nth-child(4) > a:nth-child(1) > span:nth-child(2)");



    }
    public void ingresarSeccionCrearUsuario(){click(crearUsuarioBy);}
    public void ingresarSeccionReinicarContrasenia(){click(reiniciarContraseniaBy);}
    public void ingresarSeccionVerUsuarios(){click(verUsuariosBy);}
    public void ingresarSeccionGestionSistema(){click(gestionSistemaBy);}
    public void accedoPerfilAdmin(){click(accedoPerfilAdminBy);}
    public void clickBotonNombreAdministradorPerfil(){click(botonNombreAdministradorPerfilBy);}
    public void cerrarSesionAdmin() throws InterruptedException {
        clickBotonNombreAdministradorPerfil();
        Utils.sleep(2);
        click(cerrarSesionBy);
        Utils.sleep(2);
        click(confirmarCierreBy);
    }

    public UpdateAdminProfilePage irModificarPerfilAdmin() {
        clickBotonNombreAdministradorPerfil();
        accedoPerfilAdmin();
        // get(PropertyReader.getEnviroment("environment")+PropertyReader.getEnviroment("gestionSistemaForm"));
        return new UpdateAdminProfilePage(webDriver);

    }

    public CreateTesterAccountPage irCrearUsuario() {
        ingresarSeccionCrearUsuario();
        // get(PropertyReader.getEnviroment("environment")+PropertyReader.getEnviroment("gestionSistemaForm"));
        return new CreateTesterAccountPage(webDriver);

    }
    public ViewUsersPage irVerUsuarios() {
        ingresarSeccionVerUsuarios();
        // get(PropertyReader.getEnviroment("environment")+PropertyReader.getEnviroment("gestionSistemaForm"));
        return new ViewUsersPage(webDriver);

    }

    public String tituloPagina() {
        return title();
    }
}

