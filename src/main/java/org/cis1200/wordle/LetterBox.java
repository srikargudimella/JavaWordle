package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;

public class LetterBox extends JComponent {
    private int color;
    private char letter;

    private JLabel text;

    public void setColor(int colorSet) {
        this.color = colorSet;
    }

    public void setLetter(char in) {
        this.letter = Character.toUpperCase(in);
    }

    public void paintComponent(Graphics gc, int x, int y) {
        gc.setFont(new Font("Arial", Font.BOLD, 40));

        if (color == 0) {
            gc.setColor(new Color(57, 57, 59));

            gc.drawRect(x, y, 100, 100);

        } else {
            if (color == 1) {
                gc.setColor(new Color(57, 57, 59));
            }
            if (color == 2) {
                gc.setColor(new Color(180, 170, 80));
            }
            if (color == 3) {
                gc.setColor(new Color(97, 140, 85));
            }
            gc.fillRect(x, y, 100, 100);
            gc.setColor(Color.WHITE);
            String text = String.valueOf(letter);
            FontMetrics metrics = gc.getFontMetrics();
            gc.drawString(
                    text, x + ((100 - metrics.stringWidth(text)) / 2),
                    y + ((100 - metrics.getHeight()) / 2) + metrics.getAscent()
            );
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

}
