package api.interfaces;

/**
 * Contract of a Coordinates Class.
 */
public interface ICoordinates {

    /**
     * Get the longitude coordinate of a location
     *
     * @return The longitude coordinate of a location
     */
    public double getLongitude();

    /**
     * Get the latitude coordinate of a location
     *
     * @return The latitude coordinate of a location
     */
    public double getLatitude();

    /**
     * Change the longitude coordinate of a location
     *
     * @param longitude The longitude coordinate of a location
     */
    public void setLongitude(double longitude);

    /**
     * Change the latitude coordinate of a location
     *
     * @param latitude The latitude coordinate of a location
     */
    public void setLatitude(double latitude);

    /**
     * This method aims to return a String representing the coordinates related to a location.
     *
     * @return String representing the coordinates related to a location
     */
    @Override
    public String toString();


}
