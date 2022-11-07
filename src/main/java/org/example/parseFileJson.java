package org.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.example.JSON.*;
import org.example.XML.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.stream.*;
import java.io.*;
import java.util.ArrayList;

public class parseFileJson {

    //region Get info from file

    public static literatureJSON readJSON() throws IOException {
        String path = "literature.json";
        literatureJSON literature = new literatureJSON();

        File fl = new File(path);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(fl);

        parser.nextToken();
        parser.nextToken();

        String nameOfEra = "place holder";
        if (parser.nextToken() == JsonToken.START_ARRAY) {

            while (parser.nextToken() != JsonToken.END_ARRAY) {

                parser.nextToken();
                parser.nextToken();


                if (parser.getCurrentName().equalsIgnoreCase("name")) {
                    nameOfEra = parser.getText();
                }

                parser.nextToken();
                parser.nextToken();

                if (parser.getCurrentName().equalsIgnoreCase("types")) {
                    literature.addEra(nameOfEra, readType(parser));
                    parser.nextToken();
                }


            }
        }
        return literature;
    }

    private static ArrayList<typesJSON> readType(JsonParser parser) throws IOException {
        ArrayList<typesJSON> types = new ArrayList<>();
        String nameOfType = "name";

        while (parser.nextToken() != JsonToken.END_ARRAY) {
            parser.nextToken();

            if (parser.getCurrentName().equalsIgnoreCase("name")) {
                parser.nextToken();
                nameOfType = parser.getText();

                parser.nextToken();
                parser.nextToken();

                types.add(new typesJSON(nameOfType, readAuthor(parser)));

            }
        }

        return types;
    }

    private static ArrayList<authorJSON> readAuthor(JsonParser parser) throws IOException {

        ArrayList<authorJSON> authors = new ArrayList<>();
        String nameOfAuthor = "name";

        parser.nextToken();
        while (parser.currentToken() != JsonToken.END_ARRAY) {
            parser.nextToken();
            if (parser.getCurrentName().equalsIgnoreCase("name")) {
                parser.nextToken();
                nameOfAuthor = parser.getText();

                parser.nextToken();
                parser.nextToken();

                authors.add(new authorJSON(nameOfAuthor, readBook(parser)));
                parser.nextToken();
                parser.nextToken();
            }
        }
        parser.nextToken();
        return authors;
    }

    private static ArrayList<bookJSON> readBook(JsonParser parser) throws IOException {

        ArrayList<bookJSON> books = new ArrayList<>();
        String nameOfBook = "place holder";
        Integer year = -1;

        parser.nextToken();
        while (parser.currentToken() != JsonToken.END_ARRAY) {
            parser.nextToken();
            if (parser.getCurrentName().equalsIgnoreCase("name")) {
                parser.nextToken();
                nameOfBook = parser.getText();
                parser.nextToken();
                parser.nextToken();
                year = Integer.parseInt(parser.getText());

                parser.nextToken();
                books.add(new bookJSON(nameOfBook, year));

                parser.nextToken();
            }
        }
        return books;
    }

    //endregion


    public static literatureXML convertJSONtoXML(literatureJSON litr){
        literatureXML outLitr = new literatureXML();
        for (int i = 0; i < litr.length(); i++) {
            directionsJSON direction = litr.get(i);
            String directionName = direction.getName();
            ArrayList<authorXML> authors = null;

            for (int j = 0; j < direction.length(); j++) {
                typesJSON type = direction.get(j);
                typeXML xmlType;
                int index = outLitr.contains(type.getName());
                if (index == -1) {
                    outLitr.addType(type.getName(), new ArrayList<>());
                    index = outLitr.length() - 1;
                }
                xmlType = outLitr.get(index);


                authors = new ArrayList<>();
                for (int k = 0; k < type.length(); k++) {
                    authorJSON author = type.get(k);
                    String authorName = author.getName();

                    ArrayList<bookXML> books = new ArrayList<>();
                    for (int l = 0; l < author.length(); l++) {
                        bookJSON book = author.get(l);
                        books.add(new bookXML(book.getName(), book.getYear()));
                    }
                    authors.add(new authorXML(authorName,books));

                }

                xmlType.addEra(directionName,authors);

            }


        }
        return outLitr;
    }

    public static void createXML(literatureXML xmlClass, String path) {
        try(FileOutputStream out = new FileOutputStream(path)){
            writeXml(out, xmlClass);
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
    private static void writeXml(OutputStream out, literatureXML xmlClass) throws XMLStreamException {
        //stax cursor
        XMLOutputFactory output = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = output.createXMLStreamWriter(out);

        writer.writeStartDocument("utf-8", "1.0");

        //header
        writer.writeStartElement("literature");



        // insides
        for (int i = 0; i < xmlClass.length(); i++) {
            writer.writeStartElement("type");
            writer.writeStartElement("name");
            writer.writeCharacters(xmlClass.get(i).getName());
            writer.writeEndElement();

            writer.writeStartElement("directions");


            for (int j = 0; j < xmlClass.getTypes().get(i).getEras().size(); j++) {
                eraXML era = xmlClass.getTypes().get(i).getEras().get(j);

                writer.writeStartElement("era");


                writer.writeStartElement("name");
                writer.writeCharacters(era.getName());
                writer.writeEndElement();

                writer.writeStartElement("authors");

                for (int k = 0; k < era.getAuthors().size(); k++) {
                    authorXML author = era.getAuthors().get(k);

                    writer.writeStartElement("author");

                    writer.writeStartElement("name");
                    writer.writeCharacters(author.getName());
                    writer.writeEndElement();

                    writer.writeStartElement("books");

                    for (int l = 0; l < author.getBooks().size(); l++) {
                        bookXML book = author.getBooks().get(l);

                        writer.writeStartElement("book");

                        writer.writeStartElement("name");
                        writer.writeCharacters(book.getName());
                        writer.writeEndElement();

                        writer.writeStartElement("year");
                        writer.writeCharacters(String.valueOf(book.getYear()));
                        writer.writeEndElement();

                        writer.writeEndElement();//end book


                    }
                    writer.writeEndElement();//end books
                    writer.writeEndElement();//end author

                }
                writer.writeEndElement();//end authors
                writer.writeEndElement();//end era
            }
            writer.writeEndElement();//end directions
            writer.writeEndElement();//end type

        }

        writer.writeEndElement();//end litr



        writer.flush();

        writer.close();
    }
}



