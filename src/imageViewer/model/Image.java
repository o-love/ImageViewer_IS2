package imageViewer.model;

public interface Image {
    byte[] data();

    int width();

    int height();

    String location();

    Image prev();

    Image next();
}
