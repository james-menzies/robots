package robots.views;

import robots.controllers.IController;
import robots.controllers.RobotDescription;

import java.util.List;

public class Display {

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

    static void dispatch(String command) {

    }

}
