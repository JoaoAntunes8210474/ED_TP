package api.interfaces;

/** Contract of a Coordinates Class. */
public interface ICoordinates {

    /**
     * Get the longitude coordinate of a location.
     *
     * @return The longitude coordinate of a location
     */
    double getLongitude();

    /**
     * Get the latitude coordinate of a location.
     *
     * @return The latitude coordinate of a location
     */
    double getLatitude();

    /**
     * Change the longitude coordinate of a location.
     *
     * @param longitude The longitude coordinate of a location
     */
    void setLongitude(double longitude);

    /**
     * Change the latitude coordinate of a location.
     *
     * @param latitude The latitude coordinate of a location
     */
    public void setLatitude(double latitude);

    /**
     * Gets the string representation of the coordinates.
     * @return The string representation of the coordinates
     */
    @Override
    public String toString();

}