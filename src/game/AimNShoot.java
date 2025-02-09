package game;
import static game.Constants.DT;
public class AimNShoot implements BasicController {
    public static final double SHOOTING_DISTANCE = AIBullet.BULLET_LIFE * AIBullet.MUZZLE_VELOCITY;

    public static final double SHOOTING_ANGLE = 0.6 * Ship.STEER_RATE * DT;

    public BasicGame game;
    private Ship ship;
    private Controls action = new Controls();

    public AimNShoot(BasicGame game) {
        this.game = game;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public Controls control() {
        GameObject target = Controllers.findTarget(ship, game.objects, SHOOTING_DISTANCE * 1);
        if (target == null) {
            action.turn = 0;
            action.shoot = false;
            return action;
        }
        //System.out.println("target found");
        action.turn = Controllers.aim(ship, target);
        //System.out.println("turn = " + action.turn);
        if (action.turn == 0) {
            double distanceToTarget = ship.distance(target);
            //System.out.println("target dist = " + distanceToTarget);
            action.shoot = distanceToTarget < SHOOTING_DISTANCE + target.radius;
        }
        action.thrust = 0;
        return action;
    }

}
