package it.cnvcrew.sonar;

import java.util.Vector;

/**
 * Created by electrocamel on 14/06/16.
 */
public class Category {

    int id;
    Vector<Interest> interests = new Vector<Interest>();

    public Category(int id, Vector<Interest> interests) {
        this.id = id;
        this.interests = interests;
    }

    public Category(int id){
        this.id = id;
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

    public void setInterests(Interest interest) {
        this.interests.add(interest);
    }
}
