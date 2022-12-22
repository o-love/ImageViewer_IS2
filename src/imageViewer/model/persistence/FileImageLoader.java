package imageViewer.model.persistence;

import imageViewer.base.ImageFileUtil;
import imageViewer.model.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

public interface FileImageLoader {

    Image load();

    class NoImagesException extends Exception {
    }

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
                    private byte[] data;
                    private int width;
                    private int height;

                    {
                        try {
                            this.data = load(files[index]);
                            BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
                            this.width = image.getWidth();
                            this.height = image.getHeight();
                        } catch (IOException e) {
                            this.data = new byte[0];
                            this.width = 0;
                            this.height = 0;
                        }
                    }

                    private byte[] load(File file) throws IOException {
                        return Files.readAllBytes(file.toPath());
                    }

                    @Override
                    public byte[] data() {
                        return data;
                    }

                    @Override
                    public int width() {
                        return width;
                    }

                    @Override
                    public int height() {
                        return height;
                    }

                    @Override
                    public String location() {
                        return files[index].getAbsolutePath();
                    }

                    @Override
                    public Image prev() {
                        return imageOf(index == 0 ? files.length - 1 : index - 1);
                    }

                    @Override
                    public Image next() {
                        return imageOf(index == files.length - 1 ? 0 : index + 1);
                    }
                };
            }
        };
    }
}
