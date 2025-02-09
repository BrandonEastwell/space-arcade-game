package game;

public class RotateNShoot implements BasicController {
    Controls action = new Controls();

    @Override
    public Controls control() {
        action.shoot = true;
        action.turn = 1;
        return action;
    }
}
