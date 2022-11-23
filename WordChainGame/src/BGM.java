import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BGM {
	public Clip clip;
	
	public void playMusic(String musicName) {
		
		try {
			File musicPath = new File("./bgm/" + musicName);
			if(musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				
				gainControl.setValue(-20.0f);
				
				clip.start();
			}
			else {
				System.out.println("music file isn't exist!");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		clip.stop();
	}
}