package com.techelevator.GoodReadsAPI;

public class Author {
	
	private String authorId;
	private long authorLongId;

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
		this.authorLongId = Long.parseLong(this.authorId);
	}
	
	public long getAuthorLongId() {
		return authorLongId;
	}
	

}
