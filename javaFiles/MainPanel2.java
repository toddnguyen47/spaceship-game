package javaFiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel2 extends JPanel implements ActionListener {
	private static final long serialVersionUID = 3L;
	
	// Variable declaration - Panel usage
	public static final int FRAME_WIDTH = 400, FRAME_HEIGHT = 400;
	public static final int MAX_SCORE = 50;
	public static final int MAX_SUNS = 50;
	private int maxRocks = 8;
	private static final int SHIP_WIDTH = 45;
	private static final int TIMER_DELAY = 15;
	private java.util.Timer explosionTimer;
	
	// Create stars and suns
	private MyPoint2 stars[], suns[];
	private Timer timer;
	private Timer timerUpdate;
	private Random r;
	
	// Create rocks, a ship, and sounds
	private ArrayList<Asteroid2> rocks;
	private ArrayList<Explosion2> explosions;
	private Spaceship2 ship;
	private int score = 0, lives = 5;
	private Sounds2 explosionSounds;
	private Sounds2 wonGameSound;
	private Sounds2 shipHitSound;
	
	// Create mouse overlay
	private int curMouseX, curMouseY, prevMouseX, prevMouseY;
	private boolean isInGame;
	private boolean wonGame;
	
	public MainPanel2() {
		isInGame = true;
		wonGame = false;
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		// Create movement for suns/rocks/stars
		Handler hnd = new Handler();
		this.setFocusable(true);
		
		
		// Create stars, suns, rocks, ship, explosions, and 
		// explosion sounds
		stars = new MyPoint2[MAX_SUNS];
		suns = new MyPoint2[MAX_SUNS / 3];
		rocks = new ArrayList<Asteroid2>();
		explosions = new ArrayList<Explosion2>();
		explosionTimer = new java.util.Timer();
		initExplosionSounds();
		
		// Set action listeners
		timer = new Timer(25, hnd);
		timerUpdate = new Timer(TIMER_DELAY, this);
		this.addMouseMotionListener(new MouseMoveHandler());
		this.addKeyListener(new FireLaserKey());
		
		// Create stars, suns, rocks, ship, and start the timer
		initStarsSuns();
		initRocks();
		timer.start();
		timerUpdate.start();
		ship = new Spaceship2((FRAME_WIDTH - SHIP_WIDTH)/2, FRAME_HEIGHT * 2 / 3);
	}
	
	private void initExplosionSounds() {
		explosionSounds = new Sounds2();
		explosionSounds.setDefaultLoadingDirectory(Sprite2.USER_DIR);
		
		wonGameSound = new Sounds2();
		wonGameSound.setDefaultLoadingDirectory(Sprite2.USER_DIR + "\\src\\audio\\");
		
		shipHitSound = new Sounds2();
		shipHitSound.setDefaultLoadingDirectory(Sprite2.USER_DIR + "\\src\\audio\\");
	}
	
	private void initStarsSuns() {
		for(int i = 0; i < MAX_SUNS; i++) // Stars move 3 pixels vertically
			stars[i] = new MyPoint2();
		for(int i = 0; i < MAX_SUNS / 3; i++) // Suns move 1 pixel vertically
			suns[i] = new MyPoint2(1);
	}
	
	private void initRocks() {
		for(int i = 0; i < new Random().nextInt(maxRocks); i++) {// Rocks move 5 pixels vertically
			r = new Random();
			rocks.add(new Asteroid2(5));
		}
	}
	
	private void initExplosion(int x, int y) {
		explosions.add(new Explosion2(x, y));
	}
	
	// Paint
	public void paint(Graphics g) {
		if (isInGame && wonGame == false) {
			drawObjects(g);
		} else if (wonGame) {
			drawWonGame(g);
		} else {
			drawGameOver(g);
		}
	}
	
	private void drawWonGame(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Fill the background
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// You won!
		String msg = "You got " + MAX_SCORE + " points!";
		String msg2 = "Congratulations! You won!";
		Font trebuchet = new Font("Helvetica", Font.BOLD, 20);
		FontMetrics fm = getFontMetrics(trebuchet);
		
		g.setColor(Color.WHITE);
		g.setFont(trebuchet);
		g.drawString(msg, (MainPanel2.FRAME_WIDTH - fm.stringWidth(msg)) / 2, 
				(MainPanel2.FRAME_HEIGHT) / 2);
		g.drawString(msg2, (MainPanel2.FRAME_WIDTH - fm.stringWidth(msg2)) / 2,
				(MainPanel2.FRAME_HEIGHT) / 2 - 50);
	}
	
	private void initWonGameSound() {
		wonGameSound.play("woohoo.wav", false);
	}
	
	private void drawGameOver(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Fill the background
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Draw game over when you have no lives
		String msg = "Game Over";
		Font trebuchet = new Font("Helvetica", Font.BOLD, 20);
		FontMetrics fm = getFontMetrics(trebuchet);
		
		g.setColor(Color.WHITE);
		g.setFont(trebuchet);
		g.drawString(msg, (MainPanel2.FRAME_WIDTH - fm.stringWidth(msg)) / 2, 
				(MainPanel2.FRAME_HEIGHT) / 2);
	}
	
	private void drawObjects(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Fill the background
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		drawSunsStars(g2);
		drawScore(g2);
		drawRocks(g);
		drawShip(g);
		drawExplosion(g, Asteroid2.ASTEROID_WIDTH, Asteroid2.ASTEROID_WIDTH);
		
		// Explode the ship once lives are at 0
		if (lives == 0) {
			ship.setVisible(false);
			int shipX = (int) ship.getShipBound().getX();
			int shipY = (int) ship.getShipBound().getY();
			initExplosion(shipX - ship.getShipWidth() + 10, shipY - ship.getShipHeight() + 10);
			drawExplosion(g, ship.getShipWidth() * 3, ship.getShipHeight() * 3);
		}
	}
	
	private void drawExplosion(Graphics g, int width, int height) {
		for (Explosion2 explo : explosions) {
			if (explo.isExplosionVisible()) {
				g.drawImage(explo.getImage(), explo.getX(), explo.getY(), 
						width, height, this);
			}
		}
	}
	
	private void drawSunsStars(Graphics2D g2) {
		g2.setColor(Color.CYAN);
		for(int i = 0; i < MAX_SUNS; i++) 			
			g2.fillRect(stars[i].getX(), stars[i].getY(), 2, 2);
			
		g2.setColor(Color.YELLOW);
		for(int i = 0; i < MAX_SUNS / 3; i++) 
			g2.fillRect(suns[i].getX(), suns[i].getY(), 3, 3);
	}
	
	private void drawScore(Graphics2D g2) {
		int directionX = (int) Math.floor(FRAME_WIDTH * 0.05);
		int directionY = (int) Math.floor(FRAME_HEIGHT * 0.10);
		int stringX = (int) Math.floor(FRAME_WIDTH * 0.70);
		// int stringY = (int) Math.floor(FRAME_HEIGHT * 0.20);
		
		g2.setColor(Color.WHITE);
		g2.drawString("X = " + curMouseX + ", Y = " + curMouseY, curMouseX, curMouseY);
		
		g2.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		g2.setColor(Color.WHITE);

		g2.drawString("SCORE: " + score, stringX, directionY);
		g2.drawString("LIVES:  " + lives, stringX, directionY + 20);
		
		g2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		g2.drawString("DIRECTIONS", directionX, directionY);
		
		g2.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		g2.drawString("Arrow keys to move", directionX, directionY + 20);
		g2.drawString("Space to shoot", directionX, directionY + 40);
	}
	
	private void drawRocks(Graphics g) {
		for (Asteroid2 a : rocks) {
			if (a.isAsteroidVisible()) {
				a.paint(g);
			}
		}
	}
	
	private void drawShip(Graphics g) {
		if (ship.isVisible()) {
			ship.drawShip(g);
		}
		
		ArrayList<Laser2> laser = ship.getLaser();
		for (Laser2 l : laser) {
			if (l.isLaserVisible()) {
				l.drawLaser(g, this);
			}
		}
	}
	
	// Update the whole panel
	public void actionPerformed(ActionEvent e) {
		isInGame();
		updateLaser();
		updateRocks();
		updateExplosion();
		checkCollision();
		
		repaint();
	}
	
	// Check if the game is still going on
	private void isInGame() {
		if (lives == 0) {
			isInGame = false;
		}
		
		if (isInGame == false) {
			timer.stop();
		}
	}
	
	// Make the explosions disappear
	private void updateExplosion() {
		for (Explosion2 explo : explosions) {
			explosionTimer.schedule(new java.util.TimerTask() {
				public void run() {
					explo.setExplosionVisible(false);
				}
			}, 250);
		}
	}
	
	// Move the laser
	private void updateLaser() {
		ArrayList<Laser2> laser = ship.getLaser();
		
		for (int i = 0; i < laser.size(); i++) {
			Laser2 l = laser.get(i);
			if (l.isLaserVisible()) {
				l.move();
			} else {
				laser.remove(i);
			}
		}
	}
	
	// Check for collision
	private void checkCollision() {
		Rectangle shipRect = ship.getShipBound();
		
		for (Asteroid2 a : rocks) {
			Rectangle asteRect = a.getAsteroidBounds();
			if (shipRect.intersects(asteRect)) {
				//ship.setVisible(false);
				a.setAsteroidVisible(false);
				lives--;
				shipHitSound.play("shipAlert.wav", false);
				
				if (lives == 0) {
					isInGame = false;
				}
			}
		}
		
		ArrayList<Laser2> laser = ship.getLaser();
		for (Laser2 l : laser) {
			Rectangle laserRect = l.getLaserBound();
			
			for (Asteroid2 a : rocks) {
				Rectangle asteRect = a.getAsteroidBounds();
				if (laserRect.intersects(asteRect)) {
					int rocksX = (int) asteRect.getX();
					int rocksY = (int) asteRect.getY();
					initExplosion(rocksX, rocksY);
					a.setAsteroidVisible(false);
					l.setLaserVisible(false);
					playExplosionSounds();
					score++;
					if (score == MAX_SCORE) {
						wonGame = true;
						// Wait before playing victory sound
						new java.util.Timer().schedule(new java.util.TimerTask() {
							public void run() {
								initWonGameSound();
							}
						}, 1000);
					}
				}
			}
		}
	}
	
	private void updateRocks() {
		for (int i = 0; i < rocks.size(); i++) {
			Asteroid2 a = rocks.get(i);
			if (a.isAsteroidVisible() == false) {
				rocks.remove(i);
			}
		}
		
		if (rocks.size() < (int)Math.ceil(maxRocks / 2.0)) {
			initRocks();
		}
	}
	
	// Mouse movement
	private class MouseMoveHandler implements MouseMotionListener {
		public void mouseDragged(MouseEvent arg0) {	}
		public void mouseMoved(MouseEvent arg0) {
			prevMouseX = curMouseX;
			prevMouseY = curMouseY;
			curMouseX = arg0.getX();
			curMouseY = arg0.getY();
			repaint();
		}
		
	}
	
	private int randomRocks = new Random().nextInt(maxRocks);
	
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == timer) {
				for(int i = 0; i < MAX_SUNS; i++) {
					stars[i].moveDown();
					repaint();
				}
				for(int i = 0; i < MAX_SUNS / 3; i++) {
					suns[i].moveDown();
					repaint();
				}
				for (int i = 0; i < rocks.size(); i++) {
					if (i < randomRocks) {
						rocks.get(i).refresh();
					} else {
						rocks.get(i).refreshCCW();
					}
				}
			}
		}
	}
	
	private class FireLaserKey extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			ship.keyPressed(e);
		}
	}
	
	private void playExplosionSounds() {
		explosionSounds.play("\\src\\audio\\explosionSounds.wav", false);
		/*new java.util.Timer().schedule(new java.util.TimerTask() {
			public void run() {
				explosionSounds.stopSounds();
			}
		}, 500);*/
	}
	
	// Accessors
	public Spaceship2 getSpaceship() {return ship;}
	public Random getRandom() {return r;}
	public int getPrevMouseX() {return prevMouseX;}
	public int getPrevMouseY() {return prevMouseY;}
}
