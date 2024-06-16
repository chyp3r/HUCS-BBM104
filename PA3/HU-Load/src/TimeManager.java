import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class TimeManager {
    private static int time = 1;

    public static int getTime() {
        return time;
    }

    /**
     * Starts game universe time when the game starts
     * 
     * @param pane    Main pane object that holds all objects
     * @param drill   Main drill object for checking of gravity
     * @param gameMap Object belonging to the GameMap class where the navigated
     *                tiles are kept
     */
    public static void startTime(Pane pane, Drill drill, GameMap gameMap) {
        GravityManager.gravityManager(pane, drill, gameMap); // Start gravity of universe
        Runnable task = () -> {
            while (time > 0 && drill.getFuel() > 0) {
                time++;
                drill.fuelTextManager(pane);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            Platform.runLater(() -> {
                WindowManager.gameOverGreen(drill.getMoneyBank());
            });
        };
        new Thread(task).start();
    }
}
