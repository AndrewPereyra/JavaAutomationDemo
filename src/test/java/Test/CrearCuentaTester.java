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

public class CrearCuentaTester {

    //WebDriver para configurar y manipular pagina
    protected static WebDriver driver;
    //Valores para utilizar en test
    protected static String valorHash;
    //Valores para utilzar en asserts
    protected static String urlSitioReiniciarContrasenia,mensajeError,
            tituloMenuPrincipal,msjDesplegadoPostCreacionTesterCorrecto,
            msjDesplegadoPostCreacionTesterUsuarioCreado,msjCrearUsuario;

    //Page Object
    protected PreLoginMainPage preLoginMain;
    protected AdminLoggedInMainPage adminLoggedInMain;
    protected CreateTesterAccountPage createTesterAccount;
    protected LoginAsAdminFormPage loginAsAdminForm;

    protected SystemManagementPage systemManagement;
    protected ViewUsersPage viewUsers;
    // Extent Reports
    protected static ExtentSparkReporter sparkReporter;
    protected static ExtentReports extent;
    protected static ExtentTest extentTest;

    @BeforeAll
    public static void antesDeTodosLosTest() {
        //Valores inicializacion pre-test
        valorHash = "3)ea60e0be3ba12c6ecd%7297868%5c4";

        //Valores para asserts
        urlSitioReiniciarContrasenia = "http://cestore.ces.com.uy/adminces/forgot-password";
        tituloMenuPrincipal= "ACCESOS";
        mensajeError = "No estamos en el sitio correcto.";
        msjCrearUsuario = "Crear usuario";
        msjDesplegadoPostCreacionTesterCorrecto="Correcto!";
        msjDesplegadoPostCreacionTesterUsuarioCreado="Usuario creado.";

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

    @DisplayName("Creo cuenta Tester con todos los datos validos")
    @ParameterizedTest(name = "{arguments}")
    @CsvFileSource(resources = "/datos_pruebaCrearTester.csv", useHeadersInDisplayName = true)
    void creoCuentaTesterDatosValidos(String usuario,String password,String nombre, String apellido,
                                      String email, String pais,String passwordTester, String idTipoTester) throws InterruptedException {

        extentTest = extent.createTest("creoCuentaTesterDatosValidos", "Creo cuenta Tester con todos los datos validos");
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

        // vamos a seccion "Crear usuarios"

        createTesterAccount = adminLoggedInMain.irCrearUsuario();

        /* Verificar que estamos en seccion para crear usuarios de tipo "tester" en el sistema,
           validando que se encuentra el mensaje "ALTA DE CUENTA PARA TESTER"*/

        WebElement validoSeccionAltaCuentaTester = driver.findElement(By.xpath("/html/body/div/div/div/div/div/div/div/div/div[2]/div/h5"));
        assertEquals("ALTA DE CUENTA PARA TESTER",validoSeccionAltaCuentaTester.getText());

        //Ingresar nombre de tester en campo "Nombre"
        createTesterAccount.ingresarNombre(nombre);

        //Ingresar apellido de tester en campo "Apellido"
        createTesterAccount.ingresarApellido(apellido);

        //Ingresar mail de tester en campo "Email"
        createTesterAccount.ingresarEmail(email);

        //Seleccionar pais X en el campo desplegable "Pais de nacimiento"
        createTesterAccount.ingresarPais(pais);

        //Ingresar "12345" en campo "Contraseña por defecto"
        createTesterAccount.ingresarContrasenia(passwordTester);

        //Seleccionar categoria "Tester" Y (testerSenior)
        //corregir valor hardcodeado
        createTesterAccount.ingresarTipoTester();

        //Presionar boton "Crear cuenta"
        createTesterAccount.clickBotonCrearCuenta();
        //validar que se despliega mensaje "Correcto!." y "Usuario creado."
        Utils.sleep(3);
        WebElement validoMsjCorrecto = driver.findElement(By.id("swal2-title"));
        WebElement validoMsjUsuarioModificado = driver.findElement(By.id("swal2-html-container"));
        assertEquals(msjDesplegadoPostCreacionTesterCorrecto,validoMsjCorrecto.getText());
        assertEquals(msjDesplegadoPostCreacionTesterUsuarioCreado,validoMsjUsuarioModificado.getText());

        //presionar boton OK
        createTesterAccount.clickBotonOK();
        //validar que fuimos redirigidos al menu de inicio validando el header "ACCESOS"
        WebElement validoSeccionAccesos = driver.findElement(By.xpath("/html/body/div/div/div/div/div/h4"));
        assertEquals("ACCESOS",validoSeccionAccesos.getText());


        //ingresar a seccion "Ver Usuarios"
        adminLoggedInMain.ingresarSeccionVerUsuarios();

        // paso driver a page viewUsers
        viewUsers = adminLoggedInMain.irVerUsuarios();

        // usuario isDisplayed:
        Utils.sleep(3);
        // valido que tester recien creado existe en la tabla de usuarios en el sistema:
        WebElement table = driver.findElement(By.xpath("//*[@id=\"dataTable\"]"));
        viewUsers.validoCuentaTesterEnTabla(email,table);

        // cerrar sesion admin
        adminLoggedInMain = createTesterAccount.irAdminMainPage();
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
