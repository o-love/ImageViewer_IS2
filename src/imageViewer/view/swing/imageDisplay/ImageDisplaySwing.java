package imageViewer.view.swing.imageDisplay;

import imageViewer.base.IntEvent;
import imageViewer.view.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImageDisplaySwing extends JPanel implements ImageDisplay {

    private BufferedImage bi;
    private int xOffset = 0;
    private boolean grabbed = false;

    @Override
    public void show(BufferedImage image) {
        this.xOffset = 0;
        this.bi = image;
        this.repaint();
    }

    @Override
    public void setHorizontalOffset(int value) {
        this.xOffset = value;
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (bi == null) return;
        ImageWindow window = ImageWindow.of(bi).adjustTo(this.getWidth(), this.getHeight()).offsetX(this.xOffset);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(bi, window.x(), window.y(), window.width(), window.height(), this);
    }

    public void withMouseEvents(IntEvent onGrabbed, IntEvent onDrag, IntEvent onRelease) {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                grabbed = true;
                onGrabbed.execute(e.getX());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                grabbed = false;
                onRelease.execute(e.getX());
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!grabbed) return;

                onDrag.execute(e.getX());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }
}
