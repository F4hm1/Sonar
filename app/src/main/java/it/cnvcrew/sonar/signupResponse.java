package it.cnvcrew.sonar;

import java.io.Serializable;

/**
 * Created by alessandro on 21/11/2016.
 */
public class SignupResponse implements Serializable{
    private String message;

    public SignupResponse(String message){
        this.message = message;
    }

    public SignupResponse(){}

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString(){
        return message;
    }
}
