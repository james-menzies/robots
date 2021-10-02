package robots;

import robots.controllers.Controller;
import robots.models.Table;
import robots.views.Display;

public class Main {

    public static void main(String[] args) {
        /*
        This method bootstraps the application. The height and width are hard-coded
        here, however there's no reason why a different table size could be employed.
         */

        int height = 5;
        int width = 5;

        Table table = new Table(width, height);
        Controller.getInstance().setTable(table);

        Display.run();
    }
}
