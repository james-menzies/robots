package robots.models;

public class MockTableEntity implements TableEntity {
    /*
     The intent of this class is to stub out the Robot class for testing
     purposes. It allows for arbitrary selection of a coordinate for the
     getIntent() method so tests can be easily manipulated.
     */

    private Coordinate intent;

    public MockTableEntity() {
        this.intent = new Coordinate(0,0);
    }

    @Override
    public Coordinate getIntent(Coordinate currentPosition) {
        return this.intent;
    }

    public void setIntent(int x, int y) {

        this.intent = new Coordinate(x, y);
    }
}
