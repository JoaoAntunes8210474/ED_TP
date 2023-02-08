package api.app;

import api.exceptions.NotPlaceInstanceException;
import api.implementation.*;
import api.interfaces.*;
import collections.implementation.ArrayUnorderedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class of the application made for the API project
 */
public class Main {
    /**
     * Time before a connector can be used again
     */
    private static final int COOLDOWN = 3;

    /**
     * Number of actions planned for a connector to be used
     */
    private static final int NUM_ACTIONS_CONNECTOR = 1;

    /**
     * Number of actions planned for a portal to be used
     */
    private static final int NUM_ACTIONS_PORTAL = 3;

    /**
     * Number of actions planned for a player to move
     */
    private static final int NUM_AVAILABLE_ACTIONS = 7;

    /**
     * Number of portals needed to win the game
     */
    private static final int NUM_PORTALS_TO_WIN = 3;

    /**
     * Game timer
     */
    private static LocalTime gameTimer = LocalTime.now();

    /**
     * Player's turn
     */
    private static int playerTurn = 0;

    /**
     * Method that gets the option selected by the user
     * @return option selected by the user
     */
    private static int getOptionInput(Scanner scanner) {
        int option;
        do {
            option = scanner.nextInt();
        } while (option < 0 || option > 9);

        return option;
    }

