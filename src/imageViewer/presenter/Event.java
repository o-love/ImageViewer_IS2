package imageViewer.presenter;

public interface Event {
    Event NULL = () -> {};

    void execute();
}
