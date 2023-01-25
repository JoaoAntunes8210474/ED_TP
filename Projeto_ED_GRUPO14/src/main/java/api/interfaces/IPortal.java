
package api.interfaces;

import api.implementation.Player;

/**
 *
 * 
 */
public interface IPortal extends ILocal {
    
    public int getMaxEnergy();

    public Player getOwnerPlayer();

    public String getTeamPlayer();

    public void setMaxEnergy(int maxEnergy);

    public void setOwnerPlayer(Player ownerPlayer);
    
    public void setTeamPlayer(String teamPlayer);

    @Override
    public String toString();
    
    
}
