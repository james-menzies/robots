package robots.controllers;

import robots.models.Coordinate;
import robots.models.Orientation;

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
}
