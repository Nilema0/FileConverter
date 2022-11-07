package org.example.JSON;

import java.util.ArrayList;

public class directionsJSON {
    private String name;

    private ArrayList<typesJSON> types;

    public directionsJSON(String name, ArrayList<typesJSON> directions) {
        this.name = name;
        this.types = directions;
    }


    public void addDirection(String name, ArrayList<authorJSON> authors){
        types.add(new typesJSON(name, authors));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<typesJSON> getTypes() {
        return types;
    }

    public int length(){
        return types.size();
    }

    public typesJSON get(int index){
        return types.get(index);
    }
}
