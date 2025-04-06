package net.etfbl.ip.smartrideclient.dto;

public class Bike {
    private String id;
    private String manufacturerName;
    private String model;
    private Integer maxRange;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(Integer maxRange) {
        this.maxRange = maxRange;
    }
}
