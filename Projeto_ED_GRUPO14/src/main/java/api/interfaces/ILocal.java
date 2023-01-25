
package api.interfaces;

import api.implementation.Coordinates;

/**
 *
 * 
 */
public interface ILocal {
   
   
    public int getId();

    public String getName();

    public String getLocalType();

    public int getAmountEnergyItHas();

    public Coordinates getCoordinates();

    public void setId(int id);

    public void setName(String name);

    public void setLocalType(String localType);

    public void setAmountEnergyItHas(int amountEnergyItHas);

    public void setCoordinates(Coordinates coordinates);

    @Override
    public String toString();
    
}
