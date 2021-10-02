package robots;

import org.junit.Test;
import robots.controllers.Controller;
import robots.controllers.RobotDescription;
import robots.models.Coordinate;
import robots.models.Orientation;
import robots.models.Table;
import robots.views.Display;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntegrationTests {

    @Test
    public void sampleRun1() {

        String [] commands = new String[] {
                "PLACE 0,0,NORTH",
                "MOVE",
                "REPORT"
        };
        List<RobotDescription> results = new ArrayList<>();
        results.add(new RobotDescription("1", new Coordinate(0, 1), Orientation.NORTH));

        runSample(commands, results);
    }

    @Test
    public void sampleRun2() {
        String [] commands = new String[] {
                "PLACE 0,0,NORTH",
                "LEFT",
                "REPORT"
        };

        List<RobotDescription> results = new ArrayList<>();
        results.add(new RobotDescription("1", new Coordinate(0,0), Orientation.WEST));

        runSample(commands, results);
    }

    @Test
    public void sampleRun3() {
        String [] commands = new String[] {
                "PLACE 1,2,EAST",
                "MOVE",
                "MOVE",
                "LEFT",
                "MOVE",
                "REPORT"
        };

        List<RobotDescription> results = new ArrayList<>();
        results.add(new RobotDescription("1", new Coordinate(3,3), Orientation.NORTH));

        runSample(commands, results);
    }

    @Test
    public void sampleRun4() {
        // Custom case to test multiple robots
        String [] commands = new String[] {
                "PLACE 1,1,SOUTH",
                "RIGHT",
                "RIGHT",
                "MOVE",
                "PLACE 2,2,WEST",
                "ROBOT 2",
                "MOVE",
                "LEFT",
                "MOVE"
        };

        List<RobotDescription> results = new ArrayList<>(
        );


        results.add(new RobotDescription("1", new Coordinate(1,2), Orientation.NORTH));
        results.add(new RobotDescription("2", new Coordinate(2, 1), Orientation.SOUTH));

        runSample(commands, results);
    }

    private void runSample(String[] commands, List<RobotDescription> results) {
        Table table = new Table(5, 5);
        Controller.getInstance().setTable(table);
        for (String command : commands) {
            Display.dispatch(command);
        }

        assertEquals(results, Controller.getInstance().getState());
    }
}
