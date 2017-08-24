package com.canavci.library.ctrl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

import com.canavci.library.dao.BooksDAO;
import com.canavci.library.dom.Book;
import com.canavci.library.dom.Rent;

public class Library {

	public static void addPerson(Connection conn, PreparedStatement pst, int id, String name) throws SQLException {
		pst = conn.prepareStatement("INSERT INTO Persons (personId, personName) values (?,?)");
		pst.setInt(1, id);
		pst.setString(2, name);
		pst.executeUpdate();
	}

	public static void addBook(Connection conn, PreparedStatement pst, String bookname, int categoryid)
			throws SQLException {
		pst = conn.prepareStatement("INSERT INTO Books (BookName, Category) values (?,?) ");
		pst.setString(1, bookname);
		pst.setInt(2, categoryid);
		pst.executeUpdate();

	}

	public static void hireBook(Connection conn, PreparedStatement pst, int personid, String bookname,
			String returndate, LinkedList<Book> listOfBooks) throws SQLException, ParseException {
		int bookid = -1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf.parse(returndate);

		java.sql.Date sqlDate = new Date(date.getTime());
		BooksDAO.selectBooksList(conn, listOfBooks);
		int i;

		for (i = 0; i < listOfBooks.size(); i++) {
			if (listOfBooks.get(i).getBookName().equals(bookname) && listOfBooks.get(i).getRentby().getPersonName() == null) {
				bookid = listOfBooks.get(i).getBookID();
				break;

			} else if (listOfBooks.get(i).getBookName() == bookname && listOfBooks.get(i).getRentby() != null) {
				i++;
			} else
				i++;
		}
		if (bookid == -1) {
			System.out.println("Such book doesnt exist or already rented");

		} else {
			
			pst = conn.prepareStatement("UPDATE Books SET Rentby=?, returndate = ? WHERE BookID= ?");
			pst.setInt(1, personid);
			pst.setDate(2, sqlDate);
			pst.setInt(3, bookid);
			pst.executeUpdate();
			System.out.println(bookname + " has been rented to " + personid + ".");
			Rent r = new Rent(personid, bookid, sqlDate);
			
			pst = conn.prepareStatement("INSERT INTO Rents(BookID, PersonID, hiredate, returndate)values(?,?, ?,?)");
			pst.setInt(1, bookid);
			pst.setInt(2, personid);
			pst.setDate(4, sqlDate );
			pst.setDate(3,r.getHireDate());
			pst.executeUpdate();
		}
	}

	public static void returnBook(Connection conn, PreparedStatement pst, String bookname, int personid,
			LinkedList<Book> listOfBooks) throws SQLException {
		BooksDAO.selectBooksList(conn, listOfBooks);
		int bookid = -1;
		int i;
		for (i = 0; i < listOfBooks.size(); i++) {
			if (listOfBooks.get(i).getBookName().equals(bookname)
					&& listOfBooks.get(i).getRentby().getPersonID() == personid) {
				bookid = listOfBooks.get(i).getBookID();
				
				break;
			} else
				i++;
		}
		if (bookid == -1)
			System.out.println("No such book or not rented by this person");
		else {
			pst = conn.prepareStatement("UPDATE Rents SET hasReturned='yes' WHERE BookID= ? and personid= ? and returndate = ? ");
			pst.setInt(1, bookid);
			pst.setInt(2,listOfBooks.get(i).getRentby().getPersonID() );
			pst.setDate(3, listOfBooks.get(i).getReturnDate());
			pst.executeUpdate();

			pst = conn.prepareStatement("UPDATE Books SET Rentby=NULL, returndate = NULL WHERE BookID= ?");

			pst.setInt(1, bookid);
			pst.executeUpdate();

			System.out.println("Book: " + bookname + " has returned to the library.");
			
		}
	}
	
}
