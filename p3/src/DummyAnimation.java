// add test case to test animations

public final class DummyAnimation implements Animatable {
    public int counter = 0;
    private int animationperiod;
    private WorldModel world;

    public DummyAnimation() {
        this.animationperiod = animationperiod;
        this.counter = counter;
        this.world = world;
    }

    @Override
    public int getAnimationPeriod() {
        return this.animationperiod;
    }

    @Override
    public void nextImage() {
        counter += 1;
    }

    @Override
    public Animation createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }
}
