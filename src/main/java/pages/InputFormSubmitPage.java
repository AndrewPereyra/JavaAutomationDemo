package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InputFormSubmitPage extends BasePage {
    private final By nombreBy;
    private final By apellidoBy;
    private final By emailBy;
    private final By telefonoBy;
    private final By direccionBy;
    private final By ciudadBy;
    private final By estadoBy;
    private final By zipBy;
    private final By sitiowebBy;
    private final By hostingBy;
    private final By descripcionBy;
    private final By botonBy;
    private final By etiquetaSmall;

    public InputFormSubmitPage(WebDriver webDriver) {
        super(webDriver);
        nombreBy = By.name("first_name");
        apellidoBy = By.name("last_name");
        emailBy = By.name("email");
        telefonoBy = By.name("phone");
        direccionBy = By.name("address");
        ciudadBy = By.name("city");
        estadoBy = By.name("state");
        zipBy = By.name("zip");
        sitiowebBy = By.name("website");
        hostingBy = By.name("hosting");
        descripcionBy = By.name("comment");
        botonBy = By.cssSelector(".btn");
        etiquetaSmall = By.tagName("small");
    }

    public String tituloPagina() {
        return title();
    }

    public void ingresarNombre(String nombre) {
        sendKeys(nombreBy, nombre);
    }
    public void ingresarApellido(String apellido) {
        sendKeys(apellidoBy, apellido);
    }

    public boolean camposInvalidos() {
        boolean invalid = false;

        for (var item : findElements(etiquetaSmall))
            if (getAttribute(item, "data-bv-result").equals("INVALID"))
                invalid = true;
        return invalid;
    }

    public void ingresarEmail(String email) {
        sendKeys(emailBy, email);
    }
    public void ingresarTelefono(String telefono) {
        sendKeys(telefonoBy, telefono);
    }
    public void ingresarDireccion(String direccion) {
        sendKeys(direccionBy, direccion);
    }
    public void ingresarCiudad(String ciudad) {
        sendKeys(ciudadBy, ciudad);
    }

    public void seleccionarEstado(String estado) {
        selectByText(estadoBy, estado);
    }

    public void ingresarZip(String zip) {
        sendKeys(zipBy, zip);
    }
    public void ingresarWebSite(String sitioWeb) {
        sendKeys(sitiowebBy, sitioWeb);
    }
    public void ingresarHosting(int hosting) {
        findElements(hostingBy).get(hosting).click();
    }
    public void ingresarDescripcion(String descripcion) {
        sendKeys(descripcionBy, descripcion);
    }

    public void clickSend() {
        click(botonBy);
    }
}
