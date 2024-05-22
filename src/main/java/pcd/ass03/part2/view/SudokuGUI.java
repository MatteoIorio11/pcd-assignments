package pcd.ass03.part2.view;

import pcd.ass03.part2.domain.Cell;
import pcd.ass03.part2.domain.Difficulty;
import pcd.ass03.part2.logics.Controller;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SudokuGUI extends JFrame {
    private final Controller logic;

    public SudokuGUI() {
        this.logic = new Controller(Difficulty.EASY);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(new SudokuBoard());
        this.add(new MenuPane(), BorderLayout.AFTER_LINE_ENDS);
        this.setSize(500, 500);
        this.pack();
        this.setVisible(true);
    }

    public class SudokuBoard extends JPanel {
        public static final int ROWS = 3;
        public static final int COLS = 3;

        private final SubBoard[][] subBoards;

        public SudokuBoard() {
            this.setBorder(new EmptyBorder(4, 4, 4, 4));
            this.subBoards = new SubBoard[ROWS][COLS];
            this.setLayout(new GridLayout(ROWS, COLS, 2, 2));

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    final SubBoard board = new SubBoard();
                    board.setBorder(new CompoundBorder(new LineBorder(Color.GRAY, 3), new EmptyBorder(4, 4, 4, 4)));
                    this.subBoards[i][j] = board;
                    this.setSubBoardInitialSolution(i, j);
                    this.add(board);
                }
            }
            // By default disable all cells. Cells are activate once:
            // - RMI or RabbitMQ are chosen.
            // - Connection parameters are specified.
//            this.disableAllCells();
        }

        private void setSubBoardInitialSolution(final int xOffset, final int yOffset) {
            final var initialSolution = logic.getInitialBoard();
            final var subBoard = this.subBoards[xOffset][yOffset];
            for (int i = xOffset; i < xOffset + 3; i++) {
                for (int j = yOffset; j < yOffset + 3; j++) {
                    final var value = initialSolution.get(new Cell(i, j));
                    if (value == -1) continue;
                    subBoard.setCellValue(i - xOffset, j - yOffset, value, true);
                }
            }
            System.out.println("Finished setting up subgrid: (" + xOffset + ", " + yOffset + ")");
        }

        public void disableAllCells() {
            flattenBidimensionalArray(this.subBoards).forEach(SubBoard::disableAllCells);
        }

        public void activateAllCells() {
            flattenBidimensionalArray(this.subBoards).forEach(SubBoard::activateAllCells);
        }
    }

    public class SubBoard extends JPanel {
        public static final int ROWS = 3;
        public static final int COLS = 3;

        private final Map<Cell, JTextField> cells;
        private final Map<JTextField, Cell> textFieldToCells;

        public SubBoard() {
            this.setBorder(new LineBorder(Color.LIGHT_GRAY));
            this.setLayout(new GridLayout(ROWS, COLS, 2, 2));
            this.cells = new HashMap<>();
            this.textFieldToCells = new HashMap<>();

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    final JTextField textField = this.craftTextField();
                    final Cell pos = new Cell(i, j);
                    this.cells.put(pos, textField);
                    this.textFieldToCells.put(textField, pos);
                    this.add(textField, BorderLayout.CENTER);
                }
            }
        }

        private JTextField craftTextField() {
            final JTextField textField = new JTextField(4);
            textField.setInputVerifier(new SudokuInputFilter());
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.addActionListener(e -> {
                final JTextField field = (JTextField) e.getSource();
                final Cell cell = this.textFieldToCells.get(field);
                logic.putValue(cell, Integer.parseInt(field.getText()));
            });
            return textField;
        }

        public JTextField getCellAt(final int i, final int j) {
            return this.cells.get(new Cell(i, j));
        }

        public void setCellValue(final int i, final int j, final int value, final boolean isInitial) {
            final var cell = this.cells.get(new Cell(i, j));
            cell.setText(String.valueOf(value));
            cell.setEnabled(!isInitial);
        }

        public void disableAllCells() {
            this.enableCells(false);
        }

        public void activateAllCells() {
            this.enableCells(true);
        }

        private void enableCells(boolean enable) {
            this.cells.values().forEach(c -> c.setEnabled(enable));
        }
    }

    public static class MenuPane extends JPanel {

        public MenuPane() {
            this.setBorder(new EmptyBorder(4, 4, 4, 4));
            this.setLayout(new GridBagLayout());
            final GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            this.addButton("New", (e) -> {}, gbc);
            this.addButton("Reset", (e) -> {}, gbc);
        }

        private void addButton(String text, ActionListener listener, GridBagConstraints gbc) {
            final JButton btn = new JButton(text);
            btn.addActionListener(listener);
            this.add(btn, gbc);
            gbc.gridx++;
        }
    }

    public static class SudokuInputFilter extends InputVerifier {

        @Override
        public boolean verify(JComponent input) {
            final String text = ((JTextField) input).getText();
            if (text.isEmpty() || text.isBlank()) return true;
            try {
                final int value = Integer.parseInt(text);
                return value >= 1 && value <= 9;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    private static <T> Stream<T> flattenBidimensionalArray(final T[][] elems) {
        return Arrays.stream(elems).flatMap(Arrays::stream);
    }

    public static void main(String[] args) {
        new SudokuGUI();
    }
}