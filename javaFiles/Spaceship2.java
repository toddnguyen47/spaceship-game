package javaFiles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Spaceship2 extends Sprite2 {
	private static final long serialVersionUID = -7558056742078709249L;
	
	private final static String TEMP = System.getProperty("user.dir");
	public final static int SHIP_WIDTH = 45, SHIP_HEIGHT = 40, LASER_HEIGHT = 30;
	private int shipX, shipY;
	private ArrayList<Laser2> laser;
	private Image img;
	
	// Constructor
	public Spaceship2(int x, int y) {
		super(x, y);
		this.shipX = x;
		this.shipY = y;
		initSpaceship();
	}
	
	private void initSpaceship() {
		laser = new ArrayList<Laser2>();
		loadShipImage();
		this.getImageDimensions(img);
	}
	
	private void loadShipImage() {
		DataInputStream dis;
		try {
			dis = new DataInputStream(new FileInputStream(TEMP + 
					"\\src\\images\\spaceship\\spaceship01.png"));
			img = ImageIO.read(dis);
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
			fire();
		}
	}
	
	// Create the lasers
	public void fire() {
		if (laser.size() < 5) {
			laser.add(new Laser2(this.shipX + Spaceship2.SHIP_WIDTH / 2 - 4, this.shipY - LASER_HEIGHT));
		}
	}
	
	// Accessors
	/**
	 * @return  The Array List of lasers fired by the space bar
	 */
	public ArrayList<Laser2> getLaser() {return laser;}
	public int getShipWidth() {return SHIP_WIDTH;}
	public int getShipHeight() {return SHIP_HEIGHT;}
	public int getShipXPos() {return shipX;}
	public int getShipYPos() {return shipY;}
	
	// Mutators
	public void setShipXPos(int xPos) {this.shipX = xPos;}
	public void setShipYPos(int yPos) {this.shipY = yPos;}
	
	// Draw the ship?
	public void drawShip(Graphics g) {
		g.drawImage(img, shipX, shipY, SHIP_WIDTH, SHIP_HEIGHT, null);
	}
	
	/**
	 * @return - Returns a rectangular hitbox for spaceship
	 */
	public Rectangle getShipBound() {
		return new Rectangle(shipX, shipY, SHIP_WIDTH, SHIP_HEIGHT);
	}
}
