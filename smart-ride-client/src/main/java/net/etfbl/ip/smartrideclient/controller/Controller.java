package net.etfbl.ip.smartrideclient.controller;

import net.etfbl.ip.smartrideclient.beans.UserBean;
import net.etfbl.ip.smartrideclient.dto.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/Controller")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 5 * 1024 * 1024,    // 5 MB
        maxRequestSize = 10 * 1024 * 1024 // 10 MB
)
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "/WEB-INF/pages/login.jsp";
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        System.out.println("Action = " + action);
        session.setAttribute("notification", "");

        if(action == null || action.isEmpty() || action.equals("login")){
            address = "/WEB-INF/pages/login.jsp";
        } else if (action.equals("auth")) {
            System.out.println(req.getParameter("username") + " " + req.getParameter("password"));
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            UserBean userBean = new UserBean();
            if (userBean.login(username, password)) {
                session.setAttribute("userBean", userBean);
                address = "/WEB-INF/pages/menu.jsp";
            } else {
                session.setAttribute("notification", "Invalid credentials");
            }
        } else if (action.equals("register")) {
            address = "/WEB-INF/pages/register.jsp";

        } else if (action.equals("logout")) {
            session.invalidate();
            address = "/WEB-INF/pages/login.jsp";
        } else {

            UserBean userBean = (UserBean) session.getAttribute("userBean");
            if(userBean == null || !userBean.isLoggedIn()){ //nevalidan action ili neautorizovan pristup
                address = "/WEB-INF/pages/login.jsp";
            } else { //korisnik je ulogovan i pristupa drugim stranicama
                switch (action) {
                    case "home":
                        address = "/WEB-INF/pages/menu.jsp";
                        break;
                    case "scooter-rental":
                        address = "/WEB-INF/pages/scooter-rental.jsp";
                        break;
                    case "change-avatar":
                        address = "/WEB-INF/pages/change-avatar.jsp";
                        break;
                    case "upload-avatar":
                        System.out.println("Doslo u controller");
                        Part filePart = req.getPart("avatar"); // input name u formi

                        String uploadPath = getServletContext().getRealPath("") + File.separator + "images/avatars";

                        // Kreiraj folder ako ne postoji
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdir();
                        }

                        String fileName = filePart.getSubmittedFileName();
                        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

                        String newFileName = "avatar" + userBean.getId() + fileExtension;
                        String filePath = uploadPath + File.separator + newFileName;

                        // Snimi fajl
                        filePart.write(filePath);

                        // Sačuvaj relativnu putanju u User objektu (ovo zavisi od tvoje implementacije)
                        String relativePath = "images/avatars/" + newFileName;
                        // Pretpostavimo da postoji metoda za ažuriranje slike u bazi
                        userBean.updateAvatar(relativePath);
                        session.setAttribute("userBean", userBean);

                        address = "/WEB-INF/pages/change-avatar.jsp";
                        break;
                }

            }
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(address);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
