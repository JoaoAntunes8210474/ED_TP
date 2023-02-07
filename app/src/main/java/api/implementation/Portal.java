package api.implementation;
import org.json.simple.JSONObject;


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
    private String playerTeam;
    
    
    /**
     * Constructor method: serves to instantiate objects of the portal type.
     * @param maxEnergy Integer number that represents the maximum amount of energy that a portal has.
     * @param id Integer representing the unique identifier of each location
     * @param name Location name, the name will be the name of points of interest like statues, churches ...
     * @param amountEnergyItHas Amount of energy the site contains.
     * @param coordinates Location coordinates
     */
    public Portal(int maxEnergy, int id, String name, int amountEnergyItHas, Coordinates coordinates) {
        super(id, name, "Portal", amountEnergyItHas, coordinates);
        this.maxEnergy = maxEnergy;
        this.ownerPlayer = null;
        this.playerTeam = "NEUTRAL";
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
    public String getPlayerTeam() {
        return playerTeam;
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
     * @param playerTeam Team to whom the portal belongs.
     */
    @Override
    public void setPlayerTeam(String playerTeam) {
        this.playerTeam = playerTeam;
    }

     /**
     * Transforms the portal into a JSONObject representation
     * @return the JSONObject with all the details of the Portal
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject portalToJSONObject() {
        JSONObject root = new JSONObject();
        root.put("id", getId());
        root.put("name", getName());
        root.put("localType", getLocalType());
        root.put("amountEnergyItHas", getAmountEnergyItHas());
        root.put("coordinates", getCoordinates());
        root.put("maxEnergy", this.maxEnergy);
        root.put("ownerPlayer", this.ownerPlayer);
        root.put("ownerTeam", this.playerTeam);
        return root;
    }

    
    /**
     * Returns a string representative of the location, with information referring to this
     * @return string representative of the location.
     */
    @Override
    public String toString() {
        return super.toString() + "Portal{" + "maxEnergy=" + maxEnergy + ", ownerPlayer=" + ownerPlayer + ", teamPlayer=" + playerTeam + '}' + "\n";
    }

    /**
     * 
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IPortal that = (IPortal) o;
        return getName().equals(that.getName());
    }


}
