package org.example.JSON;


import java.util.ArrayList;

public class literatureJSON {
    private ArrayList<directionsJSON> directions;
    public literatureJSON(){
        this.directions = new ArrayList<directionsJSON>();
    }

    public void addEra(String directionName, ArrayList<typesJSON> directions) {
        this.directions.add(new directionsJSON(directionName,directions));
    }

    public int length(){
        return directions.size();
    }

    public int contains(String directions){
        for (int i = 0; i < this.directions.size(); i++) {
            if (this.directions.get(i).getName().equals(directions))
                return i;
        }
        return -1;
    }
    public directionsJSON get(int index){
        return directions.get(index);
    }

    public ArrayList<directionsJSON> getDirections() {
        return directions;
    }
}
