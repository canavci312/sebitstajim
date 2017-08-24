package com.canavci.library.dom;

public class Book {
	int bookID;
	String bookName;
	Category category;
	Person rentby;
	java.sql.Date returnDate;

	public java.sql.Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(java.sql.Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Person getRentby() {
		return rentby;
	}

	public void setRentby(Person rentby) {
		this.rentby = rentby;
	}

	@Override
	public String toString() {
		return "ID=" + bookID + ", Name=" + bookName + ", Category=" + category + ", Rent by " + rentby
				+ ", Return Date= " + returnDate + "]";
	}

}
