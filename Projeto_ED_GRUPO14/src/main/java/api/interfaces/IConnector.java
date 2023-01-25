
package api.interfaces;

import api.implementation.Player;
import collections.implementation.ArrayUnorderedList;

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
    public ArrayUnorderedList<Player> getPlayers();

    /**
     * Change the specific time interval that the connector supplies power after interaction
     * @param cooldown the specific time interval that the connector supplies power after interaction
     */
    public void setCooldown(int cooldown);

    /**
     * Change the set of players that have recently interacted with the connector
     * @param players set of players who have recently interacted with the connector
     */
    public void setPlayers(ArrayUnorderedList<Player> players);

    /**
     * String representing a connector
     * @return String representing a connector
     */
    @Override
    public String toString();

    
    
}
