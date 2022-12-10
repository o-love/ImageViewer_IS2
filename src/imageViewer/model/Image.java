package imageViewer.model;

public interface Image {
    String location();
    Image prev();
    Image next();
}
