package robots.controllers;

import robots.models.Coordinate;
import robots.models.Orientation;
import robots.models.Robot;
import robots.models.Table;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Controller implements IController {

    private int activeRobot;
    private Map<Integer, Robot> robots;
    private Table table;
    /*
    The following code is used to apply the Singleton pattern to the Controller class.
     */
    private static Controller self;

    private Controller() {

    }

    public void setTable(Table table) {
        this.table = table;
    }

    public static Controller getInstance() {
        if (Objects.isNull(Controller.self)) {
            self = new Controller();
        }

        return self;
    }

    @Override
    public void onPlace(Coordinate c, Orientation orientation) {

    }

    @Override
    public void onMove() {

    }

    @Override
    public void onLeft() {

    }

    @Override
    public void onRight() {

    }

    @Override
    public void onReport() {

    }

    @Override
    public void onRobot(int reference) {

    }

    @Override
    public void setOnReportCallback(Consumer<List<RobotDescription>> callback) {

    }

    public RobotDescription[] getState() {
        return null;
    }
}
