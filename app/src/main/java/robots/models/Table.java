package robots.models;

import java.util.HashMap;
import java.util.Map;

public class Table {

    private Coordinate tableLimit;
    Map<Integer, PositionedEntity> registeredEntities;

    public Table(Coordinate tableLimit) {
        this.tableLimit = tableLimit;
        this.registeredEntities = new HashMap<>();
    }

    public boolean move(int reference) {
        return false;
    }

    public int registerEntity(TableEntity t, Coordinate c)
        throws IllegalStateException {

        return 0;
    }

    public Map<Integer, Coordinate> getPositions() {
        return new HashMap<>();
    }


}
