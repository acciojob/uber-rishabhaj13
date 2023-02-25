package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "TripBooking")
public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tripBookingId;
    String fromLocation;
    String toLocation;
    int distanceInKm;
    int bill;
    @Enumerated
    TripStatus tripStatus;

    @ManyToOne
    @JoinColumn
    Customer customer;

    @ManyToOne
    @JoinColumn
    Driver driver;



    public TripBooking(int tripBookingId, String fromLocation, String toLocation, int distanceInKm, int bill, TripStatus tripStatus, Customer customer, Driver driver) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        this.bill = bill;
        this.tripStatus = tripStatus;
        this.customer = customer;
        this.driver = driver;
    }

    public TripBooking(String fromLocation, String toLocation, int distanceInKm) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
    }

    public TripBooking() {
    }

    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookinId) {
        this.tripBookingId = tripBookinId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(int distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public TripStatus getStatus() {
        return tripStatus;
    }

    public void setStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}