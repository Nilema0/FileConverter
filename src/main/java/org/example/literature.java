package org.example;

import java.util.ArrayList;

public class literature {
    ArrayList<type> types;

    public literature(){
        this.types = new ArrayList<type>();
    }

    public void addType(String name, ArrayList<era>eras){
        this.types.add(new type(name, eras));
    }
}
