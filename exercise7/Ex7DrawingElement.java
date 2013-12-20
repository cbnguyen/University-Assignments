package ex7;

import java.awt.Graphics;

/**
 * 
 * This class will set a drawing element for the frame/panel with the features
 * that each shape will contain. Every different shape will extend this class,
 * using it's parameters.
 * 
 * @author Chau
 * 
 */
public abstract class Ex7DrawingElement {

	private int left;
	private int top;
	private int width;
	private int height;

	/**
	 * The constructor will construct the left, top, width and height of the
	 * shapes.
	 * 
	 * @param The
	 *            supposed x coordinate.
	 * @param The
	 *            supposed y coordinate.
	 * @param The
	 *            width of the shape (Or for the case of a line, the other x
	 *            coordinate of that line).
	 * @param The
	 *            height of the shape (Or for the line, the other y coordinate).
	 */
	protected Ex7DrawingElement(int left, int top, int width, int height) {

		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;

	}

	/**
	 * Sets the x coordinate of the shape.
	 * 
	 * @param sets
	 *            the x coordinate of a new shape.
	 */
	public void setLeft(int newLeft) {
		left = newLeft;
	}

	/**
	 * Gets the x coordinate of the shape.
	 * 
	 * @return the left of the shape.
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * Sets the y coordinate.
	 * 
	 * @param sets
	 *            the y coordinate of a new shape.
	 */
	public void setTop(int newTop) {
		top = newTop;
	}

	/**
	 * Gets the y coordinate.
	 * 
	 * @return the top of the shape.
	 */
	public int getTop() {
		return top;
	}

	/**
	 * Sets the width.
	 * 
	 * @param sets
	 *            the width of a new shape.
	 */
	public void setWidth(int newWidth) {
		width = newWidth;
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width of the shape.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the height.
	 * 
	 * @param the
	 *            height of the new shape.
	 */
	public void setHeight(int newHeight) {
		height = newHeight;
	}

	/**
	 * Gets the height.
	 * 
	 * @return the height of the shape.
	 */
	public int getHeight() {
		return height;
	}

	public abstract void Draw(Graphics drawingElement);

}
