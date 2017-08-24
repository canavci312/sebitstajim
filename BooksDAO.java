package com.canavci.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.canavci.library.dom.Book;
import com.canavci.library.dom.Category;
import com.canavci.library.dom.Person;

public class BooksDAO {

	public static void populateListOfBooks(Connection conn, LinkedList<Book> listOfBooks) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st
					// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
					// order by PersonID");
					.executeQuery(
							"select b.BookID, b.BookName, b.Category, b.Rentby, b.returndate,  c.CategoryID, c.CategoryName, p.personID,p.personName "
									+ " from Books b, Categories c, Persons p"
									+ " where b.Category=c.CategoryID and b.Rentby = p.personID" + " order by BookID");
			while (rs.next()) {

				Book b = new Book();
				b.setBookID(rs.getInt("BookID"));
				b.setBookName(rs.getString("BookName"));
				b.setReturnDate(rs.getDate("returndate"));
				Person p = new Person();
				p.setPersonID(rs.getInt("personID"));
				p.setPersonName(rs.getString("personName"));

				Category c = new Category();
				c.setCategoryID(rs.getInt("CategoryID"));
				c.setCategoryName(rs.getString("CategoryName"));
				b.setRentby(p);
				b.setCategory(c);

				listOfBooks.add(b);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println("Threw a SQLException creating the list of books.");
			System.err.println(se.getMessage());
		}
	}

	public static void selectBooksList(Connection conn, LinkedList<Book> listOfBooks) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st
					// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
					// order by PersonID");SELECT column_name(s)
					
					.executeQuery("Select Books.BookID, Books.BookName, Books.returndate, Books.Rentby, Persons.PersonID, Persons.PersonName, Books.category, Categories.CategoryID, Categories.CategoryName FROM Books LEFT JOIN Persons ON Books.Rentby = Persons.personID LEFT JOIN Categories ON Categories.CategoryID = Books.category ORDER BY BookID");
			while (rs.next()) {

				Book b = new Book();
				b.setBookID(rs.getInt("BookID"));
				b.setBookName(rs.getString("BookName"));
				b.setReturnDate(rs.getDate("returndate"));
				Person p = new Person();
				p.setPersonID(rs.getInt("Rentby"));
				p.setPersonName(rs.getString("personName"));
				b.setRentby(p);
				Category c = new Category();
				c.setCategoryID(rs.getInt("CategoryID"));
				c.setCategoryName(rs.getString("CategoryName"));
				b.setCategory(c);
				listOfBooks.add(b);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println("Threw a SQLException creating the list of books.");
			System.err.println(se.getMessage());
		}
	} 
	public static void selectCategoryList(Connection conn, LinkedList<Category> listOfCategory) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st
					// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
					// order by PersonID");SELECT column_name(s)
					
					.executeQuery("Select CategoryID, CategoryName from Categories");
			while (rs.next()) {
				Category c = new Category();
				c.setCategoryID(rs.getInt("CategoryID"));
				c.setCategoryName(rs.getString("CategoryName"));
				
				listOfCategory.add(c);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println("Threw a SQLException creating the list of category.");
			System.err.println(se.getMessage());
		}
	}
	public static void selectPersonsList(Connection conn, LinkedList<Person> listOfPerson) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st
					// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
					// order by PersonID");SELECT column_name(s)
					
					.executeQuery("Select *from persons");
			while (rs.next()) {
				Person p = new Person();
				p.setPersonID(rs.getInt("personID"));
				p.setPersonName(rs.getString("personName"));
				listOfPerson.add(p);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println("Threw a SQLException creating the list of persons.");
			System.err.println(se.getMessage());
		}
	}
}

