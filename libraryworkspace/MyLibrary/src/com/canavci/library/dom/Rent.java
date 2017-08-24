package com.canavci.library.dom;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rent {
	int p;

	int b;
	java.sql.Date hireDate;
	java.sql.Date returnDate;
	boolean hasReturned;
	public Rent(int p, int b, java.sql.Date returnDate) throws ParseException {
		this.p = p;
		this.b = b;
		this.returnDate = returnDate;
		
		String sdate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf.parse(sdate);
		hireDate = new Date(date.getTime());
	}
	
	public boolean isHasReturned() {
		return hasReturned;
	}

	public void setHasReturned(boolean hasReturned) {
		this.hasReturned = hasReturned;
	}

	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}

	public java.sql.Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(java.sql.Date returnDate) {
		this.returnDate = returnDate;
	}
	
	public java.sql.Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(java.sql.Date hireDate) {
		this.hireDate = hireDate;
	}
}
