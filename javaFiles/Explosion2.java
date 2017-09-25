package javaFiles;

public class Explosion2 extends Sprite2 {
	private static final long serialVersionUID = 8178478037865034226L;
	
	public static final int EXPLOSION_WIDTH = 30, EXPLOSION_HEIGHT = 30;
	private boolean isExplosionVisible;
	
	public Explosion2(int x, int y) {
		super(x, y);
		isExplosionVisible = this.vis;
		initExplosion();
	}
	
	private void initExplosion() {
		this.loadImage(Sprite2.USER_DIR + "\\src\\images\\explosion\\explosion.png");
		this.getImageDimensions();
	}
	
	// Accessors
	public boolean isExplosionVisible() {return isExplosionVisible;}
	
	// Mutators
	public void setExplosionVisible(boolean visible) {this.isExplosionVisible = visible;}
}
