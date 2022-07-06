
import java.util.List;
import java.util.LinkedList;

/**
 * A small data holder class for a session.
 */
public class Session {

    public final String id;
    public final Customer customer;
    public final List<View> views;
    //
    // And any other needed fields
    //

    public Session(String id, Customer customer)
    {
        this.id = id;
        this.customer = customer;
        this.views = new LinkedList<View>();
    }

    /**
     * toString; useful for debugging
     */
    public String toString()
    {
        return "Session(id=" + id + ", customer=" + customer + ")";
    }

    public Customer getCustomer()
    {
        return customer;
    }
    public void addView(View view)
    {
        views.add(view);
    }
}


