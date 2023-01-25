
package api.interfaces;

/**
 *
 * Contract of a Player Class.
 */
public interface IPlayer {
    
    /**
     * Get player name.
     * @return Player's name.
     */
    public String getName();

    /**
     * Get team the player belongs to.
     * @return Team the player belongs to.
     */
    public String getTeam();

    /**
     * Get level of the game the player is in.
     * @return Level the player is in the game.
     */
    public int getLevel();

    /**
     * Get the amount of points the player has in the game.
     * @return Points obtained by the player during interaction with the game.
     */
    public long getExperiencePoints();

    /**
     * Get amount of energy the player has.
     * @return Amount of energy the player has.
     */ 
    public int getCurrentEnergy();

    /**
     * Gettime since last interaction with a Connector.
     * @return Time since last interaction with a connector.
     */
    public int getTimeConnector();

    /**
     * Get amount of portals conquered by the player
     * @return Number of portals conquered by the player
     */
    public int getNumPortals();

    /**
     * 
     * @param name Player's name.
     */
    public void setName(String name);

    /**
     * Set and change a player's name.
     * @param team Team the player belongs to.
     */
    public void setTeam(String team);

    /**
     * Set and change the level in which the player is in the game
     * @param level Level the player is in the game.
     */
    public void setLevel(int level);

    /**
     * Sets and changes the amount of points the player has earned through in-game interaction.
     * @param experiencePoints Points obtained by the player during interaction with the game.
     */
    public void setExperiencePoints(long experiencePoints);

    /**
     * Sets and changes the amount of energy the player has.
     * @param currentEnergy Amount of energy the player has.
     */
    public void setCurrentEnergy(int currentEnergy);

    /**
     * Defines and changes the time interval that the player has since the last interaction with a connector.
     * @param timeConnector Time since last interaction with a connector.
     */
    public void setTimeConnector(int timeConnector);

    /**
     * Set and changes the number of portals conquered by a player.
     * @param numPortals Number of portals conquered by the player
     */
    public void setNumPortals(int numPortals);

    /**
     * Aims to return a string that represents a player, with information about him.
     * @return string that represents a player, with information about him.
     */
    @Override
    public String toString();
    
}
