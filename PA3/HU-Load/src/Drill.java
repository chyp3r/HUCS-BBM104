import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Drill {
    private ImageView characterImageView;
    private int lastRowIndex;
    private int lastColIndex;
    private int moneyBank;
    private float fuel;
    private int storage;
    private float fuelConstant; // Multiplier for fuel reduction level

    // Movement images
    private final Image leftDrillImage = ImageManager.imageCropper(70, 63, 0, 0, ("/assets/drill/drill_01.png"));
    private final Image rightDrillImage = ImageManager.imageCropper(70, 58, 0, 0, ("/assets/drill/drill_55.png"));
    private final Image upDrillImage = ImageManager.imageCropper(70, 63, 0, 0, ("/assets/drill/drill_26.png"));
    private final Image downDrillImage = ImageManager.imageCropper(70, 63, 0, 0, ("/assets/drill/drill_44.png"));

    public Drill() {
        this.characterImageView = new ImageView(this.leftDrillImage);
        this.getCharacterImageView().setFitHeight(52);
        this.getCharacterImageView().setFitWidth(52);
        this.lastColIndex = 1;
        this.lastRowIndex = 1;
        this.moneyBank = 0;
        this.fuel = 1000;
        this.storage = 0;
        this.fuelConstant = 0.01f;
    }

    // Getter and Setters for attributes

    public float getFuelConstant() {
        return this.fuelConstant;
    }

    public void setFuelConstant(float con) {
        this.fuelConstant = con;
    }

    public Image getLeftDrillImage() {
        return this.leftDrillImage;
    }

    public Image getRighttDrillImage() {
        return this.rightDrillImage;
    }

    public Image getUpDrillImage() {
        return this.upDrillImage;
    }

    public Image getDowntDrillImage() {
        return this.downDrillImage;
    }

    public void setDrillImage(Image image) {
        this.characterImageView.setImage(image);
    }

    public ImageView getCharacterImageView() {
        return this.characterImageView;
    }

    public int getLastRowIndex() {
        return this.lastRowIndex;
    }

    public void setLastRowIndex(int index) {
        this.lastRowIndex = index;
    }

    public int getLastColIndex() {
        return this.lastColIndex;
    }

    public void setLastColIndex(int index) {
        this.lastColIndex = index;
    }

    public float getFuel() {
        return this.fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public int getStorage() {
        return this.storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getMoneyBank() {
        return this.moneyBank;
    }

    public void setMoneyBank(int money) {
        this.moneyBank = money;
    }

    /**
     * Controls the behavior of the drill according to keyboard keys
     * 
     * @param event   Event occurring on the keyboard
     * @param gameMap Object belonging to the GameMap class where the navigated
     *                tiles are kept
     * @param pane    Main pane object that holds all objects
     */
    public void drillPressedController(KeyEvent event, GameMap gameMap, Pane pane) {
        KeyCode keyCode = event.getCode();
        int lastColIndex = this.getLastColIndex(); // Column information of the last frame where the drill is located
                                                   // before movement
        int lastRowIndex = this.getLastRowIndex(); // Row information of the last frame where the drill is located
                                                   // before movement
        if (keyCode == KeyCode.UP) { // Upward movement

            Animations.drillUpAnim(this);
            if (lastRowIndex != 0) {
                this.movementDrill(lastColIndex, lastRowIndex - 1, gameMap, this.upDrillImage,
                        KeyCode.UP, pane);
            } else if (gameMap.currentStage != 0) { // Return to previous stage
                gameMap.getMainMap().getChildren().remove(this.getCharacterImageView());
                gameMap.currentStage--;
                this.setLastRowIndex(14);
                gameMap.loadMap(this.getLastRowIndex(), this.getLastColIndex()); // Load previous map
                gameMap.getMainMap().add(this.getCharacterImageView(), this.getLastColIndex(), 14);
            }
        } else if (keyCode == KeyCode.DOWN) { // Downward movement
            if (lastRowIndex != 14) {
                this.movementDrill(lastColIndex, lastRowIndex + 1, gameMap, this.downDrillImage,
                        KeyCode.DOWN, pane);
            } else if (gameMap.currentStage <= gameMap.getAllTileList().size() - 1) { // Create or load new down stage
                gameMap.getMainMap().getChildren().remove(this.getCharacterImageView());
                gameMap.currentStage++;
                this.setLastRowIndex(0);
                if (gameMap.currentStage == gameMap.getAllTileList().size()) // Create next stage
                    gameMap.verticalScrollMap(this);
                else
                    gameMap.loadMap(this.getLastRowIndex(), this.getLastColIndex()); // Load next stage
                gameMap.getMainMap().add(this.getCharacterImageView(), this.getLastColIndex(), 0);
            }
        } else if (keyCode == KeyCode.RIGHT) { // Rightside movement
            if (lastColIndex != 15) {
                this.movementDrill(lastColIndex + 1, lastRowIndex, gameMap, this.rightDrillImage,
                        KeyCode.RIGHT, pane);
            }
        } else if (keyCode == KeyCode.LEFT) { // Leftside movement
            if (lastColIndex != 0) {
                this.movementDrill(lastColIndex - 1, lastRowIndex, gameMap, this.leftDrillImage,
                        KeyCode.LEFT, pane);
            }
        } else if (keyCode == KeyCode.T && gameMap.currentStage == 0 && this.getLastColIndex() == 5
                && this.getLastRowIndex() == 1 && this.getMoneyBank() >= 1000) { // Buy fuel from gas station
            this.setMoneyBank(this.getMoneyBank() - 1000);
            WindowManager.setMoneyText(pane, this.getMoneyBank()); // Decrease the money
            this.setFuel(this.getFuel() + 100);
        }
    }

    /**
     * Controls the movements of the drill
     * 
     * @param col     Column information of the square to be moved
     * @param row     Column information of the square to be moved
     * @param gameMap Object belonging to the GameMap class where the navigated
     *                tiles are kept
     * @param image   Image of the drill after movement
     * @param key     Key pressed from the keyboard
     * @param pane    Main pane object that holds all objects
     */
    public void movementDrill(int col, int row, GameMap gameMap, Image image, KeyCode key, Pane pane) {
        GridPane gameGrid = gameMap.getMainMap();
        Tile tempTail = gameMap.getAllTileList().get(gameMap.currentStage)[row][col]; // Tile array for current stage
        if (tempTail.getIsDrillable() || tempTail.getIsEmpty()) { // Is movement possible?
            if (tempTail.getIsDangerous() && key != KeyCode.UP) {
                WindowManager.gameOverRed(); // Lava game over
            } else if (tempTail.getIsDrillable() && key != KeyCode.UP) { // Dril valuables
                this.setMoneyBank(
                        this.getMoneyBank() + gameMap.getAllTileList().get(gameMap.currentStage)[row][col].getWorth());
                this.setStorage(
                        this.getStorage() + gameMap.getAllTileList().get(gameMap.currentStage)[row][col].getWeight());
                WindowManager.setMoneyText(pane, this.getMoneyBank());
                WindowManager.setHaulText(pane, this.getStorage());
                gameGrid.add(Tile.createRectangle(Color.BLACK, 52, 52), col, row); // Add empty tiles
                gameMap.getAllTileList().get(gameMap.currentStage)[row][col] = new Tile(false, true, null, 0, 0, false);
                changeDrillPosition(col, row, gameGrid, image); // Change direction and position of drill
            } else if (tempTail.getIsEmpty()) { // Just move to empty tiles
                changeDrillPosition(col, row, gameGrid, image);
            }
        }
    }

    /**
     * Changes the position and image of your drill
     * 
     * @param col      Column information of the square to be moved
     * @param row      Row information of the square to be moved
     * @param gameGrid GridPane object of the object belonging to the navigated
     *                 GameMap class
     * @param image    Image of the drill after movement
     */
    public void changeDrillPosition(int col, int row, GridPane gameGrid, Image image) {
        this.fuelManager();
        gameGrid.getChildren().remove(this.getCharacterImageView());
        gameGrid.add(this.getCharacterImageView(), col, row);
        this.setLastColIndex(col);
        this.setLastRowIndex(row);
        this.setDrillImage(image);
    }

    /**
     * When summoned, fuel consumption is faster for 10 milliseconds. It is used to
     * burn fuel faster while moving.
     * 
     */
    public void fuelManager() {
        if (this.getFuel() > 0) {
            Runnable task = () -> {
                try {
                    this.setFuelConstant(8f);
                    Thread.sleep(10);
                    this.setFuelConstant(0.1f);
                } catch (InterruptedException e) {
                }
            };
            new Thread(task).start();
        }
    }

    /**
     * Updates fuel text
     * 
     * @param pane Main pane object that holds all objects
     */
    public void fuelTextManager(Pane pane) {
        Platform.runLater(() -> {
            WindowManager.setFuelText(pane, this.getFuel() - 1 * this.getFuelConstant());
            this.setFuel(this.getFuel() - 1 * this.getFuelConstant());
        });
    }

    /**
     * Checks whether the drill touches the ground
     * 
     * @param gameMap Object belonging to the GameMap class where the navigated
     *                tiles are kept
     * @return True if it touches the ground or is on the bottom line of the stage,
     *         otherwise false
     * 
     */
    public boolean isTouchGround(GameMap gameMap) {
        if (this.getLastRowIndex() != 14)
            return gameMap.getAllTileList().get(gameMap.currentStage)[this.getLastRowIndex() + 1][this
                    .getLastColIndex()].getIsEmpty() == true
                            ? false
                            : true;
        else
            return true; // If dril's on the bottom row, gravity is turned off
    }
}
