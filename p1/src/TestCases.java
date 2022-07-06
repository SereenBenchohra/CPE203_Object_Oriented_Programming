

import edu.calpoly.testy.Testy;
import java.util.List;
import java.util.ArrayList;


/**
 * This class contains unit tests for Minecraft 2: Electric Boogaloo.
 */
public class TestCases {

    private void loadImagesTest() {
        // This will fail if an image name is misspelled.  By doing this
        // here, we make checkgit test image loading.
        VirtualWorld.loadEntityImages();
    }

    /**
     * Run the tests.
     *
     * @return The number of failures.
     */
    public int runTests() {
        return Testy.run(
                () -> loadImagesTest()
        );
    }
}
