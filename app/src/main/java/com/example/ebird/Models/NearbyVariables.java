package com.example.ebird.Models;

public class NearbyVariables {

    private String comName,sciName,locName;
    private Boolean isFavorite;

    public NearbyVariables() {

    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public void setSciName(String sciName) {
        this.sciName = sciName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public NearbyVariables(String comName, String sciName, String locName, Boolean isFavorite) {
        this.comName = comName;
        this.sciName = sciName;
        this.locName = locName;
        this.isFavorite = isFavorite;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public String getComName() {
        return comName;
    }

    public String getSciName() {
        return sciName;
    }

    public String getLocName() {
        return locName;
    }
}
