package org.example;

import org.example.JSON.authorJSON;
import org.example.JSON.bookJSON;
import org.example.JSON.directionsJSON;
import org.example.JSON.literatureJSON;
import org.example.XML.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseFileXML {

    //region Get info from file
    public static literatureXML readXML() throws FileNotFoundException, XMLStreamException {
        String path = "literature.xml";
        literatureXML literature = new literatureXML();
        File file = new File(path);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(file));

        streamReader.next();
        streamReader.next();

        String nameOfType = "place holder";

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

    private static ArrayList<bookXML> readBook(XMLStreamReader streamReader) throws
            FileNotFoundException, XMLStreamException {

        ArrayList<bookXML> books = new ArrayList<>();
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
                    books.add(new bookXML(nameOfBook, year));
                    streamReader.next();
                    streamReader.next();
                    streamReader.next();
                }
            }
        }
        return books;
    }

    private static ArrayList<authorXML> readAuthor(XMLStreamReader streamReader) throws
            FileNotFoundException, XMLStreamException {

        ArrayList<authorXML> authors = new ArrayList<>();
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

                    authors.add(new authorXML(nameOfAuthor, readBook(streamReader)));
                    streamReader.next();
                    streamReader.next();
                    streamReader.next();
                    streamReader.next();
                }
            }
        }
        return authors;
    }

    private static ArrayList<eraXML> readEra(XMLStreamReader streamReader) throws
            FileNotFoundException, XMLStreamException {
        ArrayList<eraXML> eras = new ArrayList<>();
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

                    eras.add(new eraXML(nameOfEra, readAuthor(streamReader)));
                    streamReader.next();
                    streamReader.next();
                    streamReader.next();
                    streamReader.next();
                }
            }
        }
        return eras;
    }
    //endregion

    public static literatureJSON convertXMLtoJSON(literatureXML litr){
        literatureJSON outLitr = new literatureJSON();
        for (int i = 0; i < litr.length(); i++) {
            typeXML type = litr.get(i);
            String typeName = type.getName();
            ArrayList<authorJSON> authors = null;

            for (int j = 0; j < type.length(); j++) {
                eraXML era = type.get(j);
                directionsJSON jsonEra;
                int index = outLitr.contains(era.getName());
                if (index == -1) {
                    outLitr.addEra(era.getName(), new ArrayList<>());
                    index = outLitr.length() - 1;
                }
                jsonEra = outLitr.get(index);


                authors = new ArrayList<>();
                for (int k = 0; k < era.length(); k++) {
                    authorXML author = era.get(k);
                    String authorName = author.getName();

                    ArrayList<bookJSON> books = new ArrayList<>();
                    for (int l = 0; l < author.length(); l++) {
                        bookXML book = author.get(l);
                        books.add(new bookJSON(book.getName(), book.getYear()));
                    }
                    authors.add(new authorJSON(authorName,books));

                }

                jsonEra.addDirection(typeName,authors);

            }


        }
        return outLitr;
    }

    public static void createJSON(literatureJSON json, String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path),json);
    }
}