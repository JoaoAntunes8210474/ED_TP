package api.implementation;

import api.interfaces.ICoordinates;
import org.json.simple.JSONObject;

/**
 * Class that implements the coordinate contract
 * Class that represents the coordinates of a location
 */
public class Coordinates implements ICoordinates {
    
    //Represents the longitude coordinates of a location, this value can vary between 0 and 180.
    private double longitude;
    
    //Represents the latitude coordinates of a location, this value can vary between 0 and 90.
    private double latitude;

    /**
     * Constructor: serves to instantiate objects of type Coordinates
     * @param longitude the longitude coordinates of a location, this value can vary between 0 and 180.
     * @param latitude the latitude coordinates of a location, this value can vary between 0 and 90.
     */
    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * This method aims to return a JSONObject representing the coordinates related to a location.
     * @return JSONObject representing the coordinates related to a location
     */
    protected JSONObject getCoordinatesJSON() {
        JSONObject coordinatesJSON = new JSONObject();

        coordinatesJSON.put("longitude", this.longitude);
        coordinatesJSON.put("latitude", this.latitude);

        return coordinatesJSON;
    }

    /**
     * Get the longitude coordinate of a location
     * @return The longitude coordinate of a location
     */
    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * Get the latitude coordinate of a location
     * @return The latitude coordinate of a location
     */
    @Override
    public double getLatitude() {
        return latitude;
    }

    /**
     * Change the longitude coordinate of a location
     * @param longitude The longitude coordinate of a location
     */
    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Change the latitude coordinate of a location
     * @param latitude The latitude coordinate of a location
     */
    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * This method aims to return a String representing the coordinates related to a location.
     * @return String representing the coordinates related to a location
     */
    @Override
    public String toString() {
        return "Coordinates{" + "longitude=" + longitude + ", latitude=" + latitude + '}';
    }
    
    
    
    
}