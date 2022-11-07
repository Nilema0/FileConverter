package org.example.JSON;


import java.util.ArrayList;

public class authorJSON {
    String name;
    ArrayList<bookJSON> books;
    public authorJSON(String name, ArrayList<bookJSON> books){
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<bookJSON> getBooks() {
        return books;
    }
    public bookJSON get (int index){
        return books.get(index);
    }

    public int length(){
        return books.size();
    }
}
