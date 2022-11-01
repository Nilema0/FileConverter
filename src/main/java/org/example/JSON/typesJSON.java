package org.example.JSON;

import java.util.ArrayList;

public class typesJSON {
    private String name;
    private ArrayList<authorJSON> authors;

    public typesJSON(String name, ArrayList<authorJSON> authors) {
        this.name = name;
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<authorJSON> getAuthors() {
        return authors;
    }
}
