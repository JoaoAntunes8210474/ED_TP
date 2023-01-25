
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

    /**
     * Set or change a location's unique ID number    
     * @param id The integer representing the unique identifier of each location
     */
    public void setId(int id);

    /**
     * Set or change the location name.
     * @param name Location name, the name will be the name of points of interest like statues, churches ...
     */
    public void setName(String name);

    /**
     * Set or change the location type.
     * @param localType Location type, can be portal or connector type.
     */
    public void setLocalType(String localType);

    /**
     * Set or change the amount of energy the location has.
     * @param amountEnergyItHas Amount of energy the site contains.
     */
    public void setAmountEnergyItHas(int amountEnergyItHas);

    /**
     * Set or change location coordinates.
     * @param coordinates Location coordinates
     */
    public void setCoordinates(Coordinates coordinates);

    /**
     * Aims to return a string representative of the location.
     * @return string representative of the location.
     */
    @Override
    public String toString();
    
}
