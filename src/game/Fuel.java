package game;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static game.Constants.*;

public class Fuel extends GameObject{
    public static final int RADIUS = 30;
    Sprite sprite;
    Vector2D direction;
    public Fuel(BasicGame g, double x, double y, double vx, double vy) {
        super(g, new Vector2D(x, y), new Vector2D(vx, vy), RADIUS);
        direction = new Vector2D(0, -1);
        sprite = new Sprite(Sprite.POWERUP1, position, direction, radius*2, radius*2);
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
        SoundManager.play(SoundManager.fuel);

    }
    public static Fuel spawnFuel(BasicGame g) {
        double x = Math.random() * FRAME_WIDTH; //random positional coordinates scaled with frame size
        double y = Math.random() * FRAME_HEIGHT;
        return new Fuel(g, x, y, 0, 0);

    }
}
