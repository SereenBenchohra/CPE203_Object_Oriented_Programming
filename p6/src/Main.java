
import edu.calpoly.spritely.SpriteWindow;
/**
 * The main entry point to our program.
 **/

public class Main {

    private static void usage() {
        System.out.println("Usage:  java Main [args]\n");
        System.out.println();
        System.out.println("    -text    Force program to run in text mode");
        System.out.println("    -fast    Run simuation 2x faster");
        System.out.println("    -faster  Run simuation 4x faster");
        System.out.println("    -fastest Run simuation 10x faster");
        System.out.println("    -test    Run unit tests");
        System.out.println("");
        System.exit(1);
    }

    private static void runTestsAndExit() {
        TestCases tests = new TestCases();
        int failed = tests.runTests();
        if (failed > 0) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) 
	{
        double timeScale = 1.0;
        for (String arg : args) {
            if ("-text".equals(arg)) {
                SpriteWindow.setTextMode();
            } else if ("-fast".equals(arg)) {
                timeScale = 2.0;
            } else if ("-faster".equals(arg)) {
                timeScale = 4.0;
            } else if ("-fastest".equals(arg)) {
                timeScale = 10.0;
            } else if ("-test".equals(arg)) {
                runTestsAndExit();
            } else {
                usage();
            }
        }
		//runTestsAndExit();
	    VirtualWorld vWorld = new VirtualWorld(timeScale);
        vWorld.runSimulation();
        System.exit(0);
    }

}