    /**
     * Method that gets the energy input by the user
     * @param currentPlayer player that is currently playing
     * @param currentPlayerLocation player's current location
     * @param scanner scanner used to get the input
     * @return energy input by the user
     */
    private static int getEnergyInputPortal(Player currentPlayer, Portal currentPlayerLocation, Scanner scanner) {
        int energy;
        System.out.println("The amount of energy you have is: [" + currentPlayer.getCurrentEnergy() + "/" + currentPlayer.getMaxEnergy() + "]");
        System.out.println("Portal's current energy: [" + currentPlayerLocation.getAmountEnergyItHas() + "/" + currentPlayerLocation.getMaxEnergy() + "]");
        System.out.println("Insert the amount of energy you want to use on the portal: ");

        do {
            energy = scanner.nextInt();
        } while (energy > currentPlayer.getCurrentEnergy() || energy < 0);

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
     *
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

    /**
     * Method that loads the game state from a JSON file
     * @param playerManagement list of players
     * @param localsManagement list of locals
     */
    private static void loadGameState(PlayerManagement playerManagement, LocalsManagement localsManagement) {
        JSONParser parser = new JSONParser();

        try {
            playerManagement.importJSON("files/Game.json", parser);
            localsManagement.importConnectorsFromJSON("files/Game.json");
            localsManagement.importPortalsFromJSON("files/Game.json");
            localsManagement.importPathsFromJSON("files/Game.json");
            Iterator<Player> iterator = playerManagement.getPlayerList().iterator();

            for (int i = 0; i < localsManagement.getPathGraph().size(); i++) {
                if (localsManagement.getPathGraph().get(i) instanceof Portal) {
                    System.out.println("1");
                    Portal local = (Portal) localsManagement.getPathGraph().get(i);
                    if (local.getOwnerPlayer() != null) {
                        System.out.println("2");
                        for (int j = 0; j < playerManagement.getPlayerList().size(); j++) {
                            // When we import the portals from the JSON file, we create an instance of
                            // the player that owns the portal
                            // We then check if the name and team of the player that owns the portal
                            // is the same as the one in the player list
                            // If it is, we set the owner of the portal to the player in the player list
                            // since the player that owns the portal is a new instance
                            // that simply shares the same properties as the one in the player list
                            if (local.getOwnerPlayer().getName().equals(playerManagement.getPlayerList().get(j).getName()) &&
                                    local.getOwnerPlayer().getTeam().equals(playerManagement.getPlayerList().get(j).getTeam())) {
                                local.setOwnerPlayer(playerManagement.getPlayerList().get(j));
                            }
                        }
                    }
                }
            }

            while (iterator.hasNext()) {
                Player player = iterator.next();
                for (int i = 0; i < localsManagement.getPathGraph().size(); i++) {
                    if (player.getCurrentLocation().getId() == localsManagement.getPathGraph().get(i).getId()) {
                        // For each player, we check if their current location is the same as the one in the path graph
                        // If it is, we set the current location of the player
                        // to the one in the path graph since the current location is a new instance
                        // that simply shares the same properties as the one in the path graph
                        player.setCurrentLocation(localsManagement.getPathGraph().get(i));
                    }
                }
            }

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
     * Method that saves the game state to a JSON file
     *
     * @param playerManagement list of players
     * @param localsManagement list of locals
     */
    @SuppressWarnings("unchecked")
    private static void saveGameState(@NotNull PlayerManagement playerManagement, LocalsManagement localsManagement) {
        File file = new File("files/Game.json");
        String path = file.getAbsolutePath();
        FileWriter fileWriter = null;

        JSONObject gameSettingsJSON = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray portalArray = localsManagement.getPortalsJSONArray();
        JSONArray connectorArray = localsManagement.getConnectorsJSONArray();

        for (Object o : portalArray) {
            jsonArray.add(o);
        }

        for (Object o : connectorArray) {
            jsonArray.add(o);
        }

        gameSettingsJSON.put("players", playerManagement.getPlayersAsJSONArray());
        gameSettingsJSON.put("locals", jsonArray);
        gameSettingsJSON.put("routes", localsManagement.getRoutesJSONArray());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String gameTimerFormatted = gameTimer.format(formatter);

        gameSettingsJSON.put("gameTimer", gameTimerFormatted);
        gameSettingsJSON.put("playerTurn", playerTurn);

        Gson beautifyGson = new GsonBuilder().setPrettyPrinting().create();
        String playerTurnJson = beautifyGson.toJson(gameSettingsJSON);
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(playerTurnJson);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that starts the game until it is closed by the user
     * @param playerManagement list of players
     * @param localsManagement list of locals
     * @param scanner scanner
     */
    private static void gameStart(PlayerManagement playerManagement, LocalsManagement localsManagement, Scanner scanner) {
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
                if (currentLocationIsPortal) {
                    numberOfAvailableActionsForPortals = neighbours.size();
                } else {
                    numberOfAvailableActionsForConnectors = neighbours.size();
                }

                System.out.println("Turno do jogador/a " + currentPlayer.getName());
                System.out.println("Local atual: " + currentPlayer.getCurrentLocation().getName() + " (" + currentPlayer.getCurrentLocation().getClass().getSimpleName() + ")");

                // Display the possible actions
                System.out.println("Possiveis acoes: ");
                System.out.println("0 - Sair do jogo e guardar o progresso");
                // If a player is in a portal, he can conquer it, attack it, reinforce it or visit a neighbour
                if (currentLocationIsPortal) {
                    Portal currentPlayerLocation = (Portal) currentPlayer.getCurrentLocation();
                    System.out.println("1 - Conquistar portal");
                    System.out.println("2 - Atacar portal");
                    System.out.println("3 - Reforcar portal");

                    for (int i = 0; i < Math.min(neighbours.size() - (indiceLista * numberOfAvailableActionsForPortals), numberOfAvailableActionsForPortals); i++) {
                        System.out.println((NUM_ACTIONS_PORTAL + (i + 1)) + " - Visitar local: " + neighbours.get(i).getName() + "(" + neighbours.get(i).getClass().getSimpleName() + ")");
                    }

                    System.out.println("8 - Mudar para a proxima pagina de locais a visitar");
                    System.out.println("9 - Mudar para a pagina anterior de locais a visitar");
                    System.out.println("--------------------");
                    System.out.println("Indique a acao que pretende realizar: ");

                    // Get the option selected by the user
                    option = Main.getOptionInput(scanner);
                    scanner = scanner.reset();

                    // Execute the action selected by the user
                    switch (option) {
                        case 0:
                            gameEnd = true;
                            Main.saveGameState(playerManagement, localsManagement);
                            break;
                        case 1:
                            if (currentPlayerLocation.getPlayerTeam().equals(currentPlayer.getTeam())) {
                                System.out.println("O portal ja pertence a sua equipa!");
                                break;
                            }

                            energy = getEnergyInputPortal(currentPlayer, currentPlayerLocation, scanner);
                            scanner = scanner.reset();

                            if (!(currentPlayerLocation.getPlayerTeam().equals("NEUTRAL"))) {
                                // If the portal is not neutral, the player has to attack it
                                currentPlayer.attackPortal(energy);
                            } else {
                                // If the portal is neutral, the player has to conquer it
                                currentPlayer.conquerPortal(energy);
                            }

                            playerTurnEnded = true;
                            break;
                        case 2:
                            if (currentPlayerLocation.getPlayerTeam().equals(currentPlayer.getTeam())) {
                                System.out.println("O portal ja pertence a sua equipa!");
                                break;
                            }

                            energy = getEnergyInputPortal(currentPlayer, currentPlayerLocation, scanner);
                            scanner = scanner.reset();

                            if (currentPlayerLocation.getPlayerTeam().equals("NEUTRAL")) {
                                // If the portal is neutral, the player has to conquer it
                                currentPlayer.conquerPortal(energy);
                            } else {
                                // If the portal is not neutral, the player has to attack it
                                currentPlayer.attackPortal(energy);
                            }

                            playerTurnEnded = true;
                            break;
                        case 3:
                            if (!currentPlayerLocation.getPlayerTeam().equals(currentPlayer.getTeam())) {
                                System.out.println("O portal nao pertence a sua equipa!");
                                break;
                            }

                            energy = getEnergyInputPortal(currentPlayer, currentPlayerLocation, scanner);
                            scanner = scanner.reset();
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
                            currentPlayer.setCurrentLocation(neighbours.get((indiceLista * numberOfAvailableActionsForPortals) + option - 4));
                            playerTurnEnded = true;
                            break;
                    }
                } else {
                    // If a player is in a connector, he can recharge his energy or visit a neighbour
                    System.out.println("1 - Recarregar energia");

                    for (int i = 0; i < Math.min(neighbours.size() - (indiceLista * numberOfAvailableActionsForConnectors), numberOfAvailableActionsForConnectors); i++) {
                        System.out.println((NUM_ACTIONS_CONNECTOR + (i + 1)) + " - Visitar local: " + neighbours.get(i).getName() + "(" + neighbours.get(i).getClass().getSimpleName() + ")");
                    }

                    System.out.println("8 - Mudar para a proxima pagina de locais a visitar");
                    System.out.println("9 - Mudar para a pagina anterior de locais a visitar");
                    System.out.println("--------------------");
                    System.out.println("Indique a acao que pretende realizar: ");

                    // Get the option selected by the user
                    do {
                        option = Main.getOptionInput(scanner);
                        scanner = scanner.reset();

                        // Check if the option selected by the user is valid
                        // It's considered valid if it's a number between 0, 1, the number displayed for each neighbour and the options to change the page
                        // This is done to avoid the user selecting an option between 0,9 like 7 when there are only 3 neighbours
                        if ((option < 8) && (option - 2 > neighbours.size() - 1)) {
                            System.out.println("Indique uma acao valida: ");
                        }
                    } while ((option < 8) && (option - 2 > neighbours.size() - 1));
                    // Execute the action selected by the user
                    switch (option) {
                        case 0:
                            gameEnd = true;
                            Main.saveGameState(playerManagement, localsManagement);
                            break;
                        case 1:
                            if (currentPlayer.rechargeEnergy().equals("You can t recharge your energy yet.")) {
                                System.out.println("Your turn didn t end because you couldn t recharge your energy.");
                            } else {
                                playerTurnEnded = true;
                            }
                            break;
                        case 8:
                            indiceLista++;
                            break;
                        case 9:
                            indiceLista--;
                            break;
                        default:
                            currentPlayer.setCurrentLocation(neighbours.get((indiceLista * numberOfAvailableActionsForConnectors) + option - 2));
                            playerTurnEnded = true;
                            break;
                    }
                }

                // If option 0 was selected, the game ends
                if (gameEnd) {
                    break;
                }

                if (!playerTurnEnded) {
                    System.out.println("O turno do jogador/a " + currentPlayer.getName() + " nao terminou!");
                } else {
                    playerTurnEnded = false;
                    // Change the turn to the next player in the list of players
                    // If the player is the last in the list, the turn goes to the first player
                    playerTurn = (playerTurn + 1) % playerManagement.getPlayerList().size();
                    Main.gameTimer = Main.gameTimer.plusMinutes(1);
                }

                // Check if the game can end
                // If the game can end, the game ends
                gameEnd = Main.checkIfGameCanEnd(playerManagement);

                if (gameEnd) {
                    System.out.println("O jogo terminou!");
                }
            } catch (NotPlaceInstanceException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the game timer
     * @return Game timer
     */
    public static LocalTime getGameTimer() {
        return Main.gameTimer;
    }

    /**
     * Returns the cooldown
     * @return Cooldown
     */
    public static int getCooldown() {
        return Main.COOLDOWN;
    }

    /**
     * Main method
     * @param args Arguments passed to the main method (not used)
     */
    public static void main(String[] args) {
        IImportExportFiles importExportFiles = new ImportExportFiles();

        IPlayerManagement playerManagement = new PlayerManagement();

        ILocalsManagement localsManagement = new LocalsManagement();

        Player player = new Player("Joao", "Giants");
        Player player1 = new Player("Regina", "Sparks");

        //File file = new File("files/ExportTest.json");
        //String path = file.getAbsolutePath();

        JSONParser jsonParser = new JSONParser();

        FileWriter fileWriter = null;
        try {
            //fileWriter = new FileWriter(path);
        //} catch (IOException e) {
          //  e.printStackTrace();
        } finally {

        }

        ///**
        System.out.println("Deseja continuar o jogo anterior ou comecar um jogo novo?\n (0 - Sair, 1 - Continuar, 2 - Novo jogo)");
        Scanner scanner = new Scanner(System.in);

        int option;
        do {
            option = scanner.nextInt();

            if (option < 0 || option > 2) {
                System.out.println("Opção inválida. Tente novamente.");
            }
        } while (option < 0 || option > 2);

        switch (option) {
            case 0:
                break;
            case 1:
                Main.loadGameState((PlayerManagement) playerManagement, (LocalsManagement) localsManagement);
                Main.gameStart((PlayerManagement) playerManagement, (LocalsManagement) localsManagement, scanner);
                break;
            case 2:
                Random random = new Random();

                Coordinates coordinates = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
                Coordinates coordinates1 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
                Coordinates coordinates2 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
                Coordinates coordinates3 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
                Coordinates coordinates4 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
                Coordinates coordinates5 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));

                ILocal local = new Portal(100, random.nextInt(3000), "Palacio de Monserratelo", 0, coordinates);
                ILocal local1 = new Portal(100, random.nextInt(3000), "Palacio da Pena", 40, coordinates1);
                ILocal local2 = new Portal(100, random.nextInt(3000), "Quinta da regaleira", 15, coordinates2);
                ILocal local3 = new Portal(100, random.nextInt(3000), "Palacio de Monserrate", 1, coordinates3);
                ILocal local4 = new Connector(Main.COOLDOWN, random.nextInt(3000), "Arco do Triunfo", 60, coordinates4);
                ILocal local5 = new Connector(Main.COOLDOWN, random.nextInt(3000), "Torre Eifel", 80, coordinates5);

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

                player.setCurrentLocation(local);
                player1.setCurrentLocation(local4);
                playerManagement.addPlayer(player);
                playerManagement.addPlayer(player1);
                Main.gameStart((PlayerManagement) playerManagement, (LocalsManagement) localsManagement, scanner);
                break;
        }

        scanner.close();
        //**/
        /**
        System.out.println("--------------------------------------LISTA DE CONNECTORES----------------------------------------------" + "\n" );
        System.out.println(localsManagement.getAllConnectorsListing() );
        System.out.println("--------------------------------------LISTA DE CONNECTORES POR ORDEM CRESCENTE DE ENERGIA QUE CONTEM--------------------------------------"+ "\n" );
        System.out.println(localsManagement.getConnectorsOrderedByEnergyItHasListing() );
        System.out.println("--------------------------------------LISTA DE PORTAIS--------------------------------------");
        System.out.println(localsManagement.getAllPortalsListing() + "\n");
        System.out.println("--------------------------------------LISTA DE PORTAIS QUE PERTENCEM A UMA EQUIPA--------------------------------------" + "\n");
        System.out.println(localsManagement.getPortalsByTeamListing("Sparks"));
        System.out.println("--------------------------------------LISTA DE PORTAIS SEM EQUIPA/NEUTROS--------------------------------------" + "\n");
        System.out.println(localsManagement.getPortalsWithoutTeamListing());
        System.out.println("--------------------------------------LISTA DE PORTAIS ORDENADOS DE FORMA CRESCENTE PELA ENERGIA QUE CONTÊM--------------------------------------" + "\n");
        System.out.println(localsManagement.getPortalsOrderedByEnergyItHasListing());
        //System.out.println("---------------------------------------CAMINHO MAIS CURTO ENTRE 2 LOCAIS PASSANDO POR UM CONECTOR-------------------------------------" + "\n");
        System.out.println(localsManagement.getPortalsPlayerListing(player1));
         //**/
        /**
        try {
            Iterator<ILocal> iterator = localsManagement.getPathGraph().shortestPathAtleastOneConnector(local, local3);
            while (iterator.hasNext()) {
                System.out.println(iterator.next().toString());
            }
        } catch (NotPlaceInstanceException e) {
            throw new RuntimeException(e);
        }
         **/
        //System.out.println(importExportFiles.exportJSON(path, (PlayerManagement) playerManagement, (LocalsManagement) localsManagement, fileWriter));
        //System.out.println(importExportFiles.importJSON(path, (PlayerManagement) playerManagement, (LocalsManagement) localsManagement));
        /**
        try {
            localsManagement.getPathGraph().exportShortestPathWithOnlyPortals(local, local3, path);
            //localsManagement.getPathGraph().exportShortestPathWithOnlyConnectors(local4, local5, path);
            //localsManagement.getPathGraph().exportShortestPathAtleastOneConnector(local, local3, path);
            //localsManagement.getPathGraph().exportShortestPathBetweenTwoPoints(local, local3, path);
        } catch (NotPlaceInstanceException e) {
            throw new RuntimeException(e);
        }
         **/
    }
}
