package net.etfbl.ip.smartrideclient.beans;

import net.etfbl.ip.smartrideclient.dao.RentalPriceConfigDAO;

public class RentalPriceConfigBean {
    public double getScooterPrice() {
        return RentalPriceConfigDAO.getScooterPrice();
    }

    public double getCarPrice() {
        return RentalPriceConfigDAO.getCarPrice();
    }

    public double getBikePrice() {
        return RentalPriceConfigDAO.getBikePrice();
    }
}
