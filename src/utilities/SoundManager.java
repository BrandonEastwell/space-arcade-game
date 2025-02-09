package utilities;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;
import java.io.File;

// SoundManager for Asteroids
//Resources from Lab 5 sounds and images
//https://moodle.essex.ac.uk/pluginfile.php/713935/mod_resource/content/4/lab5/lab5.html
public class SoundManager {
	static int nBullet = 0;
	final static String path = "sounds/";
	public final static Clip[] bullets = new Clip[16];
	public final static Clip[] AIbullets = new Clip[16];
	public final static Clip[] dangerclip = new Clip[16];
	public final static Clip[] thrustclip = new Clip[16];

	public final static Clip bangLarge = getClip("thrust");
	public final static Clip bangMedium = getClip("Explosion5");
	public final static Clip bangSmall = getClip("Explosion6");
	public final static Clip beat1 = getClip("beat1");
	public final static Clip beat2 = getClip("beat2");
	public final static Clip hurt = getClip("Hit_Hurt3");
	public final static Clip fire = getClip("Laser_Shoot13");
	public final static Clip fire2 = getClip("Laser_Shoot");
	public final static Clip danger = getClip("Blip_Select3");
	public final static Clip fuel = getClip("powerup");
	public final static Clip thrust = getClip("thrust");
	public final static Clip pickup = getClip("Pickup_Coin3");
	public final static Clip pickupShield = getClip("Powerup7");
	public final static Clip shieldHit = getClip("Randomize3");
	public final static Clip[] clips = {bangLarge, bangMedium, bangSmall, beat1, beat2, 
		hurt, fire, fire2, danger, fuel, thrust, pickup, pickupShield};
	
 static {
		for (int i = 0; i < bullets.length; i++)
			bullets[i] = getClip("Laser_Shoot13");
	}
	static {
		for (int i = 0; i < AIbullets.length; i++)
			AIbullets[i] = getClip("Laser_Shoot3");
	}
	static {
		for (int i = 0; i < dangerclip.length; i++)
			dangerclip[i] = getClip("Blip_Select3");
	}
	static {
		for (int i = 0; i < thrustclip.length; i++)
			thrustclip[i] = getClip("thrust");
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 20; i++) {
			fire();
			Thread.sleep(100);
		}
		for (Clip clip : clips) {
			play(clip);
			Thread.sleep(1000);
		}
	}
	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}
	private static Clip getClip(String filename) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
					+ filename + ".wav")); //finds audio file
			clip.open(sample);
			FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(-15.0f); //reduces volume of sound effects
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	public static void fire() {
		// fire the n-th bullet and increment the index
		Clip clip = bullets[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % bullets.length;
	}
	public static void fireAI() {
		// fire the n-th bullet and increment the index
		Clip clip = AIbullets[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % AIbullets.length;
	}
	public static void danger() {
			Clip clip = dangerclip[nBullet];
			clip.setFramePosition(0);
			clip.start();
			nBullet = (nBullet + 1) % dangerclip.length;
	}
	public static void thrust() {
		Clip clip = thrustclip[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % thrustclip.length;
	}

}
