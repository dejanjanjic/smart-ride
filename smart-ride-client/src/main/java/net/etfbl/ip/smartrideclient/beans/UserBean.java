package net.etfbl.ip.smartrideclient.beans;


import net.etfbl.ip.smartrideclient.dao.UserDAO;
import net.etfbl.ip.smartrideclient.dto.User;

public class UserBean {
    private User user;



    public boolean login(String username, String password) {
        return (user = UserDAO.selectByUsernameAndPassword(username, password)) != null;
    }




}
