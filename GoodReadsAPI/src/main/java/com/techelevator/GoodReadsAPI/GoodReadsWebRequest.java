package com.techelevator.GoodReadsAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodReadsWebRequest {
	
	BookParser parser = new BookParser();
	
	
	public void determineAuthorID(String authorName) throws IOException {
		String urlReplace = authorName.replace(" ", "%20");
	    URL urlForGetRequest = new URL("https://www.goodreads.com/api/author_url/"+ urlReplace + "?key=YAlLGMcHE1fx8JP1n3IgNg");
	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
	    conection.setRequestMethod("GET");
	    conection.setRequestProperty("Orson Scott Card", "YAlLGMcHE1fx8JP1n3IgNg"); // set userId its a sample here
	    int responseCode = conection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
	        File xmlResponse = new File("xmlAuthorId.txt");
	        FileWriter outFile = new FileWriter(xmlResponse);
	        outFile.write(response.toString());
	        outFile.close();
	        // print result
	        System.out.println("JSON String Result " + response.toString());
	        //GetAndPost.POSTRequest(response.toString());
	    } else {
	        System.out.println("GET NOT WORKED");
	    }
	}
	
	public ArrayList<Book> getListOfAuthorBooks(long authorId) throws IOException, InterruptedException {
		ArrayList <Book> books = new ArrayList<Book>();
		int currPage = 1;
		int finalPage = 5;
		String authorNum = String.valueOf(authorId);
		while (finalPage >= currPage) {
	    URL urlForGetRequest = new URL("https://www.goodreads.com/author/list/" + authorNum + "?format=xml&key=YAlLGMcHE1fx8JP1n3IgNg&page=" + currPage);
	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
	    conection.setRequestMethod("GET");
	    conection.setRequestProperty(authorNum, "YAlLGMcHE1fx8JP1n3IgNg"); // set userId its a sample here
	    int responseCode = conection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	        	if(readLine.contains("<books start") && currPage == 1) {
	        		finalPage = determineFinalPage(readLine);
	        	}
	            response.append(readLine);
	        } in .close();
	        // print result
	        File xmlResponse = new File("xmlresponse.txt");
	        FileWriter outFile = new FileWriter(xmlResponse);
	        outFile.write(response.toString());
	        outFile.close();
	        for (Book book : parser.readConfig("xmlresponse.txt")) {
	        	books.add(book);
	        }
	        System.out.println("JSON String Result " + response.toString());
	        //GetAndPost.POSTRequest(response.toString());
	    } else {
	        System.out.println("GET NOT WORKED");
	    }
    currPage++;
    Thread.sleep(1000);
    }
		return books;
	}
	
	public static int determineFinalPage(String bookStartTag) {
		String[] split = bookStartTag.split("\"");
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (String string : split) {
			if(string.matches("\\d+")) {
				nums.add(Integer.parseInt(string));
			}
		}
		return (nums.get(2) / 30) + 1;
	}
	
	public String formatAuthorName (String authorName) {
		return "";
	}
	
	
}
