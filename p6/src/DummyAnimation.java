
public final class DummyAnimation implements Animatable {
    public int counter = 0;
    private int animationPeriod;
    private WorldModel world;

    public DummyAnimation() {
        this.animationPeriod = animationPeriod;
        this.counter = counter;
        this.world = world;
    }

    @Override
    public int getAnimationPeriod() {
        return this.animationPeriod;
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
