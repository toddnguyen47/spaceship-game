package javaFiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Random;

public class Asteroid2 extends MyPoint2 {
	private static final long serialVersionUID = -6203515965079546039L;
	
	private static BufferedImage imgs[];
	private int curFrame, delay, rngMove;
	public static final int ASTEROID_WIDTH = 30, NUMBER_OF_IMGS = 8;
	private boolean isAsteroidVisible;

	public Asteroid2(int interval) {
		super(interval, true);
		delay = 0;
		if (imgs == null) {
			loadImages(); // if no images at the beginning
		}
		rngMove = new Random().nextInt(super.getMaxRNG());
		isAsteroidVisible = true;
	}
	
	// Accessors
	public int getAsteroidSize() {return ASTEROID_WIDTH;}
	public boolean isAsteroidVisible() {return this.isAsteroidVisible;}
	
	// Mutators
	public void setAsteroidVisible(boolean visible) {this.isAsteroidVisible = visible;}
	
	public void paint(Graphics g) {
		// Grabs current frame
		BufferedImage img = imgs[curFrame];
		// Draws the frame
		g.drawImage(img, getX(), getY(), ASTEROID_WIDTH, ASTEROID_WIDTH, null);
	}
	
	public void refresh() {
		// Grabs the next frame
		if (delay++ < 2) 
			return;
		delay = 0;
		if (curFrame < imgs.length - 1) {
			curFrame++;
		} else {
			curFrame = 0;
		}
		//Graphics g = new Graphics
		randomMove(rngMove);
	}
	
	public void refreshCCW() {
		// Grabs the next frame
		if (delay++ < 2) {
			return;
		}
		delay = 0;
		if (curFrame == 0) {
			curFrame = (imgs.length - 1);
		} else {
			curFrame--;
		}
		
		randomMove(rngMove);
	}
	
	/**
	 * <b>Purpose:</b> To load the 8 images for a CLOCKWISE rotation
	 * @version 5 May 2016
	 */
	private void loadImages() {
		DataInputStream dis;
		imgs = new BufferedImage[NUMBER_OF_IMGS];
		String temp = System.getProperty("user.dir");
		try {
			dis = this.loadImages(temp + "\\src\\images\\asteroids\\Asteroid1.0.gif");
			imgs[0] = ImageIO.read(dis);
			dis.close();
			
			dis = this.loadImages(temp + "\\src\\images\\asteroids\\Asteroid1.5.gif");
			imgs[1] = ImageIO.read(dis);
			dis.close();
			
			dis = this.loadImages(temp + "\\src\\images\\asteroids\\Asteroid2.0.gif");
			imgs[2] = ImageIO.read(dis);
			dis.close();
			
			dis = this.loadImages(temp + "\\src\\images\\asteroids\\Asteroid2.5.gif");
			imgs[3] = ImageIO.read(dis);
			dis.close();
			
			dis = this.loadImages(temp + "\\src\\images\\asteroids\\Asteroid3.0.gif");
			imgs[4] = ImageIO.read(dis);
			dis.close();
			
			dis = this.loadImages(temp + "\\src\\images\\asteroids\\Asteroid3.5.gif");
			imgs[5] = ImageIO.read(dis);
			dis.close();
			
			dis = this.loadImages(temp + "\\src\\images\\asteroids\\Asteroid4.0.gif");
			imgs[6] = ImageIO.read(dis);
			dis.close();
			
			dis = this.loadImages(temp + "\\src\\images\\asteroids\\Asteroid4.5.gif");
			imgs[7] = ImageIO.read(dis);
			dis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private DataInputStream loadImages(String directory) throws FileNotFoundException {
		return new DataInputStream(new FileInputStream(directory));
	}
	
	/**
	 * Return a rectangular box for asteroids for the purpose of collision detection.
	 * @return - A rectangle according to the rocks
	 */
	public Rectangle getAsteroidBounds() {return new Rectangle(getX(), getY(), 
			ASTEROID_WIDTH, ASTEROID_WIDTH);}
}
