package com.techelevator.GoodReadsAPI;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.html.parser.Parser;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class APIGetLauncher {

	public static void main(String[] args) throws IOException, SAXException, InterruptedException {
		Book book = new Book();
		StaXParser parser = new StaXParser();
		GoodReadsWebRequest testRequest = new GoodReadsWebRequest();
		
		ArrayList<Book> books = new ArrayList<Book>();
		for (int i = 1; i <= 5; i++) {
		GoodReadsWebRequest.getListOfAuthorBooks(i);
		Thread.sleep(1000);
		for (Book item : parser.readConfig("xmlresponse.txt")) {
			books.add(item);
		}
		}
		System.out.println(books.size());
}
}
