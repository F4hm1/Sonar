package it.cnvcrew.sonar;

/**
 * Created by Gianmarco on 02/03/2016.
 */
public class UtenteLogin {
    private String username;
    private String password;

    public UtenteLogin(){}

    public UtenteLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
