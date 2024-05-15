package pcd.ass03.part1.view;

import pcd.ass03.part1.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private final JButton startButton;
    private final JButton stopButton;
    private final JTextField inputField;
    private final JComboBox<String> comboBox;
    private final Controller controller;

    public MainGUI() {
        this.controller = new Controller();

        this.setTitle("Assignment 01 GUI");
        this.setSize(700, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        this.startButton = new JButton("Start");
        this.stopButton = new JButton("Stop");
        this.inputField = new JTextField(20);
        this.comboBox = new JComboBox<>(controller.getSimulations().toArray(new String[0]));

        this.add(this.startButton);
        this.add(this.stopButton);
        this.add(this.inputField);
        this.add(this.comboBox);

        this.stopButton.setEnabled(false);

        this.startButton.addActionListener(e -> {
            final String text = this.inputField.getText();
            if(!text.isEmpty()) {
                try {
                    final int inputValue = Integer.parseInt(text);
                    this.startButton.setEnabled(false);
                    this.stopButton.setEnabled(true);
                    this.controller.run((String) this.comboBox.getSelectedItem(), inputValue);
                }catch (IllegalArgumentException exception){
                    JOptionPane.showMessageDialog(this, exception.getMessage());
                }
            }else{
                JOptionPane.showMessageDialog(this, "The input field is empty.");
            }
        });

        this.stopButton.addActionListener(e -> {
            this.startButton.setEnabled(true);
            this.controller.stopSimulation();
            this.stopButton.setEnabled(false);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new MainGUI().setVisible(true));
    }
}
