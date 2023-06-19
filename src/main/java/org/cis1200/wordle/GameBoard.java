package org.cis1200.wordle;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class instantiates a Wordle object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Wordle wdl; // model for the game
    private JLabel status; // current status text
    final private Instructions ip = new Instructions();

    // Game constants
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 700;

    /**
     * Initializes the game board.
     */

    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(new Color(17, 17, 17));
        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        status = statusInit; // initializes the status JLabel
        wdl = new Wordle(); // initializes model for the game

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char in = e.getKeyChar();
                if ((in >= 'A' && in <= 'Z') || (in >= 'a' && in <= 'z')) {
                    wdl.addLetter(in);
                    paintComponent(getGraphics());

                }
                if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                    wdl.delLetter();
                    paintComponent(getGraphics());

                }

            }

        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        wdl.reset();
        status.setText(
                "Input your guess. Round:" + wdl.getRound() + ". Index: " + wdl.getInputIndex()
        );
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void save() {
        wdl.save();
        status.setText("Game Saved");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void load() {
        wdl.loadSave();
        status.setText(
                "Saved Game opened: Input your guess. Round:" + wdl.getRound() + ". Index:"
                        + wdl.getInputIndex()
        );
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void instructMenu() {
        ip.flip();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        status.setText(
                "Input your guess. Round:" + wdl.getRound() + ". Index: " + wdl.getInputIndex()
        );
        if (wdl.isGameOver()) {
            status.setText("Answer was " + wdl.getAnswer());
        }
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid

        int y = 5;
        for (int i = 0; i < 6; i++) {
            int x = 40;
            for (int j = 0; j < 5; j++) {

                LetterBox b = new LetterBox();
                b.setColor(wdl.getCellColor(i, j));
                b.setLetter(wdl.getCellLetter(i, j));
                b.paintComponent(g, x, y);
                x += 105;

            }
            ip.draw(g);

            y += 105;
            updateStatus();

        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
