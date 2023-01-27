
package api.interfaces;

import api.implementation.Player;

/**
 *
 * Contract of a Portal Class.
 */
public interface IPortal extends ILocal {
    
    /**
     * Get the maximum amount of energy the portal has.
     * @return maximum amount of energy the portal has.
     */
    public int getMaxEnergy();

    /**
     * Get player who conquered the portal
     * @return Player who conquered the portal.
     */
    public Player getOwnerPlayer();

    /**
     * Get the team that owns the portal.
     * @return Team to whom the portal belongs.
     */
    public String getTeamPlayer();

    /**
     * Set and change the maximum amount of energy the portal has.
     * @param maxEnergy maximum amount of energy the portal has.
     */
    public void setMaxEnergy(int maxEnergy);

    /**
     * Define and change the player who owns the portal, that is, the player who conquered the portal.
     * @param ownerPlayer Player who conquered the portal.
     */
    public void setOwnerPlayer(Player ownerPlayer);
    
    /**
     * Define and change the team that owns the portal. 
     * @param teamPlayer Team to whom the portal belongs.
     */
    public void setTeamPlayer(String teamPlayer);

    /**
     * Returns a string representative of the location, with information referring to this
     * @return string representative of the location.
     */
    @Override
    public String toString();

    
    
}
