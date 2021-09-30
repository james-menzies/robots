package robots.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    @Test
    public void testCorrectIntent() {

        Coordinate c = new Coordinate(3,3);

        Robot r1 = new Robot(Orientation.NORTH);
        Coordinate northResult = r1.getIntent(c);
        assertEquals(3, northResult.getX());
        assertEquals(4, northResult.getY());

        Robot r2 = new Robot(Orientation.EAST);
        Coordinate eastResult = r2.getIntent(c);
        assertEquals(4, eastResult.getX());
        assertEquals(3, eastResult.getY());

        Robot r3 = new Robot(Orientation.SOUTH);
        Coordinate southResult = r3.getIntent(c);
        assertEquals(3, southResult.getX());
        assertEquals(2, southResult.getY());

        Robot r4 = new Robot(Orientation.WEST);
        Coordinate westResult = r4.getIntent(c);
        assertEquals(2, westResult.getX());
        assertEquals(3, westResult.getY());
    }

    @Test
    public void testTurningLeft() {

        Robot r = new Robot(Orientation.NORTH);

        r.turn(Direction.LEFT);
        assertEquals(Orientation.WEST, r.getOrientation());

        r.turn(Direction.LEFT);
        assertEquals(Orientation.SOUTH, r.getOrientation());

        r.turn(Direction.LEFT);
        assertEquals(Orientation.EAST, r.getOrientation());

        r.turn(Direction.LEFT);
        assertEquals(Orientation.NORTH, r.getOrientation());
    }

    @Test
    public void testTurningRight() {

        Robot r = new Robot(Orientation.SOUTH);

        r.turn(Direction.RIGHT);
        assertEquals(Orientation.WEST, r.getOrientation());

        r.turn(Direction.RIGHT);
        assertEquals(Orientation.NORTH, r.getOrientation());

        r.turn(Direction.RIGHT);
        assertEquals(Orientation.EAST, r.getOrientation());

        r.turn(Direction.RIGHT);
        assertEquals(Orientation.SOUTH, r.getOrientation());
    }


    @Test
    public void testGracefulOutOfBounds() {
        /*
        This test is to ensure that should the robot produce a coordinate that
        would produce a coordinate that has a negative index, that it simply
        return the Coordinate object that was passed into the getIntent() method.
         */
        Robot r = new Robot(Orientation.SOUTH);
        Coordinate c = new Coordinate(3,0);

        assertEquals("returned Coordinate must be the same as the one passed in",
                r.getIntent(c), c);
    }
}