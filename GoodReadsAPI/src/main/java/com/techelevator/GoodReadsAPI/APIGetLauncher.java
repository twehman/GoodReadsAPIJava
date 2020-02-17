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
		
		GoodReadsWebRequest.determineAuthorID();
		
		GoodReadsWebRequest.getListOfAuthorBooks();
		ArrayList<Book> books = new ArrayList<Book>();
		System.out.println(parser.readConfig("xmlresponse.txt").size());

}
}
