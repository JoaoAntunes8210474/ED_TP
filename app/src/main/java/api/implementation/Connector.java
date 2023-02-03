package api.implementation;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import api.interfaces.IConnector;
import collections.implementation.ArrayUnorderedList;
import collections.interfaces.UnorderedListADT;
import collections.exceptions.EmptyCollectionException;


/**
 * Class that implements the contract of a Connector.
 */
public class Connector extends Local implements IConnector{
    
    // specific time interval that the connector supplies power after interaction
    private int cooldown;
    
    //set of players that interacted with the connector
    private UnorderedListADT<ConnectorPlayerInteration> players;

    /**
     * Constructor: instantiate objects of type connector
     * @param cooldown specific time interval that the connector supplies power after interaction
     * @param id Integer representing the unique identifier of each location
     * @param name Location name, the name will be the name of points of interest like statues, churches ...
     * @param localType Location type, can be portal or connector type.
     * @param amountEnergyItHas Amount of energy the site contains.
     * @param coordinates Location coordinates
     */
    public Connector(int cooldown, int id, String name, int amountEnergyItHas, Coordinates coordinates) {
        super(id, name, "Connector", amountEnergyItHas, coordinates);
        this.cooldown = cooldown;
        this.players = new ArrayUnorderedList<>();  
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
    @SuppressWarnings("unchecked")
    @Override
    public ArrayUnorderedList<ConnectorPlayerInteration> getPlayers() {
        return (ArrayUnorderedList<ConnectorPlayerInteration>) this.players;
    }

    /**
     * Change the set of players that have recently interacted with the connector
     * @param players set of players who have recently interacted with the connector
     */
    @Override
    public void setPlayers(ArrayUnorderedList<ConnectorPlayerInteration> players) {
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
     * Returns a list of players who interacted with the connector
     * @return the iterator with the list of players
     * @throws EmptyCollectionException
     */
    @Override
    public Iterator<ConnectorPlayerInteration> getListOfPlayersInteration() throws EmptyCollectionException {
        return this.players.iterator();
    }



    /**
     * Transforms the connector into a JSONObject representation
     * @return the JSONObject with all the details of the Connector
     * @throws EmptyCollectionException
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject connectorToJSONObject() throws EmptyCollectionException {
        JSONObject root = new JSONObject();
        root.put("Id", getId());
        root.put("Name", getName());
        root.put("Local Type", getLocalType());
        root.put("Amount Energy It Has", getAmountEnergyItHas());
        root.put("Cordinates", getCoordinates());
        root.put("Cooldown time", this.cooldown);
        
        if (this.players.isEmpty()) {
            root.put("Players", "Don't have any players");
        } else {
            JSONArray playersOfConnector = new JSONArray();
            Iterator<ConnectorPlayerInteration> iterator = this.getListOfPlayersInteration();
            while (iterator.hasNext()) {
                playersOfConnector.add(iterator.next());
            }
            root.put("Players", playersOfConnector);
        }
        return root; 
    }


    /**
     * String representing a connector
     * @return String representing a connector
     */
    @Override
    public String toString() {
        return "Connector{" + "cooldown=" + cooldown + ", players=" + players + '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IConnector that = (IConnector) o;
        return getName().equals(that.getName());
    }

    
    
}