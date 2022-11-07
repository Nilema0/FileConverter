package org.example;

import org.example.JSON.literatureJSON;
import org.example.XML.literatureXML;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws XMLStreamException, IOException {

        //literatureXML literature = ParseFileXML.readXML();


        //ParseFileXML.createJSON(ParseFileXML.convertXMLtoJSON(literature),"NewFile.json");

        literatureJSON literature = parseFileJson.readJSON();
        var w = parseFileJson.convertJSONtoXML(literature);
        parseFileJson.createXML(w, "NewFile.xml");
        int s =2;

    }
}