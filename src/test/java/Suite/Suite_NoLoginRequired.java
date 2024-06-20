package Suite;

import Test.CrearCuentaAdmin;
import Test.ReiniciarContrasenia;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({CrearCuentaAdmin.class, ReiniciarContrasenia.class})
//@IncludeTags(value = "No login")
public class Suite_NoLoginRequired {

}
