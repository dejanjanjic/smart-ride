package net.etfbl.ip.smartrideclient.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Rental {
    private Long id;
    private boolean active;
    private LocalDateTime dateTime;
    private int durationInSeconds;
    private String vehicleId;
    private Long clientId;
    private BigDecimal price;
    private double endLocationX;
    private double endLocationY;
    private double startLocationX;
    private double startLocationY;

    public Rental() {
    }

    public Rental(boolean active, LocalDateTime dateTime, int durationInSeconds, String vehicleId, Long clientId, BigDecimal price, double startLocationX, double startLocationY) {
        this.active = active;
        this.dateTime = dateTime;
        this.durationInSeconds = durationInSeconds;
        this.vehicleId = vehicleId;
        this.clientId = clientId;
        this.price = price;
        this.startLocationX = startLocationX;
        this.startLocationY = startLocationY;
    }

    public Rental(Long id, boolean active, LocalDateTime dateTime, int durationInSeconds, String vehicleId, Long clientId, BigDecimal price, double endLocationX, double endLocationY, double startLocationX, double startLocationY) {
        this.id = id;
        this.active = active;
        this.dateTime = dateTime;
        this.durationInSeconds = durationInSeconds;
        this.vehicleId = vehicleId;
        this.clientId = clientId;
        this.price = price;
        this.endLocationX = endLocationX;
        this.endLocationY = endLocationY;
        this.startLocationX = startLocationX;
        this.startLocationY = startLocationY;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getEndLocationX() {
        return endLocationX;
    }

    public void setEndLocationX(double endLocationX) {
        this.endLocationX = endLocationX;
    }

    public double getEndLocationY() {
        return endLocationY;
    }

    public void setEndLocationY(double endLocationY) {
        this.endLocationY = endLocationY;
    }

    public double getStartLocationX() {
        return startLocationX;
    }

    public void setStartLocationX(double startLocationX) {
        this.startLocationX = startLocationX;
    }

    public double getStartLocationY() {
        return startLocationY;
    }

    public void setStartLocationY(double startLocationY) {
        this.startLocationY = startLocationY;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", active=" + active +
                ", dateTime=" + dateTime +
                ", durationInSeconds=" + durationInSeconds +
                ", vehicleId='" + vehicleId + '\'' +
                ", clientId=" + clientId +
                ", price=" + price +
                ", endLocationX=" + endLocationX +
                ", endLocationY=" + endLocationY +
                ", startLocationX=" + startLocationX +
                ", startLocationY=" + startLocationY +
                '}';
    }
}

