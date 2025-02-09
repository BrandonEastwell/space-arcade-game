package game;

import utilities.JEasyFrame;
import utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static game.Constants.*;

//class developed using lab1 resources from moodle
public class BasicGame {
    //initial asteroids
    public static final int ASTEROIDS = 7;
    //game objects to be rendered
    public List<GameObject> objects;
    //bullets, enemy player ships
    public List<Ship> ships;
    static PlayerShip ship;
    //control input from player
    BasicKeys ctrl;
    private static int score = 0;
    private static int lives = 5;
    private static int level = 1;
    static public boolean gameOver = false;

    public BasicGame() {
        //initial first level on startup
        objects = new ArrayList<GameObject>();
        ships = new ArrayList<Ship>();
        for (int i = 0; i < ASTEROIDS; i++) {
            objects.add(BasicAsteroid.makeRandomAsteroid(this)); //creates an asteroid object with no arguments
        }
        objects.add(Fuel.spawnFuel(this));
        ctrl = new BasicKeys();
        ship = new PlayerShip(this, ctrl, 5);
        objects.add(ship);
        ships.add(ship);
        spawnEnemyCannon(1, 1); //with a shield
        spawnEnemyCannon(1, 0); //no shield
    }
    private void spawnEnemyShip(int quantity, int shieldPoints) { //enemy ship with random flight AI
        for (int i = 0; i < quantity; i++) {
            BasicController AI = new RandomAction();
            Random r = new Random();
            Vector2D s = new Vector2D(r.nextInt(Constants.FRAME_WIDTH), r.nextInt(Constants.FRAME_HEIGHT)); //random location in frame
            Ship enemy = new EnemyShip(this, AI, shieldPoints);
            objects.add(enemy);
            ships.add(enemy);
        }
    }
    private void spawnEnemyCannon(int quantity, int shieldPoints) { //enemy ship with static aim and shoot cannon AI
        for (int i = 0; i < quantity; i++) {
            BasicController AI = new AimNShoot(this);
            Random r = new Random();
            Vector2D s = new Vector2D(r.nextInt(Constants.FRAME_WIDTH), r.nextInt(Constants.FRAME_HEIGHT)); //random location in frame
            Ship enemy = new EnemyShip(this, AI, shieldPoints);
            //ensures spawn is not too close to player
            ((AimNShoot) AI).setShip(enemy);
            Vector2D posDiff = new Vector2D(enemy.position).subtract(ship.position);
            if (posDiff.mag() < AimNShoot.SHOOTING_DISTANCE) {
                enemy.position = new Vector2D(ship.position).addScaled(posDiff.normalise(), AimNShoot.SHOOTING_DISTANCE * 0.75);
            }
            objects.add(enemy);
            ships.add(enemy);
        }

    }

    public void newLevel() {
        level++;
        objects.clear();
        ships.clear();
        PlayerShip.fuel = 100;
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {

        }
        if (level == 2) { //level 2 objects initialisation, higher difficulty
            synchronized(BasicGame.class) {
                for (int i = 0; i < ASTEROIDS + 4; i++) {
                    objects.add(BasicAsteroid.makeRandomAsteroid(this));
                }
                objects.add(Fuel.spawnFuel(this));
                ship = new PlayerShip(this, ctrl, 5);
                spawnEnemyShip(2, 0);
                objects.add(ship);
                ships.add(ship);
            }
        }
        if (level == 3) { //level 3 objects initialisation, higher difficulty
            synchronized(BasicGame.class) {
                for (int i = 0; i < ASTEROIDS + 4; i++) {
                    objects.add(BasicAsteroid.makeRandomAsteroid(this));
                }
                objects.add(Fuel.spawnFuel(this));
                ship = new PlayerShip(this, ctrl, 5);
                spawnEnemyCannon(2, 0);
                spawnEnemyShip(2, 0);
                objects.add(ship);
                ships.add(ship);
            }
        }
        if (level == 4) { //level 4 objects initialisation, higher difficulty
            synchronized(BasicGame.class) {
                for (int i = 0; i < ASTEROIDS + 4; i++) {
                    objects.add(BasicAsteroid.makeRandomAsteroid(this));
                }
                objects.add(Fuel.spawnFuel(this));
                ship = new PlayerShip(this, ctrl, 5);
                spawnEnemyCannon(2, 2);
                spawnEnemyShip(2, 2);
                objects.add(ship);
                ships.add(ship);
            }
        }
        if (level == 5) { //level 5 objects initialisation, higher difficulty
            synchronized(BasicGame.class) {
                for (int i = 0; i < ASTEROIDS + 4; i++) {
                    objects.add(BasicAsteroid.makeRandomAsteroid(this));
                }
                objects.add(Fuel.spawnFuel(this));
                ship = new PlayerShip(this, ctrl, 5);
                spawnEnemyCannon(2, 4);
                spawnEnemyShip(4, 2);
                objects.add(ship);
                ships.add(ship);
            }
        }

    }

