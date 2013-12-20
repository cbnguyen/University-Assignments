package ex7;

import java.awt.Graphics;

/**
 * The class to set up and draw the rectangle.
 * 
 * @author Chau
 * 
 */
public class Ex7Rectangle extends Ex7DrawingElement {

	/**
	 * The constructor for the class that calls the class Ex7DrawingElement to
	 * put it's x, y, width and height to the drawing element.
	 * 
	 * @param the
	 *            x coordinate.
	 * @param the
	 *            y coordinate.
	 * @param the
	 *            width of the shape.
	 * @param the
	 *            height of the shape.
	 */
	protected Ex7Rectangle(int left, int top, int width, int height) {
		super(left, top, width, height);
	}

	/**
	 * Draws the shape, though it will determine how it will be drawn properly,
	 * depending on the way the user drags the mouse to draw the shape.
	 */
	@Override
	public void Draw(Graphics drawingElement) {
		int x1 = getLeft();
		int y1 = getTop();
		int x2 = getWidth();
		int y2 = getHeight();

		if (x1 < x2 && y1 < y2) {
			// draw ellipse as usual.
			x2 = x2 - x1;
			y2 = y2 - y1;

			drawingElement.drawRect(x1, y1, x2, y2);
		} else if (x1 > x2 && y1 > y2) {
			// reverse the coordinates.
			x1 = x1 - x2;
			y1 = y1 - y2;

			drawingElement.drawRect(x2, y2, x1, y1);
		} else if (x1 > x2 && y1 < y2) {
			// other way round.
			x1 = x1 - x2;
			y2 = y2 - y1;

			drawingElement.drawRect(x2, y1, x1, y2);
		} else if (x1 < x2 && y1 > y2) {
			// (alternate) other way round.
			x2 = x2 - x1;
			y1 = y1 - y2;

			drawingElement.drawRect(x1, y2, x2, y1);
		} else if (x1 == x2 && y1 == y2) {
			// draw no shape.
			x2 = x1 - x2;
			y2 = y1 - y2;
			x1 = x2 - x1;
			y1 = y2 - y1;

			drawingElement.drawRect(x1, y1, x2, y2);
		}
	}

}
