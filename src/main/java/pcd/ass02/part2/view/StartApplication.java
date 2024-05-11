package pcd.ass02.part2.view;

import javax.swing.*;

public class StartApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new View().setVisible(true));
    }
}
