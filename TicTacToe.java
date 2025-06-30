/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerXTurn = true;
    private boolean vsAI = false;
    private JLabel statusLabel;
    private JButton resetButton;
    private JButton modeButton;
    private Random random = new Random();

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);

        // Create 3x3 grid of buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                topPanel.add(buttons[i][j]);
            }
        }

        // Bottom panel with status and controls
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1));
        statusLabel = new JLabel("Choose a mode to start", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resetButton = new JButton("Restart Game");
        modeButton = new JButton("Switch to AI Mode");

        resetButton.addActionListener(e -> resetGame());
        modeButton.addActionListener(e -> switchMode());

        bottomPanel.add(statusLabel);
        bottomPanel.add(resetButton);
        bottomPanel.add(modeButton);

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void switchMode() {
        vsAI = !vsAI;
        String modeText = vsAI ? "AI Mode" : "Manual Mode";
        statusLabel.setText(modeText + " - Player X starts");
        modeButton.setText(vsAI ? "Switch to Manual Mode" : "Switch to AI Mode");
        resetGame();
    }

    private void resetGame() {
        playerXTurn = true;
        for (JButton[] row : buttons)
            for (JButton btn : row)
                btn.setText("");
        statusLabel.setText((vsAI ? "AI Mode" : "Manual Mode") + " - Player X starts");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals(""))
            return;

        clicked.setText(playerXTurn ? "X" : "O");

        if (checkWin()) {
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + " wins!");
            disableButtons();
            return;
        } else if (isBoardFull()) {
            statusLabel.setText("Draw!");
            return;
        }

        playerXTurn = !playerXTurn;

        if (vsAI && !playerXTurn) {
            computerMove();
        } else {
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");
        }
    }

    private void computerMove() {
        // Basic AI: Random empty cell
        int i, j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (!buttons[i][j].getText().equals(""));

        buttons[i][j].setText("O");

        if (checkWin()) {
            statusLabel.setText("Computer wins!");
            disableButtons();
        } else if (isBoardFull()) {
            statusLabel.setText("Draw!");
        } else {
            playerXTurn = true;
            statusLabel.setText("Player X's Turn");
        }
    }

    private boolean checkWin() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = buttons[i][j].getText();

        // Rows, Columns, Diagonals
        for (int i = 0; i < 3; i++)
            if (!board[i][0].equals("") &&
                board[i][0].equals(board[i][1]) &&
                board[i][1].equals(board[i][2]))
                return true;

        for (int i = 0; i < 3; i++)
            if (!board[0][i].equals("") &&
                board[0][i].equals(board[1][i]) &&
                board[1][i].equals(board[2][i]))
                return true;

        if (!board[0][0].equals("") &&
            board[0][0].equals(board[1][1]) &&
            board[1][1].equals(board[2][2]))
            return true;

        if (!board[0][2].equals("") &&
            board[0][2].equals(board[1][1]) &&
            board[1][1].equals(board[2][0]))
            return true;

        return false;
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                if (btn.getText().equals(""))
                    return false;
        return true;
    }

    private void disableButtons() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                btn.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}

