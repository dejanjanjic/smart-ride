package net.etfbl.ip.smartrideclient.controller;

import net.etfbl.ip.smartrideclient.beans.*;
import net.etfbl.ip.smartrideclient.dto.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
        System.out.println("GET Action = " + action);

        session.setAttribute("notification", session.getAttribute("notification") != null ? session.getAttribute("notification") : "");


        if (action == null || action.isEmpty() || "login".equals(action)) {
            address = "/WEB-INF/pages/login.jsp";
        } else if ("register".equals(action)) {
            address = "/WEB-INF/pages/register.jsp";
        } else if ("logout".equals(action)) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "?action=login");
            return;
        } else {
            //provjera da li je korisnik ulogovan za sve ostale akcije
            UserBean userBean = (UserBean) session.getAttribute("userBean");
            if (userBean == null || !userBean.isLoggedIn()) {
                session.setAttribute("notification", "Please login to access this page.");
                address = "/WEB-INF/pages/login.jsp";
            } else {
                //ulogovan
                RentalPriceConfigBean configBean = new RentalPriceConfigBean();
                switch (action) {
                    case "home":
                        address = "/WEB-INF/pages/menu.jsp";
                        break;
                    case "scooter-rental":
                        loadScooterRentalPage(req, configBean);
                        address = "/WEB-INF/pages/scooter-rental.jsp";
                        break;
                    case "car-rental":
                        loadCarRentalPage(req, configBean);
                        address = "/WEB-INF/pages/car-rental.jsp";
                        break;
                    case "bike-rental":
                        loadBikeRentalPage(req, configBean);
                        address = "/WEB-INF/pages/bike-rental.jsp";
                        break;
                    case "change-avatar":
                        req.setAttribute("uploadStatus", session.getAttribute("uploadStatus"));
                        session.removeAttribute("uploadStatus");
                        address = "/WEB-INF/pages/change-avatar.jsp";
                        break;
                    case "active-ride":
                        String activeScooterId = (String) session.getAttribute("activeScooterId");
                        Long activeRentalId = (Long) session.getAttribute("rentalId");
                        if (activeScooterId != null) {
                            loadActiveScooterRidePage(req, configBean, activeScooterId, activeRentalId);
                            address = "/WEB-INF/pages/active-ride.jsp";
                        } else {
                            session.setAttribute("notification", "No active ride information found.");
                            resp.sendRedirect(req.getContextPath() + "?action=home");
                            return;
                        }
                        break;
                    default:
                        address = "/WEB-INF/pages/menu.jsp"; //TODO: staviti error.jsp
                        break;

                }
            }
        }

        if (!resp.isCommitted()) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(address);
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        UserBean userBean = (UserBean) session.getAttribute("userBean");
        String nextPage = "/WEB-INF/pages/login.jsp";
        boolean useRedirect = false;

        System.out.println("POST Action = " + action);

        if (userBean == null || !userBean.isLoggedIn()) {
            if (!"auth".equals(action) && !"register".equals(action)) {
                session.setAttribute("notification", "Session expired or invalid access. Please login.");
                resp.sendRedirect(req.getContextPath() + "?action=login");
                return;
            }
        }

        if ("auth".equals(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            UserBean newUserBean = new UserBean();
            if (newUserBean.login(username, password)) {
                session.setAttribute("userBean", newUserBean);
                nextPage = "?action=home";
                useRedirect = true;
            } else {
                session.setAttribute("notification", "Invalid credentials");
                nextPage = "/WEB-INF/pages/login.jsp";
                useRedirect = false;
            }
        } else if ("upload-avatar".equals(action)) {
            try {
                manageAvatarUpload(req, session, userBean);
                session.setAttribute("uploadStatus", "Avatar updated successfully!");
            } catch (Exception e) {
                System.err.println("Avatar upload failed: " + e.getMessage());
                session.setAttribute("uploadStatus", "Avatar upload failed. Please try again.");
            }
            nextPage = "/?action=change-avatar";
            useRedirect = true;

        } else if ("startScooterRide".equals(action)) {
            String scooterId = req.getParameter("selectedScooterId");
            String cardNumber = req.getParameter("cardNumber");
            cardNumber = cardNumber != null ? cardNumber.replace(" ", "") : null;

            if (scooterId == null || scooterId.trim().isEmpty() || cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
                System.err.println("Server validation failed: Missing scooter ID or invalid card number.");
                loadScooterRentalPage(req, new RentalPriceConfigBean());
                nextPage = "/WEB-INF/pages/scooter-rental.jsp";
                useRedirect = false;
            } else {
                try {
                    Long clientId = userBean.getId();
                    RentalBean rentalBean = new RentalBean();
                    Long rentalId = rentalBean.startRental(clientId, scooterId);
                    if (rentalId != null) {
                        session.setAttribute("activeScooterId", scooterId);
                        session.setAttribute("rentalId", rentalId);
                        nextPage = "/?action=active-ride";
                        useRedirect = true;
                    } else {
                        System.err.println("Controller: RentalBean reported failure to create rental for user " + clientId + ", scooter " + scooterId);
                        session.setAttribute("notification", "Could not start rental. The scooter might be unavailable or a server error occurred. Please try again.");
                        loadScooterRentalPage(req, new RentalPriceConfigBean());
                        nextPage = "/WEB-INF/pages/scooter-rental.jsp";
                        useRedirect = false;
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Server validation failed: Invalid scooter ID format.");
                    session.setAttribute("notification", "Invalid scooter selected.");
                    loadScooterRentalPage(req, new RentalPriceConfigBean());
                    nextPage = "/WEB-INF/pages/scooter-rental.jsp";
                    useRedirect = false;
                } catch (Exception e) {
                    // Uhvati druge greške (npr. greška pri komunikaciji sa bazom)
                    System.err.println("Error starting ride: " + e.getMessage());
                    session.setAttribute("notification", "An error occurred while starting the ride. Please try again.");
                    loadScooterRentalPage(req, new RentalPriceConfigBean());
                    nextPage = "/WEB-INF/pages/scooter-rental.jsp";
                    useRedirect = false;
                    e.printStackTrace();
                }
            }
        } else if ("endRide".equals(action)) {
            System.out.println("Processing 'endRide' action for user: " + userBean.getId());

            String rentalIdStr = req.getParameter("rentalId");

            if (rentalIdStr == null || rentalIdStr.trim().isEmpty()) {
                System.err.println("Error ending ride: rentalId parameter missing from request.");
                nextPage = "?action=home";
                useRedirect = true;
            } else {
                try {
                    Long rentalId = Long.parseLong(rentalIdStr);
                    Long userId = userBean.getId();

                    RentalBean rentalBean = new RentalBean();
                    Rental details = rentalBean.finishScooterRentalById(rentalId, userId);

                    if (details != null) {
                        System.out.println("Controller: Ride ended successfully. RentalID: " + details.getId()
                                + ", Duration: " + details.getDurationInSeconds() + "s, Cost: " + details.getPrice());

                        session.removeAttribute("activeRentalId");
                        session.removeAttribute("activeScooterId");


                        nextPage = "?action=home";
                        useRedirect = true;
                    } else {
                        System.err.println("Controller: Could not end ride for rentalId=" + rentalId + ". finishRentalById returned null.");
                        nextPage = "?action=home";
                        useRedirect = true;
                    }

                } catch (NumberFormatException e) {
                    // Greška pri parsiranju rentalId
                    System.err.println("Error ending ride: Invalid rentalId format '" + rentalIdStr + "'.");
                    session.setAttribute("postRedirectNotification", "Could not end ride: invalid data received.");
                    nextPage = "?action=home";
                    useRedirect = true;
                } catch (Exception e) {
                    // Neka druga greška iz Beana ili DAO sloja
                    System.err.println("Error ending ride: Exception occurred.");
                    e.printStackTrace();
                    session.setAttribute("postRedirectNotification", "An unexpected error occurred while ending the ride. Please contact support.");
                    nextPage = "?action=home";
                    useRedirect = true;
                }
            }
        } else {
            System.out.println("Unknown POST action or missing handler: " + action);
            session.setAttribute("notification", "Invalid operation requested.");
            nextPage = "?action=home";
            useRedirect = true;
        }


        if (useRedirect) {
            resp.sendRedirect(req.getContextPath() + nextPage);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher(nextPage);
            dispatcher.forward(req, resp);
        }

    }


    private void loadScooterRentalPage(HttpServletRequest req, RentalPriceConfigBean configBean) {
        ScooterBean scooterBean = new ScooterBean();
        List<Scooter> availableScooters = scooterBean.getAllAvailable();
        req.setAttribute("availableScooters", availableScooters);
        double scooterPrice = configBean.getScooterPrice();
        req.setAttribute("scooterPrice", scooterPrice);
    }

    private void loadActiveScooterRidePage(HttpServletRequest req, RentalPriceConfigBean rentalPriceConfigBean, String scooterId, Long activeRentalId) {
        ScooterBean scooterBean = new ScooterBean();
        Scooter scooter = scooterBean.getById(scooterId);
        System.out.println(scooter);
        req.setAttribute("scooter", scooter);
        double scooterPrice = rentalPriceConfigBean.getScooterPrice();
        System.out.println(scooterPrice);
        req.setAttribute("scooterPrice", scooterPrice);
        req.setAttribute("icon", "electric_scooter");
        req.setAttribute("activeRentalId", activeRentalId);
    }

    private void loadCarRentalPage(HttpServletRequest req, RentalPriceConfigBean configBean) {
        CarBean carBean = new CarBean();
        List<Car> availableCars = carBean.getAllAvailable();
        req.setAttribute("availableCars", availableCars);
        double carPrice = configBean.getCarPrice();
        req.setAttribute("carPrice", carPrice);
    }

    private void loadBikeRentalPage(HttpServletRequest req, RentalPriceConfigBean configBean) {
        BikeBean bikeBean = new BikeBean();
        List<Bike> availableBikes = bikeBean.getAllAvailable();
        req.setAttribute("availableBikes", availableBikes);
        double bikePrice = configBean.getBikePrice();
        req.setAttribute("bikePrice", bikePrice);
    }

    private void manageAvatarUpload(HttpServletRequest req, HttpSession session, UserBean userBean) throws IOException, ServletException {
        Part filePart = req.getPart("avatar"); // input name u formi

        if (filePart == null || filePart.getSize() == 0 || filePart.getSubmittedFileName() == null || filePart.getSubmittedFileName().isEmpty()) {
            throw new ServletException("No file uploaded or file is empty.");
        }

        String uploadPath = getServletContext().getRealPath("") + File.separator + "images/avatars";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdirs()) {
                throw new IOException("Could not create upload directory: " + uploadPath);
            }
        }

        String fileName = filePart.getSubmittedFileName();
        fileName = new File(fileName).getName();
        String fileExtension = "";
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            fileExtension = fileName.substring(lastDotIndex);
        }

        String contentType = filePart.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType) && !"image/gif".equals(contentType)) {
            throw new ServletException("Invalid file type. Only JPG, PNG, GIF are allowed.");
        }


        String newFileName = "avatar" + userBean.getId() + System.currentTimeMillis() + fileExtension;
        String filePath = uploadPath + File.separator + newFileName;

        try {
            filePart.write(filePath);
            System.out.println("Avatar saved to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing avatar file: " + e.getMessage());
            throw new IOException("Error saving uploaded file.", e);
        }


        String relativePath = "images/avatars/" + newFileName;
        boolean updated = userBean.updateAvatar(relativePath);
        if (updated) {
            session.setAttribute("userBean", userBean);
        } else {
            System.err.println("Failed to update avatar path in user data for path: " + relativePath);
            throw new ServletException("Could not update user avatar information.");
        }

    }

}