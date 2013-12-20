package ex7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * The JPanel for the JFrame that will be placed for the role as the canvas for drawing the shapes.
 * @author Chau
 *
 */
public class Ex7JPanelDraw extends JPanel {
	// variable for shape, 0 = line, 1 = ellipse, 2 = rectangle
	private int shape = 0;

	/**
	 * This method will set the value of what shape it is depending on what button the user press.
	 * @param newShape
	 */
	public void setShape(int newShape) {
		this.shape = newShape;
	}

	private Ex7Drawing drawing;

	/**
	 * The constructor that will construct the attributes of the panel and also the implementation of the mouse listeners when the user presses the mouse button, releases it and drags the mouse.
	 * @param drawing
	 */
	public Ex7JPanelDraw(final Ex7Drawing drawing) {

		super();
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		this.drawing = drawing;

		Ex7MouseListener m1 = new Ex7MouseListener() {

			private int x1;
			private int y1;
			private int x2;
			private int y2;

			@Override
			public void mouseDragged(MouseEvent e) {

				
				
			}

			/**
			 * This will get the x and y coordinate where the mouse button is released. It will draw the shape depending on the values. Each values represent a shape.
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				x2 = e.getX();
				y2 = e.getY();
				
				switch (shape) {
				case 0:
					Ex7Line line = new Ex7Line(x1, y1, x2, y2);
					drawing.addDrawingElement(line);
					break;

				case 1:
					Ex7Ellipse ellipse = new Ex7Ellipse(x1, y1, x2, y2);
					drawing.addDrawingElement(ellipse);
					break;

				case 2:
					Ex7Rectangle rect = new Ex7Rectangle(x1, y1, x2, y2);
					drawing.addDrawingElement(rect);
					break;
					
				}
				repaint();
				
				// Testing purposes only.
				System.out.println("Mouse is released");

			}

			/**
			 * When the mouse is pressed, it will get the x and y coordinates where the mouse is pressed.
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				x1 = e.getX();
				y1 = e.getY();
				
				// Testing purposes only/
				System.out.println("Mouse is pressed");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				

			}

			@Override
			public void mouseExited(MouseEvent e) {
				

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				

			}
		};

		// This will add the mouse listener from the class, Ex7MouseListener.
		this.addMouseListener(m1);
		this.addMouseMotionListener(m1);
		// Repaints the panel so that will actually draw the shape.
		this.repaint();

	}

	/**
	 * Draws the shape onto the panel.
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		drawing.draw(g);

	}

}