    public void newLife() {
        //player loses a life, resets fuel
        lives--;
        PlayerShip.fuel = 100;
        if (lives==0) {
            gameOver = true;
        } else {
            ship = new PlayerShip(this, ctrl,0);
            objects.add(ship);
            ships.add(ship);
        }
    }
    public static void main(String[] args) {
        BasicGame game = new BasicGame();
        UI view = new UI(game);
        new JEasyFrame(view, "Space Game").addKeyListener(game.ctrl); //control input
        while (true) { //updates viewport and game objects
            if (!gameOver) {
                game.update(); //object state update
                view.repaint(); //frame view update
            }
            //reset button for when game state is over
            if (game.ctrl.action.restart) {
                game.restart();
                game.ctrl.action.restart = false;
                gameOver = false;
            }
            try {
                Thread.sleep(DELAY); //20fps
            } catch (Exception e) {
            }
        }
    }
    public void restart() { //resets back to level 1 without rebuilding game
        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {

        }
        synchronized(BasicGame.class) {
            objects.clear();
            ships.clear();
            lives = 5;
            level = 1;
            score = 0;
            for (int i = 0; i < ASTEROIDS; i++) {
                objects.add(BasicAsteroid.makeRandomAsteroid(this));
            }
            objects.add(Fuel.spawnFuel(this));
            ship = new PlayerShip(this, ctrl, 5);
            PlayerShip.fuel = 100;
            objects.add(ship);
            ships.add(ship);
            spawnEnemyCannon(2, 0);
        }
    }
    public void update() {
        //Posteriori Collision Detection
        List<GameObject> alive = new ArrayList<>();
        boolean noAsteroids = true;
        boolean noShip = true;
        //updating state of every object
        for (GameObject object : objects) {
            object.update();
            if (object instanceof PlayerShip) noShip = false;
            if (!object.dead) {
                alive.add(object);
            }
            //checks for asteroids
            if (object instanceof BasicAsteroid asteroid) {
                noAsteroids = false;
                if (!asteroid.alive.isEmpty()) {
                    alive.addAll(asteroid.alive);
                    asteroid.alive.clear();
                }
            }
            //checks if fuel charge has been taken
            if (object instanceof Fuel fuel) {
                if (fuel.dead) {
                    alive.add(Fuel.spawnFuel(this));
                }
            }
            //renders ships bullets on fire
            for (Ship ship : ships)
                if (ship.playerBullet != null) {
                    alive.add(ship.playerBullet);
                    ship.playerBullet = null;
                } else if (ship.AIbullet != null) {
                    alive.add(ship.AIbullet);
                    ship.AIbullet = null;
                }
        }
        objects.clear();
        objects.addAll(alive);

        //checking for collisions
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                objects.get(i).collisionHandling(objects.get(j)); //checks for collision
            }
        }
        if (noAsteroids) { //new level condition
            if (level < 5) {
                newLevel();
            } else {
                gameOver = true;
            }
        }
        if (noShip) {
            newLife();
        }
    }

    public static void addScore(int inc) {
        score += inc;
        System.out.println("SCORE " + score);
        if (score % 2000 == 0) { //every 1000 points, a life is added
            System.out.println("+1 LIFE");
            lives++;
        }
    }
    public static void decScore(int dec) { //score deducted on deaths
        score -= dec;
        System.out.println("SCORE " + score);
    }

    public static int getScore() {
        return score;
    }

    public static int getLevel() {
        return level;
    }

    public static int getLives() {
        return lives;
    }
    public static int getFuel() {
        return (int) PlayerShip.fuel;
    }
    public static int getShield() {
        if (ship.shield != null) {
            return ship.shield.shieldPoints;
        } else {
            return 0;
        }
    }



}

