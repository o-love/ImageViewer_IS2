package imageViewer.view.swing.imageDisplay;

import java.awt.Image;


public class ImageWindow {

    public static ImageWindow of(Image image) {
        return new ImageWindow(image.getWidth(null), image.getHeight(null));
    }

    public static ImageWindow of(int width, int height) {
        return new ImageWindow(width, height);
    }

    private int x;
    private int y;
    private int width;
    private int height;
    private double ratio;


    private ImageWindow(int width, int height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.ratio = 1. * width / height;
    }

    public ImageWindow adjustTo(int width, int height) {
        if (requireScale(width, height)) {
            this.width = scaleOnHeight(width,height) ? width : (int) (height * this.ratio);
            this.height = scaleOnHeight(width,height) ? (int) (width / this.ratio) : height;
        }
        this.x = (width - this.width) >> 1;
        this.y = (height - this.height) >> 1;
        return this;
    }

    private boolean requireScale(int width1, int height1) {
        return width1 < this.width || height1 < this.height;
    }

    private boolean scaleOnHeight(int width1, int height1) {
        return 1. * width1 / height1 < this.ratio;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
