package game;

import utilities.SoundManager;
import utilities.Vector2D;

import java.io.IOException;
import java.util.ArrayList;

import java.awt.Graphics2D;
import java.util.List;

import static game.Constants.*;

public class BasicAsteroid extends GameObject{
    public static final double MAX_SPEED = 150;
    public static int RADIUS = 15;
    public static double ROTATION_PER_FRAME = Math.PI/50;
    public boolean isLarge;
    public List<BasicAsteroid> alive = new ArrayList<BasicAsteroid>();
    Sprite sprite;
    Vector2D direction;
    public BasicAsteroid(BasicGame g, double x, double y, double vx, double vy, boolean isLarge, int radius) {
        super(g, new Vector2D(x,y), new Vector2D(vx, vy), radius);
        this.isLarge = isLarge;
        if (!isLarge) {
            radius *= 2.0 /3;
        }
        direction = new Vector2D(0, 1);
        sprite = new Sprite(Sprite.ASTEROID1, position, direction, radius*3, radius*3);

    }

    public void draw(Graphics2D g){
        sprite.draw(g);
        if (dead) {
            Explosion explosion = new Explosion((int) position.x, (int) position.y);
            try {
                explosion.loadAnimation("explosion");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            explosion.draw(g);
            explosion.update();
        }
    }

    public void update(){
        super.update();
        direction.rotate(ROTATION_PER_FRAME);
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public static BasicAsteroid makeRandomAsteroid(BasicGame g){
        double vx = Math.random() * MAX_SPEED; //xy random velocity scaled with max speed in pixels per second
        double vy = Math.random() * MAX_SPEED;
        double x = Math.random() * FRAME_WIDTH; //random positional coordinates scaled with frame size
        double y = Math.random() * FRAME_HEIGHT;
        if (Math.random()<0.5) {//varying negative velocity
            vx *= -1;
        }
        if (Math.random()<0.5) {
            vy *= -1;
        }
        return new BasicAsteroid(g, x,y,vx,vy,true, RADIUS);
    }

    private void spawn(BasicGame g){ //draws smaller asteroids on explosion
        for (int i = 0; i<2; i++) {
            double vx = Math.random() * MAX_SPEED;
            if (Math.random() < 0.5) vx *= -1;
            double vy = Math.random() * MAX_SPEED;
            if (Math.random() < 0.5) vy *= -1;
            alive.add( new BasicAsteroid(g, position.x, position.y, vx, vy, false, RADIUS));
        }

    }

    @Override
    public void hit() { //collision state
        super.hit();
        if (isLarge) {
            spawn(game);
        }
        SoundManager.play(SoundManager.bangSmall);
    }

    public String toString() {
        return "Asteroid: " + super.toString();
    }
}
