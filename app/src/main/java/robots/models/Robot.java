package robots.models;

public class Robot implements TableEntity{

    // caching used for performance reasons.
    static private Orientation[] orientations = Orientation.values();

    private Orientation orientation;

    public Robot(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void turn(Direction d) {
        // Here we attempt to find correct index of the orientations variable
        // so that we can update the Robot's orientation.
        int targetOrientationIndex = this.orientation.ordinal();
        targetOrientationIndex = d == Direction.LEFT ?
                targetOrientationIndex - 1 : targetOrientationIndex + 1;

        // Ensure orientation out-of-bounds does not occur.
        targetOrientationIndex = (targetOrientationIndex + 4) % 4;

        //Finally, refer to cached orientations to update orientation of robot.
        this.orientation = Robot.orientations[targetOrientationIndex];
    }

    public Coordinate getIntent(Coordinate c) {

        int newX = c.getX();
        int newY = c.getY();

        switch (this.orientation) {
            case EAST:
                newX++;
                break;
            case WEST:
                newX--;
                break;
            case NORTH:
                newY++;
                break;
            case SOUTH:
                newY--;
                break;
        }

        // return original Coordinate if indices are negative.
        if (newX < 0 || newY < 0) return c;

        return new Coordinate(newX, newY);
    }
}
