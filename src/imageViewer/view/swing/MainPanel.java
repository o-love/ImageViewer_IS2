package imageViewer.view.swing;

import imageViewer.presenter.Event;
import imageViewer.view.swing.imageDisplay.ImageDisplaySwing;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainPanel extends JPanel {

    public static class Builder {
        ImageDisplaySwing imageDisplaySwing;
        Event onPreviousImage = Event.NULL;
        Event onNextImage = Event.NULL;

        public static Builder from(ImageDisplaySwing imageDisplaySwing) {
            Objects.requireNonNull(imageDisplaySwing);

            return new Builder(imageDisplaySwing);
        }

        private Builder(ImageDisplaySwing imageDisplaySwing) {
            this.imageDisplaySwing = imageDisplaySwing;
        }

        public Builder onPreviousImage(Event event) {
            Objects.requireNonNull(event);
            this.onPreviousImage = event;
            return this;
        }

        public Builder onNextImage(Event event) {
            Objects.requireNonNull(event);
            this.onNextImage = event;
            return this;
        }

        public MainPanel build() {
            return new MainPanel(imageDisplaySwing, onPreviousImage, onNextImage);
        }
    }

    private MainPanel(ImageDisplaySwing imageDisplaySwing, Event previousImage, Event nextImage) {
        this.setLayout(new BorderLayout());
        this.add(imageDisplaySwing);
        this.add(nextButton(nextImage), BorderLayout.EAST);
        this.add(prevButton(previousImage), BorderLayout.WEST);
    }

    private Component nextButton(Event event) {
        final JButton button = new JButton(">");
        button.addActionListener(e -> event.execute());
        return button;
    }

    private Component prevButton(Event event) {
        final JButton button = new JButton("<");
        button.addActionListener(e -> event.execute());
        return button;
    }

}
