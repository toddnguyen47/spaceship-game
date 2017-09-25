package javaFiles;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream; 
 
import javax.sound.sampled.AudioFormat; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.DataLine; 

public class Sounds2 {
	private Clip bg;
	private String defDir = "";
	private Clip clip;
	
	/**
	 * Play the sound specified with the fileName.
	 * @param fileName - Location of the sounds
	 * @param isBG - Boolean to determine if it is background music. TRUE for background music
	 */
	public void play(String fileName, boolean isBG) {
		try {
			String path = defDir + fileName;
			InputStream inputStream = new FileInputStream(path);
			InputStream buf = new BufferedInputStream(inputStream);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(buf);
			AudioFormat audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInputStream);
			if (isBG) {
				bg = clip;
			}
			clip.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopSounds() {
		clip.stop();
	}
	
	/**
	 * Set default loading directoy for audio sounds
	 * @param path - The path of the default directory
	 */
	public void setDefaultLoadingDirectory(String path) {
		defDir = path;
	}
	
	/**
	 * Put this in a thread or timer to loop the background music.
	 */
	public void loopBG() {
		if (bg.getFramePosition() == bg.getFrameLength()) {
			bg.setFramePosition(0);
		}
	}
	
	/**
	 * Use this to determine if background music is paused.
	 * @param paused - Determine if the background music is paused. Set TRUE
	 * to pause the music.
	 */
	public void setBGPaused (boolean paused) {
		if (paused) {
			bg.stop();
		} else {
			bg.start();
		}
	}
	
	/**
	 * Get the current paused-state of the background music.
	 * @return - TRUE if background music is paused, else FALSE.
	 */
	public boolean getBGPaused() {
		if (bg.isRunning()) {
			return false;
		} else {
			return true;
		}
	}
}
