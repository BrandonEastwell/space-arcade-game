package game;

import utilities.Vector2D;
import utilities.SoundManager;

public abstract class Ship extends GameObject {

    public static final int RADIUS = 15;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 1 * Math.PI;

    // acceleration when thrust is applied
    public static final double MAG_ACC = 200;
    // constant speed loss factor
    public static final double DRAG = 0.01;

    public static final double DRAWING_SCALE = 1.5;

    static float fuel = 100;

    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    public boolean thrusting;
    public PlayerBullet playerBullet;
    public AIBullet AIbullet;

    private double lastBullet = System.currentTimeMillis();
    protected BasicController controller;
    //shield
    public Shield shield;
    static double lastSound = System.currentTimeMillis();
    public Ship(BasicGame g, Vector2D position, Vector2D velocity, double radius) {
        super(g, position, velocity, radius);
    }

    private void createAIBullet(){ //AI bullet with different velocity and sprite
        Vector2D Pos = new Vector2D(position);
        Vector2D Vel = new Vector2D(velocity);
        Vel.addScaled(direction,  200); //muzzle velocity
        AIbullet = new AIBullet(game, Pos, Vel, this instanceof EnemyShip, direction);
        AIbullet.position.addScaled(direction, (radius+ AIbullet.radius)* 2);
    }
    private void createPlayerBullet(){ //Player bullet with different velocity and sprite
        Vector2D Pos = new Vector2D(position);
        Vector2D Vel = new Vector2D(velocity);
        Vel.addScaled(direction,  600); //muzzle velocity
        playerBullet = new PlayerBullet(game, Pos, Vel, this instanceof PlayerShip, direction);
        playerBullet.position.addScaled(direction, (radius+ playerBullet.radius)* 2);
    }

    @Override
    public void update() {
        Controls action = controller.control(); //controller for player input
        direction.rotate(action.turn * STEER_RATE * Constants.DT); //physics
        velocity = new Vector2D(direction).mult(velocity.mag());
        velocity.addScaled(direction, MAG_ACC * Constants.DT * action.thrust);
        velocity.addScaled(velocity, -DRAG);

        double time = System.currentTimeMillis(); //stores current system time of when the bullet is shot
        if (action.shoot && this instanceof PlayerShip) { //Player fires a bullet
            if (time - lastBullet > (1000.0 / 5)) { //latest bullet time with the previous bullet shot deducted, bullet interval in milliseconds
                lastBullet = time;
                action.shoot = false;
                SoundManager.fire(); //plays sound effect for bullet shot
                createPlayerBullet(); //creates a bullet instance
            }
        } else if (action.shoot && this instanceof EnemyShip) {
            if (time - lastBullet > (1000.0 / 1)) { //latest bullet time with the previous bullet shot deducted, bullet interval in milliseconds
                lastBullet = time;
                action.shoot = false;
                SoundManager.fireAI(); //plays sound effect for bullet shot
                createAIBullet();
            }
        }
        if (fuel < 35 && fuel > 0 && this instanceof PlayerShip) { //danger sound for low fuel
            if (time - lastSound > (1000.0 / 5)) {
                SoundManager.danger();
                lastSound = System.currentTimeMillis();
            }
        }
        thrusting = action.thrust != 0;
        if (thrusting && fuel > 0 && this instanceof PlayerShip) { //thrust action sound effect and fuel drain
            fuel -= 0.25;
            if (time - lastSound > (1000.0 / 5)) {
                SoundManager.thrust();
                lastSound = System.currentTimeMillis();
            }
        }
        super.update();
    }

    public boolean canHit(GameObject other) {
        return (other instanceof BasicAsteroid  || other instanceof PlayerShip);
    }

}
