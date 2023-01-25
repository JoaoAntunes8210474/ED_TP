
package api.interfaces;

import api.implementation.Player;

/**
 *
 * 
 */
public interface IConnector extends ILocal{
    
    public int getCooldown();

    public Player[] getPlayers();

    public void setCooldown(int cooldown);

    public void setPlayers(Player[] players);

    @Override
    public String toString();

    
    
}
