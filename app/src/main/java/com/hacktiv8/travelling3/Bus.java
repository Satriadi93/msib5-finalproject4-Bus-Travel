package com.hacktiv8.travelling3;

public class Bus {
    private String pt_name, price, facility, departure, travel_time, city_to;
    private int number_seats, number_beds;

    public String getCity_to() {
        return city_to;
    }

    public void setCity_to(String city_to) {
        this.city_to = city_to;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public int getNumber_seats() {
        return number_seats;
    }

    public void setNumber_seats(int number_seats) {
        this.number_seats = number_seats;
    }

    public int getNumber_beds() {
        return number_beds;
    }

    public void setNumber_beds(int number_beds) {
        this.number_beds = number_beds;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }


    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}