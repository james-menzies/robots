package robots.controllers;

import robots.models.*;

import java.util.*;
import java.util.function.Consumer;

public class Controller implements IController {

    private int activeRobot;
    private Map<Integer, Robot> robots;
    private Table table;
    private Consumer<List<RobotDescription>> onReportCallback;

    /*
    The following code is used to apply the Singleton pattern to the Controller class.
     */
    private static Controller self;

    private Controller() {
        robots = new HashMap<>();
        onReportCallback = (robots) -> {
        };
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
        Robot robot = new Robot(orientation);
        try {
            int id = table.registerEntity(robot, c);
            robots.put(id, robot);
            if (activeRobot == 0) {
                activeRobot = id;
            }
        } catch (IllegalStateException e) {
            return;
        }
    }

    @Override
    public void onMove() {
        if (activeRobot == 0) {
            return;
        }

        table.move(activeRobot);
    }

    @Override
    public void onLeft() {

        if (activeRobot == 0) {
            return;
        }

        robots.get(activeRobot).turn(Direction.LEFT);
    }

    @Override
    public void onRight() {

        if (activeRobot == 0) {
            return;
        }

        robots.get(activeRobot).turn(Direction.RIGHT);
    }

    @Override
    public void onReport() {
        onReportCallback.accept(getState());
    }

    @Override
    public void onRobot(int reference) {
        if (!robots.containsKey(reference)) {
            return;
        }

        this.activeRobot = reference;
    }

    @Override
    public void setOnReportCallback(Consumer<List<RobotDescription>> callback) {
        this.onReportCallback = callback;
    }

    public List<RobotDescription> getState() {
        Map<Integer, Coordinate> positions = table.getPositions();
        ArrayList<RobotDescription> returnValue = new ArrayList<>();
        for (int key : robots.keySet()) {
            returnValue.add(
                    new RobotDescription(
                            String.valueOf(key),
                            positions.get(key),
                            robots.get(key).getOrientation()
                    )
            );
        }

        return returnValue;
    }
}
