package net.etfbl.ip.smartrideclient.beans;

import net.etfbl.ip.smartrideclient.dao.RentalPriceConfigDAO;

public class RentalPriceConfigBean {
    public double getScooterPricePerMinute() {
        return RentalPriceConfigDAO.getScooterPricePerMinute();
    }
}
