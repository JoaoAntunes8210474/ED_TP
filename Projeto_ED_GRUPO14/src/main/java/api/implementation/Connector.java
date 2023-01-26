
package api.implementation;

import api.enumerations.LocalTypeEnum;
import api.interfaces.IConnector;
import collections.implementation.LinkedQueue;


/**
 * Class that implements the contract of a Connector.
 */
public class Connector extends Local implements IConnector{
    
    // specific time interval that the connector supplies power after interaction
    private int cooldown;
    
    //set of players that interacted with the connector
    private LinkedQueue<ConnectorPlayerInteration> players;

    /**
     * Constructor: instantiate objects of type connector
     * @param cooldown specific time interval that the connector supplies power after interaction
     * @param players set of players that interacted with the connector
     * @param id Integer representing the unique identifier of each location
     * @param name Location name, the name will be the name of points of interest like statues, churches ...
     * @param localType Location type, can be portal or connector type.
     * @param amountEnergyItHas Amount of energy the site contains.
     * @param coordinates Location coordinates
     */
    public Connector(int cooldown, int id, String name, LocalTypeEnum localType, int amountEnergyItHas, Coordinates coordinates) {
        super(id, name, localType, amountEnergyItHas, coordinates);
        this.cooldown = cooldown;
        this.players = new LinkedQueue<>();  
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
    public LinkedQueue<ConnectorPlayerInteration> getPlayers() {
        return this.players;
    }

    /**
     * Change the set of players that have recently interacted with the connector
     * @param players set of players who have recently interacted with the connector
     */
    @Override
    public void setPlayers(LinkedQueue<ConnectorPlayerInteration> players) {
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