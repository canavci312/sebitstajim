package com.canavci.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import com.canavci.library.ctrl.Library;
import com.canavci.library.dao.BooksDAO;
import com.canavci.library.dom.Book;
import com.canavci.library.gui.LibraryGui;

public class ConnDB {

	public static void main(String[] args) {
/*		// TODO Auto-generated method stub

		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
			
			
			
			
			
			
			
			
			
			
			
			// get the data
			LinkedList<Book> listOfBooks = new LinkedList<Book>();
	//		BooksDAO.populateListOfBooks(connection, listOfBooks);
//			BooksDAO.selectBooksList(connection, listOfBooks);
			// print data
			// listOfBooks.forEach(System.out::println);
			PreparedStatement pst = null;
			// Statement st=null;
	//	 	Library.addBook(connection, pst, "Hatıralarım", 1);
			 Library.hireBook(connection,pst, 1, "Beşiktaşın Tarihi","2017-10-20", listOfBooks);
			// listOfBooks);
	//		listOfBooks.forEach(System.out::println);
//			Library.returnBook(connection, pst, "Beşiktaşın Tarihi", 2, listOfBooks);
		
		//	Library.hireBook(connection, pst, 2, "Tarih", "2017-09-20", listOfBooks);
//			Library.returnBook(connection, pst, "Tarih", 2, listOfBooks);
			connection.close();			
			System.out.println("Connection successful");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
	LibraryGui test = new LibraryGui();

}}