package org.beginners.saran.weather;

/**
 * Created by Nizam Uddin Shamrat on 1/8/2018.
 */

public class CurrentWeatherModel {

    private int currentId;
    private String currentTemp;
    private String currentCity;
    private String currentCountry;
    private String currentDes;
    private String currentHumanity;
    private String currentPresser;
    private String currentMaxTexp;
    private String currentMinTemp;


    public CurrentWeatherModel(String currentTemp, String currentCity, String currentCountry, String currentDes, String currentHumanity, String currentPresser, String currentMaxTexp, String currentMinTemp) {
        this.currentTemp = currentTemp;
        this.currentCity = currentCity;
        this.currentCountry = currentCountry;
        this.currentDes = currentDes;
        this.currentHumanity = currentHumanity;
        this.currentPresser = currentPresser;
        this.currentMaxTexp = currentMaxTexp;
        this.currentMinTemp = currentMinTemp;
    }

    public CurrentWeatherModel(int currentId, String currentTemp, String currentCity, String currentCountry, String currentDes, String currentHumanity, String currentPresser, String currentMaxTexp, String currentMinTemp) {
        this.currentId = currentId;
        this.currentTemp = currentTemp;
        this.currentCity = currentCity;
        this.currentCountry = currentCountry;
        this.currentDes = currentDes;
        this.currentHumanity = currentHumanity;
        this.currentPresser = currentPresser;
        this.currentMaxTexp = currentMaxTexp;
        this.currentMinTemp = currentMinTemp;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(String currentCountry) {
        this.currentCountry = currentCountry;
    }

    public String getCurrentDes() {
        return currentDes;
    }

    public void setCurrentDes(String currentDes) {
        this.currentDes = currentDes;
    }

    public String getCurrentHumanity() {
        return currentHumanity;
    }

    public void setCurrentHumanity(String currentHumanity) {
        this.currentHumanity = currentHumanity;
    }

    public String getCurrentPresser() {
        return currentPresser;
    }

    public void setCurrentPresser(String currentPresser) {
        this.currentPresser = currentPresser;
    }

    public String getCurrentMaxTexp() {
        return currentMaxTexp;
    }

    public void setCurrentMaxTexp(String currentMaxTexp) {
        this.currentMaxTexp = currentMaxTexp;
    }

    public String getCurrentMinTemp() {
        return currentMinTemp;
    }

    public void setCurrentMinTemp(String currentMinTemp) {
        this.currentMinTemp = currentMinTemp;
    }
}
