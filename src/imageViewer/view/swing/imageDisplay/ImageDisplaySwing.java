package imageViewer.view.swing.imageDisplay;

import imageViewer.view.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplaySwing extends JPanel implements ImageDisplay {

    private BufferedImage bi;

    @Override
    public void show(BufferedImage image) {
        this.bi = image;
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (bi == null) return;
        ImageWindow window = ImageWindow.of(bi).adjustTo(this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(bi, window.x(), window.y(), window.width(), window.height(), this);
    }
}
