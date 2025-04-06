package net.etfbl.ip.smartrideclient.beans;

import net.etfbl.ip.smartrideclient.dao.BikeDAO;
import net.etfbl.ip.smartrideclient.dto.Bike;

import java.util.List;

public class BikeBean {
    public List<Bike> getAllAvailable(){
        return BikeDAO.selectAvailableBikes();
    }
}
