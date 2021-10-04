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
        Coordinate targetLocation = activeEntity.getTableEntity().getIntent(activeEntity.getCoordinate());

        if (coordinateOutOfRange(targetLocation)) {
            return false;
        }

        for (int index : registeredEntities.keySet()) {

            if (targetLocation.equals(registeredEntities.get(index).getCoordinate())) {
                return false;
            }
        }

        activeEntity.setCoordinate(targetLocation);
        return true;
    }

    public int registerEntity(TableEntity tableEntity, Coordinate coordinate)
            throws IllegalStateException {

        if (coordinateOutOfRange(coordinate)) {
            throw new IllegalStateException("Provided coordinate is out of bounds of the table");
        }

        for (PositionedEntity positionedEntity : registeredEntities.values()) {

            if (positionedEntity.getCoordinate() == coordinate) {
                throw new IllegalStateException("Position already occupied in table.");
            }
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
