package com.techelevator.GoodReadsAPI;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcBookDao implements BookDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcBookDao(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	@Override
	public List<Book> sortedBookList(int minReviews) {
		
		List<Book> books = new ArrayList<Book>();
		
		SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT title, average_rating, number_of_ratings from books WHERE number_of_ratings = ? ORDER BY number_of_ratings DESC;", minReviews);

		while (results.next()) {
			books.add(mapRowToBook(results));
		}
		return books;
	}

	@Override
	public void addBooksToDB(ArrayList<Book> books, long authorId, String authorName) {
		
		for(Book book: books) {
			book.setAuthorId(String.valueOf(authorId));
			book.setAuthorName(authorName);
			int authorId2 = Integer.parseInt(book.getAuthorId());
			if (book.getIsbn().equals(null) || book.getIsbn().equals("Not Found")) {
				book.setIsbn("000");
			}
			try { 
				long isbn = Long.parseLong(book.getIsbn());
			}
			catch (NumberFormatException e) {
				book.setIsbn("000");
			}
			if (book.getNumOfPages().equals(null)) {
				book.setNumOfPages("0");
			}
			int numPages = Integer.parseInt(book.getNumOfPages());
			if (book.getAverageRating().equals(null)) {
				book.setAverageRating("0.0");
			}
			double avgRating = Double.parseDouble(book.getAverageRating());
			try { if (book.getRatingCount().equals(null)) {
				book.setRatingCount("0");
			}
			}
			catch (NullPointerException e) {
				book.setRatingCount("0");
			}
			long numRatings = Long.parseLong(book.getRatingCount());
		jdbcTemplate.update("Insert into books (author_id, author_name, isbn, title, book_link, number_of_ages, "
				+ "average_rating, number_of_ratings, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING;"
				,authorId2, book.getAuthorName(), Long.parseLong(book.getIsbn()), book.getBookTitle(), book.getBookLink(), numPages, avgRating, numRatings, book.getDescription());
		}
	}
	
	public Book mapRowToBook(SqlRowSet result) {
		Book returnBook = new Book();
		returnBook.setAuthorId(result.getNString("author_id"));
		returnBook.setAuthorName(result.getString("author_name"));
		returnBook.setIsbn(result.getNString("isbn"));
		returnBook.setBookTitle(result.getString("title"));
		returnBook.setBookLink(result.getString("book_link"));
		returnBook.setNumOfPages(result.getNString("num_of_ages"));
		returnBook.setAverageRating(result.getNString("average_rating"));
		returnBook.setRatingCount(result.getNString("number_of_ratings"));
		returnBook.setDescription(result.getString("description"));
		return returnBook;
	}
	
}
