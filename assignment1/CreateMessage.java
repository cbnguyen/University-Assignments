package assignment1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateMessage extends AbstractAction {

	private JFrame messageframe;
	private JTextArea messageArea;
	private JTextField sendtoField;
	private JTextField ccField;
	private JTextField subjectField;
	private JMenuItem mntmSend;
	private JMenuItem mntmAttach;
	IMAPConnect connect;
	String password;
	String username;
	final JFileChooser fc = new JFileChooser();
	File file;
	int returnVal;
	MimeMessage message;
	String filelocation;
	String filename;
	Multipart multipart;
	MimeBodyPart messageBodyPart;

	/*
	 * This constructor has these parameters so that the connection can be passed along to this class.
	 */
	public CreateMessage(String username, String password, IMAPConnect connect) {
		this.password = password;
		this.username = username;
		this.connect = connect;
		initialise();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		run();
	}

	/*
	 * Runs the class when the user selects the option to create message.
	 */
	public void run() {
		try {
			CreateMessage window = new CreateMessage(username, password, connect);
			window.messageframe.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Initialises the content of the GUI window.
	 */
	public void initialise() {
		messageframe = new JFrame("New Message");
		messageframe.setBounds(100, 140, 492, 443);
		messageframe.getContentPane().setLayout(null);

		// This part is the content of the JMenuBar and its items
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 123, 21);
		messageframe.getContentPane().add(menuBar);
		JMenu messageMenu = new JMenu("File");
		menuBar.add(messageMenu);
		mntmSend = new JMenuItem("Send");
		messageMenu.add(mntmSend);
		mntmAttach = new JMenuItem("Attach File");
		messageMenu.add(mntmAttach);

		// The text field for the TO recipient.
		sendtoField = new JTextField();
		sendtoField.setBounds(10, 35, 456, 20);
		messageframe.getContentPane().add(sendtoField);
		// Text field for the CC recipient.
		ccField = new JTextField();
		ccField.setBounds(10, 70, 456, 20);
		messageframe.getContentPane().add(ccField);
		// Text field for the subject of the email.
		subjectField = new JTextField();
		subjectField.setBounds(10, 105, 456, 20);
		messageframe.getContentPane().add(subjectField);
		
		// Adds scroll pane to the text area, 'messageArea'.
		JScrollPane messageScrollPane = new JScrollPane();
		messageScrollPane.setBounds(10, 155, 456, 239);
		messageframe.getContentPane().add(messageScrollPane);
		
		// Anything can be typed here for the user to edit any content of the email.
		messageArea = new JTextArea();
		messageScrollPane.setViewportView(messageArea);

		// Labels to show which text field is what for the user.
		JLabel lblTo = new JLabel("To");
		lblTo.setBounds(10, 21, 46, 14);
		messageframe.getContentPane().add(lblTo);

		JLabel lblCc = new JLabel("Cc");
		lblCc.setBounds(10, 56, 46, 14);
		messageframe.getContentPane().add(lblCc);

		JLabel lblMessage = new JLabel("Message");
		lblMessage.setBounds(10, 136, 55, 14);
		messageframe.getContentPane().add(lblMessage);

		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setBounds(10, 90, 55, 14);
		messageframe.getContentPane().add(lblSubject);

		/*
		 * The following is a pair of action listeners for the menu items, Send and Attach.
		 */

		// When clicking on "Send" from the "File" menu, it will do an action where it would send the message.
		message = new MimeMessage(connect.getSession());

		mntmSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Extra thread just so that when the user clicks on "Send Message", the menu quickly collapse/minimise.
				 */
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							sendMessage();
							messageframe.dispose();
						} catch (AddressException e) {e.printStackTrace();} 
						catch (MessagingException e) {e.printStackTrace();} 
						catch (IOException e) {e.printStackTrace();}
					}
				});
				t.start();
			}

			// Sends the message.
			public void sendMessage() throws AddressException, MessagingException, IOException {
				final String emailSuffix = "@gmail.com";
				/*
				 * This part of the code here is based on the code, SendSMTPMail.java
				 */
				try{
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(sendtoField.getText()));
					message.setRecipients(Message.RecipientType.CC,
							InternetAddress.parse(ccField.getText()));
					message.setSubject(subjectField.getText());

					/*
					 *  Checks if the message does not contain any file attachment then 
					 *  it would send a message with no attachment, else sends it with a file attachment. 
					 */
					if(filelocation == null) {
						message.setText(messageArea.getText());
					} else {
						messageBodyPart = new MimeBodyPart();
						messageBodyPart.setText(messageArea.getText());
						multipart = new MimeMultipart();
						multipart.addBodyPart(messageBodyPart);
						message.setContent(multipart);
						addAttachment(filelocation, filename);
					}

					System.out.println("Message Sent");

					message.saveChanges();

					/*
					 * Quickly sends the message to recipient using thread.
					 */
					Thread sendMessageThread = new Thread(new Runnable() {
						@Override
						public void run() {
							try{
								// Finally sends the message.
								Transport tr = connect.getSession().getTransport("smtp");
								tr.connect(connect.getSMTPHost(), username, password.toString());
								tr.sendMessage(message, message.getAllRecipients());
							} catch (MessagingException e){e.printStackTrace();}
						}
					});
					sendMessageThread.start();
					// This system print is used for testing.
					System.out.println("Done");
				} catch (MessagingException e) { e.printStackTrace(); }
			}
		});

		// Opens the file chooser and gets the selected file so that it can be called into the sendMessage method.
		mntmAttach.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == mntmAttach) {
					returnVal = fc.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						// Gets the file location and file name from the JFileChooser.
						file = fc.getSelectedFile();
						filelocation = file.toString();
						filename = fc.getName(file);
					}
				}
			}
		});

	}

	/*
	 * Adds the attachment to the message.
	 */
	public void addAttachment(String filelocation, String filename) throws MessagingException {
		messageBodyPart = new MimeBodyPart();
		DataSource filesource = new FileDataSource(filelocation);
		messageBodyPart.setDataHandler(new DataHandler(filesource));
		messageBodyPart.setFileName(filename);
		multipart.addBodyPart(messageBodyPart);

		// Sets the filename to the message when the file is selected.
		message.setContent(multipart);

		// Testing purposes only.
		System.out.println("File Attached");
	}

}
