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

        importExportFiles.exportJSON("files/ExportTest.json", (PlayerManagement) playerManagement);
    }
}
