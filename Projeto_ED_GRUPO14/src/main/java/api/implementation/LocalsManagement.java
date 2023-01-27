package api.implementation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;

import api.interfaces.IConnector;
import api.interfaces.ILocal;
import api.interfaces.ILocalsManagement;
import api.interfaces.IPathGameGraphADT;
import api.interfaces.IPlayer;
import api.interfaces.IPortal;
import api.interfaces.IRoute;
import collections.exceptions.EmptyCollectionException;

public class LocalsManagement implements ILocalsManagement {
    
    
    /**
     * Network graph that have information about the locals and path between them.
     */
    private IPathGameGraphADT<ILocal> pathGraph;
    private

    /**
     *
     */
    public LocalsManagement() {
        this.pathGraph = new PathGameGraph<>();

    }


     /**
     * Adds a new location to the graph
     * @param local to be added.
     * @return string string that informs if the operation was performed successfully
     */
    @Override
    public String addLocals (ILocal local) {
        if (local == null) {
            throw new IllegalArgumentException("Place cannot be null!");
        }
         this.pathGraph.addVertex(local);

         return "O local foi adicionado com sucesso";
    }

    /**
     * Remove a location from the graph
     * @param place to be deleted 
     * @return string string that informs if the operation was performed successfully
     */
    @Override
    public String removeLocals (ILocal local){
        if (local == null) {
            throw new IllegalArgumentException("Place cannot be null!");
        }
        this.pathGraph.removeVertex(local);

        return "O local foi removido com sucesso";
    }

    /**
     * Add a new interaction of a player to the connector and remove the first one from the queue if the cooldown time has already passed
     * @param connector connector where there was interaction
     * @param interation information about the interaction to be added to the connector
     */
    @Override
    public String addInterationConnector (IConnector connector, ConnectorPlayerInteration interation){
        if (connector == null) {
            throw new IllegalArgumentException("Place cannot be null!");
        }
        if (connector == interation) {
            throw new IllegalArgumentException("Place cannot be null!");
        }

       

        return " Interação adicionada com sucesso com sucesso";
    }

    /**
     * Add new path between two points on graph.
     * @param local1 first location
     * @param local2 segundo local
     */
    @Override
    public String addPath(ILocal local1, ILocal local2){
        if (local1 == null || local2 == null) {
            throw new IllegalArgumentException("Place cannot be null!");
        }
        
        this.pathGraph.addEdge(local1, local2);

        return "O Caminho foi adicionado com sucesso";
    }

    /**
     * Gets the textual listing of all Portals
     * @return string with portals listing
     */
    @Override
    public String getAllPortalsListing() {
        String string = "Portals: {\n";
        if (this.pathGraph.getNumberOfPortals() != 0) {
            Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
            while (iteratorPortal.hasNext()) {
                string += iteratorPortal.next().toString() + "\n";
            }
        } else {
            string += "There is no Portals to list!\n";
        }
        string += "}";
        return string;
    }

    /**
     * Get a textual list with the Portals that are not conquered, that is, that are not associated with any team.
     * @return string with Portals that are not conquered.
     */
    @Override
    public String getPortalsWithoutTeamListing(){
        String string = "Portals: {\n";
        if (this.pathGraph.getNumberOfPortals() != 0) {
            Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
            while (iteratorPortal.hasNext()) {
                IPortal portal = iteratorPortal.next();
                if (portal.getTeamPlayer().equals("Neutro")){
                    string += iteratorPortal.next().toString() + "\n";
                }
            }
        } else {
            string += "There is no Portals to list!\n";
        }
        string += "}";
        return string;
    }

    /**
     * Get a textual list of Portals conquered by a specific player.
     * @param player owner of the portals.
     * @return string with all locations belonging to the player.
     */
    @Override
    public String getPortalsPlayerListing(IPlayer player){
        String string = "Portals: {\n";
        if (this.pathGraph.getNumberOfPortals() != 0) {
            Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
            while (iteratorPortal.hasNext()) {
                IPortal portal = iteratorPortal.next();
                if (portal.getOwnerPlayer().equals(player)){
                        string += iteratorPortal.next().toString() + "\n";
                }
            }
        } else {
            string += "There is no Portals to list!\n";
        }
        string += "}";
        return string;
    }

    /**
     * Get a textual list with the Portals conquered by a specific team.
     * @param team string with all locations belonging to the player.
     * @return string with all locations belonging to the team.
     */
    @Override
    public String getPortalsByTeamListing(String team){
        String string = "Portals: {\n";
        if (this.pathGraph.getNumberOfPortals() != 0) {
            Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
            while (iteratorPortal.hasNext()) {
                IPortal portal = iteratorPortal.next();
                if (portal.getTeamPlayer().equals(team)){
                        string += iteratorPortal.next().toString() + "\n";
                }
            }
        } else {
            string += "There is no Portals to list!\n";
        }
        string += "}";
        return string;
    }

