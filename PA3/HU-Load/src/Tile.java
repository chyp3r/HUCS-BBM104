import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {
    private boolean isDrillable;
    private boolean isEmpty;
    private boolean isDangerous;
    private ImageView tileImageView;
    private int worth;
    private int weight;

    // Getters and setters

    public boolean getIsDrillable() {
        return this.isDrillable;
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public ImageView getTileImageView() {
        return this.tileImageView;
    }

    public int getWorth() {
        return this.worth;
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean getIsDangerous() {
        return this.isDangerous;
    }

    public Tile(boolean isDrillable, boolean isEmpty, ImageView tImageView, int worth, int weight,
            boolean isDangerous) {
        this.isDrillable = isDrillable;
        this.isEmpty = isEmpty;
        this.tileImageView = tImageView;
        this.weight = weight;
        this.worth = worth;
        this.isDangerous = isDangerous;
    }

    /**
     * Create a rectangle from given color,width and height
     * 
     * @param color  color of rectangle
     * @param width  width of rectangle
     * @param height height of rectangle
     * @return created rectangle
     */
    public static Rectangle createRectangle(Color color, double width, double height) {
        Rectangle tempRectangle = new Rectangle();
        tempRectangle.setWidth(width);
        tempRectangle.setHeight(height);
        tempRectangle.setFill(color);
        return tempRectangle;
    }
}
