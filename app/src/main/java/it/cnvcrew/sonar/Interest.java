package it.cnvcrew.sonar;

/**
 * Created by Alessandro on 14/05/2016.
 */
public class Interest {

    private int id, category_id;
    private String name;
    private boolean is_selected;

    public Interest(int id, int category_id, String name, boolean is_selected) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.is_selected = is_selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is_selected() {
        return is_selected;
    }

    public void setChecked(boolean checked) {
        is_selected = checked;
    }

    public int getCategory_id() { return category_id; }

    public void setCategory_id(int category_id) { this.category_id = category_id; }

    @Override
    public String toString() {
        return "Interest{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", name='" + name + '\'' +
                ", is_selected=" + is_selected +
                '}';
    }
}
