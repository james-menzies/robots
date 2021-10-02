package robots.views;

import robots.controllers.Controller;
import robots.controllers.RobotDescription;

import java.util.List;

public class Display {

    private static Controller controller;

    // package private so that the test class can mock out the controller.
    static void setController(Controller controller) {
        Display.controller = controller;
        Display.controller.setOnReportCallback(Display::displayCallback);
    }

    static void displayCallback(List<RobotDescription> descriptions) {

    }

    public static void run() {

    }

    static void dispatch(String command) {

    }

}
