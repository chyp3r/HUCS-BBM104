import javafx.application.Platform;
import javafx.scene.layout.Pane;

class GravityManager {
    /**
     * Checks gravity every 800 milliseconds
     * 
     * @param pane    Main pane object that holds all objects
     * @param drill   Main drill object for checking of gravity 
     * @param gameMap Object belonging to the GameMap class where the navigated
     *                tiles are kept
     */
    public static void gravityManager(Pane pane, Drill drill, GameMap gameMap) {
        Runnable task = () -> {
            while (TimeManager.getTime() > 0) {
                Platform.runLater(() -> {
                    if (drill.isTouchGround(gameMap) != true) {
                        drill.changeDrillPosition(drill.getLastColIndex(), drill.getLastRowIndex() + 1,
                                gameMap.getMainMap(), drill.getDowntDrillImage());
                    }
                });
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                }
            }
        };
        new Thread(task).start();
    }
}
