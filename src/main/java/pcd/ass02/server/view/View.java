package pcd.ass02.server.view;

import io.vertx.core.Future;
import pcd.ass02.server.controller.Controller;
import pcd.ass02.server.model.lib.strategy.CounterModality;
import pcd.ass02.server.model.lib.response.Response;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static javax.swing.JOptionPane.showMessageDialog;

public class View extends JFrame {
    private JTextField urlField, wordField, depthField;
    private JTextArea outputArea;
    private JButton stopButton;
    private JButton startButton;
    private JComboBox comboBox;
    private boolean inProgress = false;
    private final Controller controller;
    private final List<CounterModality> options;
    public View(){
        this.controller = new Controller();
        this.options = this.controller.getAlgorithms();
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
        this.addStartHandler();
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        this.addStopHandler();
        buttonPanel.add(stopButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addStopHandler(){
        this.stopButton.addActionListener(e -> {
            this.outputArea.setText("");
            this.controller.stop();
        });
    }

    private void addStartHandler(){
        this.startButton.addActionListener(e ->  {
            final int depth = Integer.parseInt(this.depthField.getText());
            final String url = this.urlField.getText();
            final String word= this.wordField.getText();
            final CounterModality algorithm = options.get(this.comboBox.getSelectedIndex());
            try {
                if(!this.inProgress) {
                    final CompletableFuture<Future<Response>> response = this.controller.startSearch(url, depth, word, algorithm);
                    this.inProgress = true;
                    SwingUtilities.invokeLater(() -> {
                        this.outputArea.setText("The algorithm is running, please wait...");
                        this.createVirtualEvent(() -> {
                            try {
                                response.get().onSuccess(r -> {
                                    this.inProgress = false;
                                });
                            } catch (InterruptedException | ExecutionException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        this.createVirtualEvent(() -> {
                            while(this.inProgress) {
                                this.outputArea.setText("");
                                this.controller.getPartialResult()
                                        .ifPresent(r -> r.count().forEach((key, value) -> this.outputArea.append("Page: " + key + " Occurrences: " + value + "\n")));
                                this.waitTime(100);
                            }
                            this.inProgress = false;
                        });
                    });
                }else {
                    showMessageDialog(this, "Already running please wait.");
                }
            }catch (IllegalArgumentException | ExecutionException | InterruptedException exception){
                showMessageDialog(this, exception.getMessage());
            }
        });
    }

    private void createVirtualEvent(Runnable runnable){
        Thread.ofVirtual().start(runnable);
    }
    private void waitTime(final long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
