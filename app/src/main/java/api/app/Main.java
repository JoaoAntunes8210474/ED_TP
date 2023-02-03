package api.app;

import api.exceptions.NotPlaceInstanceException;
import api.implementation.*;
import api.interfaces.*;
import collections.implementation.ArrayUnorderedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int COOLDOWN = 3;
    private static final int NUM_ACTIONS_CONNECTOR = 1;

    private static final int NUM_ACTIONS_PORTAL = 3;

    private static final int NUM_AVAILABLE_ACTIONS = 7;

    private static final int NUM_PORTALS_TO_WIN = 5;

    private static LocalTime gameTimer = LocalTime.now();

    private static int playerTurn = 0;
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

    private static void loadGameState(PlayerManagement playerManagement, LocalsManagement localsManagement) {
        IImportExportFiles importExportFiles = new ImportExportFiles();

        importExportFiles.importJSON("files/Game.json", playerManagement, localsManagement);
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("files/Game.json"));
            JSONObject jsonObject = (JSONObject) obj;
            long longPlayerTurn = (long) jsonObject.get("playerTurn");
            playerTurn = (int) longPlayerTurn;
            gameTimer = LocalTime.parse((String) jsonObject.get("gameTimer"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Method that saves the game state to a file
     */
    @SuppressWarnings("unchecked")
    private static void saveGameState(PlayerManagement playerManagement, LocalsManagement localsManagement) throws IOException {
        IImportExportFiles importExportFiles = new ImportExportFiles();

        importExportFiles.exportJSON("files/Game.json", playerManagement, localsManagement);
        JSONObject playerTurnJSON = new JSONObject();
        JSONObject gameTimerJSON = new JSONObject();
        playerTurnJSON.put("playerTurn", playerTurn);
        gameTimerJSON.put("gameTimer", gameTimer);

        Gson beautifyGson = new GsonBuilder().setPrettyPrinting().create();
        String playerTurnJson = beautifyGson.toJson(playerTurnJSON);
        String gameTimerJson = beautifyGson.toJson(gameTimerJSON);

        FileWriter fileWriter = new FileWriter("files/Game.json");
        fileWriter.write(playerTurnJson);
        fileWriter.write(gameTimerJson);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * Method that starts the game until it is closed by the user
     * @param playerManagement list of players
     * @param localsManagement list of locals
     */
    private static void gameStart(PlayerManagement playerManagement, LocalsManagement localsManagement) {
        boolean gameEnd = false;
        boolean currentLocationIsPortal;
        boolean playerTurnEnded = false;
        Player currentPlayer;

        int indiceLista = 0;
        int option;

        int numberOfAvailableActionsForPortals = NUM_AVAILABLE_ACTIONS - NUM_ACTIONS_PORTAL;
        int numberOfAvailableActionsForConnectors = NUM_AVAILABLE_ACTIONS - NUM_ACTIONS_CONNECTOR;

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
                            Main.saveGameState(playerManagement, localsManagement);
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
                            Main.saveGameState(playerManagement, localsManagement);
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

        Player player = new Player("Joao", "Giants");
        Player player1 = new Player("Regina", "Sparks");

        playerManagement.addPlayer(player);
        playerManagement.addPlayer(player1);

        Random random = new Random();

        Coordinates coordinates = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates1 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates2 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates3 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates4 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates5 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));

        ILocal local = new Portal(100, random.nextInt(3000), "Palácio de Monserratelo", 0, coordinates);
        ILocal local1 = new Portal(100, random.nextInt(3000), "Palácio da Pena", 0, coordinates1);
        ILocal local2 = new Portal(100, random.nextInt(3000), "Quinta da regaleira", 0, coordinates2);
        ILocal local3 = new Portal(100, random.nextInt(3000), "Palácio de Monserrate", 0, coordinates3);
        ILocal local4 = new Connector(Main.COOLDOWN, random.nextInt(3000), "Arco do Triunfo", 50, coordinates4);
        ILocal local5 = new Connector(Main.COOLDOWN, random.nextInt(3000), "Torre Eifel", 50, coordinates5);

        IRoute route = new Route(local, local1);
        IRoute route1 = new Route(local1, local2);
        IRoute route2 = new Route(local2, local3);
        IRoute route3 = new Route(local3, local4);
        IRoute route4 = new Route(local4, local5);
        IRoute route5 = new Route(local5, local);
        IRoute route6 = new Route(local4, local1);
        IRoute route7 = new Route(local3, local2);
        IRoute route8 = new Route(local2, local5);
        IRoute route9 = new Route(local1, local4);
        IRoute route10 = new Route(local, local3);

        localsManagement.addLocals(local);
        localsManagement.addLocals(local1);
        localsManagement.addLocals(local2);
        localsManagement.addLocals(local3);
        localsManagement.addLocals(local4);
        localsManagement.addLocals(local5);
        localsManagement.addPath(route);
        localsManagement.addPath(route1);
        localsManagement.addPath(route2);
        localsManagement.addPath(route3);
        localsManagement.addPath(route4);
        localsManagement.addPath(route5);
        localsManagement.addPath(route6);
        localsManagement.addPath(route7);
        localsManagement.addPath(route8);
        localsManagement.addPath(route9);
        localsManagement.addPath(route10);

        System.out.println("Deseja continuar o jogo anterior ou começar um jogo novo?\n (1 - Continuar, 2 - Novo jogo)");
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
                Main.loadGameState((PlayerManagement) playerManagement, (LocalsManagement) localsManagement);
                Main.gameStart((PlayerManagement) playerManagement, (LocalsManagement) localsManagement);
                break;
            case 2:
                Main.gameStart((PlayerManagement) playerManagement, (LocalsManagement) localsManagement);
                break;
        }
    }
}
