package assignment1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class EmailClient {

	private JFrame frame;
	public static IMAPConnect connect = new IMAPConnect();
	private JTable table;
	static EmailTableList messagelist = new EmailTableList();
	private JMenu mnSettings;
	RemoveEmail remove;
	Timer timer;
	LoginDialogForm login;
	int row;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmailClient window = new EmailClient();
					window.frame.setVisible(true);
					System.out.println("gui shown");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public EmailClient() throws MessagingException, IOException{
		login = new LoginDialogForm();
		initialize();
		showEmailDetails();
		timer = new Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							if(messagelist.getRowCount() < connect.getFolder().getMessageCount()){
								timer.stop();
								JOptionPane.showMessageDialog(null, "New Message!");
								refreshEmailList();
								System.out.println("notified");
								timer.start();
							}
						}catch (MessagingException e){e.printStackTrace();}
					}
				});

				thread.start();
			}
		});
		timer.start();
	}


	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Mail Client");
		frame.setBounds(100, 100, 644, 675);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		table = new JTable(messagelist);
		JScrollPane tableScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tableScrollPane.setBounds(10, 38, 608, 211);
		frame.getContentPane().add(tableScrollPane);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 93, 21);
		frame.getContentPane().add(menuBar);

		mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);

		JMenuItem mntmCreateMessage = new JMenuItem(new CreateMessage(login.getUsn(), login.getPwd(), connect));
		mntmCreateMessage.setText("Create Message");
		mnSettings.add(mntmCreateMessage);

		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mnSettings.add(mntmRefresh);

		JMenuItem mntmRemoveMessage = new JMenuItem("Delete Message");
		mnSettings.add(mntmRemoveMessage);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 274, 608, 352);
		frame.getContentPane().add(scrollPane);

		final JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);


		JLabel lblInbox = new JLabel("Inbox");
		lblInbox.setBounds(10, 21, 46, 14);
		frame.getContentPane().add(lblInbox);

		JLabel lblContent = new JLabel("Content");
		lblContent.setBounds(10, 260, 46, 14);
		frame.getContentPane().add(lblContent);

		/*
		 * Adds the list selection listener so that when the user selects a row then the textArea will display the contents of the email.
		 * (Assignment 2) Now instead of list selection, I used MouseListener to make the GUI more responsive when implementing runnable. 
		 */
//		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//			}
//		});

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {
				row = table.getSelectedRow();
				System.out.println(row);
				// This variable makes the code less messy and can be easily passed along 
				// with the rest of this part of the code.
				final Part message = messagelist.data.get(row);

				/*
				 * New runnable thread to make viewing email messages more responsive.
				 */
				Thread showMessageThread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							if(message.getContentType().contains("TEXT/PLAIN")) {
								String content = message.getContent().toString();
								textArea.setText(content);
							} else {
								Multipart multipart = (Multipart) message.getContent();
								for (int x = 0; x < multipart.getCount(); x++) {
									BodyPart bodypart = multipart.getBodyPart(x);
									if(bodypart.getContentType().contains("TEXT/PLAIN")) {
										String content = bodypart.getContent().toString();
										textArea.setText(content);
									}
								}
							}
						} catch (MessagingException e1) {e1.printStackTrace();} catch (IOException e1) {e1.printStackTrace();}
					}
				});
				showMessageThread.run();
				System.out.println("message shown");
			}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});

		/*
		 * (Deprecated) Refreshes the table of email messages whenever the user clicks on 'Refresh'
		 * Now the notification automatically refreshes the table therefore this refresh feature is only an extra and almost useless...  
		 */
		mntmRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(messagelist.getRowCount() != connect.getFolder().getMessageCount()){
						/*
						 * Uses thread to manually refresh the email table quicker. 
						 */
						Thread refreshThread = new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									// Clears the messages and sets them into the array list of messages again.
									Message messages[] = connect.getFolder().getMessages();
									messagelist.data.clear();
									messagelist.setMessages(messages);
									messagelist.fireTableDataChanged();
								} catch (MessagingException e) {e.printStackTrace();}
							}
						});
						refreshThread.start();
					}
					System.out.println("Refreshed");
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		});

		/*
		 * This deletes the selected message off the email list.
		 */
		mntmRemoveMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				try{
//				if(messagelist.getRowCount() != connect.getFolder().getMessageCount()){
				int row = table.getSelectedRow();
				messagelist.deleteMessage(row);
				remove = new RemoveEmail();
				remove.run();
				System.out.println("Message deleted");
//				}
//				} catch (MessagingException e) {e.printStackTrace();}
			}
		});

	}

	/*
	 * This will show details of the email, such as the inbox and the email's contents.
	 */
	private void showEmailDetails() throws MessagingException, IOException {
		// Connects to the user's email, using their username and password. 
		connect.emailConnect(login.getUsn(), login.getPwd());

		/*
		 *  Opens the folder and to display the user's inbox, it would have to set the messages
		 *  by using a remote access to the user's email and display each of the messages in the folder, inbox. 
		 */
		try {
			// Checks if the folder from the user's email is not open.
			if(!connect.getFolder().isOpen()) {
				connect.getFolder().open(Folder.READ_WRITE);
				// Sets the messages from the inbox folder into the JTable.
				Message messages[] = connect.getFolder().getMessages();
				messagelist.setMessages(messages);
			}
		} catch (NoSuchProviderException e) { e.printStackTrace(); } 
		catch (MessagingException e) { e.printStackTrace();	} finally {}
	}

	/*
	 * Used to delete the email from the email server using thread 
	 * (this is called into the action listener for delete message menu item)
	 */
	private class RemoveEmail implements Runnable{
		@Override
		public void run() {
			try{
				System.out.println(row);
				Message message = connect.getFolder().getMessage(row + 1);
				message.setFlag(Flags.Flag.DELETED, true);

				connect.getFolder().close(true);
				connect.getFolder();
				connect.getFolder().open(Folder.READ_WRITE);
			} catch (MessagingException e){e.printStackTrace();}
		}
	}

	/*
	 * Refreshes the email list and it's used for the timer in the constructor.
	 */
	private void refreshEmailList() {
		try {
			// Clears the messages and sets them into the array list of messages again.
			Message messages[] = connect.getFolder().getMessages();
			messagelist.data.clear();
			messagelist.setMessages(messages);
			messagelist.fireTableDataChanged();
		} catch (MessagingException e) {e.printStackTrace();}
	}
}
