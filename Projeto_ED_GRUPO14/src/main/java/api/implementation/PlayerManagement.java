
package api.implementation;

import api.interfaces.IPlayerManagement;
import collections.exceptions.ElementNotFoundException;
import collections.exceptions.EmptyCollectionException;
import collections.exceptions.NonComparableElementException;
import collections.implementation.ArrayOrderedList;
import collections.implementation.ArrayUnorderedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author reginaneto
 */
public class PlayerManagement implements IPlayerManagement {
    private ArrayUnorderedList<Player> playerList;

    public PlayerManagement() {
        this.playerList = new ArrayUnorderedList<>();
    }

    public ArrayUnorderedList<Player> getPlayerList() {
        return this.playerList;
    }

    /**
     * Adds a player to the list of players
     * @param player the player to be added into the list of players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String addPlayer(Player player) {
        if (player == null) {
            throw new NullPointerException("Foi enviado uma referência nula");
        }

        if (player.getClass() != Player.class) {
            throw new IllegalArgumentException("Nao foi enviado um jogador");
        }

        this.playerList.addToFront(player);

        return "O jogador foi adicionado a lista de jogadores!";
    }

    /**
     * Updates a player from the list of players
     * @param player the player to be updated
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String updatePlayer(Player player) {
        if (player == null) {
            throw new NullPointerException("Foi enviado uma referência nula");
        }

        if (player.getClass() != Player.class) {
            throw new IllegalArgumentException("Nao foi enviado um jogador");
        }

        if (!(this.playerList.contains(player))) {
            throw new ElementNotFoundException("O jogador nao esta na lista de jogadores");
        }

        int option;
        Scanner scanner = new Scanner(System.in);

        System.out.println("[Selecione um dos campos para alterar]");
        System.out.println("[1 - Nome]");
        System.out.println("[2 - Equipa]");

        do {
            option = scanner.nextInt();

            if (option < 1 || option > 2) {
                System.out.println("[Possiveis valores de selecao] \n [- 1] \n [- 2]");
            }
        } while (option < 1 || option > 2);

        scanner.reset();

        if (option == 1) {
            String oldName = player.getName();
            String newName;

            System.out.println("Escreva o novo nome do jogador");
            newName = scanner.nextLine();
            scanner.close();

            player.setName(newName);

            return ("O nome do jogador " + oldName + " foi mudado para " + newName);
        } else {
            String oldTeam = player.getTeam();
            String newTeam;
            System.out.println("Insira o nome da equipa que quer colocar o jogador");
            do {
                newTeam = scanner.nextLine();
                if (newTeam.equals("Giants") || newTeam.equals("Sparks")) {
                    System.out.println("[Possiveis Equipas] \n [- Giants] \n [- Sparks]");
                }
            } while (newTeam.equals("Giants") || newTeam.equals("Sparks"));

            scanner.close();

            return ("A equipa do jogador " + player.getName() + " foi mudada da " + oldTeam + " para a " + newTeam);
        }
    }

    /**
     * Removes and returns a player from the list of players
     * @param player the player to be removed
     * @return the removed player
     */
    @Override
    public Player removePlayer(Player player) {
        if (player == null) {
            throw new NullPointerException("Foi enviado uma referência nula");
        }

        if (player.getClass() != Player.class) {
            throw new IllegalArgumentException("Nao foi enviado um jogador");
        }

        if (!(this.playerList.contains(player))) {
            throw new ElementNotFoundException("O jogador nao esta na lista de jogadores");
        }

        Player result = null;

        try {
            result = this.playerList.remove(player);
        } catch (EmptyCollectionException e) {
            return null;
        }

        return result;
    }

