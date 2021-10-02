package robots.controllers;

import robots.models.Coordinate;
import robots.models.Orientation;

import java.util.List;
import java.util.function.Consumer;

public class MockController implements IController {


    private String lastHandleCalled;

    public MockController() {

        this.lastHandleCalled = "";
    }
    @Override
    public void onPlace(Coordinate c, Orientation orientation) {
        this.lastHandleCalled = "onPlace";
    }

    @Override
    public void onMove() {
        this.lastHandleCalled = "onMove";

    }

    @Override
    public void onLeft() {
        this.lastHandleCalled = "onLeft";

    }

    @Override
    public void onRight() {
        this.lastHandleCalled = "onRight";

    }

    @Override
    public void onReport() {

        this.lastHandleCalled = "onReport";
    }

    @Override
    public void onRobot(int reference) {
        this.lastHandleCalled = "onRobot";

    }

    @Override
    public void setOnReportCallback(Consumer<List<RobotDescription>> callback) {

    }

    public String getLastHandleCalled() {
        return lastHandleCalled;
    }
}
