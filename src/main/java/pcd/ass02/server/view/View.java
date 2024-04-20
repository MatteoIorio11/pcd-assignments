package pcd.ass02.server.view;

import pcd.ass02.server.controller.Controller;
import pcd.ass02.server.model.lib.CounterModality;

import javax.swing.*;
import java.awt.*;
import java.util.List;
public class View extends JFrame {
    private JTextField urlField, wordField, depthField;
    private JTextArea outputArea;
    private JButton stopButton;
    private JButton startButton;
    private JComboBox comboBox;
    private final Controller controller;
    public View(){
        this.controller = new Controller();
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
        urlField = new JTextField(10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 0.9;
        panelTop.add(urlField, gbc);

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
        final List<CounterModality> options = this.controller.getAlgorithms();
        comboBox = new JComboBox<>(options.toArray());
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

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton = new JButton("Start");
        startButton.addActionListener(e ->  {
           final String depth = this.depthField.getText();
           final String url = this.urlField.getText();
           final String word= this.wordField.getText();
           final CounterModality algorithm = options.get(this.comboBox.getSelectedIndex());

        });
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
                outputArea.append("\n");
        });
        buttonPanel.add(stopButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
