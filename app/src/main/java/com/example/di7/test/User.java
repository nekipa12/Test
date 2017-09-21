package com.example.di7.test;

public class User {

    private String username;
    private String email;
    private double longitude;
    private double latitude;
    private boolean isRider;
    private long lastTime;


    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isRider() {
        return isRider;
    }

    public void setRider(boolean rider) {
        isRider = rider;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, double longtitude, double lattitude, boolean isRider, int lastTime) {
        this.username = username;
        this.email = email;
        this.longitude = longtitude;
        this.latitude = lattitude;
        this.isRider = isRider;
        this.lastTime = lastTime;
    }

    public User(String username, String email, boolean isRider) {
        this.username = username;
        this.email = email;
        this.isRider = isRider;
    }
}
