package ex7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The JFrame class for the program.
 * 
 * @author Chau
 * 
 */
public class Ex7JFrame extends JFrame {

	/**
	 * Calls the JFrame class so this class will be able to implement the
	 * features to customise the frame of the program.
	 */
	public Ex7JFrame() {

		super();

	}

	/**
	 * Sets up the frame with these different attributes.
	 */
	public void frameSetup() {

		this.setTitle("Shape Drawing");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 500);
		this.setVisible(true);

	}

	/**
	 * This main will use the drawing, frame and the panel as objects of their
	 * classes. The drawing object will be able to be added to the panel, and
	 * that panel object will be added to the frame, whereas the frame will be
	 * add the buttons to another panel object..
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Ex7Drawing drawing = new Ex7Drawing();

		Ex7JFrame frame = new Ex7JFrame();
		Ex7JPanelDraw panelDraw = new Ex7JPanelDraw(drawing);

		JButton exit = new JButton("Exit");
		JButton line = new JButton("Line");
		JButton ellipse = new JButton("Ellipse");
		JButton rectangle = new JButton("Rectangle");
		JPanel panel = new JPanel();

		line.addActionListener(new LineButton(panelDraw));
		ellipse.addActionListener(new EllipseButton(panelDraw));
		rectangle.addActionListener(new RectButton(panelDraw));

		// Exits the program will the button is clicked.
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		frame.add(panelDraw);
		frame.add(panel, BorderLayout.SOUTH);

		panel.add(line, BorderLayout.SOUTH);
		panel.add(ellipse, BorderLayout.SOUTH);
		panel.add(rectangle, BorderLayout.SOUTH);
		panel.add(exit, BorderLayout.SOUTH);

		frame.frameSetup();

	}

	/**
	 * This static class will set the shape for the panel, Ex7JPanelDraw,
	 * specifically to set the line.
	 * 
	 * @author Chau
	 * 
	 */
	static class LineButton implements ActionListener {

		private Ex7JPanelDraw panel;

		public LineButton(Ex7JPanelDraw _panel) {
			super();
			panel = _panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			panel.setShape(0);
			System.out.println("0");
		}

	}

	/**
	 * Like the above but sets the value to let the user be able to draw an
	 * ellipse.
	 * 
	 * @author Chau
	 * 
	 */
	static class EllipseButton implements ActionListener {

		private Ex7JPanelDraw panel;

		public EllipseButton(Ex7JPanelDraw _panel) {
			super();
			panel = _panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			panel.setShape(1);
			System.out.println("1");
		}

	}

	/**
	 * Same but for the rectangle.
	 * 
	 * @author Chau
	 * 
	 */
	static class RectButton implements ActionListener {

		private Ex7JPanelDraw panel;

		public RectButton(Ex7JPanelDraw _panel) {
			super();
			panel = _panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			panel.setShape(2);
			System.out.println("2");
		}

	}

}
