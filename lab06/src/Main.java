
import java.io.IOException;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Log file(s) not specified.");
            System.exit(1);
        }

        try {
            for (String fileName : args) {
                LogParser parser = new LogParser(new File(fileName));
                LogAnalyzer analyzer = new LogAnalyzer(parser.parse());
                analyzer.analyze();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        System.exit(0);
    }
}
