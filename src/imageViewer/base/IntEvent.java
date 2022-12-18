package imageViewer.base;

public interface IntEvent {
    IntEvent NULL = value -> {
    };

    void execute(int value);
}
