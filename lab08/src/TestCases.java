

import static edu.calpoly.testy.Assert.assertFalse;
import static edu.calpoly.testy.Assert.assertTrue;
import edu.calpoly.testy.Testy;


public class TestCases {

    private final double fps;
    private final int delayAtEnd;

    public TestCases(double fps, int delayAtEnd) {
        this.fps = fps;
        this.delayAtEnd = delayAtEnd;
    }

    private final static String[] SUCCESS_LAYOUT = new String[] {
        ".....",
        "W...G",
        "....."
    };

    private void testSuccess() {
        PathingMain m = new PathingMain(SUCCESS_LAYOUT, fps, delayAtEnd);
        boolean found = m.run();
        assertTrue("testSuccess()", found);
    }

    private final static String[] FAILURE_LAYOUT = new String[] {
        "W....",
        "..OOO",
        "..O.G"
    };

    private void testFailure() {
        PathingMain m = new PathingMain(FAILURE_LAYOUT, fps, delayAtEnd);
        boolean found = m.run();
        assertFalse("testFailure()", found);
    }


    public int run() {
        return Testy.run(
            () -> testSuccess(),
            () -> testFailure()
        );
    }

}
