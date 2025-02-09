package game;

import utilities.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static game.Constants.FRAME_WIDTH;
import static game.Constants.FRAME_HEIGHT;

public class UI extends JComponent {
    private BasicGame game;
    Image im = Constants.MILKYWAY1;
    AffineTransform bgTransf;
    Sprite sprite;
    Sprite gameover;
    public UI(BasicGame game){
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 :
                Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 :
                Constants.FRAME_HEIGHT/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.drawImage(im, bgTransf,null);
        synchronized (BasicGame.class) {
            for (GameObject object : game.objects)
                object.draw(g);
        }
        g.setColor(Color.YELLOW);g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        g.drawString("SCORE: "+BasicGame.getScore(), FRAME_WIDTH-200, 20);
        int width = 0;
        for (int i = 0; i <= BasicGame.getLives(); i++) {
            width += 25;
            sprite = new Sprite(Sprite.HEARTS, new Vector2D(20, width), new Vector2D(0, 0), 40, 40);
            sprite.draw(g);
        }
        g.drawString("Fuel: "+BasicGame.getFuel(), 50, 20);
        g.drawString("Shield: "+BasicGame.getShield(), 50, 60);
        g.drawString("Level: "+BasicGame.getLevel(), 50, 100);
        if (BasicGame.getLives()==0 || BasicGame.getFuel()==0) {
            BasicGame.gameOver = true;
            gameover = new Sprite(Sprite.gameover, new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2), new Vector2D(0, 0), 600, 600);
            gameover.draw(g);
            g.drawString("SPACEBAR TO RESTART", FRAME_WIDTH/2-100, FRAME_HEIGHT/2+250);

        }
    }

    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }
}
