import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class WindowManager {
    /**
     * Sets the given stage height and width
     * 
     * @param stage Stage to be set
     * @return New stage
     */
    public static Stage stageManager(Stage stage) {
        stage.setTitle("HU-Load");
        stage.setMaxHeight(52 * 15.6);
        stage.setMaxWidth(52 * 16.2);
        stage.setMinHeight(52 * 15.6);
        stage.setMinWidth(52 * 16.2);
        return stage;
    }

    /**
     * Add text to given pane
     * 
     * @param gameMap Object belonging to the GameMap class where the navigated
     *                tiles are kept
     * @return created pane with text
     */
    public static Pane createPanewithText(GameMap gameMap) {
        final Pane tempPane = new Pane();
        final Text fuelText = new Text("\nfuel:0,0");
        final Text haulText = new Text("\n\nhaul:0,0");
        final Text moneyText = new Text("\n\n\nmoney:0,0");
        fuelText.setFont(Font.font(20));
        fuelText.setFill(Color.WHITE);
        haulText.setFont(Font.font(20));
        haulText.setFill(Color.WHITE);
        moneyText.setFont(Font.font(20));
        moneyText.setFill(Color.WHITE);
        tempPane.getChildren().addAll(gameMap.getMainMap(), fuelText, haulText, moneyText); // Add texts to pane
        return tempPane;
    }

    /**
     * Change fuel on UI
     * 
     * @param pane Main pane of game
     * @param data Fuel data
     */
    public static void setFuelText(Pane pane, float data) {
        pane.getChildren().remove(1);
        final Text fuelText = new Text(String.format("\nfuel:%.3f", data));
        fuelText.setFont(Font.font(20));
        fuelText.setFill(Color.WHITE);
        pane.getChildren().add(1, fuelText);
    }

    /**
     * Change haul on UI
     * 
     * @param pane Main pane of game
     * @param data Haul data
     */
    public static void setHaulText(Pane pane, float data) {
        pane.getChildren().remove(2);
        final Text haulText = new Text(String.format("\n\nhaul:%.1f", data));
        haulText.setFont(Font.font(20));
        haulText.setFill(Color.WHITE);
        pane.getChildren().add(2, haulText);
    }

    /**
     * Change money on UI
     * 
     * @param pane Main pane of game
     * @param data Money data
     */
    public static void setMoneyText(Pane pane, float data) {
        pane.getChildren().remove(3);
        final Text moneyText = new Text(String.format("\n\n\nmoney:%.1f", data));
        moneyText.setFont(Font.font(20));
        moneyText.setFill(Color.WHITE);
        pane.getChildren().add(3, moneyText);
    }

    /**
     * Create game over from lava
     */
    public static void gameOverRed() {
        final StackPane pane = new StackPane();
        final Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font(50));
        gameOverText.setFill(Color.WHITE);
        pane.getChildren().add(gameOverText);
        pane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        StackPane.setAlignment(gameOverText, Pos.CENTER);
        final Scene scene = new Scene(pane);
        Main.getMainStage().setScene(scene);

    }

    /**
     * Create game over from fuel
     * 
     * @param money last money data from drill
     */
    public static void gameOverGreen(int money) {
        final StackPane pane = new StackPane();
        LeadBoard.addLeadData(String.format("Game Number %d", LeadBoard.getLeadData().length + 1), money); // Add last
                                                                                                           // score
        final String[] getData = LeadBoard.getLeadData(); // Get last scores
        final Text gameOverText = new Text(String.format("GAME OVER\nCOLLECTED MONEY:%d", money));
        gameOverText.setFont(Font.font(50));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(gameOverText);
        StackPane.setAlignment(gameOverText, Pos.CENTER);

        // Create last scores tab to top left of screen
        final Text lasText = new Text("Last Scores");
        lasText.setFont(Font.font(20));
        lasText.setFill(Color.WHITE);
        pane.getChildren().add(lasText);
        StackPane.setAlignment(lasText, Pos.TOP_LEFT);

        // New line operand for every diffrenet points
        for (int i = 0; (i < getData.length); i++) {
            if (i == 10)
                break; // Just take last 10 try
            String temp = "";
            for (int j = 0; j < i; j++) {
                temp += "\n";
            }
            final Text boardText = new Text(String.format("\n%s%s", temp, getData[getData.length - 1 - i]));
            boardText.setFont(Font.font(20));
            boardText.setFill(Color.WHITE);
            pane.getChildren().add(boardText);
            StackPane.setAlignment(boardText, Pos.TOP_LEFT);
        }

        pane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

        final Scene scene = new Scene(pane);
        Main.getMainStage().setScene(scene); // Change scene
    }
}