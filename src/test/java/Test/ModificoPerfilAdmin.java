package Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.*;
import utils.PropertyReader;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModificoPerfilAdmin {


    //WebDriver for page configuration & manipulation
    protected static WebDriver driver;
    //Values to use in test
    protected static String sitioPruebasCes,hashValue, usuario,password,nombre,apellido,email,pais;
    //Values to use in asserts
    protected static String urlSitioReiniciarContrasenia,mensajeError,tituloMenuPrincipal,
            msjCrearUsuario,msjDesplegadoPostModificacionPerfilAdminCorrecto,
            msjDesplegadoPostModificacionPerfilAdmin,msjDesplegadoAlIniciarSesionCorrecto;

    //Page Object
    protected PreLoginMainPage preLoginMain;
    protected SystemManagementPage systemManagement;
    protected LoginAsAdminFormPage loginAsAdminForm;
    protected AdminLoggedInMainPage adminLoggedInMain;
    protected UpdateAdminProfilePage updateAdminProfile;
    // Extent Reports
    protected static ExtentSparkReporter sparkReporter;
    protected static ExtentReports extent;
    protected static ExtentTest extentTest;


    @BeforeAll
    public static void antesDeTodosLosTest() {
        //Values for test
        hashValue = "3)ea60e0be3ba12c6ecd%7297868%5c4";

        //Values for asserts
        urlSitioReiniciarContrasenia = "http://cestore.ces.com.uy/adminces/forgot-password";
        tituloMenuPrincipal= "ACCESOS";
        mensajeError = "No estamos en el sitio correcto.";
        msjCrearUsuario = "Crear usuario";
        msjDesplegadoPostModificacionPerfilAdminCorrecto="Correcto!";
        msjDesplegadoAlIniciarSesionCorrecto = "Correcto!";
        msjDesplegadoPostModificacionPerfilAdmin="Usuario modificado.";

        // Engine
        extent = new ExtentReports();

        // Reporter
        sparkReporter = new ExtentSparkReporter("C:\\Users\\andre\\Documents\\automated-platform-Suites\\src\\test\\resources\\report.html");
        extent.attachReporter(sparkReporter);

        sparkReporter.config().setOfflineMode(true);
        sparkReporter.config().setDocumentTitle("Simple automation report");
        sparkReporter.config().setReportName("Test Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        sparkReporter.config().setEncoding("UTF-8");
    }

    @BeforeEach
    public void antesDeCadaTest() throws InterruptedException {
        String navegador;
        // para definir el navegador en el cual ejecutar las pruebas,
        // dirigirse a src-> main -> java -> resources -> browser.properties
        // y modificar el valor de navegador por el navegador que se quiera utilizar.
        // En el caso de esta tarea, los valores a utilizar pueden ser "chrome" o "edge" ( sin comillas )
        // Ej.-> navegador = edge
        navegador = PropertyReader.getBrowser("navegador");
        driver = Utils.configureDriver(navegador);
        //Instancia para PO
        preLoginMain = new PreLoginMainPage(driver);
        //Utils.sleep(3);
        // Ingreso primer pagina de AdminCES, donde pide el hash
        preLoginMain.ingresarHash(hashValue);
        preLoginMain.ingresarSitioPostHash();
        preLoginMain.ingresarSeccionGestionSistema();

        // ingreso a seccion Carga de Datos
        systemManagement = preLoginMain.irGestionSistema();
        systemManagement.cargarDatos();
        systemManagement.confirmarCargaDatos();
        systemManagement.botonCargarDatosOK();

        // vuelvo a seccion main page ( pre-login )
        preLoginMain = systemManagement.irPreLoginMain();
    }

    @AfterEach
    public void despuesDeCadaTest() {
        preLoginMain.cerrarSesion();
    }

    @DisplayName("Modificacion de perfil de administrador existente")
    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/datos_pruebaModificoPerfilAdmin.csv", useHeadersInDisplayName = true)
    void modificoPerfilAdminExistente(String usuario,String password,String nombre, String apellido,
                                      String email,String pais) throws InterruptedException {
        extentTest = extent.createTest("modificoPerfilAdminExistente", "Modificacion de perfil de administrador existente");
        try {
        // ingreso a seccion iniciar sesion
        loginAsAdminForm = preLoginMain.irIniciarSesion();
        //ingreso datos de admin en el cual inicio sesion
        loginAsAdminForm.ingresarEmailLogin(usuario);
        loginAsAdminForm.ingresarContraseniaLogin(password);
        loginAsAdminForm.clickBotonIniciarSesion();
        loginAsAdminForm.clickBotonOK();

        // voy a la seccion main del admin
        adminLoggedInMain = loginAsAdminForm.irAdminMainPage();
        // verifico que inicie sesion apropiadamente , controlando que
        // en el menu existe la opcion "Crear Usuario"

        WebElement crearUsuario = driver.findElement(By.linkText("Crear usuario"));
        assertEquals(msjCrearUsuario,crearUsuario.getText());


        // selecciono nombre de admin y accedo a su perfil
        updateAdminProfile = adminLoggedInMain.irModificarPerfilAdmin();

        //valido que estoy en modificacion de datos de perfil administrador
        WebElement validoSeccionModificarPerfil = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div/div[1]/div/h5"));
        assertEquals("DETALLES DEL PERFIL",validoSeccionModificarPerfil.getText());

        // modifico datos de perfil de administrador //
        //////////////////////////////////////////////

        // vacio campo nombre e ingreso nuevo nombre de administrador
        updateAdminProfile.vaciarCampoNombre();
        updateAdminProfile.ingresarNombre(nombre);

        // vacio campo apellido e ingreso nuevo nombre de administrador
        updateAdminProfile.vaciarCampoApellido();
        updateAdminProfile.ingresarApellido(apellido);

        // vacio campo email e ingreso nuevo nombre de administrador
        updateAdminProfile.vaciarCampoEmail();
        updateAdminProfile.ingresarEmail(email);

        // vacio campo paisNacimiento e ingreso nuevo nombre de administrador
        updateAdminProfile.vaciarCampoPaisNacimiento();
        updateAdminProfile.ingresarPaisNacimiento(pais);

        // presionar boton "Confirmar cambios"
        updateAdminProfile.clickBotonConfirmarCambiosPerfil();

        //Valido depliegue de mensaje "Correcto!" y "Usuario modificado."//
        Utils.sleep(3);
        WebElement validoMsjCorrecto = driver.findElement(By.id("swal2-title"));
        WebElement validoMsjUsuarioModificado = driver.findElement(By.id("swal2-html-container"));
        assertEquals(msjDesplegadoPostModificacionPerfilAdminCorrecto,validoMsjCorrecto.getText());
        assertEquals(msjDesplegadoPostModificacionPerfilAdmin,validoMsjUsuarioModificado.getText());

        // presionar boton OK
        updateAdminProfile.clickBotonOK();
        Utils.sleep(3);
        // verificar que estamos en el perfil de usuario validando que existe "DETALLES DEL PERFIL"
        assertEquals("DETALLES DEL PERFIL",validoSeccionModificarPerfil.getText());

        // re accedo a perfil administrador
        updateAdminProfile.reAccederPerfil();
        // valido que nombre y apellido fueron modificados recientemente
        Utils.sleep(2);
        WebElement nombreApellidoModificadosAdmin = driver.findElement(By.name("nameUser"));
        assertEquals(nombre+" "+apellido,nombreApellidoModificadosAdmin.getText());

        // cerrar sesion admin
        adminLoggedInMain = updateAdminProfile.irAdminMainPage();
        Utils.sleep(3);

        adminLoggedInMain.cerrarSesionAdmin();
        extentTest.log(Status.PASS, "Test 'Reinicio de Contrasenia de usuario existente' paso");
        extent.flush();
    }catch (Exception ex){
        extentTest.log(Status.FAIL,ex);
        extent.flush();
        throw ex;
    }

    }


}
