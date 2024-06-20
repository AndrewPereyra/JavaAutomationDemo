package Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;
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




public class ReiniciarContrasenia {

    //region [Variables]

    //WebDriver para configurar y manipular pagina
    protected static WebDriver driver;
    //Valores para utilizar en test
    protected static String sitioPruebasCes,valorHash, emailCuentaReiniciarPassword, passwordNueva;
    //Valores para utilzar en asserts
    protected static String urlSitioReiniciarContrasenia,mensajeError,tituloMenuPrincipal,
            msjDesplegadoPostReinicioContraseniaCorrecto,msjDesplegadoPostContraseniaReiniciada,
            msjDesplegadoAlIniciarSesionCorrecto,msjDesplegadoSesionIniciada;

    //Page Object
    protected PreLoginMainPage preLoginMain;
    protected SystemManagementPage systemManagement;
    protected ResetPasswordPage resetPassword;
    protected LoginAsAdminFormPage loginAsAdminForm;

    protected AdminLoggedInMainPage adminLoggedInMain;

    // Extent Reports
    protected static ExtentSparkReporter sparkReporter;
    protected static ExtentReports extent;
    protected static ExtentTest extentTest;

    //endregions

    //region [Configuración para pruebas]
    @BeforeAll
    public static void antesDeTodosLosTest() {

        //Valores para test
        valorHash = "3)ea60e0be3ba12c6ecd%7297868%5c4";

        //Valores para asserts
        sitioPruebasCes = "http://cestore.ces.com.uy/adminces/";
        urlSitioReiniciarContrasenia = "http://cestore.ces.com.uy/adminces/forgot-password";
        tituloMenuPrincipal= "ACCESOS";
        mensajeError = "No estamos en el sitio correcto.";
        msjDesplegadoPostReinicioContraseniaCorrecto="Correcto!";
        msjDesplegadoPostContraseniaReiniciada="Contraseña reiniciada.";
        msjDesplegadoAlIniciarSesionCorrecto = "Correcto!";
        msjDesplegadoSesionIniciada = "Sesión iniciada.";

        // Inicializo extentReports

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
        Utils.sleep(3);

        // Ingreso primer pagina de AdminCES, donde pide el hash
        preLoginMain.ingresarHash(valorHash);
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
    //endregion


    //region [Pruebas]

    @DisplayName("Reinicio de Contrasenia de usuario existente")
    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/datos_pruebaReiniciarContrasenia.csv", useHeadersInDisplayName = true)
    void reinicioContraseniaUsuarioExistente(String passwordNueva,String emailCuentaReiniciarPassword) throws InterruptedException {


            extentTest = extent.createTest("reinicioContraseniaUsuarioExistente", "Reinicio de Contrasenia de usuario existente");
        try {

            Utils.sleep(3);
            resetPassword = preLoginMain.irReiniciarContrasenia();
            //
            //validar que estamos en seccion para reiniciar contraseña verificando que se
            // encuentra mensaje "Ingrese su mail para su nueva password"
            WebElement validoSeccionReiniciarContrasenia = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div/div/div/div/div[2]/div/div[1]/p"));
            assertEquals("Ingrese su mail para su nueva password", validoSeccionReiniciarContrasenia.getText());

            // ingresar mail del usuario a reiniciar contraseña en campo "Email"
            resetPassword.ingresarEmail(emailCuentaReiniciarPassword);
            // ingresar contraseñaNueva en campo "Contraseña"
            resetPassword.ingresarContrasenia(passwordNueva);
            // ingresar contraseñaNueva en campo "Repetir contraseña"
            resetPassword.confirmarContrasenia(passwordNueva);
            // Presionar boton "Rei. Contraseña"
            resetPassword.clickReiniciarContraseniaSend();
            Utils.sleep(3);

            //Valido depliegue de mensaje "Correcto!" y "Contraseña reiniciada."
            WebElement validoMsjCorrecto = driver.findElement(By.xpath("/html/body/div[2]/div/h2"));
            WebElement validoMsjContraseniaReiniciada = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]"));
            assertEquals(msjDesplegadoPostReinicioContraseniaCorrecto, validoMsjCorrecto.getText());
            assertEquals(msjDesplegadoPostContraseniaReiniciada, validoMsjContraseniaReiniciada.getText());

            // presionar boton OK
            resetPassword.clickConfirmarReinicioContraseniaSend();

            // validar que fuimos redirigidos al menu principal, validando que se encuentra
            // seccion "ACCESOS"
            WebElement h4 = preLoginMain.h4Displayed();
            assertEquals(tituloMenuPrincipal, h4.getText(), mensajeError);

            // ingreso a seccion iniciar sesion
            loginAsAdminForm = preLoginMain.irIniciarSesion();
            //ingreso datos de admin en el cual inicio sesion
            loginAsAdminForm.ingresarEmailLogin(emailCuentaReiniciarPassword);
            loginAsAdminForm.ingresarContraseniaLogin(passwordNueva);
            loginAsAdminForm.clickBotonIniciarSesion();
            //Valido despliegue de mensaje "Correcto!" y "Sesión iniciada"//

            Utils.sleep(3);
            WebElement validoMsjIniciarSesionCorrecto = driver.findElement(By.id("swal2-title"));
            WebElement validoMsjSesionIniciada = driver.findElement(By.id("swal2-html-container"));
            assertEquals(msjDesplegadoAlIniciarSesionCorrecto, validoMsjIniciarSesionCorrecto.getText());
            assertEquals(msjDesplegadoSesionIniciada, validoMsjSesionIniciada.getText());
            loginAsAdminForm.clickBotonOK();
            extentTest.log(Status.PASS, "Test 'Reinicio de Contrasenia de usuario existente' paso");
            extent.flush();
        }catch (Exception ex){
            extentTest.log(Status.FAIL,ex);
            extent.flush();
            throw ex;
        }

    }
    //endregion

}
