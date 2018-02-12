package com.poe.trajetfacile.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OfferARideForm {

    
    @NotNull
    @NotEmpty
    private String fromCity;
    
    @NotNull
    @NotEmpty
    private String toCity;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull
    private short startHours;
    @NotNull
    private short startMinutes;
    
    @Min(0)
    private Double cost;
    @Min(1)
    private short seats;



    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public short getStartHours() {
        return startHours;
    }

    public void setStartHours(short startHours) {
        this.startHours = startHours;
    }

    public short getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(short startMinutes) {
        this.startMinutes = startMinutes;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public short getSeats() {
        return seats;
    }

    public void setSeats(short seats) {
        this.seats = seats;
    }

}
