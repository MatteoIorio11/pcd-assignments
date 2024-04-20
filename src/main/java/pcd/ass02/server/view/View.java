package pcd.ass02.server.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JTextField addressField, wordField, depthField;
    private JTextArea outputArea;
    private JButton stopButton;
    private JComboBox<String> comboBox;
    public View(){
        this.createGUI();
    }

    public void createGUI(){
        setTitle("Main GUI");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.LINE_START;

        panelTop.add(new JLabel("Url:"), gbc);
        addressField = new JTextField(10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0.9;
        panelTop.add(addressField, gbc);

        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.weightx = 0.1;
        panelTop.add(new JLabel("Word:"), gbc);
        wordField = new JTextField(10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0.9;
        panelTop.add(wordField, gbc);

        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.weightx = 0.1;
        panelTop.add(new JLabel("Depth:"), gbc);
        depthField = new JTextField(10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0.9;
        panelTop.add(depthField, gbc);

        // ComboBox
        String[] options = {"Opzione 1", "Opzione 2", "Opzione 3"};
        comboBox = new JComboBox<>(options);
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.weightx = 0.1;
        panelTop.add(new JLabel("Algorithm:"), gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0.9;
        panelTop.add(comboBox, gbc);

        add(panelTop, BorderLayout.NORTH);


        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            outputArea.append("Stop!\n");
        });
        add(stopButton, BorderLayout.SOUTH);
    }
}
