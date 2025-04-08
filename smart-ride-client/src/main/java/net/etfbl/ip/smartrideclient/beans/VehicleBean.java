package net.etfbl.ip.smartrideclient.beans;

import net.etfbl.ip.smartrideclient.dao.VehicleDAO;
import net.etfbl.ip.smartrideclient.dto.Location;

public class VehicleBean {

    public Location getLocation(String vehicleId){
        return VehicleDAO.getLocation(vehicleId);
    }
}
