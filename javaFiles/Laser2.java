package javaFiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Laser2 extends Sprite2 {
	private static final long serialVersionUID = -6426265049008010045L;
	
	private static final String TEMP = System.getProperty("user.dir");
	private static final int MISSILE_SPEED = 3;
	private static final int LASER_WIDTH = 10, LASER_HEIGHT = 30;
	private boolean isLaserVisible;
	
	// Constructor
	public Laser2(int x, int y) {
		super(x, y);
		initLaser();
		isLaserVisible = true;
	}
	
	private void initLaser() {
		this.loadImage(TEMP + "\\src\\images\\laser\\red_laser.png");
		this.getImageDimensions();
	}

	public void move() {
		y = y - MISSILE_SPEED;
		if (y < 0 - LASER_HEIGHT) {
			isLaserVisible = false;
		}
	}
	
	public void drawLaser(Graphics g, ImageObserver imgO) {
		g.drawImage(this.image, this.x, this.y, LASER_WIDTH, LASER_HEIGHT, imgO);
	}
	
	/**
	 * @return - The laser representing the laser's hitbox
	 */
	public Rectangle getLaserBound() {return new Rectangle(this.x, this.y, LASER_WIDTH, LASER_HEIGHT);}
	public boolean isLaserVisible() {return this.isLaserVisible;}
	
	// Mutators
	public void setLaserX(int x) {this.x = x;}
	public void setLaserY(int y) {this.y = y;}
	public void setLaserVisible(boolean visible) {this.isLaserVisible = visible;}
}
