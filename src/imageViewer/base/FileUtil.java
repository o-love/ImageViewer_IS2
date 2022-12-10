package imageViewer.base;

public interface FileUtil {
    static String extensionOf(String filename) {
        return filename.substring(filename.lastIndexOf(".")+1);
    }
}
