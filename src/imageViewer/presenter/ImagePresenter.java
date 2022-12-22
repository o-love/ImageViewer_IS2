package imageViewer.presenter;

import imageViewer.model.Image;
import imageViewer.view.ImageDisplay;

import java.util.Objects;

public class ImagePresenter {

    public static ImagePresenter of(Image image, ImageDisplay imageDisplay) {
        Objects.requireNonNull(image);
        Objects.requireNonNull(imageDisplay);

        return new ImagePresenter(image, imageDisplay);
    }

    private Image image;
    private final ImageDisplay imageDisplay;

    private ImagePresenter(Image image, ImageDisplay imageDisplay) {
        this.image = image;
        this.imageDisplay = imageDisplay;
    }

    public void whileGrabbed(int offset) {
        loadCurrentImageToView(offset);

        if (offset == 0) return;
        if (offset < 0) loadPartialNextImageToView(offset);
        else loadPartialPrevImageToView(offset);
    }

    public void onRelease(int offset) {
        if (isNotOffsetTrigger(offset)) {
            return;
        }

        if (offset > 0) onNextImage();
        else onPreviousImage();
    }

    private boolean isNotOffsetTrigger(int value) {
        return !(Math.abs(value) > imageDisplay.width() / 2);
    }

    public void onNextImage() {
        this.image = this.image.next();
        loadCurrentImageToView();
    }

    public void onPreviousImage() {
        this.image = this.image.prev();
        loadCurrentImageToView();
    }

    public void loadPartialPrevImageToView(int offset) {
        Image prev = image.prev();
        this.imageDisplay.paintImage(prev.data(), imageWindowOf(prev).offsetX(offset - imageDisplay.width()));
    }

    public void loadPartialNextImageToView(int offset) {
        Image next = image.next();
        this.imageDisplay.paintImage(next.data(), imageWindowOf(next).offsetX(offset + imageDisplay.width()));
    }

    public void loadCurrentImageToView() {
        loadCurrentImageToView(0);
    }

    public void loadCurrentImageToView(int offset) {
        imageDisplay.clear();
        imageDisplay.paintImage(this.image.data(), imageWindowOf(this.image).offsetX(offset + imageDisplay.width()));
    }

    private ImageDisplay.ImageWindow imageWindowOf(Image image) {
        ImageDisplay.ImageWindow window = new ImageDisplay.ImageWindow(image.width(), image.height());
        window.adjustTo(imageDisplay.width(), imageDisplay.height());
        return window;
    }

}
