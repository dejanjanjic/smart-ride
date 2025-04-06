package net.etfbl.ip.smartrideclient.beans;

import net.etfbl.ip.smartrideclient.dao.CarDAO;
import net.etfbl.ip.smartrideclient.dto.Car;

import java.util.List;

public class CarBean {
    public List<Car> getAllAvailable(){
        return CarDAO.selectAvailableCars();
    }
}
