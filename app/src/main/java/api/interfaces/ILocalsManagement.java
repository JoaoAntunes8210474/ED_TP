package api.interfaces;

import java.io.IOException;

import api.exceptions.ElementAlreadyExistsException;
import api.implementation.ConnectorPlayerInteration;
import collections.exceptions.EmptyCollectionException;
import collections.implementation.MatrixGraph;

/**
 * Class for managing locations and routes
 */
public interface ILocalsManagement {
    
    /**
     * Adds a new location to the graph
     *
     * @param local to be added.
     * @return String with the result of the operation
     */
    public String addLocals (ILocal local);

    /**
     * Remove a location from the graph
     *
     * @param local to be deleted
     * @return String with the result of the operation
     */
    public String removeLocals (ILocal local);

    /**
     * Add new path between two points on graph.
     *
     * @param route path to be added
     * @return String with the result of the operation
     */
    public String addPath(IRoute route);

    /**
     * Remove a path between two points on graph.
     *
     * @param route path to be removed
     * @return String with the result of the operation
     */
    public String removePath (IRoute route);

    /**
     * Gets the textual listing of all Portals
     *
     * @return string with portals listing
     */
    public String getAllPortalsListing();

    /**
     * Get a textual list with the Portals that are not conquered, that is, that are not associated with any team.
     * @return string with Portals that are not conquered.
     */
    public String getPortalsWithoutTeamListing();

    /**
     * Get a textual list of Portals conquered by a specific player.
     * @param player owner of the portals.
     * @return string with all locations belonging to the player.
     */
    public String getPortalsPlayerListing(IPlayer player);

    /**
     * Get a textual list with the Portals conquered by a specific team.
     * @param team string with all locations belonging to the player.
     * @return string with all locations belonging to the team.
     */
    public String getPortalsByTeamListing(String team);

    /**
     * Get a textual list ordered in descending order by the amount of energy the portal has.
     * @return string with the portals ordered by the amount of energy they have.
     */
    public String getPortalsOrderedByEnergyItHasListing();

    /**
     * Gets the textual listing of all Connectors
     * @return string with connectors listing
     */
    public String getAllConnectorsListing();

    /**
     * Get a textual list of all connectors ordered in descending order by the amount of energy they have.
     * @return string with the connectors ordered according to the amount of energy they have.
     */
    public String getConnectorsOrderedByEnergyItHasListing();


    /**
     * Export all portals from a graph to a Json file
     *
     * @param fileName fileName to use for the export
     * @throws IOException if occurs an error trying to write the file.
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String exportPortalsToJson(String fileName) throws IOException;

    /**
     * Export all Connectors from a graph to a Json file
     *
     * @param fileName fileName to use for the export
     * @throws IOException if occurs an error trying to write the file.
     * @throws EmptyCollectionException if the collection is empty.
     * @return A string indicating whether the operation was successful or something went wrong.
     */
    public String  exportConnectorsToJson(String fileName) throws IOException, EmptyCollectionException;

    /**
     * Export all paths from a graph to a Json file
     *
     * @param fileName fileName to use for the export
     * @throws IOException if occurs an error trying to write the file.
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String exportPathsToJson(String fileName) throws IOException;

    /**
     * Import portals from Json file to graph
     * @param fileName fileName to use for the import
     * @throws IOException if occurs an error trying to read the file.
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importPortalsFromJSON(String fileName) throws IOException;

    /**
     * Import connectors from Json file to graph
     * @param fileName fileName to use for the import
     * @throws IOException if occurs an error trying to read the file.
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importConnectorsFromJSON(String fileName) throws IOException;

    /**
     * Import paths from a Json file into a graph
     * @param fileName fileName to use for the import
     * @throws IOException if occurs an error trying to read the file.
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importPathsFromJSON(String fileName) throws IOException;

    /**
     * Get the graph of the game
     * @return the graph of the game
     */
    public IPathGameGraphADT<ILocal> getPathGraph();
}