package org.cis1200.wordle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Instructions {
    public static final String IMG_FILE = "files/instructions.png";
    private static BufferedImage img;

    private boolean visibility;

    public void flip() {
        visibility = !visibility;
    }

    public Instructions() {
        visibility = true;
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public void draw(Graphics g) {
        if (visibility) {

            g.drawImage(img, 50, 50, 500, 500, null);
            g.setColor(Color.GRAY);
            g.drawRect(50, 50, 500, 500);

        }
    }
}
