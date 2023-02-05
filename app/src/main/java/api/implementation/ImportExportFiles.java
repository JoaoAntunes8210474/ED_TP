package api.implementation;

import api.app.Main;
import api.interfaces.IImportExportFiles;
import api.interfaces.ILocal;
import api.interfaces.IRoute;
import collections.exceptions.EmptyCollectionException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ImportExportFiles implements IImportExportFiles {

    /**
     * Calls the import method of all instances passed through reference
     * @param fileName of file to import from
     * @param playerList list of all players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importJSON(String fileName, PlayerManagement playerList, LocalsManagement pathGraph){
        StringBuilder mensagemSucesso = new StringBuilder();
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;

            // Importing players
            JSONArray playersArray = (JSONArray) jsonObject.get("players");
            for (Object object : playersArray) {
                ILocal currentLocation;
                JSONObject playerToCreate = (JSONObject) object;
                String name = (String) playerToCreate.get("name");
                String team = (String) playerToCreate.get("team");
                JSONObject currentLocationJSON = (JSONObject) playerToCreate.get("currentLocation");
                long amountEnergyItHasLong = (long) currentLocationJSON.get("amountEnergyItHas");
                long idLong = (long) currentLocationJSON.get("id");
                int id = (int) idLong;
                int amountEnergyItHas = (int) amountEnergyItHasLong;
                String nameLocal = (String) currentLocationJSON.get("name");
                JSONObject coordinatesJSON = (JSONObject) currentLocationJSON.get("coordinates");
                double longitude = (double) coordinatesJSON.get("longitude");
                double latitude = (double) coordinatesJSON.get("latitude");
                if (currentLocationJSON.get("localType").equals("Portal")) {
                    long maxEnergyLong = (long) currentLocationJSON.get("maxEnergy");
                    int maxEnergy = (int) maxEnergyLong;
                    currentLocation = new Portal(maxEnergy, id, nameLocal, amountEnergyItHas, new Coordinates(longitude, latitude));
                } else {
                    long cooldownLong = (long) currentLocationJSON.get("cooldown");
                    int cooldown = (int) cooldownLong;
                    currentLocation = new Connector(cooldown, id, nameLocal, amountEnergyItHas, new Coordinates(longitude, latitude));
                }

                long levelLong = (long) playerToCreate.get("level");
                long experiencePointsLong = (long) playerToCreate.get("experiencePoints");
                long currentEnergyLong = (long) playerToCreate.get("currentEnergy");
                long numPortalsConqueredLong = (long) playerToCreate.get("numPortalsConquered");
                int level = (int) levelLong;
                int experiencePoints = (int) experiencePointsLong;
                int currentEnergy = (int) currentEnergyLong;
                int numPortalsConquered = (int) numPortalsConqueredLong;
                Player player = new Player(name, team, level, experiencePoints, currentEnergy, numPortalsConquered);
                player.setCurrentLocation(currentLocation);
                playerList.addPlayer(player);
            }

            // Importing portals and connectors
            JSONArray localsArray = (JSONArray) jsonObject.get("locals");
            for (Object o : localsArray) {
                JSONObject localsToCreate = (JSONObject) o;
                ILocal local;
                long idLong = (long) localsToCreate.get("id");
                long amountEnergyItHasLong = (long) localsToCreate.get("amountEnergyItHas");
                int id = (int) idLong;
                String name = (String) localsToCreate.get("name");
                int amountEnergyItHas = (int) amountEnergyItHasLong;
                JSONObject coordinatesJSON = (JSONObject) localsToCreate.get("coordinates");
                double longitude = (double) coordinatesJSON.get("longitude");
                double latitude = (double) coordinatesJSON.get("latitude");
                if (localsToCreate.get("localType").equals("Portal")) {
                    long maxEnergyLong = (long) localsToCreate.get("maxEnergy");
                    int maxEnergy = (int) maxEnergyLong;
                    local = new Portal(maxEnergy, id, name, amountEnergyItHas, new Coordinates(longitude, latitude));
                } else {
                    long cooldownLong = (long) localsToCreate.get("cooldown");
                    int cooldown = (int) cooldownLong;
                    local = new Connector(cooldown, id, name, amountEnergyItHas, new Coordinates(longitude, latitude));
                }
                pathGraph.addLocals(local);
            }

            // Importing routes
            JSONArray routesArray = (JSONArray) jsonObject.get("routes");
            ILocal from = null;
            ILocal to = null;
            for (Object o : routesArray) {
                JSONObject routesToCreate = (JSONObject) o;
                long fromPortalsIdLong = (long) routesToCreate.get("from");
                long toPortalsIdLong = (long) routesToCreate.get("to");
                int fromPortalsId = (int) fromPortalsIdLong;
                int toPortalsId = (int) toPortalsIdLong;
                for (int i = 0; i < pathGraph.getPathGraph().size(); i++) {
                    if (pathGraph.getPathGraph().get(i).getId() == fromPortalsId) {
                        from = pathGraph.getPathGraph().get(i);
                    }
                    if (pathGraph.getPathGraph().get(i).getId() == toPortalsId) {
                        to = pathGraph.getPathGraph().get(i);
                    }
                }
                Route route = new Route(from, to);
                pathGraph.addPath(route);
            }

        } catch (ParseException e) {
            return "Houve um problema a fazer o import dos jogadores";
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        mensagemSucesso.append("Importacao do ficheiro " + fileName + " efetuada com sucesso.");

        return mensagemSucesso.toString();
    }

    /**
     * Calls the export method of all instances passed through reference
     * @param fileName of file to export into
     * @param playerList list of all players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String exportJSON(String fileName, PlayerManagement playerList, LocalsManagement pathGraph, FileWriter fileWriter) {
        StringBuilder mensagemSucesso = new StringBuilder();

        JSONArray jsonArray = new JSONArray();
        JSONArray playerArray = playerList.getPlayersAsJSONArray();
        JSONArray portalArray = pathGraph.getPortalsJSONArray();
        JSONArray connectorArray = pathGraph.getConnectorsJSONArray();
        JSONArray pathArray = pathGraph.getRoutesJSONArray();

        for (Object o : portalArray) {
            jsonArray.add(o);
        }

        for (Object o : connectorArray) {
            jsonArray.add(o);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("players", playerArray);
        jsonObject.put("locals", jsonArray);
        jsonObject.put("routes", pathArray);

        Gson beautifyJson = new GsonBuilder().setPrettyPrinting().create();
        String json = beautifyJson.toJson(jsonObject);

        try {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            return e.getMessage();
        }

        mensagemSucesso.append("Exportacao para o ficheiro " + fileName + " efetuada com sucesso.");

        return mensagemSucesso.toString();
    }

    
  
}