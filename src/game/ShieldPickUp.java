package game;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;

public class ShieldPickUp extends GameObject{
    public static final int RADIUS = 20;
    Sprite sprite;
    Vector2D direction;
    public ShieldPickUp(BasicGame g, double x, double y, double vx, double vy) {
        super(g, new Vector2D(x, y), new Vector2D(vx, vy), RADIUS);
        direction = new Vector2D(0, 0);
        sprite = new Sprite(Sprite.POWERUP2, position, direction, radius*2, radius*2);
    }
    public void update(){
    }
    @Override
    public void draw(Graphics2D g) {
        sprite.draw(g);
    }
    @Override
    public void hit() {
        super.hit();
        SoundManager.play(SoundManager.pickupShield);

    }
}
