package robots.views;

import robots.controllers.Controller;
import robots.controllers.IController;
import robots.controllers.RobotDescription;
import robots.models.Coordinate;
import robots.models.Orientation;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Display {
    /*
    Any errors that emerge from parsing user input will be ignored. Whilst not
    best practice, there are no guidelines in the description as to how this
    situation should be handled.

    If users were meant to receive feedback on invalid input, it would be contained
    within this class.
     */
    static {
        controller = Controller.getInstance();
    }

    private static IController controller;

    // package private so that the test class can mock out the controller.
    static void setController(IController controller) {
        Display.controller = controller;
        Display.controller.setOnReportCallback(Display::displayCallback);
    }

    static void displayCallback(List<RobotDescription> descriptions) {

    }

    public static void run() {

    }

    public static void dispatch(String command) {

        if (command.length() == 0) {
            return;
        }

        String[] tokens = command.split(" ");
        String operation = tokens[0].toUpperCase(Locale.ROOT);
        String argument = tokens.length > 1 ? tokens[1] : null;

        switch (operation) {
            case "LEFT":
                controller.onLeft();
                break;
            case "RIGHT":
                controller.onRight();
                break;
            case "REPORT":
                controller.onReport();
                break;
            case "MOVE":
                controller.onMove();
                break;
            case "PLACE":
                Display.processOnPlace(argument);
                break;
            case "ROBOT":
                Display.processOnRobot(argument);
                break;
            default:
                return;
        }


    }

    private static void processOnPlace(String argument) {

        if (Objects.isNull(argument)) {
            return;
        }

        String [] tokens = argument.split(",");

        if (tokens.length != 3) {
            return;
        }

        try {
            int x = Integer.parseInt(tokens[0]);
            int y = Integer.parseInt(tokens[1]);
            Coordinate coordinate = new Coordinate(x, y);
            Orientation orientation = Orientation.valueOf(tokens[2]);
            controller.onPlace(coordinate, orientation);
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    private static void processOnRobot(String argument) {
        try {
            int reference = Integer.parseInt(argument);
            controller.onRobot(reference);

        } catch (NumberFormatException e) {
            return;
        }
    }
}
