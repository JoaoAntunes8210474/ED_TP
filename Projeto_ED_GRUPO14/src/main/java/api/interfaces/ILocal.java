
package api.interfaces;

import api.implementation.Coordinates;

/**
 *
 * Contract of a Local Class.
 */
public interface ILocal {
   
    /**
     * Get unique location identification number.
     * @return The integer representing the unique identifier of each location
     */
    public int getId();

    /**
     * Get place name.
     * @return The location name, the name will be the name of points of interest like statues, churches ...
     */
    public String getName();

    /**
     * Get location type
     * @return The location type, can be portal or connector type.
     */
    public String getLocalType();

    /**
     * Get amount of energy the location has.
     * @return The amount of energy the site contains.
     */
    public int getAmountEnergyItHas();

    /**
     * Get location coordinates.
     * @return The location coordinates
     */
    public Coordinates getCoordinates();

    public void setId(int id);

    public void setName(String name);

    public void setLocalType(String localType);

    public void setAmountEnergyItHas(int amountEnergyItHas);

    public void setCoordinates(Coordinates coordinates);

    @Override
    public String toString();
    
}
