package imageViewer.presenter;

import imageViewer.model.Image;
import imageViewer.view.ImageDisplay;
import imageViewer.view.swing.imageDisplay.ImageDisplaySwing;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImagePresenter {

    public static ImagePresenter of (Image image, ImageDisplaySwing imageDisplaySwing) {
        Objects.requireNonNull(image);
        Objects.requireNonNull(imageDisplaySwing);

        return new ImagePresenter(image, imageDisplaySwing);
    }

    private Image image;
    private final ImageDisplay imageDisplaySwing;

    private ImagePresenter(Image image, ImageDisplaySwing imageDisplaySwing) {
        this.image = image;
        this.imageDisplaySwing = imageDisplaySwing;
    }

    public void onNextImage() {
        this.image = this.image.next();
        loadCurrentImageToView();
    }

    public void onPreviousImage() {
        this.image = this.image.prev();
        loadCurrentImageToView();
    }

    public void loadCurrentImageToView() {
        try {
            imageDisplaySwing.show(ImageIO.read(new File(image.location())));
        } catch (IOException e) {
            throw new RuntimeException("Image location passed to presenter caused: ", e);
        }
    }

}
