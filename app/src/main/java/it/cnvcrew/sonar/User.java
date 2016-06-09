package it.cnvcrew.sonar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;

/**
 * Created by Alessandro on 31/03/2016.
 */
public class User implements Serializable {

    String nome,cognome,username,email,password,dob;
    String image;
    Bitmap imagebmp;
    int id;

    public User(int id, String nome, String cognome, String username, String email, String image) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.image = image;
        byte[] imgBytes = Base64.decode(image, 0);
        this.imagebmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
    }

    public User(String nome, String cognome, String username, String email, String password, String dob) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    public User(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileBase64() {
        return image;
    }

    public Bitmap getProfile(){
        return imagebmp;
    }

    public void setProfile(Bitmap image) {
        this.imagebmp = image;
    }

    public void setProfileBase64(String string){
        this.image=string;
        if(string.equals("file")){
            imagebmp=null;
        }else{
            byte[] imgBytes = Base64.decode(image, 0);
            this.imagebmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id + '\'' +
                ", image=" + getProfileBase64() +
                '}';
    }
}
