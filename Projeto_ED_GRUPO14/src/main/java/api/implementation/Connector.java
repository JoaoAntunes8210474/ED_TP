
package api.implementation;

import api.interfaces.IConnector;
import collections.implementation.ArrayUnorderedList;


/**
 * Class that implements the contract of a Connector.
 */
public class Connector extends Local implements IConnector{
    
    // specific time interval that the connector supplies power after interaction
    private int cooldown;
    
    //set of players that interacted with the connector
    private ArrayUnorderedList<Player> players;

    /**
     * Constructor: instantiate objects of type connector
     * @param cooldown specific time interval that the connector supplies power after interaction
     * @param players
     * @param id
     * @param name
     * @param localType
     * @param amountEnergyItHas
     * @param coordinates 
     */
    public Connector(int cooldown, ArrayUnorderedList<Player> players, int id, String name, String localType, int amountEnergyItHas, Coordinates coordinates) {
        super(id, name, localType, amountEnergyItHas, coordinates);
        this.cooldown = cooldown;
        this.players = players;
    }

    /**
     * get the specific time interval that the connector supplies power after interaction
     * @return specific time interval that the connector supplies power after interaction
     */
    @Override
    public int getCooldown() {
        return this.cooldown;
    }

    /**
     * Get a set of players that have recently interacted with the connector
     * @return players who have recently interacted with the connector
     */
    @Override
    public ArrayUnorderedList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Change the set of players that have recently interacted with the connector
     * @param players set of players who have recently interacted with the connector
     */
    @Override
    public void setPlayers(ArrayUnorderedList<Player> players) {
        this.players = players;
    }
    
    /**
     * Change the specific time interval that the connector supplies power after interaction
     * @param cooldown the specific time interval that the connector supplies power after interaction
     */
    @Override
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    
    /**
     * String representing a connector
     * @return String representing a connector
     */
    @Override
    public String toString() {
        return "Connector{" + "cooldown=" + cooldown + ", players=" + players + '}';
    }


}