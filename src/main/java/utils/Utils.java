package utils;

import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Utils {

    //region [Constructores]
    private Utils() {
    }
    //endregion

    //region [Metodos de la clase]
    /**
     * Toma una screenshot y retorna el codigo en BASE64.
     *
     * @param webDriver Driver previamente configurado
     * @return Captura de pantalla en BASE64
     */
    public static String getScreenshot(WebDriver webDriver) {
        TakesScreenshot screenshot = (TakesScreenshot) webDriver;
        return screenshot.getScreenshotAs(OutputType.BASE64);
    }

    /**
     * Devuelve la fecha como un string.
     * Se podría parametrizar para que utilice diferentes formatos.
     *
     * @return Fecha con formato yyyy-mm-dd hh.mm.ss
     */
    public static String getDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH_mm_ss"));
    }

    public static String pathToSaveData(String typeReport) {
        String pathAndFileName = "";
        if ("html".equals(typeReport)) {
            String pathReportHtml = PropertyReader.getEnviroment("pathReportHtml");
            String userDir = System.getProperty("user.dir");
            String path = pathReportHtml.equals("") ? userDir + "\\src\\reportHTML" : pathReportHtml;
            String rootFolder = "\\" + userDir.split(Pattern.quote("\\"))[userDir.split(Pattern.quote("\\")).length - 1];
            String dateTime = "\\reporte " + Utils.getDateTime() + ".html";
            pathAndFileName = pathReportHtml.equals("") ? path + dateTime : path + rootFolder + dateTime;
        }
        return pathAndFileName;
    }

    /**
     * Devuelve un objeto de tipo WebDriver.
     *
     * @return driver
     */
    public static WebDriver configureDriver(String navegador) {
        String chrome = "chrome", edge = "edge";

            if (navegador.equals(chrome)) {
                //Configuración de web driver para Chrome
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
                chromeOptions.addArguments("--ignore-certificate-errors");
                WebDriver driver = new ChromeDriver(chromeOptions);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return driver;
            } else {

                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("start-maximized");
                edgeOptions.addArguments("--ignore-certificate-errors");
                WebDriver driver = new EdgeDriver(edgeOptions);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return driver;
            }

    }

    public static String titulo(WebDriver driver) {
        return driver.getTitle();
    }

    @SneakyThrows
    public static void sleep(int second) throws InterruptedException {
        Thread.sleep(second * 1000L);
    }
    //endregion
}
