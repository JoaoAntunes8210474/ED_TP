package api.implementation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LocalsManagement implements ILocalsManagement {

    /**
     * Network graph that have information about the locals and path between them.
     */
    private IPathGameGraphADT<ILocal> pathGraph;


    /**
     * Constructor
     */
    public LocalsManagement() {
        this.pathGraph = new PathGameGraph<>();
    }

    public IPathGameGraphADT<ILocal> getPathGraph() {
        return this.pathGraph;
    }

    /**
     * Adds a new location to the graph
     *
     * @param local to be added.
     * @return string that informs if the operation was performed successfully
     */
    @Override
    public String addLocals(ILocal local) {
        if (local == null) {
            throw new NullPointerException("Place cannot be null!");
        }
        this.pathGraph.addVertex(local);

        return "O local foi adicionado com sucesso";
    }

    /**
     * Remove a location from the graph
     *
     * @param local to be deleted
     * @return String that informs if the operation was performed successfully
     */
    @Override
    public String removeLocals(ILocal local) {
        if (local == null) {
            throw new NullPointerException("Place cannot be null!");
        }

        this.pathGraph.removeVertex(local);

        return "O local foi removido com sucesso";
    }

    /**
     * Add a new interaction of a player to the connector and remove the first one from the queue if the cooldown time has already passed
     *
     * @param connector connector where there was interaction
     * @param iteration information about the interaction to be added to the connector
     */
    @Override
    public String addInterationConnector(IConnector connector, ConnectorPlayerInteration iteration) {
        if (connector == null) {
            throw new IllegalArgumentException("Place cannot be null!");
        }
        if (connector == iteration) {
            throw new IllegalArgumentException("Place cannot be null!");
        }


        return " Interação adicionada com sucesso com sucesso";
    }

    /**
     * Add new path between two points on graph.
     *
     * @param route to be added.
     */
    @Override
    public String addPath(IRoute route) {
        if (route.getFrom() == null || route.getTo() == null) {
            throw new NullPointerException("Place cannot be null!");
        }

        this.pathGraph.addEdge((ILocal) route.getFrom(), (ILocal) route.getTo());

        return "O caminho foi adicionado com sucesso";
    }

    @Override
    public String removePath(IRoute route) {
        if (route.getFrom() == null && route.getTo() == null) {
            throw new NullPointerException("Place cannot be null!");
        }

        this.pathGraph.removeEdge((ILocal) route.getFrom(), (ILocal) route.getTo());

        return "O caminho foi removido  com sucesso";
    }


    /**
     * Gets the textual listing of all Portals
     *
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
     *
     * @return string with Portals that are not conquered.
     */
    @Override
    public String getPortalsWithoutTeamListing() {
        String string = "Portals: {\n";
        if (this.pathGraph.getNumberOfPortals() != 0) {
            Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
            while (iteratorPortal.hasNext()) {
                IPortal portal = iteratorPortal.next();
                if (portal.getPlayerTeam().equals("NEUTRAL")) {
                    string += portal + "\n";
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
     * @return
     */
    @Override
    public String getPortalsPlayerListing(IPlayer player) {
        if (player == null) {
            throw new NullPointerException("Player cannot be null!");
        }

        String string = "Portals: {\n";
        if (this.pathGraph.getNumberOfPortals() != 0) {
            Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
            while (iteratorPortal.hasNext()) {
                IPortal portal = iteratorPortal.next();
                if (portal.getOwnerPlayer() == null) {
                    break;
                }

                if (portal.getOwnerPlayer().equals(player)) {
                    string += portal + "\n";
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
     *
     * @param team string with all locations belonging to the player.
     * @return string with all locations belonging to the team.
     */
    @Override
    public String getPortalsByTeamListing(String team) {
        String string = "Portals: {\n";
        if (this.pathGraph.getNumberOfPortals() != 0) {
            Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
            while (iteratorPortal.hasNext()) {
                IPortal portal = iteratorPortal.next();
                if (portal.getPlayerTeam().equals(team)) {
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
     *
     * @return string with the portals ordered by the amount of energy they have.
     */
    @Override
    public String getPortalsOrderedByEnergyItHasListing() {
        String string = "Portals: {\n";
        int numberOfPortals = this.pathGraph.getNumberOfPortals();
        IPortal[] portals = new IPortal[numberOfPortals];
        int i = 0;
        if (numberOfPortals != 0) {
            Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
            while (iteratorPortal.hasNext()) {
                portals[i++] = iteratorPortal.next();
            }
        } else {
            string += "There is no Portals to list!\n";
            return string + "}";
        }
        for (int j = 0; j < portals.length; j++) {
            for (int k = 0; k < portals.length - j - 1; k++) {
                if (k >= 0 && k + 1 < portals.length && portals[k].getAmountEnergyItHas() > portals[k + 1].getAmountEnergyItHas()) {
                    IPortal temp = portals[k];
                    portals[k] = portals[k + 1];
                    portals[k + 1] = temp;
                }
            }
        }
        for (IPortal portal : portals) {
            string += portal.toString() + "\n";
        }
        string += "}";
        return string;
        }




    /**
     * Gets the textual listing of all Connectors
     *
     * @return string with connectors listing
     */
    @Override
    public String getAllConnectorsListing() {
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
     *
     * @return string with the connectors ordered according to the amount of energy they have.
     */
    @Override
    public String getConnectorsOrderedByEnergyItHasListing() {
        String string = "Connectors: {\n";
        IConnector[] connectors = new IConnector[this.pathGraph.getNumberOfConnectores()];
        int i = 0;
        if (this.pathGraph.getNumberOfPortals() != 0) {
            Iterator<IConnector> iteratorConnectors = this.pathGraph.getConnectores();
            while (iteratorConnectors.hasNext()) {
                connectors[i++] = iteratorConnectors.next();
            }
        } else {
            string += "There is no Portals to list!\n";
        }
        for (int j = 0; j < connectors.length; j++) {
            for (int k = 0; k < connectors.length - j - 1; k++) {
                if (connectors[k].getAmountEnergyItHas() > connectors[k + 1].getAmountEnergyItHas()) {
                    IConnector temp = connectors[k];
                    connectors[k] = connectors[k + 1];
                    connectors[k + 1] = temp;
                }
            }
        }
        for (IConnector connector : connectors) {
            string += connector.toString() + "\n";
        }
        string += "}";
        return string;
    }


    /**
     * Put all the Portals that are in the graph in a JSONArray
     *
     * @return the JSONArray with all the locals present on the graph
     */
    @SuppressWarnings("unchecked")
    protected JSONArray getPortalsJSONArray() {
        JSONArray portalsArray = new JSONArray();
        Iterator<IPortal> iteratorPortal = this.pathGraph.getPortals();
        while (iteratorPortal.hasNext()) {
            portalsArray.add(iteratorPortal.next().portalToJSONObject());
        }
        return portalsArray;
    }


    /**
     * Put all the Portals that are in the graph in a JSONArray
     *
     * @return the JSONArray with all the locals present on the graph
     */
    @SuppressWarnings("unchecked")
    protected JSONArray getConnectorsJSONArray() {
        JSONArray connectorsArray = new JSONArray();
        Iterator<IConnector> iteratorConnectors = this.pathGraph.getConnectores();
        while (iteratorConnectors.hasNext()) {
            connectorsArray.add(iteratorConnectors.next().connectorToJSONObject());
        }
        return connectorsArray;
    }

    /**
     * Put all the Portals that are in the graph in a JSONArray
     *
     * @return the JSONArray with all the locals present on the graph
     */
    @SuppressWarnings("unchecked")
    protected JSONArray getRoutesJSONArray() {
        JSONArray routesArray = new JSONArray();
        Iterator<IRoute<ILocal>> iteratorRoute = this.pathGraph.getRoutes();
        while (iteratorRoute.hasNext()) {
            IRoute<ILocal> path = iteratorRoute.next();
            routesArray.add(path.routeToJSONObject(path));
        }
        return routesArray;
    }


    /**
     * Export all portals from a graph to a Json file
     *
     * @return A string indicating whether the operation was successful or something went wrong
     * @throws IOException if occurs an error trying to write the file.
     */
    @Override
    public String exportPortalsToJson(String fileName) throws IOException {
        if (fileName.trim().equals("") || Files.notExists(Paths.get(fileName))) {
            throw new IOException("O ficheiro em que estava a tentar escrever nao existe");
        }

        JSONArray portalsJSONArray = this.getPortalsJSONArray();
        JSONObject portals = new JSONObject();
        portals.put("portals", portalsJSONArray);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(portals);
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.flush();

        return "O export foi feito com sucesso";
    }


    /**
     * Export all Connectors from a graph to a Json file
     *
     * @return A string indicating whether the operation was successful or something went wrong.
     * @throws IOException if occurs an error trying to write the file.
     */
    @Override
    public String exportConnectorsToJson(String fileName) throws IOException {
        if (fileName.trim().equals("") || Files.notExists(Paths.get(fileName))) {
            throw new IOException("O ficheiro em que estava a tentar escrever nao existe");
        }

        JSONArray connectorsJSONArray = this.getConnectorsJSONArray();
        JSONObject connectors = new JSONObject();

        connectors.put("connectors", connectorsJSONArray);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(connectors);
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.flush();

        return "O export foi feito com sucesso";
    }

    /**
     * Export all paths from a graph to a Json file
     *
     * @return A string indicating whether the operation was successful or something went wrong
     * @throws IOException if occurs an error trying to write the file.
     */
    @Override
    public String exportPathsToJson(String fileName) throws IOException {
        if (fileName.trim().equals("") || Files.notExists(Paths.get(fileName))) {
            throw new IOException("O ficheiro em que estava a tentar escrever nao existe");
        }

        JSONArray pathsJSONArray = this.getRoutesJSONArray();
        JSONObject paths = new JSONObject();

        paths.put("paths", pathsJSONArray);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(paths);
        FileWriter writer = new FileWriter(fileName);
        writer.write(json);
        writer.flush();

        return "O export foi feito com sucesso";
    }

    /**
     * Import locations from Json file to graph
     *
     * @param fileName fileName to use for the import
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String importPortalsFromJSON(String fileName) throws IOException {
        if (fileName.trim().equals("") || Files.notExists(Paths.get(fileName))) {
            throw new IOException("O ficheiro em que estava a tentar escrever nao existe");
        }
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("locals");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject localsToCreate = (JSONObject) jsonArray.get(i);
                if (localsToCreate.get("localType").equals("Portal")) {
                    long amountEnergyItHasLong = (long) localsToCreate.get("amountEnergyItHas");
                    long idLong = (long) localsToCreate.get("id");
                    int id = (int) idLong;
                    String name = (String) localsToCreate.get("name");
                    int amountEnergyItHas = (int) amountEnergyItHasLong;
                    JSONObject coordenadasJSON = (JSONObject) localsToCreate.get("coordinates");
                    double longitude = (double) coordenadasJSON.get("longitude");
                    double latitude = (double) coordenadasJSON.get("latitude");
                    Coordinates coordenadas = new Coordinates(longitude, latitude);
                    long maxEnergyLong = (long) localsToCreate.get("maxEnergy");
                    int maxEnergy = (int) maxEnergyLong;
                    Portal portal = new Portal(maxEnergy, id, name, amountEnergyItHas, coordenadas);
                    this.pathGraph.addVertex(portal);
                }
            }
        } catch (ParseException e) {
            return "Houve um problema a fazer o import dos portais";
        }
        return "O import foi feito com sucesso";
    }

    /**
     * Import connectors from Json file to graph
     *
     * @param fileName fileName to use for the import
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importConnectorsFromJSON(String fileName) throws IOException {
        if (fileName.trim().equals("") || Files.notExists(Paths.get(fileName))) {
            throw new IOException("O ficheiro em que estava a tentar escrever nao existe");
        }
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("locals");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject localsToCreate = (JSONObject) jsonArray.get(i);
                if (localsToCreate.get("localType").equals("Connector")) {
                    long idLong = (long) localsToCreate.get("id");
                    long amountEnergyItHasLong = (long) localsToCreate.get("amountEnergyItHas");
                    int id = (int) idLong;
                    String name = (String) localsToCreate.get("name");
                    int amountEnergyItHas = (int) amountEnergyItHasLong;
                    JSONObject coordenadasJSON = (JSONObject) localsToCreate.get("coordinates");
                    double longitude = (double) coordenadasJSON.get("longitude");
                    double latitude = (double) coordenadasJSON.get("latitude");
                    Coordinates coordenadas = new Coordinates(longitude, latitude);
                    long cooldownLong = (long) localsToCreate.get("cooldown");
                    int cooldown = (int) cooldownLong;
                    Connector connector = new Connector(cooldown, id, name, amountEnergyItHas, coordenadas);
                    this.pathGraph.addVertex(connector);
                }
            }
        } catch (ParseException e) {
            return "Houve um problema a fazer o import dos connectores";
        }
        return "O import foi feito com sucesso";
    }

    /**
     * Import paths from a Json file into a graph
     *
     * @param fileName to use for the import
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String importPathsFromJSON(String fileName) throws IOException {
        if (fileName.trim().equals("") || Files.notExists(Paths.get(fileName))) {
            throw new IOException("O ficheiro em que estava a tentar escrever nao existe");
        }
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray routesArray = (JSONArray) jsonObject.get("routes");
            ILocal from = null;
            ILocal to = null;
            for (Object o : routesArray) {
                JSONObject routesToCreate = (JSONObject) o;
                long fromPortalsIdLong = (long) routesToCreate.get("from");
                long toPortalsIdLong = (long) routesToCreate.get("to");
                int fromPortalsId = (int) fromPortalsIdLong;
                int toPortalsId = (int) toPortalsIdLong;
                for (int i = 0; i < this.pathGraph.size(); i++) {
                    if (this.pathGraph.get(i).getId() == fromPortalsId) {
                        from = this.pathGraph.get(i);
                    }
                    if (this.pathGraph.get(i).getId() == toPortalsId) {
                        to = this.pathGraph.get(i);
                    }
                }
                Route route = new Route(from, to);
                this.addPath(route);
            }
        } catch (ParseException e) {
            return "Houve um problema a fazer o import das rotas";
        }
        return "O import foi feito com sucesso";
    }
}