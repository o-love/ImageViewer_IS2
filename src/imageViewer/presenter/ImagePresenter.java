package imageViewer.presenter;

import imageViewer.model.Image;
import imageViewer.view.ImageDisplay;
import imageViewer.view.swing.imageDisplay.ImageDisplaySwing;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImagePresenter {

    public static ImagePresenter of(Image image, ImageDisplaySwing imageDisplaySwing) {
        Objects.requireNonNull(image);
        Objects.requireNonNull(imageDisplaySwing);

        return new ImagePresenter(image, imageDisplaySwing);
    }

    private static final int IMAGE_OFFSET_TRIGGER = 10;

    private Image image;
    private final ImageDisplay imageDisplaySwing;
    private int initialGrabPosition;

    private ImagePresenter(Image image, ImageDisplaySwing imageDisplaySwing) {
        this.image = image;
        this.imageDisplaySwing = imageDisplaySwing;
    }

    public void onGrab(int value) {
        initialGrabPosition = value;
    }

    public void whileGrabbed(int value) {
        imageDisplaySwing.setHorizontalOffset(currentGrabPosition(value));
    }

    public void onRelease(int value) {
        if (isPrevTrigger(value)) {
            onPreviousImage();
        } else if (isNextTrigger(value)) {
            onNextImage();
        } else {
            imageDisplaySwing.setHorizontalOffset(0);
        }
    }

    private int currentGrabPosition(int value) {
        return value - initialGrabPosition;
    }

    private boolean isNextTrigger(int value) {
        return currentGrabPosition(value) < -IMAGE_OFFSET_TRIGGER;
    }

    private boolean isPrevTrigger(int value) {
        return currentGrabPosition(value) > IMAGE_OFFSET_TRIGGER;
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
