package org.beginners.saran.weather;

/**
 * Created by Nizam Uddin Shamrat on 1/8/2018.
 */

public class ForcastWeatherModel {

        private int forId;
        private String forDate;
        private String forDes;
        private String forMinTemp;
        private String forMaxTemp;

    public ForcastWeatherModel(String forDate, String forDes, String forMinTemp, String forMaxTemp) {
        this.forDate = forDate;
        this.forDes = forDes;
        this.forMinTemp = forMinTemp;
        this.forMaxTemp = forMaxTemp;
    }

    public ForcastWeatherModel(int forId, String forDate, String forDes, String forMinTemp, String forMaxTemp) {
        this.forId = forId;
        this.forDate = forDate;
        this.forDes = forDes;
        this.forMinTemp = forMinTemp;
        this.forMaxTemp = forMaxTemp;
    }

    public int getForId() {
        return forId;
    }

    public void setForId(int forId) {
        this.forId = forId;
    }

    public String getForDate() {
        return forDate;
    }

    public void setForDate(String forDate) {
        this.forDate = forDate;
    }

    public String getForDes() {
        return forDes;
    }

    public void setForDes(String forDes) {
        this.forDes = forDes;
    }

    public String getForMinTemp() {
        return forMinTemp;
    }

    public void setForMinTemp(String forMinTemp) {
        this.forMinTemp = forMinTemp;
    }

    public String getForMaxTemp() {
        return forMaxTemp;
    }

    public void setForMaxTemp(String forMaxTemp) {
        this.forMaxTemp = forMaxTemp;
    }
}
