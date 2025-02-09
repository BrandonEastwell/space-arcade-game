package game;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game.Constants.*;

//class implemented using lab2 content from moodle
//https://moodle.essex.ac.uk/pluginfile.php/707105/mod_resource/content/5/lab2/lab2.html
//better ship
//https://moodle.essex.ac.uk/pluginfile.php/709623/mod_resource/content/1/lab3/lab3.html
public class PlayerShip extends Ship { //inherits GameObject
    Sprite sprite;
    Sprite sprite2;
    boolean INVINCIBILITY = true;
    BasicGame g;
    public PlayerShip(BasicGame g, BasicController ctrl, int shieldPoints) {
        super(g, new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 1.5), new Vector2D(0, 0), RADIUS); //inherits GameObject constructor
        direction = new Vector2D(0,-1); //facing forwards/upwards
        this.controller = ctrl;
        this.g = g;
        sprite = new Sprite(Sprite.SHIP1, position, direction, radius*2, radius*2);
        sprite2 = new Sprite(Sprite.SHIP2, position, direction, radius*2, radius*2);
        if (shieldPoints > 0) {
            shield = new Shield(g, this, position, velocity, shieldPoints);
            g.objects.add(shield);
        }
    }
    @Override
    public void draw(Graphics2D g) {
        double imW = sprite.image.getWidth(null);
        double imH = sprite.image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle() + Math.PI*1.5);
        t.scale(DRAWING_SCALE, DRAWING_SCALE);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        if (thrusting) {
            g.drawImage(sprite2.image, t, null);
            g.setTransform(t0);
        } else {
            g.drawImage(sprite.image, t, null);
            g.setTransform(t0);
        }
    }
    public void incShield() {
        if (shield != null) {
            shield.shieldPoints++;
        } else {
            shield = new Shield(g, this, position, velocity, 1);
            g.objects.add(shield);
        }
    }
    @Override
    public void hit() {
        super.hit();
        SoundManager.play(SoundManager.hurt);

    }


    public String toString() {
        return "Ship: " + super.toString();
    }
}
