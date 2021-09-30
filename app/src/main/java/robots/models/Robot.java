package robots.models;

public class Robot {

    private Orientation orientation;

    public Robot(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void turn(Direction d) {

    }

    public Coordinate getIntent(Coordinate c) {
        return null;
    }
}
