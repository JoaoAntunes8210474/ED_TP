
package api.interfaces;

import api.implementation.Player;
import collections.implementation.ArrayUnorderedList;

/**
 *
 * 
 */
public interface IConnector extends ILocal{
    
    public int getCooldown();

    public ArrayUnorderedList<Player> getPlayers();

    public void setCooldown(int cooldown);

    public void setPlayers(ArrayUnorderedList<Player> players);

    @Override
    public String toString();

    
    
}
