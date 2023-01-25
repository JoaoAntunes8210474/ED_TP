
package api.implementation;
import api.interfaces.IPortal;


/**
 *  Class that represents a portal in the game
 * 
 */
public class Portal extends Local implements IPortal{
    
    private int maxEnergy;
    private Player ownerPlayer;
    private String teamPlayer;
    
    

    public Portal(int maxEnergy, Player ownerPlayer, String teamPlayer, int id, String name, String localType, int amountEnergyItHas, Coordinates coordinates) {
        super(id, name, localType, amountEnergyItHas, coordinates);
        this.maxEnergy = maxEnergy;
        this.ownerPlayer = ownerPlayer;
        this.teamPlayer = teamPlayer;
    }

    @Override
    public int getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    @Override
    public String getTeamPlayer() {
        return teamPlayer;
    }

    @Override
    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    @Override
    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    @Override
    public void setTeamPlayer(String teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    @Override
    public String toString() {
        return "Portal{" + "maxEnergy=" + maxEnergy + ", ownerPlayer=" + ownerPlayer + ", teamPlayer=" + teamPlayer + '}';
    }

    
    
    
    
}
