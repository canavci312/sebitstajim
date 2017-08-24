package com.canavci.library.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.canavci.library.ctrl.Library;
import com.canavci.library.dom.Book;
import com.canavci.library.dom.Category;
import com.canavci.library.dom.Person;
import com.canavci.library.dom.RentList;
import com.canavci.library.dao.*;
public class LibraryGui extends JFrame {

	JPanel rentsTableJPanel = new JPanel();
	JPanel booksTableJPanel = new JPanel();
	JPanel personsTableJPanel = new JPanel();
	JTable personsTable;
	
	private static final long serialVersionUID = 1L;

	public LibraryGui() {
		setTitle("Library System");
		setSize(800,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(LibraryGui.EXIT_ON_CLOSE);
		setVisible(true);
		this.initComponents();
		
	}
	private void initComponents() {
		JTabbedPane tp = new JTabbedPane();
		tp.addTab("Books", getBooksTable());
		tp.addTab("Persons", getPersonsTable());
		tp.addTab("Rent Logs", getRentsTable());
		tp.addTab("Control Panel", controlPanel());
		getContentPane().add(tp);
	}
	private JPanel controlPanel() {
		JPanel controlJPanel = new JPanel();
		controlJPanel.setLayout(new FlowLayout());
		JButton addPerson = new JButton("Add Person");
		JButton addBook = new JButton("Add Book");
		JButton hireBook = new JButton("Hire Book");
		JButton returnBook = new JButton("Return Book");
		
		addPerson.addActionListener((new ButtonListener()));
		addBook.addActionListener(new BookListener());
		hireBook.addActionListener(new HireListener());
		returnBook.addActionListener(new ReturnListener());
		
		controlJPanel.add (addPerson);
		controlJPanel.add (addBook);
		controlJPanel.add (hireBook);
		controlJPanel.add (returnBook);
		return controlJPanel;

	}
	private JPanel getRentsTable() {
		
		
		
		rentsTableJPanel.setLayout(new BorderLayout());
		
		// Column Header
		String[] columns = {

				"Book ID", "Book Name", "Person ID", "Person Name", "Hire Date", "Return Date", "has Returned?" };

		// Getting Data for Table from Database

		Object[][] data =getRentList();

		// Creating JTable object passing data and header
		
		 DefaultTableModel model = new DefaultTableModel(data, columns);


		 JTable rentTable =  new JTable(model);
			rentsTableJPanel.add(rentTable.getTableHeader(), BorderLayout.NORTH);
			rentsTableJPanel.add(rentTable, BorderLayout.CENTER);


		
		JButton refreshButton = new JButton("Refresh");
		rentsTableJPanel.add(refreshButton,BorderLayout.PAGE_END);
		refreshButton.addActionListener(new ActionListener() 
		{    
		    public void actionPerformed(ActionEvent e)
		    {                   
		    	DefaultTableModel dtm = (DefaultTableModel)rentTable.getModel();
		    	dtm.setRowCount(0);
		    	dtm.setColumnCount(0);
		    	dtm.setDataVector(getRentList(), columns);
		    }               
		});
		

		return rentsTableJPanel;
	}
	private Object[][] getRentList() 
	{
		Object[][] data = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn1 = null;	
			conn1 = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
		 
			Statement st = conn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st
					// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
					// order by PersonID");
					.executeQuery(
							"select b.BookID, b.BookName, r.BookID, r.PersonID, r.hiredate, r.returndate,r.hasReturned, p.PersonName, p.PersonID from Books b, Rents r, Persons p where b.BookID = r.BookID and p.PersonID=r.PersonID");
		int rowCount = getRowCount(rs); // Row Count
		int columnCount = getColumnCount(rs); // Column Count

		data = new Object[rowCount][columnCount];

		// Starting from First Row for Iteration
		rs.beforeFirst();

		int i = 0;
		
		while (rs.next()) {

			int j = 0;

			data[i][j++] = rs.getInt("BookID");
			data[i][j++] = rs.getString("BookName");
			data[i][j++] = rs.getInt("PersonID");
			data[i][j++] = rs.getString("PersonName");
			data[i][j++] = rs.getDate("hiredate");
			data[i][j++] = rs.getDate("returndate");
			data[i][j++] = rs.getBoolean("hasReturned");

			i++;
		
		}
		}
			 catch (SQLException | ClassNotFoundException se) {
					System.err.println("Threw a SQLException creating the list of books.");
					System.err.println(se.getMessage());
				}
		return data;
	}
	private JPanel getBooksTable() {
		
		
		
		booksTableJPanel.setLayout(new BorderLayout());
		
		// Column Header
		String[] columns = {

				"Book ID", "Book Name", "Category", "Person ID",  "Return Date"};

		// Getting Data for Table from Database
		Object[][] data =getBookList();

		 DefaultTableModel model = new DefaultTableModel(data, columns);


		 JTable bookTable =  new JTable(model);
			JButton refreshButton = new JButton("Refresh");
			booksTableJPanel.add(refreshButton,BorderLayout.PAGE_END);
			refreshButton.addActionListener(new ActionListener() 
			{    
			    public void actionPerformed(ActionEvent e)
			    {                   
			    	DefaultTableModel dtm = (DefaultTableModel)bookTable.getModel();
			    	dtm.setRowCount(0);
			    	dtm.setColumnCount(0);
			    	dtm.setDataVector(getBookList(), columns);
			    }               
			});
			
		
		booksTableJPanel.add(bookTable.getTableHeader(), BorderLayout.NORTH);
		booksTableJPanel.add(bookTable, BorderLayout.CENTER);
		
		return booksTableJPanel;
	}
	private Object[][] getBookList()
	{
		Object[][] data = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn1 = null;	
			conn1 = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
		 
			Statement st = conn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st
					// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
					// order by PersonID");
					.executeQuery(
							"select b.BookID, b.BookName,b.returndate, c.CategoryID, b.Category, c.CategoryName, b.rentby from Books b, Categories c where CategoryID = Category ORDER BY BookID");
		int rowCount = getRowCount(rs); // Row Count
		int columnCount = getColumnCount(rs); // Column Count

		data = new Object[rowCount][columnCount];

		// Starting from First Row for Iteration
		rs.beforeFirst();

		int i = 0;
		
		while (rs.next()) {

			int j = 0;

			data[i][j++] = rs.getInt("BookID");
			data[i][j++] = rs.getString("BookName");
			data[i][j++] = rs.getString("CategoryName");
			data[i][j++] = rs.getInt("rentby");
			data[i][j++] = rs.getDate("returndate");
			
			i++;
		
		}
		}
			 catch (SQLException | ClassNotFoundException se) {
					System.err.println("Threw a SQLException creating the list of books.");
					System.err.println(se.getMessage());
				}
		return data;
	}
		// Method to get Row Count from ResultSet Object
		private int getRowCount(ResultSet rs) {

			try {
				
				if(rs != null) {
					
					rs.last();
					
					return rs.getRow(); 
				}
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			return 0;
		}

		// Method to get Column Count from ResultSet Object
		private int getColumnCount(ResultSet rs) {

			try {

				if(rs != null)
					return rs.getMetaData().getColumnCount();

			} catch (SQLException e) {

				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			return 0;
		}
		private JPanel getPersonsTable() {
			
			
			
			personsTableJPanel.setLayout(new BorderLayout());
			
			// Column Header
			String[] columns = {

					"Person ID", "Person Name" };

			// Getting Data for Table from Database
			Object[][] data =getPersonList();

			 DefaultTableModel model = new DefaultTableModel(data, columns);


			 JTable personsTable =  new JTable(model);
				JButton refreshButton = new JButton("Refresh");
				personsTableJPanel.add(refreshButton,BorderLayout.PAGE_END);
				refreshButton.addActionListener(new ActionListener() 
				{    
				    public void actionPerformed(ActionEvent e)
				    {                   
				    	DefaultTableModel dtm = (DefaultTableModel)personsTable.getModel();
				    	dtm.setRowCount(0);
				    	dtm.setColumnCount(0);
				    	dtm.setDataVector(getPersonList(), columns);
				    }               
				});
				
			
			
			personsTableJPanel.add(personsTable.getTableHeader(), BorderLayout.NORTH);
			personsTableJPanel.add(personsTable, BorderLayout.CENTER);
			
			return personsTableJPanel;
		}
		private Object[][] getPersonList() 
		{
			Object[][] data = null;
			try {
				Class.forName("org.postgresql.Driver");
				Connection conn1 = null;	
				conn1 = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
			 
				Statement st = conn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = st
						// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
						// order by PersonID");
						.executeQuery(
								"select p.PersonID, p.PersonName from Persons p ORDER BY p.PersonID");
			int rowCount = getRowCount(rs); // Row Count
			int columnCount = getColumnCount(rs); // Column Count

			data = new Object[rowCount][columnCount];

			// Starting from First Row for Iteration
			rs.beforeFirst();

			int i = 0;
			
			while (rs.next()) {

				int j = 0;

				data[i][j++] = rs.getInt("PersonID");
				data[i][j++] = rs.getString("PersonName");


				i++;
			
			}
			}
				 catch (SQLException | ClassNotFoundException se) {
						System.err.println("Threw a SQLException creating the list of books.");
						System.err.println(se.getMessage());
					}
			return data;
		}

		private class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				String personName;
				String idStr;
				int personID;
				
				personName =JOptionPane.showInputDialog("Enter person name: ");
				if (personName == null) {
					return;
				}
				idStr = JOptionPane.showInputDialog("Enter person id: ");
				if (idStr == null) {
					return;
				}
				personID = Integer.parseInt(idStr);
				try {
					Class.forName("org.postgresql.Driver");
					Connection conn1 = null;	
					conn1 = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
					PreparedStatement pst = null;
					Library.addPerson(conn1, pst, personID, personName);

				
				
				}

				 catch (SQLException | ClassNotFoundException se) {
					 JOptionPane.showMessageDialog(null,
							    "Person ID must be unique",
							    "ERROR",
							    JOptionPane.ERROR_MESSAGE);
						System.err.println("Threw a SQLException while adding a person.");
						System.err.println(se.getMessage());
					}
		
			}		
		}
		private class BookListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				String bookName;
				
				int categoryID;
				 Category cat = null;
				
				JComboBox<Category> comboBox=new JComboBox<Category>();
				
	
			
				try {
					Class.forName("org.postgresql.Driver");
					Connection conn1 = null;	
					conn1 = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
					PreparedStatement pst = null;
					
					LinkedList<Category> listOfCategory = new LinkedList<Category>();
					bookName =JOptionPane.showInputDialog("Enter book name: ");
					if (bookName == null) {
						return;
					}
					BooksDAO.selectCategoryList( conn1,  listOfCategory);
					
					for(int i = 0; i < listOfCategory.size(); i++)
						   comboBox.addItem(listOfCategory.get(i));
					 int result = JOptionPane.showConfirmDialog(null, comboBox, "Select a category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		                if (result == JOptionPane.OK_OPTION) {
		                    cat = (Category) comboBox.getSelectedItem();}
		                else return;
					categoryID = cat.getCategoryID();
					
					Library.addBook(conn1, pst, bookName, categoryID);
				}

				 catch (SQLException | ClassNotFoundException se) {
						System.err.println("Threw a SQLException creating the list of books.");
						System.err.println(se.getMessage());
					}
		
			}		
		
		}
		private class HireListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				LinkedList<Person> listOfPerson = new LinkedList<Person>();
				LinkedList<Book> listOfBooks = new LinkedList<Book>();
				LinkedList<Book> listOfUnhiredBooks = new LinkedList<Book>();
				JComboBox<Person> comboBox=new JComboBox<Person>();
				JComboBox<Book> comboBox2 = new JComboBox<Book>();
				String bookName;
				String returnDate;
				Person p;
				Book book;
				int personID;
				

			
				try {
					Class.forName("org.postgresql.Driver");
					Connection conn1 = null;	
					conn1 = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
					PreparedStatement pst = null;
					BooksDAO.selectPersonsList(conn1, listOfPerson);
					BooksDAO.selectBooksList(conn1, listOfBooks);
					for(int i = 0; i < listOfPerson.size(); i++)
						  
						comboBox.addItem(listOfPerson.get(i));
					
					int result = JOptionPane.showConfirmDialog(null, comboBox, "Select a person", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	                
					if (result == JOptionPane.OK_OPTION) {
	                    p = (Person) comboBox.getSelectedItem();}
	                else {
	                	return;
	                }
					
					personID =p.getPersonID();
					
					for (int i = 0; i < listOfBooks.size(); i++) {
						if(listOfBooks.get(i).getReturnDate()==null) {
							listOfUnhiredBooks.add(listOfBooks.get(i));
						}
						
					}
					for(int i = 0; i < listOfUnhiredBooks.size(); i++)
						   comboBox2.addItem(listOfUnhiredBooks.get(i));
					int result2 = JOptionPane.showConfirmDialog(null, comboBox2, "Select a Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	                if (result2 == JOptionPane.OK_OPTION) {
	                    book = (Book) comboBox2.getSelectedItem();}
	                else {
	                	return;
	                }
					bookName =book.getBookName();
					
					returnDate =JOptionPane.showInputDialog("Enter return date !!! 'YYYY-MM-DD' !!!: ");
					if (returnDate ==null)
						return;
					Library.hireBook(conn1, pst, personID, bookName, returnDate, listOfBooks);
							
				}

				 catch (Exception  se) {
					 JOptionPane.showMessageDialog(null,
							    "Enter a valid date!",
							    "ERROR",
							    JOptionPane.ERROR_MESSAGE);
						System.err.println("Threw a parse exception adding return date.");
					} 
			
			}		
		
		}
		private class ReturnListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				LinkedList<Book> listOfBooks = new LinkedList<Book>();
				LinkedList<Book> listOfHiredBooks = new LinkedList<Book>();
				JComboBox<Book> comboBox=new JComboBox<Book>();
				Book book=null;
				String bookName;
				
				int personID;
				

			
				try {
					Class.forName("org.postgresql.Driver");
					Connection conn1 = null;	
					conn1 = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
					PreparedStatement pst = null;
					BooksDAO.selectBooksList(conn1, listOfBooks);
					for (int i = 0; i < listOfBooks.size(); i++) {
						if(listOfBooks.get(i).getRentby().getPersonName()!=null) {
							listOfHiredBooks.add(listOfBooks.get(i));
						}
						
					}
					for(int i = 0; i < listOfHiredBooks.size(); i++)
						   comboBox.addItem(listOfHiredBooks.get(i));
					int result = JOptionPane.showConfirmDialog(null, comboBox, "Select a Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	                if (result == JOptionPane.OK_OPTION) {
	                    book = (Book) comboBox.getSelectedItem();}
	                else {
	                	return;
	                }
	                personID = book.getRentby().getPersonID();
	                bookName =book.getBookName();
					Library.returnBook(conn1, pst,  bookName,personID, listOfBooks);
					
				}

				 catch (SQLException | ClassNotFoundException se) {
						System.err.println("Threw a SQLException creating the list of books.");
						System.err.println(se.getMessage());
					}
		
			}		
		
		}
		
//		public String getClassroomName(int classroomId) {
//
//			String classroomName = "";
//
//			try {
//
//				String query = "SELECT name FROM ";
//				classroomName = jdbcTemplate.queryForObject(query, new Object[] { classroomId }, String.class);
//
//			} catch (DataAccessException e) {
//				// do nothing, it can be empty
//			}
//			return classroomName;
//		}

}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

