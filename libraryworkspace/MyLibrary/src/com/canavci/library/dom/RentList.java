package com.canavci.library.dom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class RentList {
	
	
	public Object[][] getRentList() throws ClassNotFoundException
	{
		Object[][] data = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn1 = null;	
			conn1 = DriverManager.getConnection("jdbc:postgresql://10.106.2.63:5432/mylibrary", "odoo", "5678");
		 
			Statement st = conn1.createStatement();
			ResultSet rs = st
					// .executeQuery("select p.PersonID, p.FirstName, p.LastName from Persons p
					// order by PersonID");
					.executeQuery(
							"select b.BookID, b.BookName, r.BookID, r.PersonID, r.hiredate, r.returndate,r.hasReturned from Books b, Rents r where b.BookID = r.BookID");
		int rowCount = getRowCount(rs); // Row Count
		int columnCount = getColumnCount(rs); // Column Count

		data = new Object[rowCount][columnCount];

		// Starting from First Row for Iteration
		rs.beforeFirst();

		int i = 0;
		
		while (rs.next()) {

			int j = 0;

			data[i][j++] = rs.getInt("b.BookID");
			data[i][j++] = rs.getString("b.BookName");
			data[i][j++] = rs.getInt("p.PersonID");
			data[i][j++] = rs.getString("p.PersonName");
			data[i][j++] = rs.getDate("r.hiredate");
			data[i][j++] = rs.getDate("r.returndate");
			data[i][j++] = rs.getBoolean("r.hasReturned");

			i++;
		
		}
		}
			 catch (SQLException se) {
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







}
