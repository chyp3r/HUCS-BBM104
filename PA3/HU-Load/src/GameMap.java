import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class GameMap {
    private GridPane mainMap; // Main game map
    private int notSoilCount; // Keeps the number of non-ground tiles
    private final String[] valuables; // Valuables data read from file
    private ArrayList<Tile[][]> allTileList; // All tiles created in the entire game
    public int currentStage;

    public GridPane getMainMap() {
        return this.mainMap;
    }

    public ArrayList<Tile[][]> getAllTileList() {
        return this.allTileList;
    }

    public GameMap() {
        this.currentStage = 0;
        this.allTileList = new ArrayList<Tile[][]>();
        this.valuables = FileIO.readFile("src\\assets\\extras\\atributes_of_valuables.txt", true, true);
        this.starterMapGenerator();

    }

    /**
     * Creates the map on earth
     */
    public void starterMapGenerator() {
        this.mainMap = new GridPane();
        Tile[][] tileList = new Tile[15][16]; // Tile array for this stage
        this.notSoilCount = 0;
        this.mainMap.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY))); // Change
                                                                                                                     // background
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 16; col++) {
                int randomTileConst = (new Random()).nextInt(2) + 1; // Create constant for create different type of top
                                                                     // tile
                int randomTileConst2 = (new Random()).nextInt(1) + 1; // Create constant for create different type of
                                                                      // obstacle tile
                if (row == 1 && col == 5) { // Create gas station
                    ImageView croppedImage = new ImageView(
                            ImageManager.imageCropper(150, 150, 0, 0, "/assets/extras/sprite/Overground.png"));
                    croppedImage.setFitWidth(52);
                    croppedImage.setFitHeight(52);
                    Tile tempTile = new Tile(false, true, croppedImage, 0, 0, false);
                    this.mainMap.add(tempTile.getTileImageView(), col, row);
                    tileList[row][col] = tempTile;
                } else if (row < 2) { // Create sky
                    this.mainMap.add(Tile.createRectangle(Color.BLUE, 52, 52), col, row);
                    tileList[row][col] = new Tile(false, true, null, 0, 0, false);
                } else if (row == 2) { // Create grass tiles
                    Tile tempTile = new Tile(true, false,
                            new ImageView(new Image(String.format("/assets/underground/top_0%s.png", randomTileConst),
                                    52, 52, false, false)),
                            0, 0,
                            false);
                    this.mainMap.add(tempTile.getTileImageView(), col, row);
                    tileList[row][col] = tempTile;
                } else if (row > 2 && col != 0 && col != 15) { // Create valuables
                    Tile tempTile = mineItemGenerator();
                    this.mainMap.add(tempTile.getTileImageView(), col, row);
                    tileList[row][col] = tempTile;
                } else if (col == 0 || col == 15) { // Create obstcales
                    Tile tempTile = new Tile(false, false,
                            new ImageView(
                                    new Image(String.format("/assets/underground/obstacle_0%s.png", randomTileConst2),
                                            52, 52, false, false)),
                            0, 0,
                            false);
                    this.mainMap.add(tempTile.getTileImageView(), col, row);
                    tileList[row][col] = tempTile;
                }
            }
        }
        this.allTileList.add(tileList); // Add current tile array to global array
    }

    /**
     * Creates new stages underground. Works like starterMapGenerator() but does not
     * generate sky.
     * 
     * @param drill Drill object from game
     */
    public void verticalScrollMap(Drill drill) {
        this.mainMap.getChildren().clear(); // Delete tiles from previous stage
        Tile[][] tileList = new Tile[15][16]; // Tile array for this stage
        this.notSoilCount = 0;
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 16; col++) {
                int randomTileConst = (new Random()).nextInt(1) + 1; // Create constant for create different type of
                                                                     // obstacle tile
                if (drill.getLastColIndex() == col && drill.getLastRowIndex() == row) { // Drill current tile
                    this.mainMap.add(Tile.createRectangle(Color.BLACK, 52, 52), col, row);
                    tileList[row][col] = new Tile(false, true, null, 0, 0, false);
                } else if (col == 0 || col == 15) { // Create obstacles
                    Tile tempTile = new Tile(false, false,
                            new ImageView(
                                    new Image(String.format("/assets/underground/obstacle_0%s.png", randomTileConst),
                                            52, 52, false, false)),
                            0, 0,
                            false);
                    this.mainMap.add(tempTile.getTileImageView(), col, row);
                    tileList[row][col] = tempTile;
                } else if (col != 0 && col != 15) { // Generate valuables, soils, lavas and obstacles
                    Tile tempTile = mineItemGenerator();
                    this.mainMap.add(tempTile.getTileImageView(), col, row);
                    tileList[row][col] = tempTile;
                }
            }
        }
        this.allTileList.add(tileList); // Add current tile array to global array
    }

    /**
     * Loads previously created stages
     * 
     * @param emptyRow Row data for tile trying to return
     * @param emptyCol Column data for tile trying to return
     */
    public void loadMap(int emptyRow, int emptyCol) {
        this.mainMap.getChildren().clear(); // Delete tiles from previous stage
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 16; col++) {
                Tile tempTile = this.allTileList.get(currentStage)[row][col]; // Take current stage's tile 
                if (emptyRow == row && emptyCol == col) {
                    this.mainMap.add(Tile.createRectangle(Color.BLACK, 52, 52), col, row);
                    this.allTileList.get(currentStage)[row][col] = new Tile(false, true, null, 0, 0, false);
                } else if (tempTile.getTileImageView() != null)
                    this.mainMap.add(tempTile.getTileImageView(), col, row);
                else if (row < 2 && currentStage == 0)
                    this.mainMap.add(Tile.createRectangle(Color.BLUE, 52, 52), col, row);
                else
                    this.mainMap.add(Tile.createRectangle(Color.BLACK, 52, 52), col, row);
            }
        }
    }

    /**
     * Generate valuables, soils, lavas and obstacles
     * 
     * @return Generated tile
     */
    public Tile mineItemGenerator() {
        Random random = new Random();
        int randomItem = random.nextInt(33); // Random tile determination
        int randomTileConst = random.nextInt(3) + 1; // Create constant for create different type of tiles
        if (randomItem >= 12 || notSoilCount > 15 * 8 - 1) { // Filling at least half of the map with soil
            return new Tile(true, false,
                    new ImageView(new Image(String.format("/assets/underground/soil_0%s.png", randomTileConst), 52, 52,
                            false, false)),
                    0, 0,
                    false);

        } else if (randomItem == 11) { // Create obstacle
            notSoilCount++;
            return new Tile(false, false,
                    new ImageView(
                            new Image(String.format("/assets/underground/obstacle_0%s.png", randomTileConst),
                                    52, 52, false, false)),
                    0, 0,
                    false);
        } else if (randomItem == 10) { // Create lava
            notSoilCount++;
            return new Tile(true, false,
                    new ImageView(
                            new Image(String.format("/assets/underground/lava_0%s.png", randomTileConst), 52, 52,
                                    false, false)),
                    0, 0,
                    true);

        } else { // Create valuables from extra's
            String[] data = valuables[randomItem + 1].split("\t");
            notSoilCount++;
            while (true) {
                if ((int) (Math.abs(Integer.parseInt(data[4].split(" ")[0])) / 400) <= currentStage) { // Check depth of current stage
                    break;
                } else {
                    randomItem = random.nextInt(9); // For wrong depth choose another tile
                    data = valuables[randomItem + 1].split("\t");
                }
            }
            return new Tile(true, false,
                    new ImageView(new Image(String.format("/assets/underground/valuable_%s.png", data[0]), 52, 52,
                            false, false)),
                    Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                    false); // Return new tile
        }
    }
}
