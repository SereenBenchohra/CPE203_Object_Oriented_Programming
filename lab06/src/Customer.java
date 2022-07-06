
import java.util.LinkedList;
import java.util.List;

// TODO:  Fill this in

public class Customer {

    private CustomerID customerId;
    private List <Session> sessions;

    public Customer(CustomerID customerId)
    {
        this.customerId = customerId;
        sessions = new LinkedList<Session>();
    }
    public List<Session> getSessions()
    {
        return sessions;
    }

    public void addSession(Session session)
    {
        sessions.add(session);
    }
}
