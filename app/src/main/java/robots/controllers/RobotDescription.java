package robots.controllers;

import robots.models.Coordinate;
import robots.models.Orientation;

import java.util.Objects;

public class RobotDescription {
    private String name;
    private Coordinate coordinate;
    private Orientation orientation;

    public RobotDescription(String name, Coordinate coordinate, Orientation orientation) {
        this.name = name;
        this.coordinate = coordinate;
        this.orientation = orientation;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotDescription that = (RobotDescription) o;
        return getName().equals(that.getName())
                && getCoordinate().equals(that.getCoordinate())
                && getOrientation() == that.getOrientation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCoordinate(), getOrientation());
    }
}
