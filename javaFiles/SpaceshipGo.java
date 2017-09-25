package javaFiles;
import java.awt.EventQueue;

import javax.swing.*;

public class SpaceshipGo extends JFrame {

	private static final long serialVersionUID = 5709178593325756116L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	public static final String BG_MUSIC = System.getProperty("user.dir") + 
			"\\src\\audio\\ahrix_nova.wav";
	
	private MainPanel2 panel;
	
	public SpaceshipGo() {
		setSize(WIDTH, HEIGHT);
		setTitle("Java 114");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Add panel
		panel = new MainPanel2();
		moveSpaceship(panel);
		add(panel);
		setVisible(true);
	}
	
	private void moveSpaceship(MainPanel2 panel) {
		MoveSpaceship2 moveSpaceship = new MoveSpaceship2(panel, 15);
		moveSpaceship.addAction("UP",    0, -3);
		moveSpaceship.addAction("DOWN",  0,  3);
		moveSpaceship.addAction("LEFT", -3,  0);
		moveSpaceship.addAction("RIGHT", 3,  0);
		panel.getSpaceship().repaint();
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new SpaceshipGo();
			}
		});
		
		playMusic(BG_MUSIC, true);
	}
	
	static private void playMusic(String name, boolean isLooping) {
		BGMusic2 bgMusic = new BGMusic2();
		bgMusic.playSound(name, isLooping);
	}
}
