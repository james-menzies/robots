package robots.views;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import robots.controllers.MockController;

import static org.junit.Assert.*;

public class DisplayTest {

    private MockController controller;

    @Before
    public void before() {

        controller = new MockController();
        Display.setController(controller);
    }

    @Test
    public void zeroArgumentCommandsAreHandled() {
        // Verify that 'left', 'right', 'move' and 'report' all get called.

        Display.dispatch("LEFT");
        assertEquals("onLeft", controller.getLastHandleCalled());

        Display.dispatch("RIGHT");
        assertEquals("onRight", controller.getLastHandleCalled());

        Display.dispatch("MOVE");
        assertEquals("onMove", controller.getLastHandleCalled());

        Display.dispatch("REPORT");
        assertEquals("onReport", controller.getLastHandleCalled());
    }

    @Test
    public void invalidCommandIsIgnored() {

        Display.dispatch("INVALID");
        assertNull(controller.getLastHandleCalled());
    }

    @Test
    public void validRobotCommandIsHandled() {
        Display.dispatch("ROBOT 2");
        assertEquals("onRobot", controller.getLastHandleCalled());
    }

    @Test
    public void invalidRobotCommandIsIgnored() {
        Display.dispatch("ROBOT TWO");
        assertNull(controller.getLastHandleCalled());
    }

    @Test
    public void validPlaceCommandIsHandled() {
        Display.dispatch("PLACE 3,3,NORTH");
        assertEquals("onPlace", controller.getLastHandleCalled());
    }

    @Test
    public void invalidPlaceCommandIsIgnored() {
        Display.dispatch("PLACE 2,ONE,NORTHWEST");
    }

    @Test
    public void commandsWithArgumentsMustHaveArguments() {
        Display.dispatch("PLACE");
        assertNull(controller.getLastHandleCalled());

        Display.dispatch("ROBOT");
        assertNull(controller.getLastHandleCalled());
    }

    @After
    public void after() {
        controller = null;
    }
}