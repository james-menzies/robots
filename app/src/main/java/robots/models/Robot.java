package robots.models;

public class Robot implements TableEntity{

    // caching used for performance reasons.
    static private final Orientation[] orientations = Orientation.values();

    private Orientation orientation;

    public Robot(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void turn(Direction direction) {
        // Here we attempt to find correct index of the orientations variable
        // so that we can update the Robot's orientation.
        int targetOrientationIndex = this.orientation.ordinal();
        targetOrientationIndex = direction == Direction.LEFT ?
                targetOrientationIndex - 1 : targetOrientationIndex + 1;

        // Ensure orientation out-of-bounds does not occur.
        targetOrientationIndex = (targetOrientationIndex + 4) % 4;

        //Finally, refer to cached orientations to update orientation of robot.
        this.orientation = Robot.orientations[targetOrientationIndex];
    }

    public Coordinate getIntent(Coordinate currentPosition) {

        int updatedXPosition = currentPosition.getX();
        int updatedYPosition = currentPosition.getY();

        switch (this.orientation) {
            case EAST:
                updatedXPosition++;
                break;
            case WEST:
                updatedXPosition--;
                break;
            case NORTH:
                updatedYPosition++;
                break;
            case SOUTH:
                updatedYPosition--;
                break;
        }

        // return original Coordinate if indices are negative.
        try {
            return new Coordinate(updatedXPosition, updatedYPosition);
        } catch (IllegalStateException e) {
            return currentPosition;
        }
    }
}
