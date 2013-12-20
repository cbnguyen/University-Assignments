package ex7;

import java.awt.Graphics;

/**
 * The class for an line drawing.
 * 
 * @author Chau
 * 
 */
public class Ex7Line extends Ex7DrawingElement {

	/**
	 * The constructor for the class that calls the class Ex7DrawingElement to
	 * put it's x, y, width and height to the drawing element.
	 * 
	 * @param the
	 *            x coordinate.
	 * @param the
	 *            y coordinate.
	 * @param the
	 *            other x coordinate of the shape.
	 * @param the
	 *            other y coordinate of the shape.
	 */
	protected Ex7Line(int left, int top, int width, int height) {
		super(left, top, width, height);
	}

	/**
	 * Draws the line.
	 */
	@Override
	public void Draw(Graphics drawingElement) {
		drawingElement.drawLine(getLeft(), getTop(), getWidth(), getHeight());
	}

}
