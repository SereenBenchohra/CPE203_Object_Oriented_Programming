import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LogParser {

        //constants to be used when pulling data out of input
        //leave these here and refer to them to pull out values
    private static final String START_TAG = "START";
    private static final int START_NUM_FIELDS = 4;
    private static final int START_SESSION_ID = 1;
    private static final int START_CUSTOMER_REGION = 2;
    private static final int START_CUSTOMER_ID_IN_REGION = 3;

    private static final String BUY_TAG = "BUY";
    private static final int BUY_NUM_FIELDS = 5;
    private static final int BUY_SESSION_ID = 1;
    private static final int BUY_PRODUCT_ID = 2;
    private static final int BUY_PRICE = 3;
    private static final int BUY_QUANTITY = 4;

    private static final String VIEW_TAG = "VIEW";
    private static final int VIEW_NUM_FIELDS = 4;
    private static final int VIEW_SESSION_ID = 1;
    private static final int VIEW_PRODUCT_ID = 2;
    private static final int VIEW_PRICE = 3;
    private static final String END_TAG = "END";

    private static final int END_NUM_FIELDS = 2;
    private static final int END_SESSION_ID = 1;

    /*
     * The file we parse
     */
    private File inputFile;

    /*
     * The log we create
     */
    private Log log;

    /*
     * Our current line number within inputFile.
     */
    private int lineNumber;

    /**
     * Create a new Parser to parse the given file≥
     */
    public LogParser(File inputFile) {
        this.inputFile = inputFile;
        this.log = new Log();
    }

        //a good example of what you will need to do next
    private void processStartEntry(String[] words) throws ParseException
    {
        if (words.length != START_NUM_FIELDS) {
            throw new ParseException(inputFile, lineNumber, 
                                     "start tag with " + words.length + " words");
        }

        //
        // TODO:  Get the right Customer object
        //
        /*
       Function<CustomerID, Customer> createCustomer =  (id) ->
        {
            Customer customer = new Customer();
            if (Constants.DEBUG)
            {
                System.out.println("Debug:  Created " + customer);
            }

            else
            {
                return customer;

            }
        };

        */

        CustomerID customerid = new CustomerID(words[START_CUSTOMER_REGION], words[START_CUSTOMER_ID_IN_REGION]);
        //Customer customer1 = new Customer();
        final Customer customer = new Customer(customerid);

        String sessionID = words[START_SESSION_ID];

        //
        // Here's a function that creates a new session.  It's only used
        // if we haven't seen this session before.  Remember, a session
        // can end and re-start.
        //
        Function<String, Session> createSession =  (id) -> // maybe add in customer
        {
            Session result = new Session(id, customer);
            if (Constants.DEBUG)
            {
                System.out.println("Debug:  Created " + result);
            }
            return result;
        };

        Session session = log.sessionByID.computeIfAbsent(sessionID, createSession);
        if (session.customer != customer) {
            //
            // NOTE:  You're not required to do this kind of validation,
            // but you're welcome to.  Sometimes checkign file integrity
            // can help catch bugs.  This is mostly being checked here to
            // reinforce the existence constraint in our specification.
            //
            throw new ParseException(inputFile, lineNumber, 
                                     "customer " + customer + " != " + session.customer);
        }


        // When you have a customer, be sure to:
            customer.addSession(session);
    }

    private void processViewEntry(String[] words) throws ParseException
    {
        //similar to processStartEntry, should store relevant view
        //data.
        if (words.length != VIEW_NUM_FIELDS)
        {
            throw new ParseException(inputFile, lineNumber,
                    "view tag with " + words.length + " words");
        }
            Session currentSession = log.sessionByID.get(words[VIEW_SESSION_ID]);
            int price = Integer.parseInt(words[VIEW_PRICE]);
            Product currentProduct = new Product(words[VIEW_PRODUCT_ID], price);
            View view = new View(currentProduct);
            currentSession.addView(view);



    }

    private void processBuyEntry(String[] words) throws ParseException
    {
        //similar to processStartEntry, should store relevant purchases
        //data.
            if (words.length != BUY_NUM_FIELDS)
            {
                throw new ParseException(inputFile, lineNumber,
                        "buy tag with " + words.length + " words");
            }

            Session currentSession = log.sessionByID.get(words[BUY_SESSION_ID]);
            int price = Integer.parseInt(words[BUY_PRICE]);
            Product currentProduct = new Product(words[BUY_PRODUCT_ID], price);


            int quantity = Integer.parseInt(words[BUY_QUANTITY]);
    }

    private void processEndEntry(final String[] words) throws ParseException
    {
        if (words.length != END_NUM_FIELDS)
        {
            throw new ParseException(inputFile, lineNumber, 
                                     "end tag with " + words.length + " words");
        }

        Session currentSession = log.sessionByID.get(words[END_SESSION_ID]);
    }

    /*
     * Called by parse() below, this method processes the data using
     * the methods above.
     */
    private void processLine(String line) throws ParseException {
        final String[] words = line.split("\\h");

        if (words.length == 0) {
            // Skip blank lines
            return;
        }

        switch (words[0])
        {
            case START_TAG:
                processStartEntry(words);
                break;
            case VIEW_TAG:
                processViewEntry(words);
                break;
            case BUY_TAG:
                processBuyEntry(words);
                break;
            case END_TAG:
                processEndEntry(words);
                break;
            default:
                throw new ParseException(inputFile, lineNumber, 
                                         "Unrecognized tag " + words[0]);
        }
    }

    /**
     * Parse our file, and create a Log with the parsed results.
     */
    public Log parse() throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            while (true) {
                String line = reader.readLine();
                if (line == null) {     // End of file
                    break;
                }
                lineNumber++;
                processLine(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return log;
    }
}
