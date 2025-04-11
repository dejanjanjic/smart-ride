package net.etfbl.ip.smartrideclient.controller;

import net.etfbl.ip.smartrideclient.beans.*;
import net.etfbl.ip.smartrideclient.dao.UserDAO;
import net.etfbl.ip.smartrideclient.dto.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.math.RoundingMode;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

@WebServlet("/Controller")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 5 * 1024 * 1024,    // 5 MB
        maxRequestSize = 10 * 1024 * 1024 // 10 MB
)
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter RECEIPT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");


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
        } else if ("generateReceipt".equals(action)) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> receiptData = (Map<String, Object>) session.getAttribute("lastRideReceiptData");
                if (receiptData != null) {
                    generatePdfReceipt(resp, receiptData);
                    // session.removeAttribute("lastRideReceiptData");
                } else {
                    resp.setContentType("text/plain");
                    resp.getWriter().println("Error: Receipt data not found.");
                }
            } catch (Exception e) {
                System.err.println("Error generating PDF receipt: " + e.getMessage());
                e.printStackTrace();
                resp.setContentType("text/plain");
                resp.getWriter().println("Error: Could not generate PDF receipt.");
            }
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
                        Boolean triggerDownload = (Boolean) session.getAttribute("triggerPdfDownload");
                        if (triggerDownload != null && triggerDownload) {
                            System.out.println("Setting flag for auto PDF download on home page.");
                            req.setAttribute("autoDownloadReceipt", true);
                            session.removeAttribute("triggerPdfDownload");
                        }
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
                    case "active-scooter-ride":
                        String activeScooterId = (String) session.getAttribute("activeScooterId");
                        Long activeRentalId = (Long) session.getAttribute("rentalId");
                        if (activeScooterId != null) {
                            loadActiveScooterRidePage(req, configBean, activeScooterId, activeRentalId);
                            address = "/WEB-INF/pages/active-scooter-ride.jsp";
                        } else {
                            resp.sendRedirect(req.getContextPath() + "?action=home");
                            return;
                        }
                        break;
                    case "active-bike-ride":
                        String activeBikeId = (String) session.getAttribute("activeBikeId");
                        Long activeBikeRentalId = (Long) session.getAttribute("rentalId");
                        if (activeBikeId != null) {
                            loadActiveBikeRidePage(req, configBean, activeBikeId, activeBikeRentalId);
                            address = "/WEB-INF/pages/active-bike-ride.jsp";
                        } else {
                            resp.sendRedirect(req.getContextPath() + "?action=home");
                            return;
                        }
                        break;
                    case "active-car-ride":
                        String activeCarId = (String) session.getAttribute("activeCarId");
                        Long activeCarRentalId = (Long) session.getAttribute("activeRentalId");

                        if (activeCarRentalId != null && activeCarId != null) {
                            loadActiveCarRidePage(req, configBean, activeCarId, activeCarRentalId);
                            address = "/WEB-INF/pages/active-car-ride.jsp";
                        } else {
                            resp.sendRedirect(req.getContextPath() + "?action=home");
                            return;
                        }
                        break;
                    case "myProfile":
                        try {
                            RentalBean rentalBean = new RentalBean();
                            Long userId = userBean.getId();
                            List<Rental> history = rentalBean.getRentalHistory(userId);
                            req.setAttribute("rentalHistoryList", history);
                        } catch (Exception e) {
                            e.printStackTrace();
                            req.setAttribute("rentalHistoryList", new java.util.ArrayList<Rental>());
                        }
                        String passChangeStatus = (String) session.getAttribute("passwordChangeStatus");
                        if (passChangeStatus != null) {
                            req.setAttribute("passwordChangeStatus", passChangeStatus);
                            session.removeAttribute("passwordChangeStatus");
                        }
                        address = "/WEB-INF/pages/my-profile.jsp";
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
                        nextPage = "/?action=active-scooter-ride";
                        useRedirect = true;
                    } else {
                        System.err.println("Controller: RentalBean reported failure to create rental for user " + clientId + ", scooter " + scooterId);
                        loadScooterRentalPage(req, new RentalPriceConfigBean());
                        nextPage = "/WEB-INF/pages/scooter-rental.jsp";
                        useRedirect = false;
                    }
                } catch (Exception e) {
                    System.err.println("Error starting ride: " + e.getMessage());
                    loadScooterRentalPage(req, new RentalPriceConfigBean());
                    nextPage = "/WEB-INF/pages/scooter-rental.jsp";
                    useRedirect = false;
                    e.printStackTrace();
                }
            }
        } else if ("startBikeRide".equals(action)) {
            String bikeId = req.getParameter("selectedBikeId");
            String cardNumber = req.getParameter("cardNumber");
            cardNumber = cardNumber != null ? cardNumber.replace(" ", "") : null;

            if (bikeId == null || bikeId.trim().isEmpty() || cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19) {
                System.err.println("Server validation failed: Missing bike ID or invalid card number.");
                loadBikeRentalPage(req, new RentalPriceConfigBean());
                nextPage = "/WEB-INF/pages/bike-rental.jsp";
                useRedirect = false;
            } else {
                try {
                    Long clientId = userBean.getId();
                    RentalBean rentalBean = new RentalBean();
                    Long rentalId = rentalBean.startRental(clientId, bikeId);
                    if (rentalId != null) {
                        session.setAttribute("activeBikeId", bikeId);
                        session.setAttribute("rentalId", rentalId);
                        nextPage = "/?action=active-bike-ride";
                        useRedirect = true;
                    } else {
                        loadBikeRentalPage(req, new RentalPriceConfigBean());
                        nextPage = "/WEB-INF/pages/bike-rental.jsp";
                        useRedirect = false;
                    }
                } catch (Exception e) {
                    loadBikeRentalPage(req, new RentalPriceConfigBean());
                    nextPage = "/WEB-INF/pages/bike-rental.jsp";
                    useRedirect = false;
                    e.printStackTrace();
                }
            }
        } else if ("startCarRide".equals(action)) {
            String carId = req.getParameter("selectedCarId");
            String cardNumber = req.getParameter("cardNumber");
            String idNumber = req.getParameter("idNumber");
            String driverLicense = req.getParameter("driverLicense");

            cardNumber = cardNumber != null ? cardNumber.replace(" ", "") : null;

            boolean validationFailed = false;
            if (carId == null || carId.trim().isEmpty()) {
                System.err.println("Server validation failed: Missing Car ID.");
                validationFailed = true;
            }
            if (cardNumber == null || cardNumber.length() < 13 || cardNumber.length() > 19 || !cardNumber.matches("\\d+")) {
                System.err.println("Server validation failed: Invalid card number.");
                validationFailed = true;
            }
            if (idNumber == null || idNumber.trim().isEmpty()) {
                System.err.println("Server validation failed: Missing ID Document Number.");
                validationFailed = true;
            }
            if (driverLicense == null || driverLicense.trim().isEmpty()) {
                System.err.println("Server validation failed: Missing Driver's License Number.");
                validationFailed = true;
            }

            if (validationFailed) {
                loadCarRentalPage(req, new RentalPriceConfigBean());
                nextPage = "/WEB-INF/pages/car-rental.jsp";
                useRedirect = false;
            } else {
                try {
                    Long clientId = userBean.getId();
                    RentalBean rentalBean = new RentalBean();
                    Long rentalId = rentalBean.startRental(clientId, carId);

                    if (rentalId != null) {
                        System.out.println("Controller: RentalBean reported successful car rental creation with ID: " + rentalId);

                        session.setAttribute("activeCarId", carId);
                        session.setAttribute("activeRentalId", rentalId);
                        session.setAttribute("currentRentalIdNumber", idNumber);
                        session.setAttribute("currentRentalDriverLicense", driverLicense);

                        nextPage = "/?action=active-car-ride";
                        useRedirect = true;
                    } else {
                        System.err.println("Controller: RentalBean reported failure to create car rental for user " + clientId + ", car " + carId);
                        loadCarRentalPage(req, new RentalPriceConfigBean());
                        nextPage = "/WEB-INF/pages/car-rental.jsp";
                        useRedirect = false;
                    }
                } catch (Exception e) {
                    System.err.println("Error starting car ride: " + e.getMessage());
                    e.printStackTrace();
                    loadCarRentalPage(req, new RentalPriceConfigBean());
                    nextPage = "/WEB-INF/pages/car-rental.jsp";
                    useRedirect = false;
                }
            }
        } else if ("register".equals(action)) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");

            String errorMessage = null;

            if (username == null || username.trim().isEmpty()) {
                errorMessage = "All fields are required.";
            } else if (!password.equals(confirmPassword)) {
                errorMessage = "Passwords do not match.";
            } else if (UserDAO.existsByUsername(username)) {
                errorMessage = "Username already taken.";
            } else if (UserDAO.existsByEmail(email)) {
                errorMessage = "Email address already registered.";
            }

            if (errorMessage != null) {
                session.setAttribute("errorMessage", errorMessage);
                nextPage = "?action=register";
                useRedirect = true;
            } else {
                try {
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setFirstName(firstName);
                    newUser.setLastName(lastName);
                    newUser.setEmail(email);
                    newUser.setPhoneNumber(phone);

                    UserBean registrationBean = new UserBean();
                    boolean registrationSuccess = registrationBean.registerUser(newUser, password); // Prosledi plain lozinku

                    if (registrationSuccess) {
                        // Ukloni poruku o grešci ako je postojala
                        session.removeAttribute("errorMessage");
                        // Postavi poruku o uspehu za login stranicu
                        session.setAttribute("registrationSuccess", "Account created successfully! Please login.");
                        nextPage = "?action=login";
                        useRedirect = true;
                    } else {
                        session.setAttribute("errorMessage", "Registration failed. Please try again.");
                        nextPage = "?action=register";
                        useRedirect = true;
                    }
                } catch (Exception e) {
                    System.err.println("Controller: Unexpected error during registration.");
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "An unexpected error occurred.");
                    nextPage = "?action=register";
                    useRedirect = true;
                }
            }
        } else if ("endScooterRide".equals(action)) {

            String rentalIdStr = req.getParameter("rentalId");

            if (rentalIdStr == null || rentalIdStr.trim().isEmpty()) {
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
                } catch (Exception e) {
                    System.err.println("Error ending ride: Exception occurred.");
                    e.printStackTrace();
                    nextPage = "?action=home";
                    useRedirect = true;
                }
            }
        } else if ("endBikeRide".equals(action)) {

            String rentalIdStr = req.getParameter("rentalId");

            if (rentalIdStr == null || rentalIdStr.trim().isEmpty()) {
                nextPage = "?action=home";
                useRedirect = true;
            } else {
                try {
                    Long rentalId = Long.parseLong(rentalIdStr);
                    Long userId = userBean.getId();

                    RentalBean rentalBean = new RentalBean();
                    Rental details = rentalBean.finishBikeRentalById(rentalId, userId);

                    if (details != null) {
                        System.out.println("Controller: Ride ended successfully. RentalID: " + details.getId()
                                + ", Duration: " + details.getDurationInSeconds() + "s, Cost: " + details.getPrice());

                        session.removeAttribute("activeRentalId");
                        session.removeAttribute("activeBikeId");


                        nextPage = "?action=home";
                        useRedirect = true;
                    } else {
                        System.err.println("Controller: Could not end ride for rentalId=" + rentalId + ". finishRentalById returned null.");
                        nextPage = "?action=home";
                        useRedirect = true;
                    }
                } catch (Exception e) {
                    System.err.println("Error ending ride: Exception occurred.");
                    e.printStackTrace();
                    nextPage = "?action=home";
                    useRedirect = true;
                }
            }
        } else if ("endCarRide".equals(action)) {
            System.out.println("Processing 'endCarRide' action...");

            String rentalIdStr = req.getParameter("rentalId");

            if (rentalIdStr == null || rentalIdStr.trim().isEmpty()) {
                System.err.println("Error ending car ride: rentalId parameter missing.");
                nextPage = "?action=home";
                useRedirect = true;
            } else {
                try {
                    Long rentalId = Long.parseLong(rentalIdStr);
                    Long userId = userBean.getId();

                    RentalBean rentalBean = new RentalBean();
                    Rental details = rentalBean.finishCarRentalById(rentalId, userId);

                    if (details != null) {
                        session.removeAttribute("activeRentalId");
                        session.removeAttribute("activeCarId");

                        String idNumber = (String) session.getAttribute("currentRentalIdNumber");
                        String driverLicense = (String) session.getAttribute("currentRentalDriverLicense");

                        Map<String, Object> receiptData = new java.util.HashMap<>();
                        receiptData.put("rentalId", details.getId());
                        receiptData.put("userId", userId);
                        receiptData.put("userName", userBean.getName());
                        receiptData.put("idNumber", idNumber);
                        receiptData.put("driverLicense", driverLicense);
                        receiptData.put("durationSeconds", details.getDurationInSeconds());
                        receiptData.put("finalCost", details.getPrice());
                        receiptData.put("vehicleId", details.getVehicleId());
                        receiptData.put("endTime", java.time.LocalDateTime.now());

                        session.setAttribute("lastRideReceiptData", receiptData);

                        session.removeAttribute("currentRentalIdNumber");
                        session.removeAttribute("currentRentalDriverLicense");

                        session.setAttribute("triggerPdfDownload", true);

                        nextPage = "?action=home";
                        useRedirect = true;

                    } else {
                        System.err.println("Controller: Could not end car ride for rentalId=" + rentalId + ". finishRentalById returned null.");
                        Long currentSessionRentalId = (Long) session.getAttribute("activeRentalId");
                        if (currentSessionRentalId == null || !currentSessionRentalId.equals(rentalId)) {
                            System.err.println("Controller: Session data suggests ride already ended or ID mismatch.");
                            session.removeAttribute("activeRentalId");
                            session.removeAttribute("activeCarId");
                            session.removeAttribute("currentRentalIdNumber");
                            session.removeAttribute("currentRentalDriverLicense");
                        }

                        nextPage = "?action=home";
                        useRedirect = true;
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Error ending car ride: Invalid rentalId format '" + rentalIdStr + "'.");
                    nextPage = "?action=home";
                    useRedirect = true;
                } catch (Exception e) {
                    System.err.println("Error ending car ride: Exception occurred.");
                    e.printStackTrace();
                    nextPage = "?action=home";
                    useRedirect = true;
                }
            }
        } else if ("changePassword".equals(action)) {
            String newPassword = req.getParameter("newPassword");
            String confirmPassword = req.getParameter("confirmPassword");
            String statusMessage = null;
            boolean success = false;

            if (newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
                statusMessage = "Error: Both password fields are required.";
            } else if (!newPassword.equals(confirmPassword)) {
                statusMessage = "Error: Passwords do not match.";
            } else {
                Long userId = userBean.getId();
                success = userBean.changePassword(userId, newPassword);
                if (success) {
                    statusMessage = "Success: Password changed successfully!";
                    System.out.println("Password changed for user ID: " + userId);
                } else {
                    statusMessage = "Error: Could not change password due to a server error.";
                    System.err.println("Failed to change password for user ID: " + userId);
                }
            }

            session.setAttribute("passwordChangeStatus", statusMessage);
            nextPage = "?action=myProfile";
            useRedirect = true;
        } else {
            System.out.println("Unknown POST action or missing handler: " + action);
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
        req.setAttribute("scooter", scooter);
        double scooterPrice = rentalPriceConfigBean.getScooterPrice();
        req.setAttribute("scooterPrice", scooterPrice);
        req.setAttribute("activeRentalId", activeRentalId);
    }

    private void loadCarRentalPage(HttpServletRequest req, RentalPriceConfigBean configBean) {
        CarBean carBean = new CarBean();
        List<Car> availableCars = carBean.getAllAvailable();
        req.setAttribute("availableCars", availableCars);
        double carPrice = configBean.getCarPrice();
        req.setAttribute("carPrice", carPrice);
    }
    private void loadActiveCarRidePage(HttpServletRequest req, RentalPriceConfigBean configBean, String carId, Long activeRentalId) {
        CarBean carBean = new CarBean();
        Car car = carBean.getById(carId);
        if (car != null) {
            req.setAttribute("car", car);
        } else {
            req.setAttribute("car", new Car());
        }
        double carPrice = configBean.getCarPrice();
        req.setAttribute("carPrice", carPrice);
        req.setAttribute("activeRentalId", activeRentalId);
    }

    private void loadBikeRentalPage(HttpServletRequest req, RentalPriceConfigBean configBean) {
        BikeBean bikeBean = new BikeBean();
        List<Bike> availableBikes = bikeBean.getAllAvailable();
        req.setAttribute("availableBikes", availableBikes);
        double bikePrice = configBean.getBikePrice();
        req.setAttribute("bikePrice", bikePrice);
    }

    private void loadActiveBikeRidePage(HttpServletRequest req, RentalPriceConfigBean rentalPriceConfigBean, String bikeId, Long activeRentalId) {
        BikeBean bikeBean = new BikeBean();
        Bike bike = bikeBean.getById(bikeId);
        req.setAttribute("bike", bike);
        double bikePrice = rentalPriceConfigBean.getBikePrice();
        req.setAttribute("bikePrice", bikePrice);
        req.setAttribute("activeRentalId", activeRentalId);
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
    private void generatePdfReceipt(HttpServletResponse resp, Map<String, Object> data) throws IOException {
        resp.setContentType("application/pdf");
        String fileName = "receipt_" + data.getOrDefault("rentalId", "unknown") + ".pdf";
        resp.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            final PDType1Font FONT_REGULAR = PDType1Font.HELVETICA;
            final PDType1Font FONT_BOLD = PDType1Font.HELVETICA_BOLD;

            float yPosition = 750;
            float leftMargin = 50;
            float lineSpacing = 15;

            contentStream.beginText();
            contentStream.setFont(FONT_BOLD, 16);
            contentStream.newLineAtOffset(leftMargin, yPosition);
            contentStream.showText("Smart Ride - Receipt");
            contentStream.endText();
            yPosition -= lineSpacing * 2;

            contentStream.beginText();
            contentStream.setFont(FONT_REGULAR, 10);
            contentStream.newLineAtOffset(leftMargin, yPosition);
            contentStream.showText("SmartRide");
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Patre 1, 78000 Banja Luka");
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("PIB: 123456789");
            contentStream.endText();
            yPosition -= lineSpacing * 2;

            contentStream.beginText();
            contentStream.setFont(FONT_REGULAR, 12);
            contentStream.newLineAtOffset(leftMargin, yPosition);
            contentStream.showText("Rental ID: " + data.getOrDefault("rentalId", "N/A"));
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Client: " + data.getOrDefault("userName", "N/A"));
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("ID number: " + data.getOrDefault("idNumber", "N/A"));
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Driver license number: " + data.getOrDefault("driverLicense", "N/A"));
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            contentStream.showText("Vehicle ID: " + data.getOrDefault("vehicleId", "N/A"));
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            LocalDateTime endTime = (LocalDateTime) data.get("endTime");
            String formattedEndTime = (endTime != null) ? endTime.format(RECEIPT_DATE_TIME_FORMATTER) : "N/A";
            contentStream.showText("End time: " + formattedEndTime);
            yPosition -= lineSpacing;
            contentStream.newLineAtOffset(0, -lineSpacing);
            Integer durationObj = (Integer) data.get("durationSeconds");
            String durationText = (durationObj != null) ? durationObj.toString() : "0";
            contentStream.showText("Duration: " + durationText + " seconds");
            contentStream.endText();
            yPosition -= lineSpacing * 2;

            // Ukupna cena
            contentStream.beginText();
            contentStream.setFont(FONT_BOLD, 14);
            contentStream.newLineAtOffset(leftMargin, yPosition);
            Object costObj = data.get("finalCost");
            String formattedCost = "0.00";
            if (costObj instanceof BigDecimal) {
                formattedCost = ((BigDecimal) costObj).setScale(2, RoundingMode.HALF_UP).toString();
            } else if (costObj != null) {
                try {
                    formattedCost = new BigDecimal(costObj.toString()).setScale(2, RoundingMode.HALF_UP).toString();
                } catch (NumberFormatException ignored) { }
            }
            contentStream.showText("Total cost: " + formattedCost + " BAM");
            contentStream.endText();

            contentStream.close();
            document.save(resp.getOutputStream());

        } catch (IOException e) {
            System.err.println("PDFBox IOException in generatePdfReceipt: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error during PDF generation logic: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to generate PDF content.", e);
        }
    }
}