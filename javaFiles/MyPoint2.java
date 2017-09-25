package javaFiles;

import java.util.Random;
import javax.swing.JComponent;

public class MyPoint2 extends JComponent {
	private static final long serialVersionUID = -2738319499314068928L;
	
	private int x, y, y_Interval;
	public static final int maxX = 400, maxY = 400;
	private static Random r ;
	private static final int MAX_RNG = 64;
	
	// Constructors
	public MyPoint2() {
		if(r == null)
			r = new Random();
		setYInterval(3);
		setRandomPosition();
	}
		
	public MyPoint2(int interval) {
		this();
		setYInterval(interval);
	}
	
	public MyPoint2(int interval, boolean bool) {
		this(interval);
		if (bool) {
			setRandomPositionRocks();
		}
	}
	
	// Accessors
	public Random getRandom() {return r;}
	public int getMaxRNG() {return MAX_RNG;}
	
	public void setYInterval(int interval) {
		y_Interval = interval;
	}
	public void setRandomPosition() {
		x = r.nextInt(maxX);
		y = r.nextInt(maxY);
	}
	
	// Overload
	public void setRandomPositionRocks() {
		x = r.nextInt(maxX);
		y = r.nextInt(maxY/2);
	}
	
	public void moveDown() {
		if(y < maxY)
			y += y_Interval;
		else {
			y = 0;
			x = r.nextInt(maxX);
		}
	}
	
	public void moveDownRocks() {
		if(y < maxY)
			y += y_Interval;
		else {
			y = 0;
			x = r.nextInt(maxX);
		}
	}
	
	public void moveUpRocks() {
		if (y > 0)
			y -= y_Interval;
		else {
			y = maxY / 2;
			x = r.nextInt(maxX);
		}
	}
	
	public void moveLeftRocks() {
		if (x > 0) {
			x -= y_Interval;
		} else {
			x = maxX;
			y = r.nextInt(maxY / 2);
		}
	}
	
	public void moveRightRocks() {
		if (x < maxX) {
			x += y_Interval;
		} else {
			x = 0;
			y = r.nextInt(maxY / 2);
		}
	}
	
	public void moveTopRightRocks() {
		moveUpRocks();
		moveRightRocks();
	}
	
	public void moveTopLeftRocks() {
		moveUpRocks();
		moveLeftRocks();
	}
	
	public void moveBotRightRocks() {
		moveDownRocks();
		moveRightRocks();
	}
	
	public void moveBotLeftRocks() {
		moveDownRocks();
		moveLeftRocks();
	}
	
	// Random movement
	public void randomMove(int input) {
		int thirty = (int) Math.floor(getMaxRNG() * 0.30);
		int ten = (int) (getMaxRNG() - 3 * (Math.floor(getMaxRNG() * 0.30)));
		if (input < thirty)
			moveDownRocks();
		else if (input < thirty * 2) 
			moveBotLeftRocks();
		else if (input < thirty * 3)
			moveBotRightRocks();
		else if (input < thirty * 3 + ten/2)
			moveLeftRocks();
		else
			moveRightRocks();
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
}
