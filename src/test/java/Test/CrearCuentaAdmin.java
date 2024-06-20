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

public class CrearCuentaAdmin {

    //WebDriver para configurar y manipular pagina
    protected static WebDriver driver;
    //Valores para utilizar en test
    protected static String valorHash,password,nombre,apellido,email,paisNacimiento;
    //Valores para utilzar en asserts
    protected static String mensajeError,tituloMenuPrincipal,msjCrearUsuario,
            msjDesplegadoPostCreacionAdminCorrecto,msjDesplegadoAlIniciarSesionCorrecto,
            msjDesplegadoPostCreacionAdminCreado,msjDesplegadoSesionIniciada;

    //Page Object
    protected PreLoginMainPage preLoginMain;
    protected SystemManagementPage systemManagement;
    protected CreateAdminAccountPage createAdminAccount;
    protected LoginAsAdminFormPage loginAsAdminForm;

    protected AdminLoggedInMainPage adminLoggedInMain;
    // Extent Reports
    protected static ExtentSparkReporter sparkReporter;
    protected static ExtentReports extent;
    protected static ExtentTest extentTest;


    @BeforeAll
    public static void antesDeTodosLosTest() {
        //Valores pre-test
        valorHash = "3)ea60e0be3ba12c6ecd%7297868%5c4";

        //Valores para asserts
        tituloMenuPrincipal= "ACCESOS";
        mensajeError = "No estamos en el sitio correcto.";
        msjCrearUsuario = "Crear usuario";
        msjDesplegadoPostCreacionAdminCorrecto="Correcto!";
        msjDesplegadoPostCreacionAdminCreado="Usuario creado.";
        msjDesplegadoAlIniciarSesionCorrecto="Correcto!";
        msjDesplegadoSesionIniciada = "Sesión iniciada.";

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

    @DisplayName("Creo cuenta Administrador con todos los datos validos")
    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/datos_pruebaCrearAdmin.csv", useHeadersInDisplayName = true)
    void creoCuentaAdminDatosValidos(String password,String nombre, String apellido,String email,
                                     String paisNacimiento) throws InterruptedException {
        extentTest = extent.createTest("creoCuentaAdminDatosValidos", "Creo cuenta Administrador con todos los datos validos");
        try {

        // Ingresar a seccion "Registrarse"
        createAdminAccount = preLoginMain.irRegistrarse();

        // validar que estamos en seccion para crear cuenta administrador,
        // verificando "CREAR CUENTA ADMINISTRADOR"

        WebElement validoSeccionCrearCuentaAdmin = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div/div/div[2]/div/div[1]/h5"));
        assertEquals("CREAR CUENTA ADMINISTRADOR",validoSeccionCrearCuentaAdmin.getText());

        // ingreso nombre de administrador en campo "Nombre"
        createAdminAccount.ingresarNombre(nombre);

        // ingresar apellido de administrador en campo "Apellido"
        createAdminAccount.ingresarApellido(apellido);

        // ingresar mail de administrador en campo "Email"
        createAdminAccount.ingresarEmail(email);

        // ingresar como contraseña de administrador valor "12345" en campo "Contraseña"
        createAdminAccount.ingresarContrasenia(password);

        // ingresar como contraseña de administrador valor "12345" en campo "Repetir contraseña"
        createAdminAccount.ingresarRepetirContrasenia(password);

        // Ingresar pais de nacimiento de administrador en campo "Pais nacimiento"
        createAdminAccount.ingresarPaisNacimiento(paisNacimiento);

        // presionar boton "Registrarse"
        createAdminAccount.clickBotonRegistrarse();

        //Valido despliegue de mensaje "Correcto!" y "Usuario creado."//
        Utils.sleep(3);
        WebElement validoMsjCorrecto = driver.findElement(By.xpath("/html/body/div[2]/div/h2"));
        WebElement validoMsjUsuarioCreado = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]"));
        assertEquals(msjDesplegadoPostCreacionAdminCorrecto,validoMsjCorrecto.getText());
        assertEquals(msjDesplegadoPostCreacionAdminCreado,validoMsjUsuarioCreado.getText());

        // presionar boton OK
        createAdminAccount.clickBotonOK();

        // validar que fuimos redirigidos al menu principal, validando que
        // se encuentra seccion "ACCESOS"

        WebElement validoSeccionAccesos = driver.findElement(By.xpath("/html/body/div/div/div/div/div/h4"));
        assertEquals("ACCESOS",validoSeccionAccesos.getText());

        // ingreso a seccion iniciar sesion
        loginAsAdminForm = preLoginMain.irIniciarSesion();
        //ingreso datos de admin en el cual inicio sesion
        loginAsAdminForm.ingresarEmailLogin(email);
        loginAsAdminForm.ingresarContraseniaLogin(password);
        loginAsAdminForm.clickBotonIniciarSesion();
        //Valido despliegue de mensaje "Correcto!" y "Sesión iniciada"//

        Utils.sleep(3);
        WebElement validoMsjIniciarSesionCorrecto = driver.findElement(By.id("swal2-title"));
        WebElement validoMsjSesionIniciada = driver.findElement(By.id("swal2-html-container"));
        assertEquals(msjDesplegadoAlIniciarSesionCorrecto,validoMsjIniciarSesionCorrecto.getText());
        assertEquals(msjDesplegadoSesionIniciada,validoMsjSesionIniciada.getText());
        loginAsAdminForm.clickBotonOK();
        extentTest.log(Status.PASS, "Test 'Reinicio de Contrasenia de usuario existente' paso");
        extent.flush();
    }catch (Exception ex){
        extentTest.log(Status.FAIL,ex);
        extent.flush();
        throw ex;
    }


    }



}
