package game;
import java.awt.*;
import java.io.IOException;

import utilities.ImageManager;

public class Constants {
    public static final int FRAME_HEIGHT = 1000;  //
    public static final int FRAME_WIDTH = 1000;
    public static final Dimension FRAME_SIZE = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    public static final int DELAY = 20;  // milliseconds
    public static final double DT = DELAY/1000.0;  // seconds
    public static Image ASTEROID1, MILKYWAY1, POWERUP1, SHIP1, PROJECTILE1, SHIP2, SHIP3, SHIP4, PROJECTILE2, POWERUP2, SHIELD, HEARTS, gameover, PLANET1, PLANET2, PLANET3;
    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milkyway1");
            POWERUP1 = ImageManager.loadImage("powerup2");
            SHIP1 = ImageManager.loadImage("spaceship6-1");
            PROJECTILE1 = ImageManager.loadImage("projectile4");
            SHIP2 = ImageManager.loadImage("spaceship6-2");
            SHIP3 = ImageManager.loadImage("spaceship5-1");
            SHIP4 = ImageManager.loadImage("spaceship5-2");
            PROJECTILE2 = ImageManager.loadImage("projectile1");
            POWERUP2 = ImageManager.loadImage("powerup4");
            SHIELD = ImageManager.loadImage("shield");
            HEARTS = ImageManager.loadImage("heart");
            gameover = ImageManager.loadImage("gameover");
            PLANET1 = ImageManager.loadImage("gameover");
            PLANET2 = ImageManager.loadImage("gameover");
            PLANET3 = ImageManager.loadImage("gameover");
            System.out.println("loaded images");

        } catch (IOException e) { e.printStackTrace(); }
    }
}
