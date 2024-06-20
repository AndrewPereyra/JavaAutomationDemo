package Suite;


import Test.CrearCuentaTester;
import Test.EliminarCuentaTester;
import Test.ModificoPerfilAdmin;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({CrearCuentaTester.class, EliminarCuentaTester.class, ModificoPerfilAdmin.class})
public class Suite_RequireLogin {
}
