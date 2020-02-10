package com.techelevator.GoodReadsAPI;

import java.io.IOException;

public class APIGetLauncher {

	public static void main(String[] args) throws IOException {
		GoodReadsWebRequest testRequest = new GoodReadsWebRequest();
		
		GoodReadsWebRequest.determineAuthorID();
		
		GoodReadsWebRequest.getListOfAuthorBooks();
		}

}
