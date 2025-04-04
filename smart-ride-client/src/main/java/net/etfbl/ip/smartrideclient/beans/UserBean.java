package net.etfbl.ip.smartrideclient.beans;


import net.etfbl.ip.smartrideclient.dao.UserDAO;
import net.etfbl.ip.smartrideclient.dto.User;

public class UserBean {
    private User user = new User();
    private boolean isLoggedIn = false;



    public boolean login(String username, String password) {
        if ((user = UserDAO.selectByUsernameAndPassword(username, password))!=null && !user.isBlocked()){
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public String getName(){
        return user == null ? "" : user.getFirstName() + " " + user.getLastName();
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
