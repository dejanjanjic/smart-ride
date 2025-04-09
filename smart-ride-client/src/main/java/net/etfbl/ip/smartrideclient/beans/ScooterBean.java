package net.etfbl.ip.smartrideclient.beans;

import net.etfbl.ip.smartrideclient.dao.ScooterDAO;
import net.etfbl.ip.smartrideclient.dto.Scooter;

import java.util.List;

public class ScooterBean {

    public List<Scooter> getAllAvailable(){
        return ScooterDAO.selectAvailableScooters();
    }

    public Scooter getById(String scooterId){
        return ScooterDAO.getScooterById(scooterId);
    }
}
