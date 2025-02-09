package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//class implemented using lab2 content from moodle
//https://moodle.essex.ac.uk/pluginfile.php/707105/mod_resource/content/5/lab2/lab2.html
public class BasicKeys extends KeyAdapter implements BasicController {
    Controls action;
    private long lastWhen;
    private static final long THRESHOLD = 500L;
 public BasicKeys(){
     action = new Controls();
 }

    @Override
    public Controls control() {
        // this is defined to comply with the standard interface
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (PlayerShip.fuel > 0) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP -> action.thrust = 1;
                case KeyEvent.VK_LEFT -> action.turn = -1;
                case KeyEvent.VK_RIGHT -> action.turn = 1;
                case KeyEvent.VK_SPACE -> action.shoot = true;
            }
        }
        if (BasicGame.gameOver) {
            long when = e.getWhen();
            long diff = when - lastWhen;
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE && diff > THRESHOLD) {
                action.restart = true;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
     //stop the ship from thrusting when released
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> action.thrust = 0;
            case KeyEvent.VK_LEFT -> action.turn = 0;
            case KeyEvent.VK_RIGHT -> action.turn = 0;
            case KeyEvent.VK_SPACE -> action.shoot = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            action.restart = false;
        }
    }
}

