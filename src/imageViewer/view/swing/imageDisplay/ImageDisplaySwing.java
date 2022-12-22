package imageViewer.view.swing.imageDisplay;

import imageViewer.base.IntEvent;
import imageViewer.view.ImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ImageDisplaySwing extends JPanel implements ImageDisplay {

    private boolean grabbed = false;

    private final java.util.List<Order> orders;
    private IntEvent onDrag = IntEvent.NULL;
    private IntEvent onRelease = IntEvent.NULL;
    private int grabbedX;

    public ImageDisplaySwing() {
        orders = new ArrayList<>();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                grabbedX = e.getX();
                grabbed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                grabbed = false;
                onRelease.execute(e.getX() - grabbedX);
                grabbedX = 0;
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

                onDrag.execute(e.getX() - grabbedX);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    @Override
    public void paint(Graphics g) {
        clean(g);
        for (Order order : orders) {
            try {
                order.paint(g);
            } catch (IOException ignored) {
            }
        }
    }

    private void clean(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    @Override
    public int width() {
        return this.getWidth();
    }

    @Override
    public int height() {
        return this.getHeight();
    }

    @Override
    public void clear() {
        this.orders.clear();
        repaint();
    }

    @Override
    public void paintImage(byte[] data, ImageWindow window) {
        this.orders.add(new Order(data, window));
        repaint();
    }

    @Override
    public void onDragged(IntEvent event) {
        this.onDrag = event;
    }

    @Override
    public void onReleased(IntEvent event) {
        this.onRelease = event;
    }

    private record Order(byte[] data, ImageWindow window) {
        private void paint(Graphics g) throws IOException {
            BufferedImage image = ImageIO.read(inputStream());
            g.drawImage(image, window.x(), window.y(), window.width(), window.height(), null);
        }

        private InputStream inputStream() {
            return new ByteArrayInputStream(data);
        }
    }
}
