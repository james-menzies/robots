package robots.models;

import java.util.HashMap;
import java.util.Map;

public class Table {

    private Coordinate tableLimit;
    private Map<Integer, PositionedEntity> registeredEntities;
    private int registeredEntityCounter = 1;

    public Table(int width, int height) {
        this.tableLimit = new Coordinate(width - 1, height - 1);
        this.registeredEntities = new HashMap<>();
    }

    private boolean coordinateOutOfRange(Coordinate coordinate) {
        return coordinate.getX() > tableLimit.getX()
                || coordinate.getY() > tableLimit.getY();
    }

    public boolean move(int reference) throws IndexOutOfBoundsException {

        if (!registeredEntities.containsKey(reference)) {
            throw new IndexOutOfBoundsException("Entity reference does not exist on table");
        }

        PositionedEntity activeEntity = registeredEntities.get(reference);
        Coordinate currentLocation = activeEntity.getCoordinate();
        Coordinate targetLocation = activeEntity.getTableEntity().getIntent(currentLocation);


        // Do nothing if move would put entity off the table.
        if (coordinateOutOfRange(targetLocation)) {
            return false;
        }

        // Do nothing if Coordinate position is already occupied.
        if(isPositionOccupied(targetLocation)) {
            return false;
        }

        activeEntity.setCoordinate(targetLocation);
        return true;
    }

    private boolean isPositionOccupied(Coordinate coordinate) {
        for (int index : registeredEntities.keySet()) {

            Coordinate entityLocation = registeredEntities.get(index).getCoordinate();
            if (coordinate.equals(entityLocation)) {
                return true;
            }
        }

        return false;
    }

    public int registerEntity(TableEntity tableEntity, Coordinate coordinate)
            throws IllegalStateException {

        if (coordinateOutOfRange(coordinate)) {
            throw new IllegalStateException("Provided coordinate is out of bounds of the table");
        }

        if (isPositionOccupied(coordinate)) {
            throw new IllegalStateException("Position already occupied in table.");
        }

        int newIndex = registeredEntityCounter;
        registeredEntities.put(newIndex, new PositionedEntity(coordinate, tableEntity));
        registeredEntityCounter++;
        return newIndex;
    }

    public Map<Integer, Coordinate> getPositions() {
        Map<Integer, Coordinate> returnValue = new HashMap<>();

        for (int index : registeredEntities.keySet()) {
            returnValue.put(index, registeredEntities.get(index).getCoordinate());
        }

        return returnValue;
    }
}
