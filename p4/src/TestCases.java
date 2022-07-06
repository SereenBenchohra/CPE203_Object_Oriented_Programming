import edu.calpoly.testy.Testy;
import java.util.List;
import java.util.ArrayList;
import edu.calpoly.testy.Assert;
import java.util.Collections;

/**
 * This class contains unit tests for Minecraft 2: Electric Boogaloo.
 */
public class TestCases {

    private void loadImagesTest() {
        // This will fail if an image name is misspelled.  By doing this
        // here, we make checkgit test image loading.
        VirtualWorld.makeSmiths();
        VirtualWorld.makeVeins();
        VirtualWorld.makeBlobs();
        VirtualWorld.makeMiners();
        VirtualWorld.makeMinerFull();
        VirtualWorld.makeObstacles();
        VirtualWorld.makeOres();
        VirtualWorld.makeQuakes();
    }

    private void checkLenImages() {
        Assert.assertTrue(VirtualWorld.blobTiles != null);
        Assert.assertTrue(VirtualWorld.quakeTiles != null);
        Assert.assertTrue(VirtualWorld.minerFullTiles != null);
        Assert.assertTrue(VirtualWorld.obstacleTiles != null);
        Assert.assertTrue(VirtualWorld.oreTiles != null);
        Assert.assertTrue(VirtualWorld.minerTiles != null);
        Assert.assertTrue(VirtualWorld.veinTiles != null);
        Assert.assertTrue(VirtualWorld.blacksmithTiles != null);
    }

    private void checkAnimation() {
        DummyAnimation test = new DummyAnimation();
        Animation animation = new Animation(test, 30);
        EventSchedule event = new EventSchedule();
        event.scheduleEvent(null, animation, 0);
        event.processEvents(0.1);
        Assert.assertEquals(30, test.counter);
    }

    /**
     * Run the tests.
     *
     * @return The number of failures.
     */
    public int runTests() {
        return Testy.run(
                () -> loadImagesTest(),
                () -> checkLenImages(),
                () -> checkAnimation()
        );
    }
}
