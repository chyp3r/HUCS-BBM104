import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

class ImageManager {
    /**
     * Crops images from the given pixels and returns them
     * 
     * @param width  Image width (pixel unit)
     * @param height Image height (pixel unit)
     * @param tHoldW Threshold for width
     * @param tHoldH Threshold for height
     * @param path   Image path
     * @return cropped image
     */
    public static Image imageCropper(int width, int height, int tHoldW, int tHoldH, String path) {
        Image cropImg = new Image(path); // Take image
        PixelReader pRead = cropImg.getPixelReader(); // Read pixels of image
        PixelWriter pWrite = null; 
        WritableImage wImg = new WritableImage(width, height); // Create empty writable image
        pWrite = wImg.getPixelWriter(); 
        // Start to create new cropped image
        for (int readY = tHoldH; readY < height; readY++) {
            for (int readX = tHoldW; readX < tHoldW + width; readX++) {
                Color color = pRead.getColor(readX, readY);
                pWrite.setColor(readX - tHoldW, readY, color);
            }
        }
        cropImg = wImg;
        return cropImg;
    }
}
