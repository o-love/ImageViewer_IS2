package imageViewer.view.swing;

import javax.swing.*;

public class GUISwing extends JFrame {
        public GUISwing(JPanel jPanel, String string) {
            super(string);

            getContentPane().add(jPanel);

            setSize(800, 600);

            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(true);
        }
    }
