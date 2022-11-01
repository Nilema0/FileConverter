package org.example.XML;

import java.util.ArrayList;

public class authorXML {
    String name;
    ArrayList<bookXML> books;
    public authorXML(String name, ArrayList<bookXML> books){
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }
    public int length(){
        return books.size();
    }

    public bookXML get(int index){
        return books.get(index);
    }
}
