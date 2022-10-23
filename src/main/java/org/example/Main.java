package org.example;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {

        literature literature = ParseFileXML.readXML();
        
    }
}