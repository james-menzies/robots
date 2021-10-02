package robots.models;

import java.util.HashMap;
import java.util.Map;

public class Table {

    private Coordinate tableLimit;
    private Map<Integer, PositionedEntity> registeredEntities;
    private int counter = 1;

    public Table(int width, int height) {
        this.tableLimit = new Coordinate(width - 1, height - 1);
        this.registeredEntities = new HashMap<>();
    }

    public boolean move(int reference) throws IndexOutOfBoundsException {

        if (!registeredEntities.containsKey(reference)) {
            throw new IndexOutOfBoundsException("Entity reference does not exist on table");
        }

        PositionedEntity activeEntity = registeredEntities.get(reference);
        Coordinate targetLocation = activeEntity.getTableEntity().getIntent(activeEntity.getCoordinate());

        if (targetLocation.getY() > tableLimit.getY() || targetLocation.getX() > tableLimit.getX()) {
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

    public int registerEntity(TableEntity t, Coordinate c)
            throws IllegalStateException {

        if (c.getX() > tableLimit.getX() || c.getY() > tableLimit.getY()) {
            throw new IllegalStateException("Provided coordinate is out of bounds of the table");
        }

        for (PositionedEntity entity : registeredEntities.values()) {

            if (entity.getCoordinate() == c) {
                throw new IllegalStateException("Position already occupied in table.");
            }
        }

        int newIndex = counter;
        registeredEntities.put(newIndex, new PositionedEntity(c, t));
        counter++;
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
