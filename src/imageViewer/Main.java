package imageViewer;


import imageViewer.model.Image;
import imageViewer.model.persistence.FileImageLoader;
import imageViewer.presenter.ImagePresenter;
import imageViewer.view.swing.GUISwing;
import imageViewer.view.swing.imageDisplay.ImageDisplaySwing;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        new Main().setupAll();
    }

    private Image baseImage;
    private ImageDisplaySwing imageDisplaySwing;
    private ImagePresenter imagePresenter;

    public void setupAll() {
        try {
            setupModel();
        } catch (IOException | FileImageLoader.NoImagesException e) {
            throw new RuntimeException(e);
        }

        setupView();

        setupPresenter();

        finishSetup();
    }

    private void setupModel() throws IOException, FileImageLoader.NoImagesException {
        this.baseImage = FileImageLoader.from(new File("images")).load();
    }

    private void setupView() {
        this.imageDisplaySwing = new ImageDisplaySwing();
    }

    private void setupPresenter() {
        this.imagePresenter = ImagePresenter.of(this.baseImage, this.imageDisplaySwing);
    }

    private void finishSetup() {
        this.imageDisplaySwing.onDragged(this.imagePresenter::whileGrabbed);
        this.imageDisplaySwing.onReleased(this.imagePresenter::onRelease);
        SwingUtilities.invokeLater(() -> {
            new GUISwing(this.imageDisplaySwing, "Image Viewer");
            this.imagePresenter.loadCurrentImageToView();
        });
    }

}
