package pcd.ass01.view;

import pcd.ass01.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class MainGUI extends JFrame {
    private final JButton startButton;
    private final JButton stopButton;
    private final JTextField inputField;
    private final JComboBox<String> comboBox;
    private final Controller controller;

    public MainGUI() {
        this.controller = new Controller();
        setTitle("Simple GUI");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        this.startButton = new JButton("Start");
        this.stopButton = new JButton("Stop");
        this.inputField = new JTextField(20);
        this.comboBox = new JComboBox<>(controller.getSimulations().toArray(new String[0]));

        add(this.startButton);
        add(this.stopButton);
        add(this.inputField);
        add(this.comboBox);

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
