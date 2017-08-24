package test;
import java.awt.*;
import javax.swing.*;

import com.canavci.library.dom.Book;
import com.canavci.library.dom.Person;
import com.canavci.library.dom.Rent;
import com.canavci.library.dom.RentList;

public class LibraryGui extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8886648444200161512L;
	private JList<Book> bookList;
	private JList<Person> personList;
	private JList<RentList> rentList;
	
	public LibraryGui() {
		initComponents();
		
	}
	private void initComponents() {
		JTabbedPane tp = new JTabbedPane();
		tp.addTab("Books", createBookList());
		tp.addTab("Perosns", createPersonList());
		tp.addTab("Rent Logs", createRentList());
		add(tp);
	}
	private JComponent createBookList() {
		bookList = new JList<>();
		return new JScrollPane(bookList);
	}
	private JComponent createPersonList() {
		personList = new JList<>();
		return new JScrollPane(personList);
	}
	private JComponent createRentList() {
		rentList = new JList<>();
		return new JScrollPane(rentList);
	}
	
}
