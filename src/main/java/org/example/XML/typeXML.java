package org.example.XML;

import java.util.ArrayList;

public class typeXML {
    String name;
    ArrayList<eraXML> eras;

    public typeXML(String name, ArrayList<eraXML>eras){
        this.name = name;
        this.eras = eras;
    }

    public int length(){
        return eras.size();
    }
    public eraXML get(int index){
        return eras.get(index);
    }

    public String getName() {
        return name;
    }


}
