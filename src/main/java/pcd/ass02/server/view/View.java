package pcd.ass02.server.view;

import io.vertx.core.Future;
import pcd.ass02.server.controller.Controller;
import pcd.ass02.server.model.lib.CounterModality;
import pcd.ass02.server.model.lib.response.Response;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class View extends JFrame {
    private JTextField urlField, wordField, depthField;
    private JTextArea outputArea;
    private JButton stopButton;
    private JButton startButton;
    private JComboBox comboBox;
    private boolean inProgress = false;
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
           final int depth = Integer.parseInt(this.depthField.getText());
           final String url = this.urlField.getText();
           final String word= this.wordField.getText();
           final CounterModality algorithm = options.get(this.comboBox.getSelectedIndex());
           try {
               if(!this.inProgress) {
                   final CompletableFuture<Future<Response>> response = this.controller.startSearch(url, depth, word, algorithm);
                   this.inProgress = true;
                   SwingUtilities.invokeLater(() -> {
                       new Thread(() -> {
                           try {
                               response.get().onSuccess(r -> {
                                   this.outputArea.setText("");
                                   r.count().forEach((key, value) -> this.outputArea.append("Page: " + key + " Occurrences: " + value +"\n"));
                                   this.inProgress = false;
                               });
                           } catch (InterruptedException | ExecutionException ex) {
                               throw new RuntimeException(ex);
                           }
                       }).start();
                   });
               }else {
                   System.out.println("Already running please wait.");
               }
           }catch (IllegalArgumentException exception){
                System.err.println(exception.getMessage());
           } catch (ExecutionException | InterruptedException ex) {
               throw new RuntimeException(ex);
           }
        });
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> {
            this.outputArea.setText("");
            this.controller.stop();
        });
        buttonPanel.add(stopButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
