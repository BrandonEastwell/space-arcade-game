package game;

import utilities.Vector2D;

import java.awt.*;

import static game.Constants.*;

public abstract class GameObject {
    //object position in view x,y
    public Vector2D position;
    //object velocity vx, vy
    public Vector2D velocity;
    //collision radius
    public double radius;
    public boolean dead;
    BasicGame game;
    //last collision for time of player ship invincibility
    private double lastCollision = System.currentTimeMillis();


    public GameObject(BasicGame game, Vector2D position, Vector2D velocity, double radius) {
        this.game = game;
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
    }
    public void update() {
        synchronized(BasicGame.class) {
            position.addScaled(velocity, DT); //increase speed based on velocity
            if (!(this instanceof PlayerBullet)) {
                position.wrap(FRAME_WIDTH, FRAME_HEIGHT);//boundaries, flips x and y
            }
        }
    }

    public void hit() { //stops rendering in view
        dead = true;
    }

    public double distance(GameObject other) {
        return position.dist(other.position, FRAME_WIDTH, FRAME_HEIGHT);
    }

    //method resources used from lab 4 on moodle page
    //https://moodle.essex.ac.uk/pluginfile.php/711834/mod_resource/content/2/lab4/lab4.html
    public boolean overlap(GameObject other) {
        if (this.getClass() != other.getClass()) { //does not check objects of the same type (asteroids = asteroids)
            if (this.position.dist(other.position, FRAME_WIDTH, FRAME_HEIGHT) < this.radius + other.radius) { //checks
                return true;
            }
        }
        return false;
    }
    public void collisionHandling(GameObject other) {
        //Posteriori Collision
        //only tests for one overlap (A,B)
        double currentCollision = System.currentTimeMillis();
        if (currentCollision - lastCollision > (3000) && this instanceof PlayerShip b) { //latest bullet time with the previous bullet shot deducted, bullet interval in milliseconds
            b.INVINCIBILITY = false;
        }
        if (this.getClass() != other.getClass() && this.overlap(other)) {
            if (this instanceof BasicAsteroid && other instanceof PlayerBullet b) {
                if (b.firedByShip) BasicGame.addScore(100);
                this.hit();
                other.hit();
            }
            if (this instanceof PlayerBullet b && other instanceof BasicAsteroid) {
                if (b.firedByShip) BasicGame.addScore(100);
                this.hit();
                other.hit();
            }
            if (this instanceof PlayerShip b && other instanceof BasicAsteroid) {
                if (!b.INVINCIBILITY) { //if player ship is not on cooldown
                    this.hit();
                    other.hit();
                    b.INVINCIBILITY = true;
                    lastCollision = System.currentTimeMillis();
                }
            }
            if (this instanceof BasicAsteroid && other instanceof PlayerShip b) {
                if (!b.INVINCIBILITY) {
                    this.hit();
                    other.hit();
                    b.INVINCIBILITY = true;
                    lastCollision = System.currentTimeMillis();
                }
            }
            if (this instanceof EnemyShip && other instanceof PlayerBullet b) {
                if (b.firedByShip) BasicGame.addScore(250);
                this.hit();
                other.hit();
            }
            if (this instanceof PlayerBullet b && other instanceof EnemyShip) {
                if (b.firedByShip) BasicGame.addScore(250);
                this.hit();
                other.hit();
            }
            if (this instanceof Fuel && other instanceof PlayerShip) {
                PlayerShip.fuel = 100;
                this.hit();
            }
            if (this instanceof PlayerShip && other instanceof Fuel) {
                PlayerShip.fuel = 100;
                other.hit();
            }
            if (this instanceof AIBullet && other instanceof PlayerShip b) {
                if (!b.INVINCIBILITY) {
                    this.hit();
                    other.hit();
                    b.INVINCIBILITY = true;
                    lastCollision = System.currentTimeMillis();
                }
            }
            if (this instanceof PlayerShip b && other instanceof AIBullet) {
                if (!b.INVINCIBILITY) {
                    this.hit();
                    other.hit();
                    b.INVINCIBILITY = true;
                    lastCollision = System.currentTimeMillis();
                }
            }
            if (this instanceof Shield b && other instanceof AIBullet) {
                if (b.ship instanceof PlayerShip) {
                    other.hit();
                    if (b.shieldPoints > 0) {
                        b.shieldPoints--;
                    }
                    if (b.shieldPoints == 0) {
                        this.hit();
                    }
                }
            }
            if (this instanceof AIBullet && other instanceof Shield b) {
                if (b.ship instanceof PlayerShip) {
                    this.hit();
                    if (b.shieldPoints > 0) {
                        b.shieldPoints--;
                    }
                    if (b.shieldPoints == 0) {
                        other.hit();
                    }
                }
            }
            if (this instanceof Shield b && other instanceof BasicAsteroid) {
                if (b.ship instanceof PlayerShip c && !c.INVINCIBILITY) {
                    other.hit();
                    c.INVINCIBILITY = true;
                    lastCollision = System.currentTimeMillis();
                    if (b.shieldPoints > 0) {
                        b.shieldPoints--;
                    }
                    if (b.shieldPoints == 0) {
                        this.hit();
                    }
                }
            }
            if (this instanceof BasicAsteroid && other instanceof Shield b) {
                if (b.ship instanceof PlayerShip c && !c.INVINCIBILITY) {
                    this.hit();
                    c.INVINCIBILITY = true;
                    lastCollision = System.currentTimeMillis();
                    if (b.shieldPoints > 0) {
                        b.shieldPoints--;
                    }
                    if (b.shieldPoints == 0) {
                        other.hit();
                    }
                }
            }
            if (this instanceof Shield b && other instanceof PlayerBullet) {
                if (b.ship instanceof EnemyShip) {
                    other.hit();
                    if (b.shieldPoints > 0) {
                        b.shieldPoints--;
                    }
                    if (b.shieldPoints == 0) {
                        this.hit();
                    }
                }
            }
            if (this instanceof PlayerBullet && other instanceof Shield b) {
                if (b.ship instanceof EnemyShip) {
                    this.hit();
                    if (b.shieldPoints > 0) {
                        b.shieldPoints--;
                    }
                    if (b.shieldPoints == 0) {
                        other.hit();
                    }
                }
            }
            if (this instanceof ShieldPickUp && other instanceof PlayerShip b) {
                b.incShield();
                this.hit();
            }
            if (this instanceof PlayerShip b && other instanceof ShieldPickUp) {
                b.incShield();
                other.hit();
            }
        }
    }

    public abstract void draw(Graphics2D g);


    public String toString() {
        return position.x +"," + position.y;
    }
}
