package api.implementation;

import api.app.Main;
import api.interfaces.IConnector;
import api.interfaces.ILocal;
import api.interfaces.IPlayer;
import api.interfaces.IPortal;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;

/**
 * Class representing a player.
 * Class that implements the IPlayer interface contract.
 */
public class Player implements IPlayer, Comparable<Player> {

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

    protected Player(String name, String team, int level, long experiencePoints, int currentEnergy, int numPortals) {
        this.name = name;
        this.team = team;
        this.level = level;
        this.experiencePoints = experiencePoints;
        this.currentEnergy = currentEnergy;
        this.numPortals = numPortals;
    }

    /**
     * Method that adds experience points to the player depending on the action performed by the player.
     * @param actionPlayerPerformed Action performed by the player.
     */
    private void addExperiencePoints(String actionPlayerPerformed) {
        final int FLAT_ATTACK_EXPERIENCE_POINTS = 15;
        final int FLAT_CONQUER_EXPERIENCE_POINTS = 25;
        final int FLAT_REINFORCE_EXPERIENCE_POINTS = 10;
        final int FLAT_RECHARGE_EXPERIENCE_POINTS = 5;
        switch (actionPlayerPerformed) {
            case "ATTACK":
                this.experiencePoints += ((long) FLAT_ATTACK_EXPERIENCE_POINTS * (1 + this.level));
                break;
            case "REINFORCE":
                this.experiencePoints += ((long) FLAT_REINFORCE_EXPERIENCE_POINTS * (1 + this.level));
                break;
            case "CONQUER":
                this.experiencePoints += ((long) FLAT_CONQUER_EXPERIENCE_POINTS * (1 + this.level));
                break;
            case "RECHARGE":
                this.experiencePoints += ((long) FLAT_RECHARGE_EXPERIENCE_POINTS * (1 + this.level));
                break;
        }
    }

