package javaFiles;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;

public class MoveSpaceship2 implements ActionListener {
	private static final String PRESSED = "pressed ";
	private static final String RELEASED = "released ";
	
	private MainPanel2 spaceship;
	private Timer timer;
	private Map<String, Point> pressedKeys = new HashMap<String, Point>();
	
	// Constructor
	public MoveSpaceship2(MainPanel2 spaceship, int delay) {
		this.spaceship = spaceship;
		
		timer = new Timer(delay, this);
		timer.setInitialDelay(0);
	}
	
	// Add the action
	public void addAction(String keyStroke, int deltaX, int deltaY) {
		// Separate key identifiers from modifiers
		int offset = keyStroke.lastIndexOf(" ");
		String key = offset == -1 ? keyStroke : keyStroke.substring(offset + 1);
		String modifiers = keyStroke.replace(key, "");
		
		// InputMap and ActionMap
		InputMap inputMap = spaceship.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = spaceship.getActionMap();
		
		// Create action and add binding for the pressed key
		Action pressedAction = new AnimationAction(key, new Point(deltaX, deltaY));
		String pressedKey = modifiers + PRESSED + key;
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(pressedKey);
		inputMap.put(pressedKeyStroke, pressedKey);
		actionMap.put(pressedKey, pressedAction);
		
		// Create action and add binding for the released key
		Action releasedAction = new AnimationAction(key, null);
		String releasedKey = modifiers + RELEASED + key;
		KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(releasedKey);
		inputMap.put(releasedKeyStroke, releasedKey);
		actionMap.put(releasedKey, releasedAction);
	}
	
	// Add AnimationAction for key movements
	private class AnimationAction extends AbstractAction implements ActionListener {
		private static final long serialVersionUID = 7936199795386178654L;
		private Point moveDelta;
		public AnimationAction(String key, Point moveDelta) {
			super(key);
			this.moveDelta = moveDelta;
		}
		
		public void actionPerformed(ActionEvent e) {
			handleKeyEvent((String)getValue(NAME), moveDelta);
		}
	}
	
	// Invoked whenever a key is pressed or released
	private void handleKeyEvent(String key, Point moveDelta) {
		// Keep track of which keys are pressed
		if (moveDelta == null) {
			pressedKeys.remove(key);
		} else {
			pressedKeys.put(key, moveDelta);
		}
		
		// Start the timer when the first key is pressed
		if (pressedKeys.size() == 1) {
			timer.start();
		}
		
		// Stop the timer when the last key is released
		if (pressedKeys.size() == 0) {
			timer.stop();
		}
	}
	
	// Move the spaceship when the timer fires
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			moveSpaceship();
		}
	}
	
	public void moveSpaceship() {

		int shipWidth = spaceship.getSpaceship().getShipWidth();
		int shipHeight = spaceship.getSpaceship().getShipHeight();
		
		int parentWidth = MainPanel2.FRAME_WIDTH;
		int parentHeight = MainPanel2.FRAME_HEIGHT;
		
		// Calculate new move
		int deltaX = 0;
		int deltaY = 0;
		
		for (Point delta : pressedKeys.values()) {
			deltaX += delta.x;
			deltaY += delta.y;
		}
		
		int curX = spaceship.getSpaceship().getShipXPos();
		int curY = spaceship.getSpaceship().getShipYPos();
		
		// Determine the next X position
		// Stops ship if it is at the edge
		int nextX = Math.max(curX + deltaX, 0);
		if (nextX + shipWidth > parentWidth - 20) {
			nextX = parentWidth - shipWidth - 20;
		}
		
		// Determine the next Y position
		// Stops ship if it is at the edge
		int nextY = Math.max(curY + deltaY, 0);
		if (nextY + shipHeight > parentHeight - 40) {
			nextY = parentHeight - shipHeight - 40;
		}
		
		// Move the component
		spaceship.getSpaceship().setShipXPos(nextX);
		spaceship.getSpaceship().setShipYPos(nextY);
	}
}
