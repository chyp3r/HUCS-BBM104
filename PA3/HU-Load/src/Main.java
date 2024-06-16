import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Main extends Application {
    private Drill drill;
    private GameMap gameMap;
    private static Pane mainPane;
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.drill = new Drill();
        this.gameMap = new GameMap();

        gameMap.getMainMap().setGridLinesVisible(false);
        gameMap.getMainMap().add(this.drill.getCharacterImageView(), 1, 1);

        mainPane = WindowManager.createPanewithText(gameMap);
        final Scene scene = new Scene(mainPane);
        scene.setOnKeyPressed((KeyEvent event) -> {
            this.drill.drillPressedController(event, gameMap, mainPane);
        });
        mainStage = primaryStage;
        primaryStage = WindowManager.stageManager(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        TimeManager.startTime(mainPane, this.drill, gameMap);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
