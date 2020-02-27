package com.techelevator.GoodReadsAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.parser.Parser;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class APIGetLauncher {

	public static void main(String[] args) throws IOException, SAXException, InterruptedException {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/goodreads");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		JdbcBookDao bookDao = new JdbcBookDao(dataSource);
		
		Book book = new Book();
		BookParser parser = new BookParser();
		GoodReadsWebRequest testRequest = new GoodReadsWebRequest();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter author's name: ");
		
		String authorName = scanner.nextLine();
		
		testRequest.determineAuthorID(authorName);
		
		long authorId = new AuthorParser().readConfig("xmlAuthorId.txt");
		System.out.println(authorId);
		
		ArrayList<Book> books = testRequest.getListOfAuthorBooks(authorId);
		Thread.sleep(1000);
		System.out.print("Minimum # of reviews? ");
		int minReviews = Integer.parseInt(scanner.nextLine());
		
		System.out.format("%-50s%-20s%-20s", "Title", "Review Count", "Average Review Rating");
		System.out.println();
		for (Book item : parser.readConfig("xmlresponse.txt")) {
			if (Integer.parseInt(item.getRatingCount()) > minReviews) {
				System.out.format("%-50s%-20s%-20s",item.getBookTitle(), item.getRatingCount(), item.getAverageRating());
				System.out.println();
			}
			item.setAuthorId(String.valueOf(authorId));
			books.add(item);
		}

		bookDao.addBooksToDB(books, authorId, authorName);
		System.out.println();
		for (Book sortedBook : bookDao.sortedBookList(minReviews)) {
			System.out.println(sortedBook.getBookTitle());
		}

		}
}
