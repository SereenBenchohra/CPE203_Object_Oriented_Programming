

/**
 * Present a minimal GUI that draws a set of points determined by an
 * instance of makeImage.
 * <p>
 * Don't pay too much attention to what's in here.  This code is using
 * concepts we haven't explored yet.  The point of this code is to give us
 * something that we can use to play with squares and circles.
 */

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.io.IOException;

public class ShowImage extends JFrame {

    public static Dimension SIZE = new Dimension(500, 500);
    public static int NUM_SQUARES = 20;
    public static int NUM_CIRCLES = 25;

    private MakeImage maker;

    public ShowImage(String name, MakeImage maker) {
        super(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.maker = maker;
        setLayout(new BorderLayout());
        Component c = new Component() {
            @Override
            final public void paint(Graphics g) {
                maker.paint(g);
            }
        };
        c.setPreferredSize(SIZE);
        add(c);
        pack();
    }
}
