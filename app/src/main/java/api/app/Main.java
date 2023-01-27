package api.app;

import api.implementation.ImportExportFiles;
import api.implementation.Player;
import api.implementation.PlayerManagement;
import api.interfaces.IImportExportFiles;
import api.interfaces.IPlayerManagement;

public class Main {
    public static void main(String[] args) {
        IImportExportFiles importExportFiles = new ImportExportFiles();

        IPlayerManagement playerManagement = new PlayerManagement();

        Player player = new Player("Joaquim", "Giants", 0, 0, 0, 0, 0);
        Player player1 = new Player("Test1", "Sparks", 2, 0, 0, 0, 0);
        Player player2 = new Player("Test2", "Sparks", 4, 0, 0, 0, 0);
        Player player3 = new Player("Test3", "Sparks", 10, 0, 0, 0, 0);
        Player player4 = new Player("Test4", "Sparks", 5, 0, 0, 0, 0);
        Player player5 = new Player("Test5", "Sparks", 3, 0, 0, 0, 0);
        Player player6 = new Player("Test6", "Sparks", 1, 0, 0, 0, 0);

        playerManagement.addPlayer(player);
        playerManagement.addPlayer(player1);
        playerManagement.addPlayer(player2);
        playerManagement.addPlayer(player3);
        playerManagement.addPlayer(player4);
        playerManagement.addPlayer(player5);
        playerManagement.addPlayer(player6);

        importExportFiles.exportJSON("files/ExportTest.json", (PlayerManagement) playerManagement);
    }
}
