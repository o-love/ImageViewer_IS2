package imageViewer;


import imageViewer.model.Image;
import imageViewer.model.persistence.FileImageLoader;
import imageViewer.presenter.ImagePresenter;
import imageViewer.view.swing.GUISwing;
import imageViewer.view.swing.MainPanel;
import imageViewer.view.swing.imageDisplay.ImageDisplaySwing;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        new Main().setupAll();
    }

    private Image baseImage;
    private MainPanel.Builder mainPanelBuilder;
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
        this.mainPanelBuilder = MainPanel.Builder.from(imageDisplaySwing);
    }

    private void setupPresenter() {
        this.imagePresenter = ImagePresenter.of(this.baseImage, this.imageDisplaySwing);
    }

    private void finishSetup() {
        this.imagePresenter.loadCurrentImageToView();
        this.mainPanelBuilder
                .onNextImage(this.imagePresenter::onNextImage)
                .onPreviousImage(this.imagePresenter::onPreviousImage);
        SwingUtilities.invokeLater(() -> new GUISwing(this.mainPanelBuilder.build(), "Image Viewer"));
    }

}
