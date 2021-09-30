package robots.models;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {

        // Enforce positive indices only
        if (x < 0 || y < 0) {
            throw new IllegalStateException("Must not supply negative indices to Coordinate class.");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Provide equality based on x and y attributes
    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Coordinate)) {
           return false;
       }

       Coordinate other = (Coordinate) obj;

       return this.x == other.getX() && this.y == other.getY();
    }
}
