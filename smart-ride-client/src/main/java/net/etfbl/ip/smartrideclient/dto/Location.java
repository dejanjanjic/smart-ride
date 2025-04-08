package net.etfbl.ip.smartrideclient.dto;

public class Location {
    Double positionX;
    Double positionY;

    public Location(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Double getPositionX() {
        return positionX;
    }

    public void setPositionX(Double positionX) {
        this.positionX = positionX;
    }

    public Double getPositionY() {
        return positionY;
    }

    public void setPositionY(Double positionY) {
        this.positionY = positionY;
    }
}
