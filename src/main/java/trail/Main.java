package trail;

import trail.controller.GameController;
import trail.controller.GameException;

public class Main {
    static void main() throws GameException {
        GameController gameController = new GameController();
        gameController.run();
    }
}
