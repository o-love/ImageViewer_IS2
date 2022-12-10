package imageViewer.model.persistence;

import imageViewer.base.ImageFileUtil;
import imageViewer.model.Image;

import java.io.File;
import java.io.IOException;

public interface FileImageLoader {

    Image load();

    class NoImagesException extends Exception {}

    static FileImageLoader from(File file) throws IOException, NoImagesException {
        File[] files = file.listFiles(ImageFileUtil.FILE_IMAGE_FILTER);

        if (files == null) throw new IOException("Unable to access directory");
        if (files.length == 0) throw new NoImagesException();

        return FileImageLoader.create(files);
    }


    private static FileImageLoader create(File[] files) {
        return new FileImageLoader() {
            @Override
            public Image load() {
                return imageOf(0);
            }

            public Image imageOf(int index) {
                return new Image() {

                    @Override
                    public String location() {
                        return files[index].getAbsolutePath();
                    }

                    @Override
                    public Image prev() {
                        return imageOf(index == 0 ? files.length - 1 : index -1);
                    }

                    @Override
                    public Image next() {
                        return imageOf(index == files.length - 1 ? 0 : index+1);
                    }
                };
            }
        };
    }
}
