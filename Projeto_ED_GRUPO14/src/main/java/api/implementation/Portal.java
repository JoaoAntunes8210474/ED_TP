
package api.implementation;
import api.interfaces.IPortal;


/**
 *  Class that represents a portal in the game
 *  Class that complies with the IPortal interface contract.
 */
public class Portal extends Local implements IPortal{
    
    // Integer number that represents the maximum amount of energy that a portal has.
    private int maxEnergy;
    
    //Player who conquered the portal.
    private Player ownerPlayer;
    
    //Team to whom the portal belongs.
    private String teamPlayer;
    
    
    /**
     * Constructor method: serves to instantiate objects of the portal type.
     * @param maxEnergy Integer number that represents the maximum amount of energy that a portal has.
     * @param ownerPlayer Player who conquered the portal.
     * @param teamPlayer Team to whom the portal belongs.
     * @param id Integer representing the unique identifier of each location
     * @param name Location name, the name will be the name of points of interest like statues, churches ...
     * @param localType Location type, can be portal or connector type.
     * @param amountEnergyItHas Amount of energy the site contains.
     * @param coordinates Location coordinates
     */
    public Portal(int maxEnergy, Player ownerPlayer, String teamPlayer, int id, String name, String localType, int amountEnergyItHas, Coordinates coordinates) {
        super(id, name, localType, amountEnergyItHas, coordinates);
        this.maxEnergy = maxEnergy;
        this.ownerPlayer = ownerPlayer;
        this.teamPlayer = teamPlayer;
    }

    /**
     * Get the maximum amount of energy the portal has.
     * @return maximum amount of energy the portal has.
     */
    @Override
    public int getMaxEnergy() {
        return maxEnergy;
    }

    /**
     * Get player who conquered the portal
     * @return Player who conquered the portal.
     */
    @Override
    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    /**
     * Get the team that owns the portal.
     * @return Team to whom the portal belongs.
     */
    @Override
    public String getTeamPlayer() {
        return teamPlayer;
    }

    /**
     * Set and change the maximum amount of energy the portal has.
     * @param maxEnergy maximum amount of energy the portal has.
     */
    @Override
    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    /**
     * Define and change the player who owns the portal, that is, the player who conquered the portal.
     * @param ownerPlayer Player who conquered the portal.
     */
    @Override
    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    /**
     * Define and change the team that owns the portal. 
     * @param teamPlayer Team to whom the portal belongs.
     */
    @Override
    public void setTeamPlayer(String teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    /**
     * Returns a string representative of the location, with information referring to this
     * @return string representative of the location.
     */
    @Override
    public String toString() {
        return "Portal{" + "maxEnergy=" + maxEnergy + ", ownerPlayer=" + ownerPlayer + ", teamPlayer=" + teamPlayer + '}';
    }

    
    
    
    
}
