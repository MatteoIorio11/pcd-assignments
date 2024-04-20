package pcd.ass02.server.view;

import javax.swing.*;

public class StartApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new View().setVisible(true);
            }
        });
    }
}
