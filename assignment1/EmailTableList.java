package assignment1;

import java.util.ArrayList;

import javax.mail.Address;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.table.AbstractTableModel;

public class EmailTableList extends AbstractTableModel {

	//Find out how to actually set an item solely for a column, and not for any other columns.
	String[] columnNames = {"Sender", "Subject", "Date", "Unread"};
	ArrayList<Message> data = new ArrayList<Message>();

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public String[] getColumnNames(int col) {
		return columnNames;
	}

	
	// Gets the value from the data's row. 
	@Override
	public Object getValueAt(int row, int col) {
		Message message = (Message) data.get(row);
		Object value = null;
		try{
			switch (col) {
			// This will return the value for the name of the sender instead of returning the address class. 
			case 0: Address[] add = message.getFrom();
			value = add[0];
			break;
			// This will return the value for the message's subject. 
			case 1: value = message.getSubject();
			break;
			// Returns the value for the message's received date.
			case 2: value = message.getReceivedDate();
			break;
			// Returns whether the message has been read.
			case 3: value = message.getFlags().contains(Flag.SEEN);
			break;
			} 
		} catch (MessagingException e) { e.printStackTrace(); }
		
		return value;

	}

	/*
	 * Adds the messages into the array list of messages called data.
	 */
	public void setMessages(Message[] messages) {
		for(Message message:messages) {
			data.add(message);
		}
		fireTableDataChanged();
	}
	
	public void deleteMessage(int row) {
		System.out.println(data.size());
		fireTableRowsDeleted(row, row);
		System.out.println(data.size());
		fireTableDataChanged();
	}

}
