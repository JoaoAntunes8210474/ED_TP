package api.interfaces;

/**
 * Contract of a Player Class.
 */
public interface IPlayer {

    /**
     * Get player name.
     *
     * @return Player's name.
     */
    String getName();

    /**
     * Get team the player belongs to.
     *
     * @return Team the player belongs to.
     */
    String getTeam();

    /**
     * Get level of the game the player is in.
     *
     * @return Level the player is in the game.
     */
    int getLevel();

    /**
     * Get the current location of the player.
     *
     * @return Current location of the player.
     */
    ILocal getCurrentLocation();

    /**
     * Get the amount of points the player has in the game.
     *
     * @return Points obtained by the player during interaction with the game.
     */
    long getExperiencePoints();

    /**
     * Get amount of energy the player has.
     *
     * @return Amount of energy the player has.
     */
    int getCurrentEnergy();

    /**
     * Get amount of portals conquered by the player
     *
     * @return Number of portals conquered by the player
     */
    int getNumPortals();

    /**
     * @param name Player's name.
     */
    void setName(String name);

    /**
     * Set and change a player's name.
     *
     * @param team Team the player belongs to.
     */
    void setTeam(String team);

    /**
     * Set and change the current location of the player.
     *
     * @param currentLocation Current location of the player.
     */
    void setCurrentLocation(ILocal currentLocation);

    /**
     * Set and change the level in which the player is in the game
     *
     * @param level Level the player is in the game.
     */
    void setLevel(int level);

    /**
     * Sets and changes the amount of points the player has earned through in-game interaction.
     *
     * @param experiencePoints Points obtained by the player during interaction with the game.
     */
    void setExperiencePoints(long experiencePoints);

    /**
     * Sets and changes the amount of energy the player has.
     *
     * @param currentEnergy Amount of energy the player has.
     */
    void setCurrentEnergy(int currentEnergy);

    /**
     * Set and changes the number of portals conquered by a player.
     *
     * @param numPortals Number of portals conquered by the player
     */
    void setNumPortals(int numPortals);

    /**
     * Method that allows the player to attack the portal in the current location using the energy of the player.
     * If the player has enough energy, the player can conquer the portal.
     */
    void attackPortal(int energy);

    /**
     * Method that allows the player to conquer the portal in the current location using the energy of the player.
     */
    void conquerPortal(int energy);

    /**
     * Method that allows the player to reinforce the portal in the current location if the player has enough energy.
     * Only works if the portal is conquered by the player's team.
     */
    void reinforcePortal(int energy);

    /**
     * Method that allows the player to recharge his energy.
     */
    String rechargeEnergy();

    /**
     * Aims to return a string that represents a player, with information about him.
     *
     * @return string that represents a player, with information about him.
     */
    @Override
    String toString();


}
