import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of variable bindings.  A named variable is bound
 * to a value, with a command like "set a = 3".
 */
class Bindings
{ 
    private final Map<String, Double> bindings = new HashMap<>();

    public void addBinding(final String id, final double value)
    {
        bindings.put(id, value);
    }

    public double lookupBinding(final String id)
        throws UnboundIdentifierException
    {
        Double value = bindings.get(id);

        if (value == null)
        {
            throw new UnboundIdentifierException("unbound identifier: " + id);
        }
        else
        {
            return value;
        }
    }
}
