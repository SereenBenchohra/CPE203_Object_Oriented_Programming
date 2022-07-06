import edu.calpoly.testy.Testy;
import java.util.List;
import java.util.ArrayList;
import edu.calpoly.testy.Assert;

/**
 * This class contains unit tests for Minecraft 2: Electric Boogaloo.
 */
public class TestCases {

    private void loadImagesTest() {
        // This will fail if an image name is misspelled.  By doing this
        // here, we make checkgit test image loading.

        Assert.assertTrue(VirtualWorld.newSmith() != null);
        Assert.assertTrue(VirtualWorld.newBlob() != null);
        Assert.assertTrue(VirtualWorld.newMiner() != null);
        Assert.assertTrue(VirtualWorld.newMinerFull() != null);
        Assert.assertTrue(VirtualWorld.newObstacle() != null);
        Assert.assertTrue(VirtualWorld.newOre() != null);
        Assert.assertTrue(VirtualWorld.newQuake() != null);
       
    }


    private void checkAnimation() {
        DummyAnimation Dummy = new DummyAnimation();
        Animation animation = new Animation(Dummy, 30);
        EventSchedule event = new EventSchedule();
        event.scheduleEvent(null, animation, 0);
        event.processEvents(0.1);
        Assert.assertEquals(30, Dummy.counter);
    }

    /**
     * Run the tests.
     *
     * @return The number of failures.
     */
    public int runTests() {
        return Testy.run(
                () -> loadImagesTest(),
                () -> checkAnimation()
        );
    }
}
