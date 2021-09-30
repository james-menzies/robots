package robots.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void testEqualsFunctionality() {
        Coordinate c1 = new Coordinate(2, 3);
        Coordinate c2 = new Coordinate(2, 3);

        assertEquals("Coordinates with equal x and y properties should be equal",
                c1, c2);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalXAttribute() {
        new Coordinate(-1, 2);
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalYAttribute() {
        new Coordinate(1, -2);
    }
}