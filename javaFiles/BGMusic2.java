package javaFiles;

import java.io.File;
import javax.sound.sampled.*;

public class BGMusic2 {
	private final int BUFFER_SIZE = 20000000;
	private AudioInputStream ais;
	private AudioFormat audioFormat;
	private SourceDataLine sourceLine;
	
	/**
	 * Play background music
	 * @param name - Location of file
	 * @throws InterruptedException 
	 */
	public void playSound(String name, boolean isLooping) {
		try {
			File soundFile = new File(name);
			ais = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			e.printStackTrace();
			// System.exit(1);
		}
		
		audioFormat = ais.getFormat();
		DataLine.Info dataInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
		
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(dataInfo);
			sourceLine.open(audioFormat);
		} catch (Exception ex) {
			ex.printStackTrace();
			// System.exit(1);
		}
		
		sourceLine.start();
		
		int bytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		while (bytesRead != -1) {
				try {
					bytesRead = ais.read(abData, 0, abData.length);
				} catch (Exception ex) {
					System.out.println(ex.getStackTrace());
					// System.exit(1);
				}
			
			
				if (bytesRead >= 0) {
					if (isLooping) {
						while (true) {
							@SuppressWarnings("unused")
							int bytesWritten = sourceLine.write(abData, 0, bytesRead);
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					} else {
						@SuppressWarnings("unused")
						int bytesWritten = sourceLine.write(abData, 0, bytesRead);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				bytesRead = 0;	
		}
		
		sourceLine.drain();
		sourceLine.close();
	}
}
