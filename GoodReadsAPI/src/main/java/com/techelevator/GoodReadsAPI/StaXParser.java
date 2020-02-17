package com.techelevator.GoodReadsAPI;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.xml.sax.ContentHandler;

public class StaXParser {
	static final String BOOK = "book";
	static final String AUTHORID = "id";
	static final String AUTHORNAME = "name";
	static final String ISBN = "isbn";
	static final String BOOKTITLE = "title";
	static final String BOOKLINK = "link";
	static final String NUMOFPAGES = "num_pages";
	static final String AVERAGERATING = "average_rating";
	static final String RATINGCOUNT = "ratings_count";
	static final String DESCRIPTION = "description";
	static final String BOOKSTARTENDTOTALNUM = "books";
	private int startingReviewCount = Integer.MAX_VALUE;
	
	@SuppressWarnings({ "unchecked", "null" })
    public List<Book> readConfig(String configFile) {
        List<Book> items = new ArrayList<Book>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Book item = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    
                    if (startElement.getName().getLocalPart().equals(BOOK)) {
                        item = new Book();
                        // We read the attributes from this tag and add the date
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(AUTHORID)) {
                                item.setAuthorId(attribute.getValue());
                            }

                        }
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(ISBN)) {
                        event = eventReader.nextEvent();
                        item.setIsbn(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(BOOKTITLE)) {
                        event = eventReader.nextEvent();
                        item.setBookTitle(event.asCharacters().getData());
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(BOOKLINK)) {
                        event = eventReader.nextEvent();
                        if (event.asCharacters().getData().contains("https://www.goodreads.com/book/show")) {
                        	item.setBookLink(event.asCharacters().getData());
                        }
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(NUMOFPAGES)) {
                        event = eventReader.nextEvent();
                        item.setNumOfPages(event.asCharacters().getData());
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(AVERAGERATING)) {
                        event = eventReader.nextEvent();
                        if (!event.asCharacters().getData().equals("4.00")) {
                        	item.setAverageRating(event.asCharacters().getData());
                        }
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(RATINGCOUNT)) {
                        event = eventReader.nextEvent();
                        if (Integer.parseInt(event.asCharacters().getData()) <= startingReviewCount ) {
                        	item.setRatingCount(event.asCharacters().getData());
                        	startingReviewCount = Integer.parseInt(event.asCharacters().getData());
                        }
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(DESCRIPTION)) {
                        event = eventReader.nextEvent();
                        item.setDescription(event.asCharacters().getData());
                        continue;
                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(BOOK)) {
                        items.add(item);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }

}

	
