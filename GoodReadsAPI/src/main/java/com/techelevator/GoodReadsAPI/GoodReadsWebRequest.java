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
import java.util.Map;

public class GoodReadsWebRequest {
	
	
	public static void determineAuthorID() throws IOException {
	    URL urlForGetRequest = new URL("https://www.goodreads.com/api/author_url/Agatha%20Christie?key=YAlLGMcHE1fx8JP1n3IgNg");
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
	        // print result
	        System.out.println("JSON String Result " + response.toString());
	        //GetAndPost.POSTRequest(response.toString());
	    } else {
	        System.out.println("GET NOT WORKED");
	    }
	}
	
	public static void getListOfAuthorBooks(int callPage) throws IOException, InterruptedException {
//		int currPage = callPage;
//		int finalPage = 5;
//		while (finalPage >= currPage) {
	    URL urlForGetRequest = new URL("https://www.goodreads.com/author/list/123715?format=xml&key=YAlLGMcHE1fx8JP1n3IgNg&page=" + callPage);
	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
	    conection.setRequestMethod("GET");
	    conection.setRequestProperty("123715", "YAlLGMcHE1fx8JP1n3IgNg"); // set userId its a sample here
	    int responseCode = conection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	        	//if(readLine.contains("<books start") && currPage == 1) {
	        	//	finalPage = determineFinalPage(readLine);
	        	//}
	            response.append(readLine);
	        } in .close();
	        // print result
	        File xmlResponse = new File("xmlresponse.txt");
	        FileWriter outFile = new FileWriter(xmlResponse);
	        outFile.write(response.toString());
	        outFile.close();
	        System.out.println("JSON String Result " + response.toString());
	        //GetAndPost.POSTRequest(response.toString());
	    } else {
	        System.out.println("GET NOT WORKED");
	    }
//	    currPage++;
//	    Thread.sleep(1000);
//		}
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
}
