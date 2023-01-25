
package api.implementation;

import api.interfaces.IPlayerManagement;
import collections.exceptions.EmptyCollectionException;
import collections.exceptions.NonComparableElementException;
import collections.implementation.ArrayOrderedList;
import collections.implementation.ArrayUnorderedList;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        Player result = null;

        try {
            result = this.playerList.remove(player);
        } catch (EmptyCollectionException e) {
            return null;
        }

        return result;
    }

    @Override
    public String associatePlayerToTeam(Player player, String team) {
        if (!(this.playerList.contains(player))) {
            return "O jogador nao esta na lista de jogadores";
        }

        player.setTeam(team);

        return ("O jogador foi adicionado a equipa " + team);
    }

    @Override
    public String disassociatePlayerFromTeam(Player player) {
        if (!(this.playerList.contains(player))) {
            return "O jogador nao esta na lista de jogadores";
        }

        String team = player.getTeam();
        player.setTeam("");

        return ("O jogador foi removido da equipa " + team);
    }

    @Override
    public String listPlayersByTeam() {
        StringBuilder sb = new StringBuilder();
        ArrayUnorderedList<Player> tempListPlayersTeam1 = new ArrayUnorderedList<>();
        ArrayUnorderedList<Player>  tempListPlayersTeam2 = new ArrayUnorderedList<>();
        String team = this.playerList.get(0).getTeam();

        for (int i = 0; i < this.playerList.size(); i++) {
            if (team.equals(this.playerList.get(i).getTeam())) {
                tempListPlayersTeam1.addToRear(this.playerList.get(i));
            } else {
                tempListPlayersTeam2.addToRear(this.playerList.get(i));
            }
        }

        sb.append(tempListPlayersTeam1.toString()).append(tempListPlayersTeam2.toString());

        return sb.toString();
    }

    @Override
    public String listPlayersByLevel() {
        StringBuilder sb = new StringBuilder();
        ArrayOrderedList<Player> tempPlayersList = new ArrayOrderedList<>();
        int tempLevel = 0;

        for (int i = 0; i < this.playerList.size(); i++) {
            if (this.playerList.get(i).getLevel() <= tempLevel) {
                try {
                    tempPlayersList.add(this.playerList.get(i));
                } catch (NonComparableElementException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return sb.toString();
    }

    @Override
    public String listPlayersByPortalsConquered() {
        return null;
    }

    @Override
    public String importJSON(String fileName) {
        return null;
    }

    @Override
    public String exportJSON(String fileName) {
        return null;
    }
}
