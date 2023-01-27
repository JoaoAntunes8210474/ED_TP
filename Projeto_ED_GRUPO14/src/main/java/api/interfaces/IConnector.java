
package api.interfaces;

import java.util.Iterator;

import org.json.simple.JSONObject;

import api.implementation.ConnectorPlayerInteration;
import api.implementation.Player;
import collections.exceptions.EmptyCollectionException;
import collections.implementation.ArrayUnorderedList;
import collections.implementation.LinkedQueue;

/**
 *
 * Contract of a Connector Class
 */
public interface IConnector extends ILocal{
    
     /**
     * get the specific time interval that the connector supplies power after interaction
     * @return specific time interval that the connector supplies power after interaction
     */
    public int getCooldown();

    /**
     * Get a set of players that have recently interacted with the connector
     * @return players who have recently interacted with the connector
     */
    public LinkedQueue<ConnectorPlayerInteration> getPlayers();

    /**
     * Change the specific time interval that the connector supplies power after interaction
     * @param cooldown the specific time interval that the connector supplies power after interaction
     */
    public void setCooldown(int cooldown);

    /**
     * Change the set of players that have recently interacted with the connector
     * @param players set of players who have recently interacted with the connector
     */
    public void setPlayers(LinkedQueue<ConnectorPlayerInteration> players);

    /**
     * Returns a list of players who interacted with the connector
     * @return the iterator with the list of players
     */
    public Iterator<ConnectorPlayerInteration> getListOfPlayersInteration() throws EmptyCollectionException;

    /**
     * Transforms the connector into a JSONObject representation
     * @return the JSONObject with all the details of the Connector
     */
    public JSONObject connectorToJSONObject() throws EmptyCollectionException;

    /**
     * String representing a connector
     * @return String representing a connector
     */
    @Override
    public String toString();

    
    
}
