package robots.models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
