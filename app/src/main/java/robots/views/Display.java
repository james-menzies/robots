package robots.views;

import robots.controllers.Controller;
import robots.controllers.IController;
import robots.controllers.RobotDescription;
import robots.models.Coordinate;
import robots.models.Orientation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Display {
    /*
    Any errors that emerge from parsing user input will be ignored. Whilst not
    best practice, there are no guidelines in the description as to how this
    situation should be handled.

    If users were meant to receive feedback on invalid input, it would be contained
    within this class.
     */
    private static IController controller;
    private static Scanner scanner;

    static {
        controller = Controller.getInstance();
        scanner = new Scanner(System.in);
        controller.setOnReportCallback(Display::displayCallback);
    }


    // package private so that the test class can mock out the controller.
    static void setController(IController controller) {
        Display.controller = controller;
        Display.controller.setOnReportCallback(Display::displayCallback);
    }

    static void displayCallback(List<RobotDescription> descriptions) {
        if (descriptions.size() == 1) {
            System.out.println(renderRobotDescription(descriptions.get(0)));
        } else {
            descriptions.forEach( description -> {
                System.out.printf("%s: %s%n", description.getName(), renderRobotDescription(description));
            });
        }
    }

    static private String renderRobotDescription(RobotDescription description) {
        return String.format("%s,%s,%s",
                description.getCoordinate().getX(),
                description.getCoordinate().getY(),
                description.getOrientation());
    }

    public static void run()
        throws java.io.IOException {

        /*
        This program will continue to read input infinitely as there's no
        instructions on how to exit the program. If such logic were to be
        implemented, it would be implemented here.

        This program could still be ended manually by sending a SIGTERM
        signal via pressing CTRL-C in the terminal.
         */

        System.out.println("Robot simulation");

        while (true) {
            System.out.print(">> ");
            String command = scanner.nextLine();
            dispatch(command);
        }

    }

    public static void dispatch(String command) {
        /*
        Programmatically dispatch the passed in command. This helps isolate
        the Display logic for testing purposes.
         */
        if (Objects.isNull(command)) {
            return;
        }

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
        /*
        Helper method to parse the argument provided to the onPlace command,
        and call it.
         */

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
        /*
        Helper method to parse the argument provided to the onRobot command,
        and call it.
         */

        try {
            int reference = Integer.parseInt(argument);
            controller.onRobot(reference);

        } catch (NumberFormatException e) {
            return;
        }
    }
}
