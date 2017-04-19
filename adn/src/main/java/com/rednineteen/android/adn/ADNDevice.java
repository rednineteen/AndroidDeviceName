package com.rednineteen.android.adn;

/**
 * Created on 11/04/2017 by Juan Velasquez - email:  juan@rednineteen.com.
 *
 * Copyright 2017 Juan Velasquez - Rednineteen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
