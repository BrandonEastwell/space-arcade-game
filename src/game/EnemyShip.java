package game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Random;

import static game.Constants.FRAME_HEIGHT;
import static game.Constants.FRAME_WIDTH;
import static utilities.SoundManager.bangMedium;

import utilities.SoundManager;
import utilities.Vector2D;

public class EnemyShip extends Ship {
    Sprite sprite;
    public EnemyShip(BasicGame g, BasicController ctrl, int shieldPoints){
        super(g, new Vector2D(FRAME_WIDTH*Math.random(), FRAME_HEIGHT*Math.random()), new Vector2D(0, -1), RADIUS);
        this.controller = ctrl;
        direction = new Vector2D(0,-1);
        thrusting = false;
        sprite = new Sprite(Sprite.SHIP3, position, direction, radius*2, radius*2);
        if (shieldPoints > 0) { //initialising shield if shield points are provided
            shield = new Shield(g, this, position, velocity, shieldPoints);
            g.objects.add(shield); //adds shield to object
        }
    }

    public void draw(Graphics2D g) {
        //renders sprite
        double imW = sprite.image.getWidth(null);
        double imH = sprite.image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle() + Math.PI*1.5);
        t.scale(DRAWING_SCALE, DRAWING_SCALE);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(sprite.image, t, null);
        g.setTransform(t0);

        if (dead) {
            Explosion explosion = new Explosion((int) position.x, (int) position.y); //animation for death
            try {
                explosion.loadAnimation("explosion");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            explosion.draw(g);
            explosion.update();
        }
    }
    public void hit() {
        super.hit();
        Random r = new Random();
        double gen = r.nextDouble();
        if (gen < 0.2) {

        } else if (gen < 0.4) {

        } else if (gen < 0.85) { //45 percent chance of shield spawn
            game.objects.add(new ShieldPickUp(game, position.x, position.y, 0, 0));
        } else {

        }
        SoundManager.play(bangMedium);
    }
}
