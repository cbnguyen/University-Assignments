package ex7;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * This class will add a new drawing by having an array list of drawings so the
 * user can always add a new drawing element.
 * 
 * @author Chau
 * 
 */
public class Ex7Drawing {

	ArrayList<Ex7DrawingElement> drawingElement = new ArrayList<Ex7DrawingElement>();

	/**
	 * Adds a new drawing element to the array list of drawings.
	 * 
	 * @param The
	 *            drawing element for the array list.
	 */
	public void addDrawingElement(Ex7DrawingElement element) {

		drawingElement.add(element);

	}

	/**
	 * This method will draw the drawing element.
	 * 
	 * @param The
	 *            graphics of the drawing.
	 */
	public void draw(Graphics g) {

		for (int i = 0; i < drawingElement.size(); i++) {

			drawingElement.get(i).Draw(g);

		}

	}

}