    /**
     * Get a textual list ordered in descending order by the amount of energy the portal has.
     * @return string with the portals ordered by the amount of energy they have.
     */
    @Override
    public String getPortalsOrderedByEnergyItHasListing(){
       
    }

    /**
     * Gets the textual listing of all Connectors
     * @return string with connectors listing
     */
    @Override
    public String getAllConnectorsListing(){
        String string = "Connectors: {\n";
        if (this.pathGraph.getNumberOfConnectores() != 0) {
            Iterator<IConnector> iteratorConnectors = this.pathGraph.getConnectores();
            while (iteratorConnectors.hasNext()) {
                string += iteratorConnectors.next().toString() + "\n";
            }
        } else {
            string += "There is no Connector to list!\n";
        }
        string += "}";
        return string;
    }

    /**
     * Get a textual list of all connectors ordered in descending order by the amount of energy they have.
     * @return string with the connectors ordered according to the amount of energy they have.
     */
    @Override
    public String getConnectorsOrderedByEnergyItHasListing(){

    }


     /**
     * Put all the locals that are in the graph in a JSONArray
     *
     * @return the JSONArray with all the locals present on the graph
     */
    @SuppressWarnings("unchecked")
    private JSONArray getLocalsJSONArray() {
        JSONArray localsArray = new JSONArray();
        Iterator<ILocal> iteratorLocals = this.pathGraph.getLocals();
        while (iteratorLocals.hasNext()) {
            localsArray.add(iteratorLocals.next().localToJSONObject());
        }
        return localsArray;
    }

    /**
     * Put all the Portals that are in the graph in a JSONArray
     * @return the JSONArray with all the locals present on the graph
     */
    @SuppressWarnings("unchecked")
    private JSONArray getPortalsJSONArray() {
        JSONArray portalsArray = new JSONArray();
        Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
        while (iteratorPortal.hasNext()) {
            portalsArray.add(iteratorPortal.next().portalToJSONObject());
        }
        return portalsArray;
    }


    /**
     * Put all the Portals that are in the graph in a JSONArray
     * @return the JSONArray with all the locals present on the graph
     */
    @SuppressWarnings("unchecked")
    private JSONArray getConnectorsJSONArray() throws EmptyCollectionException {
        JSONArray connectorsArray = new JSONArray();
        Iterator<IConnector> iteratorConnectors = this.pathGraph.getConnectores();
        while (iteratorConnectors.hasNext()) {
            connectorsArray.add(iteratorConnectors.next().connectorToJSONObject());
        }
        return connectorsArray;
    }

    /**
     * Put all the Portals that are in the graph in a JSONArray
     * @return the JSONArray with all the locals present on the graph
     */
    @SuppressWarnings("unchecked")
    private JSONArray getRoutesJSONArray() {
        JSONArray routesArray = new JSONArray();
        Iterator<IRoute<ILocal>> iteratorRoute = this.pathGraph.getRoutes();
        while (iteratorRoute.hasNext()) {
            IRoute<ILocal> path = iteratorRoute.next();
            routesArray.add(routeToJSONObject(path));
        }
        return routesArray;
    }

     /**
     * Export all locals from a graph to a Json file
     * @throws IOException if occurs an error trying to write the file.
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public void exportAllLocalsToJson(String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this.getLocalsJSONArray());
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    /**
     * Export all portals from a graph to a Json file
     * @throws IOException if occurs an error trying to write the file.
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public void exportPortalsToJson(String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this.getPortalsJSONArray());
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.flush();
        writer.close();
    }
    

    /**
     * Export all Connectors from a graph to a Json file
     * @throws IOException if occurs an error trying to write the file.
     * @return A string indicating whether the operation was successful or something went wrong.
     */
    @Override
    public void  exportConnectorsToJson(String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this.getConnectorsJSONArray());
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    /**
     * Export all paths from a graph to a Json file
     * @throws IOException if occurs an error trying to write the file.
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public void exportPathsToJson(String fileName) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this.getRoutesJSONArray());
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    /**
     * Import locations from Json file to graph
     * @param fileName fileName to use for the import
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String importAllLocalsFromJSON(String fileName){

    }

    /**
     * Import paths from a Json file into a graph
     * @param fileName fileName fileName to use for the import
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String importPathsFromJSON(String fileName){
        
    }


}
