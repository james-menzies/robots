package robots.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TableTest {

    private Table table;
    private MockTableEntity mock1;
    private MockTableEntity mock2;

    @Before
    public void before() {

        table = new Table(5, 5);
        mock1 = new MockTableEntity();
        mock2 = new MockTableEntity();
    }


    @Test(expected = IllegalStateException.class)
    public void outOfRangePlacementThrowsException() {

        Coordinate c = new Coordinate(3,6);
        table.registerEntity(mock1, c);
    }

    @Test(expected = IllegalStateException.class)
    public void occupiedPlacementThrowsException() {

        Coordinate c = new Coordinate(2, 3);
        table.registerEntity(mock1, c);

        table.registerEntity(mock2, c);
    }

    @Test
    public void correctPositionsReturnedOnPlacement() {

        Coordinate c1 = new Coordinate(2, 2);
        Coordinate c2 = new Coordinate(3, 4);

        int mock1ID = table.registerEntity(mock1, c1);
        int mock2ID = table.registerEntity(mock2, c2);

        Map<Integer, Coordinate> returnedCoordinates = table.getPositions();
        assertEquals(returnedCoordinates.get(mock1ID), c1);
        assertEquals(returnedCoordinates.get(mock2ID), c2);
    }

    @Test
    public void validMoveCallChangesEntityPosition() {

        mock1.setIntent(1, 2);

        Coordinate updatedPosition = setLegalPlacementAndMove(new Coordinate(3,3), mock1);

        assertEquals(1, updatedPosition.getX());
        assertEquals(2, updatedPosition.getY());
    }

    @Test
    public void outOfRangeMovementIsPrevented() {

        Coordinate initial = new Coordinate(3, 3);
        mock1.setIntent(6, 2);

        Coordinate updated = setLegalPlacementAndMove(initial, mock1);
        assertEquals(initial, updated);
    }

    @Test
    public void collisionMovementIsPrevented() {
        Coordinate initial = new Coordinate(3, 3);
        table.registerEntity(mock1, new Coordinate(1, 1));

        mock2.setIntent(1, 1);
        Coordinate updated = setLegalPlacementAndMove(initial, mock2);

        assertEquals(initial, updated);
    }

    private Coordinate setLegalPlacementAndMove(Coordinate initialPlacement, TableEntity entity) {
        // helper method to set up a table move event that returns
        // the updated coordinate of the entity.
        int id = table.registerEntity(entity, initialPlacement);
        table.move(id);
        return table.getPositions().get(id);
    }

    @After
    public void after() {
        table = null;
        mock1 = null;
        mock2 = null;
    }

}