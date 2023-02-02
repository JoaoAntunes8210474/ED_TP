package api.implementation;

import api.interfaces.IConnector;
import api.interfaces.ILocal;
import api.interfaces.IPlayer;
import api.interfaces.IPortal;

/**
 * Class representing a player.
 * Class that implements the IPlayer interface contract.
 */
public class Player implements IPlayer, Comparable<Player> {
    private final int FLAT_ATTACK_EXPERIENCE_POINTS = 15;

    private final int FLAT_CONQUER_EXPERIENCE_POINTS = 25;

    private final int FLAT_REINFORCE_EXPERIENCE_POINTS = 10;

    private final int FLAT_RECHARGE_EXPERIENCE_POINTS = 5;

    private final double X_VALUE_IN_FORMULA = 0.10;

    private final int Y_VALUE_IN_FORMULA = 2;

    //Player's name
    private String name;

    //Team the player belongs to.
    private String team;

    //Level the player is in the game.
    private int level;

    //Points obtained by the player during interaction with the game.
    private long experiencePoints;

    private ILocal currentLocation;

    //Amount of energy the player has.
    private int currentEnergy;
    
    //Number of portals conquered by the player
    private int numPortals;

    /**
     * Constructor method is used to instantiate objects of type player.
     * @param name Player's name
     * @param team Team the player belongs to.
     */
    public Player(String name, String team) {
        this.name = name;
        this.team = team;
        this.level = 1;
        this.experiencePoints = 0;
        this.currentEnergy = 0;
        this.numPortals = 0;
    }


    /**
     * Method that adds experience points to the player depending on the action performed by the player.
     * @param actionPlayerPerformed Action performed by the player.
     */
    private void addExperiencePoints(String actionPlayerPerformed) {
        switch (actionPlayerPerformed) {
            case "ATTACK":
                this.experiencePoints += ((long) FLAT_ATTACK_EXPERIENCE_POINTS * this.level);
                break;
            case "REINFORCE":
                this.experiencePoints += ((long) FLAT_REINFORCE_EXPERIENCE_POINTS * this.level);
                break;
            case "CONQUER":
                this.experiencePoints += ((long) FLAT_CONQUER_EXPERIENCE_POINTS * this.level);
                break;
            case "RECHARGE":
                this.experiencePoints += ((long) FLAT_RECHARGE_EXPERIENCE_POINTS * this.level);
                break;
        }
    }

    /**
     * Method that returns a boolean value depending on whether the player can increase level.
     * @return Boolean value depending on whether the player can increase level.
     */
    private boolean canIncreaseLevel() {
        return this.experiencePoints >= Math.pow((this.level/X_VALUE_IN_FORMULA), Y_VALUE_IN_FORMULA);
    }

    /**
     * Method that increases the level of the player.
     */
    private void increaseLevel() {
        if (canIncreaseLevel()) {
            this.level++;
        }
    }

    /**
     * Get player name.
     *
     * @return Player's name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get team the player belongs to.
     *
     * @return Team the player belongs to.
     */
    @Override
    public String getTeam() {
        return team;
    }

    /**
     * Get level of the game the player is in.
     *
     * @return Level the player is in the game.
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Get the current location of the player.
     *
     * @return Current location of the player.
     */
    @Override
    public ILocal getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Get the amount of points the player has in the game.
     *
     * @return Points obtained by the player during interaction with the game.
     */
    @Override
    public long getExperiencePoints() {
        return experiencePoints;
    }

    /**
     * Get amount of energy the player has.
     *
     * @return Amount of energy the player has.
     */
    @Override
    public int getCurrentEnergy() {
        return currentEnergy;
    }

    /**
     * Get amount of portals conquered by the player
     *
     * @return Number of portals conquered by the player
     */
    @Override
    public int getNumPortals() {
        return numPortals;
    }

    /**
     * @param name Player's name.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set and change a player's name.
     *
     * @param team Team the player belongs to.
     */
    @Override
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * Set and change the current location of the player.
     *
     * @param currentLocation Current location of the player.
     */
    @Override
    public void setCurrentLocation(ILocal currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * Set and change the level in which the player is in the game
     *
     * @param level Level the player is in the game.
     */
    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Sets and changes the amount of points the player has earned through in-game interaction.
     *
     * @param experiencePoints Points obtained by the player during interaction with the game.
     */
    @Override
    public void setExperiencePoints(long experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    /**
     * Sets and changes the amount of energy the player has.
     *
     * @param currentEnergy Amount of energy the player has.
     */
    @Override
    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    /**
     * Set and changes the number of portals conquered by a player.
     *
     * @param numPortals Number of portals conquered by the player
     */
    @Override
    public void setNumPortals(int numPortals) {
        this.numPortals = numPortals;
    }

    /**
     * Method that allows the player to attack the portal in the current location using the energy of the player.
     * If the player has enough energy, the player can conquer the portal.
     */
    @Override
    public void attackPortal() {
        if (!(this.currentLocation instanceof IPortal)) {
            throw new IllegalArgumentException("The current location is not a portal.");
        }

        this.addExperiencePoints("ATTACK");
        this.increaseLevel();
    }

    /**
     * Method that allows the player to conquer the portal in the current location using the energy of the player.
     */
    @Override
    public void conquerPortal() {
        if (!(this.currentLocation instanceof IPortal)) {
            throw new IllegalArgumentException("The current location is not a portal.");
        }

        this.addExperiencePoints("CONQUER");
        this.increaseLevel();
    }

    /**
     * Method that allows the player to reinforce the portal in the current location if the player has enough energy.
     * Only works if the portal is conquered by the player's team.
     */
    @Override
    public void reinforcePortal() {
        if (!(this.currentLocation instanceof IPortal)) {
            throw new IllegalArgumentException("The current location is not a portal.");
        }

        this.addExperiencePoints("REINFORCE");
        this.increaseLevel();
    }

    /**
     * Method that allows the player to recharge his energy.
     */
    @Override
    public void rechargeEnergy() {
        if (!(this.currentLocation instanceof IConnector)) {
            throw new IllegalArgumentException("The current location is not a connector.");
        }

        Connector connector = (Connector) this.currentLocation;

        this.currentEnergy += connector.getAmountEnergyItHas();

        //connector.getPlayers().addToRear();

        this.addExperiencePoints("RECHARGE");
        this.increaseLevel();
    }

    /**
     * Aims to return a string that represents a player, with information about him.
     *
     * @return string that represents a player, with information about him.
     */
    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", team=" + team + ", level=" + level + ", experiencePoints=" + experiencePoints + ", currentEnergy=" + currentEnergy + "," + ", numPortals=" + numPortals + '}';
    }

    /**
     * Aims to compare two players by their level.
     *
     * @param player Player to compare.
     * @return 1 if the player is greater than the player to compare, 0 if they are equal, -1 if the player is less than the player to compare.
     */
    @Override
    public int compareTo(Player player) {
        return Integer.compare(this.getLevel(), player.getLevel());
    }


}
