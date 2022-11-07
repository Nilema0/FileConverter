package org.example.XML;

import java.util.ArrayList;

public class eraXML {
    String name;
    ArrayList<authorXML> authors;

    public eraXML(String name, ArrayList<authorXML> authors){
        this.name = name;
        this.authors = authors;
    }
    public String getName() {
        return name;
    }
    public int length(){
        return authors.size();
    }

    public ArrayList<authorXML> getAuthors() {
        return authors;
    }

    public authorXML get(int index){
        return authors.get(index);
    }
}