    /**
     * Method that returns a boolean value depending on whether the player can increase level.
     * @return Boolean value depending on whether the player can increase level.
     */
    private boolean canIncreaseLevel() {
        final double X_VALUE_IN_FORMULA = 0.10;
        final int Y_VALUE_IN_FORMULA = 2;
        return this.experiencePoints >= Math.pow((this.level/ X_VALUE_IN_FORMULA), Y_VALUE_IN_FORMULA);
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
    public void attackPortal(int energy) {
        if (!(this.currentLocation instanceof IPortal)) {
            throw new IllegalArgumentException("The current location is not a portal.");
        }

        Portal portal = (Portal) this.currentLocation;

        if (portal.getPlayerTeam().equals(this.team)) {
            throw new IllegalArgumentException("The portal is already conquered by your team.");
        }

        // If player attacks portal, we subtract the energy received to the portals energy
        // If the portals energy becomes negative, we check if the negative energy is over 25% of the portal's max energy in positive values
        // If it is, the portal is conquered by the player's team, and we set the portal's energy to the positive value of the energy that was over 25% of the portal's max energy
        // If it is not, we set the portal's energy to the positive value of the energy that was not over 25% of the portal's max energy and set the portal's player team to "NEUTRAL"
        portal.setAmountEnergyItHas(portal.getAmountEnergyItHas() - energy);
        if (portal.getAmountEnergyItHas() < 0) {
            if (Math.abs(portal.getAmountEnergyItHas()) > (portal.getMaxEnergy() * 0.25)) {
                portal.setPlayerTeam(this.team);
                portal.setAmountEnergyItHas(Math.abs(portal.getAmountEnergyItHas()));
                this.currentEnergy -= energy;
                this.numPortals++;
            } else {
                portal.setAmountEnergyItHas(Math.abs(portal.getAmountEnergyItHas()));
                portal.setPlayerTeam("NEUTRAL");
                this.currentEnergy -= energy;
            }
        }

        this.addExperiencePoints("ATTACK");
        this.increaseLevel();
    }

    /**
     * Method that allows the player to conquer the portal in the current location using the energy of the player.
     */
    @Override
    public void conquerPortal(int energy) {
        if (!(this.currentLocation instanceof IPortal)) {
            throw new IllegalArgumentException("The current location is not a portal.");
        }

        Portal portal = (Portal) this.currentLocation;

        if (!(portal.getPlayerTeam().equals("NEUTRAL"))) {
            throw new IllegalArgumentException("The portal is already conquered by a team.");
        }

        // If the player is charging a neutral portal that already has energy, the portal's energy is set to the sum of the amount it previously had with the energy it received
        // If the player charges the portal with 25% or more of the portal's max energy, the portal is conquered by the player's team
        // If the player charges the portal with less than 25% of the portal's max energy, the portal's team remains "NEUTRAL"
        if ((portal.getAmountEnergyItHas() + energy) >= (portal.getMaxEnergy() * 0.25)) {
            portal.setPlayerTeam(this.team);
            portal.setAmountEnergyItHas(portal.getAmountEnergyItHas() + energy);
            this.currentEnergy -= energy;
            this.numPortals++;
        } else {
            portal.setAmountEnergyItHas(portal.getAmountEnergyItHas() + energy);
            this.currentEnergy -= energy;
        }

        this.addExperiencePoints("CONQUER");
        this.increaseLevel();
    }

    /**
     * Method that allows the player to reinforce the portal in the current location if the player has enough energy.
     * Only works if the portal is conquered by the player's team.
     */
    @Override
    public void reinforcePortal(int energy) {
        if (!(this.currentLocation instanceof IPortal)) {
            throw new IllegalArgumentException("The current location is not a portal.");
        }

        Portal portal = (Portal) this.currentLocation;

        if (!portal.getPlayerTeam().equals(this.team)) {
            throw new IllegalArgumentException("The portal is not from the player's team.");
        }

        // If player reinforces portal, we add the energy received to the portals energy
        portal.setAmountEnergyItHas(portal.getAmountEnergyItHas() + energy);

        // If the portal's energy is over the portal's max energy, we set the portal's energy to the portal's max energy, and we subtract the energy left to the player's energy
        // If the portal's energy is not over the portal's max energy, we subtract the energy received to the player's energy
        if (portal.getAmountEnergyItHas() > portal.getMaxEnergy()) {
            int energyLeft = portal.getAmountEnergyItHas() - portal.getMaxEnergy();
            portal.setAmountEnergyItHas(portal.getMaxEnergy());
            this.setCurrentEnergy(this.getCurrentEnergy() - energy + energyLeft);
        } else {
            this.setCurrentEnergy(this.getCurrentEnergy() - energy);
        }

        this.addExperiencePoints("REINFORCE");
        this.increaseLevel();
    }

    /**
     * Method that allows the player to recharge his energy.
     * The player can only recharge his energy if he is in a connector.
     * @return String informing the player that his energy has been recharged or that the connector is in cooldown.
     */
    @Override
    public String rechargeEnergy() {
        if (!(this.currentLocation instanceof IConnector)) {
            throw new IllegalArgumentException("The current location is not a connector.");
        }

        Connector connector = (Connector) this.currentLocation;

        // If the player is in a connector, we check if the player has already interacted with the connector in the last 3 minutes
        Iterator<ConnectorPlayerInteration> iterator = connector.getPlayers().iterator();
        LocalTime horaJogo = Main.getGameTimer();
        LocalTime horaInteracao;
        Duration tempoDesdeInteracao;
        Duration cooldown = Duration.ofMinutes(Main.getCooldown());
        boolean jaInteragiu = false;

        // If the player has already interacted with the connector in the last 3 minutes, we return a string informing the player that he can't recharge his energy yet
        // If the player hasn't interacted with the connector in the last 3 minutes, we set the new interaction time to the current time and we add the connector's energy to the player's energy
        // If there is a player that hasn't interacted with the connector in the last 3 minutes, we remove him from the connector's list of players
        // If the player hasn't interacted with the connector, we add him to the connector's list of players
        while (iterator.hasNext()) {
            ConnectorPlayerInteration connectorPlayerInterationIterator = iterator.next();
            horaInteracao = connectorPlayerInterationIterator.getHoraInteracao();
            tempoDesdeInteracao = Duration.between(horaInteracao, horaJogo);
            if (connectorPlayerInterationIterator.getPlayer().toString().equals(this.toString()) && (tempoDesdeInteracao.compareTo(cooldown) >= 0)) {
                connectorPlayerInterationIterator.setHoraInteracao(horaJogo);
                this.currentEnergy += connector.getAmountEnergyItHas();
                jaInteragiu = true;
                break;
            } else if (connectorPlayerInterationIterator.getPlayer().toString().equals(this.toString()) && (tempoDesdeInteracao.compareTo(cooldown) < 0)){
                return "You can't recharge your energy yet.";
            }
        }

        if (!jaInteragiu) {
            connector.getPlayers().addToRear(new ConnectorPlayerInteration(this, horaJogo));
            this.currentEnergy += connector.getAmountEnergyItHas();
        }

        this.addExperiencePoints("RECHARGE");
        this.increaseLevel();

        return "You have recharged your energy.";
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
