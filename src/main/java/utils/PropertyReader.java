package utils;

import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    //region [Constructores]
    private PropertyReader() {
    }
    //endregion

    //region [Metodos de la clase]

    /**
     * Obtenemos valores de 'environment.properties' dado una key.
     *
     * @param key Clave para obtener valor.
     * @return Valor dado una key.
     */
    @SneakyThrows
    public static String getEnviroment(String key) {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader("src/main/resources/environment.properties")) {
            properties.load(fileReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key);
    }

    @SneakyThrows
    public static String getBrowser(String key) {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader("src/main/resources/browser.properties")) {
            properties.load(fileReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(key);
    }
    //endregion
}
