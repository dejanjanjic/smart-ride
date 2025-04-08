package net.etfbl.ip.smartrideclient.beans;


import net.etfbl.ip.smartrideclient.dao.UserDAO;
import net.etfbl.ip.smartrideclient.dto.User;

import javax.servlet.http.Part;
import java.io.File;

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

    public String getAvatarPath(){
        return user.getImage();
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }


    public Long getId() {
        return user.getId();
    }

    public boolean updateAvatar(String relativePath) {
        if(UserDAO.updateUserAvatar(user.getId(), relativePath)){
            user.setImage(relativePath);
            return true;
        }
        return false;
    }
}
