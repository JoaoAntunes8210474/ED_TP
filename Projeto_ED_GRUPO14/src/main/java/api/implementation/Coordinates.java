
package api.implementation;
import api.interfaces.ICoordinates;

public class Coordinates implements ICoordinates {
    
    private double longitude;
    private double latitude;

    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Coordinates{" + "longitude=" + longitude + ", latitude=" + latitude + '}';
    }
    
    
    
    
}