    /**
     * Associates a player to a team
     * @param player to associate to a team
     * @param team to associate the player into
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String associatePlayerToTeam(Player player, String team) {
        if (!(this.playerList.contains(player))) {
            return "O jogador nao esta na lista de jogadores";
        }

        player.setTeam(team);

        return ("O jogador foi adicionado a equipa " + team);
    }

    /**
     * Disassociates a player from a team
     * @param player to disassociate from a team
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String disassociatePlayerFromTeam(Player player) {
        if (!(this.playerList.contains(player))) {
            return "O jogador nao esta na lista de jogadores";
        }

        String team = player.getTeam();
        player.setTeam("");

        return ("O jogador foi removido da equipa " + team);
    }

    /**
     * List players by their team
     * @return a String of the players organized by their team
     */
    @Override
    public String listPlayersByTeam() {
        StringBuilder sb = new StringBuilder();
        ArrayUnorderedList<Player> tempListPlayersTeam = new ArrayUnorderedList<>();
        String team = this.playerList.get(0).getTeam();

        for (int i = 0; i < this.playerList.size(); i++) {
            if (team.equals(this.playerList.get(i).getTeam())) {
                tempListPlayersTeam.addToFront(this.playerList.get(i));
            } else {
                tempListPlayersTeam.addToRear(this.playerList.get(i));
            }
        }

        sb.append(tempListPlayersTeam.toString());

        return sb.toString();
    }

    /**
     * List players by their level
     * @return a String of the players organized by their level
     */
    @Override
    public String listPlayersByLevel() {
        StringBuilder sb = new StringBuilder();
        ArrayOrderedList<Player> tempPlayersList = new ArrayOrderedList<>();

        int tempLevel = 0;

        for (int i = 0; i < this.playerList.size(); i++) {
            if (this.playerList.get(i).getLevel() <= tempLevel) {
                try {
                    tempPlayersList.add(this.playerList.get(i));
                } catch (NonComparableElementException e) {}
            }
        }

        sb.append(tempPlayersList.toString());

        return sb.toString();
    }

    /**
     * List players by the number of portals they've conquered
     * @return a String of the players organized by the number of portals they've conquered
     */
    @Override
    public String listPlayersByPortalsConquered() {
        int[] countingArray = new int[6];

        for (Player player : playerList) {
            countingArray[player.getNumPortals()]++;
        }

        ArrayUnorderedList<Player> tempPlayersList = new ArrayUnorderedList<>();

        for (int i = 0; i < countingArray.length; i++) {
            for (int j = 0; j < countingArray[i]; j++) {
                for (Player player : playerList) {
                    if (player.getNumPortals() == i) {
                        tempPlayersList.addToRear(player);
                    }
                }
            }
        }

        return tempPlayersList.toString();
    }

    /**
     * Import the content of the JSON given through reference into an instance of this class
     * @param fileName to use for the import
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String importJSON(String fileName) throws IOException{
        if (fileName.trim().equals("") || Files.notExists(Paths.get(fileName))) {
            throw new IOException("O ficheiro em que estava a tentar escrever nao existe");
        }

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("players");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject playerToCreate = (JSONObject) jsonArray.get(i);
                String name = (String) playerToCreate.get("name");
                String team = (String) playerToCreate.get("team");
                int level = (int) playerToCreate.get("level");
                int experiencePoints = (int) playerToCreate.get("experiencePoints");
                int currentEnergy = (int) playerToCreate.get("currentEnergy");
                Player player = new Player (name, team, level, experiencePoints, currentEnergy, 0, 0);
                this.playerList.addToRear(player);
            }
        } catch (ParseException e) {
            return "Houve um problema a fazer o import dos jogadores";
        }

        return "O import foi feito com sucesso";
    }

    /**
     * Exports the content of this class into a specified JSON file with the name given through reference
     * @param fileName to use for the export
     * @return A string indicating whether the operation was successful or something went wrong
     */
    @Override
    public String exportJSON(String fileName) throws IOException{
        if (fileName.trim().equals("") || Files.notExists(Paths.get(fileName))) {
            throw new IOException("O ficheiro em que estava a tentar escrever nao existe");
        }

        JSONArray playersArray = new JSONArray();

        for (Player player : this.playerList) {
            JSONObject playerObject = new JSONObject();
            playerObject.put("name", player.getName());
            playerObject.put("team", player.getTeam());
            playerObject.put("level", player.getLevel());
            playerObject.put("experiencePoints", player.getExperiencePoints());
            playerObject.put("currentEnergy", player.getCurrentEnergy());
            playersArray.add(playerObject);
        }

        FileWriter writer = new FileWriter(fileName);
        writer.write("players" + playersArray.toJSONString());
        writer.flush();
        writer.close();

        return "O export foi feito com sucesso";

    }
}
