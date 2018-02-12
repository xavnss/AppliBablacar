package com.poe.trajetfacile.form;



public class BookARideForm {

    private Long userId;

    private Long rideId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
    
    public BookARideForm(){
    	
    }
    
    public BookARideForm(long userId, long rideId){
    	this.userId = userId;
    	this.rideId = rideId;
    }
    
}
