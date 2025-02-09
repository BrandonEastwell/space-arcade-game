package game;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game.Constants.DT;

public class Shield extends Ship {
    public static final int RADIUS = 40;
    Sprite sprite;
    Vector2D direction;
    Ship ship;
    int shieldPoints;
    public Shield(BasicGame g, Ship ship, Vector2D pos, Vector2D vel, int shieldPoints) {
        super(g, pos, vel, RADIUS);
        this.ship = ship;
        this.shieldPoints = shieldPoints;
        direction = new Vector2D(0, 0);
        sprite = new Sprite(Sprite.SHIELD, position, direction, radius*3, radius*3);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        double imW = sprite.image.getWidth(null);
        double imH = sprite.image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.scale(DRAWING_SCALE + 0.5, DRAWING_SCALE + 0.5);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(sprite.image, t, null);
        g.setTransform(t0);
    }

    @Override
    public void hit() {
        super.hit();
        SoundManager.play(SoundManager.shieldHit);

    }
}
