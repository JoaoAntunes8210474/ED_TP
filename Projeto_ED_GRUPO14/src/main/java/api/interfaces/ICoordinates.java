
package api.interfaces;

/**
 *
 * @author reginaneto
 */
public interface ICoordinates {
    
    public double getLongitude();

    public double getLatitude();

    public void setLongitude(double longitude);

    public void setLatitude(double latitude);

    @Override
    public String toString();
    
    
}
