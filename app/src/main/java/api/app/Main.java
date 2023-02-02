package api.app;

import api.exceptions.NotPlaceInstanceException;
import api.implementation.*;
import api.interfaces.*;
import collections.implementation.ArrayUnorderedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    private static final int COOLDOWN = 3;
    private static final int NUM_ACTIONS_CONNECTOR = 1;

    private static final int NUM_ACTIONS_PORTAL = 3;

    private static final int NUM_AVAILABLE_ACTIONS = 7;

    private static final int NUM_PORTALS_TO_WIN = 5;

    private static LocalTime gameTimer = LocalTime.now();
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

        scanner.close();

        return option;
    }

    private static int getEnergyInputPortal(Player currentPlayer, Portal currentPlayerLocation) {
        Scanner scanner = new Scanner(System.in);
        int energy;
        System.out.println("The amount of energy you have is: " + currentPlayer.getCurrentEnergy());
        System.out.println("Portal's current energy: [" + currentPlayerLocation.getAmountEnergyItHas() + "/" + currentPlayerLocation.getMaxEnergy() + "]");
        System.out.println("Insert the amount of energy you want to use to attack the portal: ");

        do {
            energy = scanner.nextInt();
        } while (energy > currentPlayer.getCurrentEnergy() || energy < 0);

        scanner.close();

        return energy;
    }

    /**
     * Method that checks if the player's current location is a portal
     * @return true if the player's current location is a portal, false otherwise
     */
    private static boolean checkIfPlayerLocationIsPortal(Player currentPlayer) {
        return currentPlayer.getCurrentLocation() instanceof IPortal;
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

        return numPortalsTeamSparks >= NUM_PORTALS_TO_WIN || numPortalsTeamGiants >= NUM_PORTALS_TO_WIN;
    }

    @SuppressWarnings("unchecked")
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
        boolean currentLocationIsPortal;
        boolean playerTurnEnded = false;
        Player currentPlayer;

        int indiceLista = 0;
        int option;

        int numberOfAvailableActionsForPortals = NUM_AVAILABLE_ACTIONS - NUM_ACTIONS_PORTAL;
        int numberOfAvailableActionsForConnectors = NUM_AVAILABLE_ACTIONS - NUM_ACTIONS_CONNECTOR;

        Scanner scanner = new Scanner(System.in);
        int energy;

        while (!(gameEnd)) {
            try {
                // Get the current player
                currentPlayer = playerManagement.getPlayerList().get(playerTurn);

                currentLocationIsPortal = Main.checkIfPlayerLocationIsPortal(currentPlayer);

                // Get the neighbours of the current location of the player
                ArrayUnorderedList<ILocal> neighbours = localsManagement.getPathGraph().getNeighbours(currentPlayer.getCurrentLocation());

                System.out.println("Turno do jogador " + currentPlayer.getName());

                // Display the possible actions
                System.out.println("Possíveis ações: ");
                System.out.println("0 - Sair do jogo e guardar o progresso");
                // If a player is in a portal, he can conquer it, attack it, reinforce it or visit a neighbour
                if (currentLocationIsPortal) {
                    Portal currentPlayerLocation = (Portal) currentPlayer.getCurrentLocation();
                    System.out.println("1 - Conquistar portal");
                    System.out.println("2 - Atacar portal");
                    System.out.println("3 - Reforçar portal");

                    for (int i = 0; i < Math.min(neighbours.size() - (indiceLista * numberOfAvailableActionsForPortals), numberOfAvailableActionsForPortals); i++) {
                        System.out.println((NUM_ACTIONS_PORTAL + (i + 1)) + " - Visitar local: " + neighbours.get(i).getName());
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
                            if (currentPlayerLocation.getPlayerTeam().equals(currentPlayer.getTeam())) {
                                System.out.println("O portal já pertence à sua equipa!");
                                break;
                            }

                            energy = getEnergyInputPortal(currentPlayer, currentPlayerLocation);
                            currentPlayer.conquerPortal(energy);
                            playerTurnEnded = true;
                            break;
                        case 2:
                            if (currentPlayerLocation.getPlayerTeam().equals(currentPlayer.getTeam())) {
                                System.out.println("O portal já pertence à sua equipa!");
                                break;
                            }

                            energy = getEnergyInputPortal(currentPlayer, currentPlayerLocation);
                            currentPlayer.attackPortal(energy);
                            playerTurnEnded = true;
                            break;
                        case 3:
                            if (!currentPlayerLocation.getPlayerTeam().equals(currentPlayer.getTeam())) {
                                System.out.println("O portal não pertence à sua equipa!");
                                break;
                            }

                            energy = getEnergyInputPortal(currentPlayer, currentPlayerLocation);
                            currentPlayer.reinforcePortal(energy);
                            playerTurnEnded = true;
                            break;
                        case 8:
                            indiceLista++;
                            break;
                        case 9:
                            indiceLista--;
                            break;
                        default:
                            currentPlayer.setCurrentLocation(neighbours.get((indiceLista * numberOfAvailableActionsForPortals) + option - numberOfAvailableActionsForPortals));
                    }

                    if (!playerTurnEnded) {
                        System.out.println("O turno do jogador " + currentPlayer.getName() + " nao terminou!");
                    } else {
                        playerTurnEnded = false;
                        // Change the turn to the next player in the list of players
                        // If the player is the last in the list, the turn goes to the first player
                        playerTurn = (playerTurn + 1) % playerManagement.getPlayerList().size();
                    }
                } else {
                    // If a player is in a connector, he can recharge his energy or visit a neighbour
                    System.out.println("1 - Recarregar energia");

                    for (int i = 0; i < Math.min(neighbours.size() - (indiceLista * numberOfAvailableActionsForConnectors), numberOfAvailableActionsForConnectors); i++) {
                        System.out.println((NUM_ACTIONS_CONNECTOR + (i + 1)) + " - Visitar local: " + neighbours.get(i).getName());
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
                            if (currentPlayer.rechargeEnergy().equals("You can't recharge your energy yet.")) {
                                System.out.println("Your turn didn't end because you couldn't recharge your energy.");
                            } else {
                                // Change the turn to the next player in the list of players
                                // If the player is the last in the list, the turn goes to the first player
                                playerTurn = (playerTurn + 1) % playerManagement.getPlayerList().size();
                            }
                            break;
                        case 8:
                            indiceLista++;
                            break;
                        case 9:
                            indiceLista--;
                            break;
                        default:
                            currentPlayer.setCurrentLocation(neighbours.get((indiceLista * numberOfAvailableActionsForConnectors) + option - numberOfAvailableActionsForConnectors));
                            break;
                    }
                }
            } catch (NotPlaceInstanceException | IOException e) {
                e.printStackTrace();
            }

            // Check if the game can end
            // If the game can end, the game ends
            gameEnd = Main.checkIfGameCanEnd(playerManagement);

            Main.gameTimer = Main.gameTimer.plusMinutes(1);
        }
    }

    public static LocalTime getGameTimer() {
        return Main.gameTimer;
    }

    public static int getCooldown() {
        return Main.COOLDOWN;
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
