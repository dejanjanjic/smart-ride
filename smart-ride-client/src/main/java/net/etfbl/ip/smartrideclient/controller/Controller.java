package net.etfbl.ip.smartrideclient.controller;

import net.etfbl.ip.smartrideclient.beans.UserBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Controller")
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
                if(action.equals("scooter-rental")){
                    address="/WEB-INF/pages/scooter-rental.jsp";
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
