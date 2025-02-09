package game;

import utilities.Vector2D;

import java.awt.*;

import static game.Constants.DT;

public class AIBullet extends GameObject {
    private double lifetime;
    public static final int MUZZLE_VELOCITY = 100;
    public static final int RADIUS = 2;
    public static final int BULLET_LIFE = 1; // seconds
    public boolean firedByShip;
    Sprite sprite;
    public AIBullet(BasicGame g, Vector2D pos, Vector2D vel, boolean firedByShip, Vector2D direction) {
        super(g, pos, vel, RADIUS);
        this.lifetime = BULLET_LIFE;
        this.firedByShip = firedByShip;
        sprite = new Sprite(Sprite.PROJECTILE2, position, direction, radius*2, radius*2);
        sprite.height = 16;
        sprite.width = 16;
    }

    @Override
    public void update() {
        super.update();
        lifetime -= DT;
        if (lifetime <= 0) dead = true;
    }

    @Override
    public void draw(Graphics2D g) {
        sprite.draw(g);

    }

    public String toString() {
        return "Bullet; " + super.toString();
    }
}
