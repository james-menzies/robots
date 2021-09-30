package robots.models;

public class PositionedEntity {

    private Coordinate coordinate;
    final private TableEntity tableEntity;

    public PositionedEntity(Coordinate coordinate, TableEntity tableEntity) {
        this.coordinate = coordinate;
        this.tableEntity = tableEntity;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public TableEntity getTableEntity() {
        return tableEntity;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
