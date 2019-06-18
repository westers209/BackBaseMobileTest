package com.example.backbasemobiletest;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 * DTO representing aboutinfo object
 */

public class AboutInfo {

    private String country;
    private String name;
    private int _id;
    private Coordinates coord;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }
}
