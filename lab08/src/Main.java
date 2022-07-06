
/**
 * A class to hold the main method.  Any class will do.  If you always
 * call it "Main," then you don't have to search when you want to run
 * the program.
 */
public class Main {

    private static final double DEFAULT_FPS = 4.0;  // frames/second
    private static final int DEFAULT_DELAY_AT_END = 2500;  // in ms

    private static final double FAST_FPS = 30.0;
    private static final int FAST_DELAY_AT_END = 0;

    /**
     * When you run "java main," this method is executed:
     */
    public static void main(String[] args) {
        System.out.println("Invoke with \"test\" to run test cases.");
        System.out.println("Invoke with \"-fast\" to run faster.");

        boolean test = false;
        double fps = DEFAULT_FPS;
        int delayAtEnd = DEFAULT_DELAY_AT_END;

        for (int i = 0; i < args.length; i++) {
            if ("test".equals(args[i])) {
                test = true;
            } else if ("-fast".equals(args[i])) {
                fps = FAST_FPS;
                delayAtEnd = FAST_DELAY_AT_END;
            } else {
                System.out.println("Command line argument " + args[i]
                                   + " not recognized.");
                System.exit(1);
            }
        }

        if (test) {
            TestCases tc = new TestCases(fps, delayAtEnd);
            tc.run();
        } else {
            PathingMain pathingMain = new PathingMain(fps, delayAtEnd);
            pathingMain.run();
            System.out.println();
        }
    }
}
