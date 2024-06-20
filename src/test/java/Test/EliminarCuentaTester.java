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

import static org.junit.jupiter.api.Assertions.*;

public class EliminarCuentaTester {

    //WebDriver para configurar y manipular pagina
    protected static WebDriver driver;
    //Valores para utilizar en test
    protected static String valorHash,usuario,password,emailTesterEliminar;
    //Valores para utilzar en asserts
    protected static String mensajeError,tituloMenuPrincipal,msjCrearUsuario,
            msjDesplegadoPreguntaAlEliminar,msjDesplegadoDeseaEliminarAlUsuario;

    //Page Object
    protected PreLoginMainPage preLoginMain;
    protected SystemManagementPage systemManagement;
    protected LoginAsAdminFormPage loginAsAdminForm;

    protected AdminLoggedInMainPage adminLoggedInMain;
    protected ViewUsersPage viewUsers;
    // Extent Reports
    protected static ExtentSparkReporter sparkReporter;
    protected static ExtentReports extent;
    protected static ExtentTest extentTest;

    @BeforeAll
    public static void antesDeTodosLosTest() {
        //Valores para test
        valorHash = "3)ea60e0be3ba12c6ecd%7297868%5c4";

        //Valores para asserts
        tituloMenuPrincipal= "ACCESOS";
        mensajeError = "No estamos en el sitio correcto.";
        msjCrearUsuario = "Crear usuario";
        msjDesplegadoPreguntaAlEliminar="Pregunta!";
        msjDesplegadoDeseaEliminarAlUsuario="¿Eliminar usuario: ";

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


        // Ingreso primer pagina de AdminCES, donde pide el hash
        preLoginMain.ingresarHash(valorHash);
        preLoginMain.ingresarSitioPostHash();
        preLoginMain.ingresarSeccionGestionSistema();

        // ingreso a seccion Carga de Datos
        systemManagement = preLoginMain.irGestionSistema();
        systemManagement.cargarDatos();
        systemManagement.confirmarCargaDatos();
        Utils.sleep(3);
        systemManagement.botonCargarDatosOK();

        // vuelvo a seccion main page ( pre-login )
        preLoginMain = systemManagement.irPreLoginMain();
    }

    @AfterEach
    public void despuesDeCadaTest() {
        preLoginMain.cerrarSesion();
    }

    @DisplayName("Elimino cuenta de Tester existente")
    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/datos_pruebaEliminarCuentaTester.csv", useHeadersInDisplayName = true)
    void eliminoCuentaTesterExistente(String usuario,String password,String emailTesterEliminar) throws InterruptedException {

        extentTest = extent.createTest("eliminoCuentaTesterExistente", "Elimino cuenta de Tester existente");
        try {
        // ingreso a seccion iniciar sesion
        loginAsAdminForm = preLoginMain.irIniciarSesion();
        //ingreso datos de admin en el cual inicio sesion
        loginAsAdminForm.ingresarEmailLogin(usuario);
        loginAsAdminForm.ingresarContraseniaLogin(password);
        loginAsAdminForm.clickBotonIniciarSesion();
        loginAsAdminForm.clickBotonOK();
        Utils.sleep(3);

        // voy a la seccion main del admin
        adminLoggedInMain = loginAsAdminForm.irAdminMainPage();
        // verifico que inicie sesion apropiadamente , controlando que
        // en el menu existe la opcion "Crear Usuario"

        WebElement crearUsuario = driver.findElement(By.linkText("Crear usuario"));
        assertEquals(msjCrearUsuario,crearUsuario.getText());


        //ingresar a seccion "Ver Usuarios"
        adminLoggedInMain.ingresarSeccionVerUsuarios();

        // paso driver a page viewUsers
        viewUsers = adminLoggedInMain.irVerUsuarios();

        // valido que tester a eliminar existe en la tabla de usuarios en el sistema:
        Utils.sleep(3);
        WebElement table = driver.findElement(By.xpath("//*[@id=\"dataTable\"]"));
        viewUsers.validoCuentaTesterEnTabla(emailTesterEliminar,table);

        //seleccionar boton para borrar usuario
        viewUsers.eliminarCuentaTester();

        //Valido depliegue de mensaje "Pregunta!" y "¿Eliminar usuario: <emailTesterEliminar>?"//
        Utils.sleep(3);
        WebElement validoMsjPregunta = driver.findElement(By.id("swal2-title"));
        WebElement validoMsjEliminarUsuario = driver.findElement(By.id("swal2-html-container"));
        assertEquals(msjDesplegadoPreguntaAlEliminar,validoMsjPregunta.getText());
        assertEquals(msjDesplegadoDeseaEliminarAlUsuario+emailTesterEliminar+"?",validoMsjEliminarUsuario.getText());

        //seleccionar boton "Sí"
        viewUsers.clickSiEliminarCuenta();

        //seleccionar boton "OK"
        viewUsers.clickOKeliminarCuenta();


        // valido que sigo en la seccion de ver usuarios, verificando el mensaje "Usuarios Registrados en el sistema"
        WebElement validoSeccionVerUsuarios = driver.findElement(By.xpath("/html/body/div/div/div/div/div/h4"));
        assertEquals("USUARIOS REGISTRADOS EN EL SISTEMA",validoSeccionVerUsuarios.getText());

        // valido que usuario ha sido efectivamente borrado ( no aparece en lista )
        WebElement table2 = driver.findElement(By.xpath("//*[@id=\"dataTable\"]"));
        viewUsers.validoCuentaTesterEnTabla(emailTesterEliminar,table2);

        // cerrar sesion admin
        adminLoggedInMain = viewUsers.irAdminMainPage();
        adminLoggedInMain.cerrarSesionAdmin();
        Utils.sleep(3);
        extentTest.log(Status.PASS, "Test 'Reinicio de Contrasenia de usuario existente' paso");
        extent.flush();
    }catch (Exception ex){
        extentTest.log(Status.FAIL,ex);
        extent.flush();
        throw ex;
    }
    }

}
