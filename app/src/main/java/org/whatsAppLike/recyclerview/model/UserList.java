package org.whatsAppLike.recyclerview.model;

/**
 * Created by Rana lucky on 8/13/2016.
 */
public class UserList {
    String id;
    String name;
    String username;
    String phone;
    String current_long;
    String current_lat;
    String password;
    String arabic_name;
    String supervisor_id;
    String supervisor_name;
    String supervisor_arabic_name;
    String supervisor_role;

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrent_long() {
        return current_long;
    }

    public void setCurrent_long(String current_long) {
        this.current_long = current_long;
    }

    public String getCurrent_lat() {
        return current_lat;
    }

    public void setCurrent_lat(String current_lat) {
        this.current_lat = current_lat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getArabic_name() {
        return arabic_name;
    }

    public void setArabic_name(String arabic_name) {
        this.arabic_name = arabic_name;
    }

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public String getSupervisor_name() {
        return supervisor_name;
    }

    public void setSupervisor_name(String supervisor_name) {
        this.supervisor_name = supervisor_name;
    }

    public String getSupervisor_arabic_name() {
        return supervisor_arabic_name;
    }

    public void setSupervisor_arabic_name(String supervisor_arabic_name) {
        this.supervisor_arabic_name = supervisor_arabic_name;
    }

    public String getSupervisor_role() {
        return supervisor_role;
    }

    public void setSupervisor_role(String supervisor_role) {
        this.supervisor_role = supervisor_role;
    }

    public String getArabic_station_name() {
        return arabic_station_name;
    }

    public void setArabic_station_name(String arabic_station_name) {
        this.arabic_station_name = arabic_station_name;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getArabic_location_name() {
        return arabic_location_name;
    }

    public void setArabic_location_name(String arabic_location_name) {
        this.arabic_location_name = arabic_location_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    String station_name;
    String arabic_station_name;
    String location_name;
    String arabic_location_name;

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    boolean checkStatus = false;

    String role;
}
