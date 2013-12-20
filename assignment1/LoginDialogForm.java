package assignment1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;

public class LoginDialogForm extends JDialog implements ActionListener {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel lblPassword;
	private JLabel lblLogin;
	private JLabel lblgmailcom;
	String usn = "";
	String pwd = "";
	IMAPConnect connect = new IMAPConnect();
	
	public String getUsn() {
		return usernameField.getText() + "@gmail.com";
	}
	
	public String getPwd() {
		return new String(passwordField.getPassword());
	}
	
	/*
	 * Constructs the GUI of the login form.
	 */
	public LoginDialogForm() {
		setLocationRelativeTo(null);
		setSize(440,325);
		getContentPane().setLayout(null);
		
		setTitle("Google Email Client Login");
		
		usernameField = new JTextField();
		usernameField.setBounds(127, 96, 190, 19);
		getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(127, 156, 190, 19);
		getContentPane().add(passwordField);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(27, 98, 82, 15);
		getContentPane().add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(27, 156, 61, 15);
		getContentPane().add(lblPassword);
		
		lblLogin = new JLabel("Login");
		lblLogin.setBounds(12, 12, 61, 15);
		getContentPane().add(lblLogin);
		
		lblgmailcom = new JLabel("@gmail.com");
		lblgmailcom.setForeground(Color.GRAY);
		lblgmailcom.setBounds(335, 98, 82, 15);
		getContentPane().add(lblgmailcom);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setBounds(73, 205, 107, 25);
		getContentPane().add(btnSignIn);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(273, 205, 107, 25);
		getContentPane().add(btnCancel);
		
		/*
		 * When clicking on "Sign in" the fields, usn and pwd are initialised to the text of the 
		 * text field and the password field respectively.
		 */
		btnSignIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				usn = usernameField.getText();
				pwd = new String(passwordField.getPassword());
				dispose();
			}
		});
		
		
		// Cancels the program.
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		setModal(true);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}
