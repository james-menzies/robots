package robots.views;

import org.junit.After;
import org.junit.Before;
import robots.controllers.MockController;

import static org.junit.Assert.*;

public class DisplayTest {

    private MockController controller;

    @Before
    public void before() {

        controller = new MockController();
        Display.setController(controller);
    }


    @After
    public void after() {
        controller = null;
    }


}