package org.example.XML;

import java.util.ArrayList;

public class literatureXML {
    ArrayList<typeXML> types;

    public literatureXML(){
        this.types = new ArrayList<typeXML>();
    }

    public void addType(String name, ArrayList<eraXML>eras){
        this.types.add(new typeXML(name, eras));
    }

    public int length(){
        return types.size();
    }

    public typeXML get(int index){
        return types.get(index);
    }

}
