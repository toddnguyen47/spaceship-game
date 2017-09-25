package javaFiles;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Sprite2 extends JComponent {
	private static final long serialVersionUID = -1076795982492110411L;
	
	public final static String USER_DIR = System.getProperty("user.dir");
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean vis;
	protected Image image;
	
	/**
	 * Set location of the newly created object
	 * @param x - x position
	 * @param y - y position
	 */
	public Sprite2(int x, int y) {
		this.x = x;
		this.y = y;
		vis = true;
	}
	
	/**
	 * Load the image dimensions
	 */
	protected void getImageDimensions() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	protected void getImageDimensions(Image img) {
		width = img.getWidth(null);
		height = img.getHeight(null);
	}
	
	/**
	 * Load the image into the class.
	 * @param fileName - Where the picture is located
	 */
	protected void loadImage(String fileName) {
		ImageIcon ii = new ImageIcon(fileName);
		image = ii.getImage();
	}
	
	// Accessors
	public Image getImage() {return image;}
	public int getX() {return x;}
	public int getY() {return y;}
	public int getSpriteWidth() {return width;}
	public int getSpriteHeight() {return height;}
	public boolean isVisible() {return vis;}
	
	// Mutators
	
	/**
	 * Set the visibility of the sprite
	 * @param visible - Boolean, TRUE for visible
	 */
	public void setVisible(Boolean visible) {this.vis = visible;}
	public Rectangle getBounds() {return new Rectangle(x, y, width, height);}
}
