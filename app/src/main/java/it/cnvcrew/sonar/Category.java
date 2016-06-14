package it.cnvcrew.sonar;

import java.util.Vector;

/**
 * Created by electrocamel on 14/06/16.
 */
public class Category {

    int id;
    String name;
    Vector<Interest> interests = new Vector<Interest>();

    public Category(int id, String name, Vector<Interest> interests) {
        this.id = id;
        this.name = name;
        this.interests = interests;
    }

    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<Interest> getInterests() {
        return interests;
    }

    public Interest[] getInterestsArray(){
        Interest[] interestsArray = new Interest[interests.size()];
        interests.copyInto(interestsArray);
        return interestsArray;
    }

    public void setInterests(Interest interest) {
        this.interests.add(interest);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
