package net.etfbl.ip.smartridemanager.beans;

import net.etfbl.ip.smartridemanager.dao.UserDAO;
import net.etfbl.ip.smartridemanager.dto.User;

import java.io.Serializable;


public class UserBean implements Serializable {

    private static final long serialVersionUID = 2L;


    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private boolean loggedIn = false;

    public UserBean() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean login(String username, String plainPassword) {
        this.loggedIn = false;

        User userFromDB = UserDAO.findManagerUserByUsernameAndPassword(username, plainPassword);

        if (userFromDB != null) {
                    this.id = userFromDB.getId();
                    this.username = userFromDB.getUsername();
                    this.firstName = userFromDB.getFirstName();
                    this.lastName = userFromDB.getLastName();
                    this.loggedIn = true;
                    this.password = null;
                    return true;
        }
        this.password = null;
        return false;
    }
}