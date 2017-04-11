package com.rednineteen.android.adn;

/**
 * Created on 11/04/2017 by Juan Velasquez - email:  juan@rednineteen.com.
 */
public class ADNDevice {

    private String brand;
    private String marketName;
    private String device;
    private String model;

    public ADNDevice(String brand, String marketName, String device, String model) {
        this.brand      = brand;
        this.marketName = marketName;
        this.device     = device;
        this.model      = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
