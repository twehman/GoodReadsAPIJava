package com.techelevator.GoodReadsAPI;

import java.util.ArrayList;
import java.util.List;

public interface BookDAO {
	
	public List<Book> sortedBookList (int minReviews);
	
	public void addBooksToDB (ArrayList<Book> books, long authorId, String authorName);
	
}
