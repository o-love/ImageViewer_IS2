package imageViewer.base;

import java.io.FilenameFilter;
import java.util.List;

import static imageViewer.base.FileUtil.extensionOf;

public interface ImageFileUtil {
    List<String> IMAGE_EXTENSIONS = List.of("png", "jpg", "jpeg");

    static boolean isFileImage(String filename) {
        return isImageExtension(extensionOf(filename));
    }

    static boolean isImageExtension(String extension) {
        return IMAGE_EXTENSIONS.contains(extension);
    }

    FilenameFilter FILE_IMAGE_FILTER = (dir, filename) -> isFileImage(filename);
}
