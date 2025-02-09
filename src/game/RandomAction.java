package game;

import game.Controls;

public class RandomAction implements BasicController {
    Controls action = new Controls();

    int rotationDirection = 1;

    @Override
    public Controls control() {
        double shoot = Math.random();
        action.shoot = shoot > 0.8;
        double thrust = Math.random();
        action.thrust = thrust>0.5 ? 1 : 0;
        double turn = Math.random();
        action.turn = turn < 0.2 ? rotationDirection : 0;
        if (Math.random() < 0.01) rotationDirection *= -1;
        return action;
    }
}
