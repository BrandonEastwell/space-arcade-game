package game;

import utilities.ImageManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Explosion extends BasicGame {
    //array of images to be rendered
    private static List<BufferedImage> animationExplosion = new ArrayList<BufferedImage>();
    private static Integer time = 0;
    public final static String path = "images/Explosion/"; //file path for animation
    public final static String ext = ".png";
    int X;
    int Y;
    public Explosion(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
    public void loadAnimation(String fname) throws IOException {
        Integer max = 7;
        for (int i = 1; i <= max; i++) {
            animationExplosion.add(ImageIO.read(new File(path + fname + i + ext)));
        }
    }
    public void update() { //incremental, updates with frame ticks to render next image in array
        if (time < animationExplosion.size()) {
            time++;
        }
        else {
            time = 0;
        }
    }
    public void draw(Graphics g) { //draws image at X, Y of parent
        Graphics2D g2;
        if (g instanceof Graphics2D) {
            g2 = (Graphics2D) g;
        }
        g.drawImage((animationExplosion.get(time)), X, Y, null);
    }
}
