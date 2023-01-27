
package api.interfaces;

import api.implementation.Player;
import collections.implementation.ArrayUnorderedList;

import java.io.IOException;

/**
 *
 *
 */
public interface IPlayerManagement{
    /**
     * Returns the list of players
     * @return list of players
     */
    public ArrayUnorderedList<Player> getPlayerList();
    /**
     * Adds a player to the list of players
     * @param player the player to be added into the list of players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String addPlayer(Player player);

    /**
     * Updates a player from the list of players
     * @param player the player to be updated
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String updatePlayer(Player player);

    /**
     * Removes and returns a player from the list of players
     * @param player the player to be removed
     * @return the removed player
     */
    public Player removePlayer(Player player);

    /**
     * Associates a player to a team
     * @param player to associate to a team
     * @param team to associate the player into
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String associatePlayerToTeam(Player player, String team);

    /**
     * Disassociates a player from a team
     * @param player to disassociate from a team
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String disassociatePlayerFromTeam(Player player);

    /**
     * List players by their team
     * @return a String of the players organized by their team
     */
    public String listPlayersByTeam();

    /**
     * List players by their level
     * @return a String of the players organized by their level
     */
    public String listPlayersByLevel();

    /**
     * List players by the number of portals they've conquered
     * @return a String of the players organized by the number of portals they've conquered
     */
    public String listPlayersByPortalsConquered();

    /**
     * Import the content of the JSON given through reference into an instance of this class
     * @param fileName to use for the import
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importJSON(String fileName) throws IOException;

    /**
     * Exports the content of this class into a specified JSON file with the name given through reference
     * @param fileName to use for the export
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String exportJSON(String fileName) throws IOException;
}
