package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewUsersPage extends BasePage {
    private final By botonEliminarCuentaTesterBy;
    private final By botonSiEliminarBy;
    private final By botonOKEliminarBy;


    public ViewUsersPage (WebDriver webDriver) {
        super(webDriver);
      //  botonEliminarCuentaTesterBy = By.cssSelector("#nahuel\\@gmail\\.com > svg:nth-child(1) > path:nth-child(1)");
        botonEliminarCuentaTesterBy = By.cssSelector("#nahuel\\@gmail\\.com");
        botonSiEliminarBy = By.cssSelector(".swal2-confirm");
        botonOKEliminarBy = By.cssSelector("html.swal2-shown.swal2-height-auto body.swal2-shown.swal2-height-auto div.swal2-container.swal2-center.swal2-backdrop-show div.swal2-popup.swal2-modal.swal2-icon-success.animate__animated.animate__fadeInDown div.swal2-actions button.swal2-confirm.swal2-styled.swal2-default-outline");

    }
    public void validoCuentaTesterEnTabla(String emailTester, WebElement table){
        boolean testerExiste;
        List<WebElement> rows = table.findElements(By.xpath(".//tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            for (WebElement cell : cells) {
                if(emailTester.equals(cell.getText())){
                    testerExiste=true;
                    assertTrue(testerExiste,"Usuario "+ emailTester +"no existe");
                    // simple print en pantalla para hacer un double check que usuario existe
                    //     System.out.println("Usuario "+emailTester+" existe");
                }
                else if(!(emailTester.equals(cell.getText()))){
                    testerExiste=false;
                    assertFalse(testerExiste,"Usuario"+ emailTester +"existe");
                    // simple print en pantalla para hacer un double check que usuario ya no existe
                    //System.out.println("Usuario "+emailTester+" eliminado");
                }
            }
        }

    }


    public void eliminarCuentaTester(){click(botonEliminarCuentaTesterBy);}
    public void clickSiEliminarCuenta(){click(botonSiEliminarBy);}
    public void clickOKeliminarCuenta(){click(botonOKEliminarBy);}
    public AdminLoggedInMainPage irAdminMainPage() {
        //get(PropertyReader.getEnviroment("environment"));
        return new AdminLoggedInMainPage(webDriver);
    }
}
