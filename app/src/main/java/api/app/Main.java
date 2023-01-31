package api.app;

import api.exceptions.NotPlaceInstanceException;
import api.implementation.ImportExportFiles;
import api.implementation.LocalsManagement;
import api.implementation.Player;
import api.implementation.PlayerManagement;
import api.interfaces.*;
import collections.implementation.ArrayUnorderedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    /**
     * Method that gets the option selected by the user
     * @return option selected by the user
     */
    private static int getOptionInput() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            option = scanner.nextInt();
        } while (option < 0 || option > 9);

        return option;
    }

    /**
     * Method that checks if the game can end
     * @param playerManagement list of players
     * @return true if the game can end, false otherwise
     */
    private static boolean checkIfGameCanEnd(@NotNull PlayerManagement playerManagement) {
        int numPortalsTeamSparks = 0;
        int numPortalsTeamGiants = 0;

        for (IPlayer player : playerManagement.getPlayerList()) {
            if (player.getTeam().equals("Sparks")) {
                numPortalsTeamSparks += player.getNumPortals();
            } else {
                numPortalsTeamGiants += player.getNumPortals();
            }
        }

        return numPortalsTeamSparks == 5 || numPortalsTeamGiants == 5;
    }

    /**
     * Method that saves the game state to a file
     */
    private static void saveGameState(PlayerManagement playerManagement, LocalsManagement localsManagement, int playerTurn) throws IOException {
        IImportExportFiles importExportFiles = new ImportExportFiles();

        importExportFiles.exportJSON("files/Game.json", playerManagement, localsManagement);
        JSONObject playerTurnJSON = new JSONObject();
        playerTurnJSON.put("playerTurn", playerTurn);

        Gson playerTurnGson = new GsonBuilder().setPrettyPrinting().create();
        String playerTurnJson = playerTurnGson.toJson(playerTurnJSON);

        FileWriter fileWriter = new FileWriter("files/Game.json");
        fileWriter.write(playerTurnJson);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Method that starts the game until it is closed by the user
     * @param playerManagement list of players
     * @param localsManagement list of locals
     */
    private static void gameStart(PlayerManagement playerManagement, LocalsManagement localsManagement) {
        int playerTurn = 0;
        boolean gameEnd = false;
        int indiceLista = 0;
        int option;

        while (!(gameEnd)) {
            try {
                // Get the neighbours of the current location of the player
                ArrayUnorderedList<ILocal> neighbours = localsManagement.getPathGraph().getNeighbours(playerManagement.getPlayerList().get(playerTurn).getCurrentLocation());

                System.out.println("Turno do jogador " + playerManagement.getPlayerList().get(playerTurn).getName());

                // Display the possible actions
                System.out.println("Possíveis ações: ");
                System.out.println("0 - Sair do jogo e guardar o progresso");
                // If a player is in a portal, he can conquer it, attack it, reinforce it or visit a neighbour
                if (playerManagement.getPlayerList().get(playerTurn).getCurrentLocation() instanceof IPortal) {
                    System.out.println("1 - Conquistar portal");
                    System.out.println("2 - Atacar portal");
                    System.out.println("3 - Reforçar portal");

                    for (int i = 0; i < Math.min(neighbours.size() - (indiceLista * 4), 4); i++) {
                        System.out.println((3 + (i + 1)) + " - Visitar local: " + neighbours.get(i).getName());
                    }

                    System.out.println("8 - Mudar para a próxima página de locais a visitar");
                    System.out.println("9 - Mudar para a página anterior de locais a visitar");
                    System.out.println("--------------------");
                    System.out.println("Indique a ação que pretende realizar: ");

                    // Get the option selected by the user
                    option = Main.getOptionInput();

                    // Execute the action selected by the user
                    switch (option) {
                        case 0:
                            gameEnd = true;
                            Main.saveGameState(playerManagement, localsManagement, playerTurn);
                            break;
                        case 1:
                            // TODO: Conquer portal (if possible) method implementation
                            playerManagement.getPlayerList().get(playerTurn).conquerPortal();
                            break;
                        case 2:
                            // TODO: Attack portal (if possible) method implementation
                            playerManagement.getPlayerList().get(playerTurn).attackPortal();
                            break;
                        case 3:
                            // TODO: Reinforce portal (if possible) method implementation
                            playerManagement.getPlayerList().get(playerTurn).reinforcePortal();
                            break;
                        case 8:
                            indiceLista++;
                            break;
                        case 9:
                            indiceLista--;
                            break;
                        default:
                            playerManagement.getPlayerList().get(playerTurn).setCurrentLocation(neighbours.get((indiceLista * 4) + (option - 4)));
                            break;
                    }

                    // Change the turn to the next player in the list of players
                    // If the player is the last in the list, the turn goes to the first player
                    playerTurn = (playerTurn + 1) % playerManagement.getPlayerList().size();
                } else {
                    // If a player is in a connector, he can recharge his energy or visit a neighbour
                    System.out.println("1 - Recarregar energia");

                    for (int i = 0; i < Math.min(neighbours.size() - (indiceLista * 6), 6); i++) {
                        System.out.println((1 + (i + 1)) + " - Visitar local: " + neighbours.get(i).getName());
                    }

                    System.out.println("8 - Mudar para a próxima página de locais a visitar");
                    System.out.println("9 - Mudar para a página anterior de locais a visitar");
                    System.out.println("--------------------");
                    System.out.println("Indique a ação que pretende realizar: ");

                    // Get the option selected by the user
                    option = Main.getOptionInput();

                    // Execute the action selected by the user
                    switch (option) {
                        case 0:
                            gameEnd = true;
                            Main.saveGameState(playerManagement, localsManagement, playerTurn);
                            break;
                        case 1:
                            // TODO: recharge energy (if not on cooldown) method implementation
                            playerManagement.getPlayerList().get(playerTurn).rechargeEnergy();
                            break;
                        case 8:
                            indiceLista++;
                            break;
                        case 9:
                            indiceLista--;
                            break;
                        default:
                            playerManagement.getPlayerList().get(playerTurn).setCurrentLocation(neighbours.get((indiceLista * 6) + (option - 2)));
                            break;
                    }

                    // Change the turn to the next player in the list of players
                    // If the current player is the last one in the list, the turn goes to the first player
                    playerTurn = (playerTurn + 1) % playerManagement.getPlayerList().size();
                }
            } catch (NotPlaceInstanceException e) {
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Check if the game can end
            // If the game can end, the game ends
            gameEnd = Main.checkIfGameCanEnd(playerManagement);
        }

    }

    public static void main(String[] args) {
        IImportExportFiles importExportFiles = new ImportExportFiles();

        IPlayerManagement playerManagement = new PlayerManagement();

        ILocalsManagement localsManagement = new LocalsManagement();

        Player player = new Player("Joaquim", "Giants");
        Player player1 = new Player("Test1", "Sparks");
        Player player2 = new Player("Test2", "Sparks");
        Player player3 = new Player("Test3", "Sparks");
        Player player4 = new Player("Test4", "Sparks");
        Player player5 = new Player("Test5", "Sparks");
        Player player6 = new Player("Test6", "Sparks");

        playerManagement.addPlayer(player);
        playerManagement.addPlayer(player1);
        playerManagement.addPlayer(player2);
        playerManagement.addPlayer(player3);
        playerManagement.addPlayer(player4);
        playerManagement.addPlayer(player5);
        playerManagement.addPlayer(player6);

        importExportFiles.exportJSON("files/ExportTest.json", (PlayerManagement) playerManagement, (LocalsManagement) localsManagement);

        System.out.println("Deseja continuar o jogo anterior ou começar um jogo novo? (1 - Continuar, 2 - Novo jogo)");
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            option = scanner.nextInt();

            if (option != 1 && option != 2) {
                System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option != 1 && option != 2);

        switch (option) {
            case 1:
                importExportFiles.importJSON("files/ExportTest.json", (PlayerManagement) playerManagement, (LocalsManagement) localsManagement);
                Main.gameStart((PlayerManagement) playerManagement, (LocalsManagement) localsManagement);
                break;
            case 2:
                Main.gameStart((PlayerManagement) playerManagement, (LocalsManagement) localsManagement);
                break;
        }
    }
}
