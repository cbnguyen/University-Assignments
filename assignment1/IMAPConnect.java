package assignment1;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import com.sun.mail.imap.IMAPFolder;

public class IMAPConnect {

	private Session session;
	private IMAPFolder folder;
	private String username;
	private String password;
	private Store store;
	private String smtphost;
	
	
	public IMAPConnect() {
	}

	public String getSMTPHost() {
		return smtphost;
	}
	
	public Session getSession() {
		return session;
	}

	public IMAPFolder getFolder() {
		return this.folder;
	}

	public String getUsername() {
		System.out.println(username.toString());
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Store getStore() {
		return store;
	}


	/**
	 * Displays the content in the inbox folder such as subject names of the e-mails and their contents.
	 * This code is also part of the example code, IMAPClient.java, however only a few segments of this 
	 * has been changed and been mostly kept for testing purposes. (Such as printing the system output)
	 * Reference: http://www.cs.bham.ac.uk/~szh/teaching/ssc/sourcecode/IMAPClient.java
	 * @param username
	 * @param password
	 * @throws IOException
	 * @throws MessagingException
	 */
	public void emailConnect(String username, String password) throws IOException, MessagingException{
		
		smtphost = "smtp.gmail.com";
		
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", "587");
		
		props.setProperty("mail.user", username );
		props.setProperty("mail.password", password);
		
		session = Session.getDefaultInstance(props);
		
		try{
			store = session.getStore("imaps");
			System.out.println(username);
			store.connect("imap.googlemail.com", username, password);
			
			folder = (IMAPFolder) store.getFolder("inbox");
			
		} 
		catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		finally 
		{
			if (folder != null && folder.isOpen()) { folder.close(true); }
			if (store != null) { store.close(); }
		}
		
	}
}
