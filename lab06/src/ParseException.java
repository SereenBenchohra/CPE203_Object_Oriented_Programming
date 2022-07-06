
import java.io.File;
import java.io.IOException;

/**
 * Am exception to throw when the syntax of the log file is incorrect.
 */
public class ParseException extends IOException {

    public ParseException(File file, int lineNumber, String message) {
        super("file " + file + " line " + lineNumber + ":  " + message);
    }
}
