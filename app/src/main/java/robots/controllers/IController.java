package robots.controllers;

import robots.models.Coordinate;
import robots.models.Orientation;

import java.util.List;
import java.util.function.Consumer;

public interface IController {

    void onPlace(Coordinate c, Orientation orientation);
    void onLeft();
    void onRight();
    void onReport();
    void onRobot(int reference);
    void setOnReportCallback(Consumer<List<RobotDescription>> callback);
}
