package game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import utilities.ImageManager;
import utilities.Vector2D;

public class Sprite {
    public static Image ASTEROID1, POWERUP1, SHIP1, PROJECTILE1, SHIP2, SHIP3, SHIP4, PROJECTILE2, POWERUP2, SHIELD, HEARTS, gameover, PLANET2, PLANET3, PLANET1;
    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Image image;
    public Vector2D position;
    public Vector2D direction;
    public double width;
    public double height;

    public Sprite(Image image, Vector2D s, Vector2D direction, double width, double height) {
        this.image = image;
        this.position = s;
        this.direction = direction;
        this.width = width;
        this.height = height;
    }

    public double getRadius() {
        return (width + height) / 4.0;
    }

    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double((position.x - width / 2), position.y - height / 2, width,
                height);
    }

    public void draw(Graphics2D g) {
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle(), 0, 0);
        t.scale(width / imW, height / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);
    }

}

