package com.techelevator.GoodReadsAPI;

public class Book {
	
	private String authorId;
	private String authorName;
	private String isbn;
	private String bookTitle;
	private String bookLink;
	private String numOfPages;
	private String averageRating;
	private String ratingCount;
	private String description;
	private String bookStartEndTotalNum;
	
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getBookLink() {
		return bookLink;
	}
	public void setBookLink(String bookLink) {
		this.bookLink = bookLink;
	}
	public String getNumOfPages() {
		return numOfPages;
	}
	public void setNumOfPages(String numOfPages) {
		this.numOfPages = numOfPages;
	}
	public String getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(String averageRating) {
		this.averageRating = averageRating;
	}
	public String getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(String ratingCount) {
		this.ratingCount = ratingCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBookStartEndTotalNum() {
		return bookStartEndTotalNum;
	}
	public void setBookStartEndTotalNum(String bookStartEndTotalNum) {
		this.bookStartEndTotalNum = bookStartEndTotalNum;
	}
	
	@Override
	public String toString() {
		return "Current title is " + bookTitle + "it has " + ratingCount + " ratings with an average of " + averageRating;
	}
	

}
