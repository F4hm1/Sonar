package it.cnvcrew.sonar;

import java.io.Serializable;

import ch.hsr.geohash.GeoHash;

/**
 * Created by Alessandro on 01/06/2016.
 */

public class Position implements Serializable {

    private double latitude,longitude;
    private int id_user;
    private String geohash;

    public Position(int userId, double latitude, double longitude, String geohash) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.geohash = geohash;
        this.id_user = userId;
    }

    public Position(int userId, double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.id_user = userId;
    }

    public String calculateHash(){
        GeoHash hash = GeoHash.withCharacterPrecision(latitude, longitude, 7);
        this.geohash = hash.toBase32();
        return hash.toString();
    }
}
