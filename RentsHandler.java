/*
package com.canavci.library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.canavci.library.dom.Book;
import com.canavci.library.dom.Category;
import com.canavci.library.dom.Person;
import com.canavci.library.dom.Rent;
import com.canavci.library.dom.RentList;

public class RentsHandler {
	public static void rentList(Connection conn, LinkedList<RentList> listOfRents) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st
					// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
					// order by PersonID");
					.executeQuery(
							"select b.BookID, b.BookName, r.BookID, r.PersonID, r.hiredate, r.returndate, r.hasReturned, p.personID, p.personName from Books b, Rents r, Persons p where b.BookID = r.BookID,  r.PersonID=b.Rentby");
			while (rs.next()) {
	
				
				RentList r = new RentList();
				r.setHireDate(rs.getDate("r.hiredate"));
				r.setHasReturned(rs.getBoolean("hasReturned"));
				r.setReturnDate(rs.getDate("r.returndate"));
				
				r.setBookID(rs.getInt("b.BookID"));
				r.setBookName(rs.getString("b.BookName"));
				
				r.setPersonID(rs.getInt("p.personID"));
				r.setPersonName(rs.getString("p.personName"));
				
				listOfRents.add(r);
			}
			rs.close();
			st.close();
		} catch (SQLException se) {
			System.err.println("Threw a SQLException creating the list of rents.");
			System.err.println(se.getMessage());
		}
	}

}
*/