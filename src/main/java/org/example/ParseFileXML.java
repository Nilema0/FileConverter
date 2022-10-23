package org.example;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class ParseFileXML {

    public static literature readXML() throws FileNotFoundException, XMLStreamException {
        String path = "literature.xml";
        literature literature = new literature();
        File file = new File(path);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(file));

        streamReader.next();
        streamReader.next();
        //streamReader.next();
        //streamReader.next();

        String nameOfType = "place holder";
        //ArrayList<type> types = null;

        while (streamReader.hasNext()) {
            streamReader.next();

            if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {

                if (streamReader.getLocalName().equalsIgnoreCase("type")) {

                    streamReader.next();
                    streamReader.next();

                    if (streamReader.getLocalName().equalsIgnoreCase("name")) {
                        nameOfType = streamReader.getElementText();
                    }

                    streamReader.next();
                    streamReader.next();

                    if (streamReader.getLocalName().equalsIgnoreCase("directions")) {

                        streamReader.next();//
                        streamReader.next();//point to era

                        literature.addType(nameOfType, readEra(streamReader));
                    }
                }

            }
        }
        return literature;
    }
        public static ArrayList<book> readBook (XMLStreamReader streamReader) throws
        FileNotFoundException, XMLStreamException {

            ArrayList<book> books = new ArrayList<>();
            String nameOfBook = "place holder";
            Integer year = -1;

            streamReader.next();
            streamReader.next();

            while ((streamReader.getEventType() != XMLStreamReader.END_ELEMENT)
                    & !(streamReader.getLocalName().equalsIgnoreCase("books"))) {

                streamReader.next();
                streamReader.next();

                if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (streamReader.getLocalName().equalsIgnoreCase("name")) {
                        nameOfBook = streamReader.getElementText();

                        streamReader.next();
                        streamReader.next();

                        year = Integer.parseInt(streamReader.getElementText());
                        streamReader.next();
                        books.add(new book(nameOfBook, year));
                        streamReader.next();
                        streamReader.next();
                        streamReader.next();
                    }
                }
            }
            return books;
        }
        public static ArrayList<author> readAuthor (XMLStreamReader streamReader) throws
        FileNotFoundException, XMLStreamException {

            ArrayList<author> authors = new ArrayList<>();
            String nameOfAuthor = "name";

            streamReader.next();
            streamReader.next();

            while ((streamReader.getEventType() != XMLStreamReader.END_ELEMENT)
                    & !(streamReader.getLocalName().equalsIgnoreCase("authors"))) {

                streamReader.next();
                streamReader.next();

                if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (streamReader.getLocalName().equalsIgnoreCase("name")) {
                        nameOfAuthor = streamReader.getElementText();

                        streamReader.next();
                        streamReader.next();

                        authors.add(new author(nameOfAuthor, readBook(streamReader)));
                        streamReader.next();
                        streamReader.next();
                        streamReader.next();
                        streamReader.next();
                    }
                }
            }
            return authors;
        }
        public static ArrayList<era> readEra (XMLStreamReader streamReader)throws
        FileNotFoundException, XMLStreamException {
            ArrayList<era> eras = new ArrayList<>();
            String nameOfEra = "name";

            while ((streamReader.getEventType() != XMLStreamReader.END_ELEMENT)
                    & !(streamReader.getLocalName().equalsIgnoreCase("directions"))) {

                streamReader.next();
                streamReader.next();

                if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                    if (streamReader.getLocalName().equalsIgnoreCase("name")) {
                        nameOfEra = streamReader.getElementText();

                        streamReader.next();
                        streamReader.next();

                        eras.add(new era(nameOfEra, readAuthor(streamReader)));
                        streamReader.next();
                        streamReader.next();
                        streamReader.next();
                        streamReader.next();
                    }
                }
            }
            return eras;
        }
}