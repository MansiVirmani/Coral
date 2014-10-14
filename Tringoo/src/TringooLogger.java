
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
//import org.omg.CORBA.Environment;
/**
 * Created by mansi on 13/10/14.
 */
public class TringooLogger {

    public static Logger log;

    public Logger SetLogger()
    {
        if (log== null)
        {
            try {
                log = Logger.getLogger("TringooLogger");
                SimpleFormatter _SF = new SimpleFormatter();
                ConsoleHandler _Console = new ConsoleHandler();
                _Console.setFormatter(_SF);
                log.addHandler(_Console);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        return log;
    }
}
