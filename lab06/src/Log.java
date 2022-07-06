
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.function.Function;

/**
 * A data holder class to hold the data read out of a log file.
 */

public final class Log {

    public final Map<String, Session> sessionByID
            = new HashMap<String, Session>();

    public final Map<CustomerID, Customer> customerByID
            = new HashMap<CustomerID, Customer>();

    // Add fields and/or methods as appropriate
}
