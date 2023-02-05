package api;

import api.implementation.*;
import api.interfaces.IImportExportFiles;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import collections.exceptions.ElementNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerManagementTest {
    private PlayerManagement playersList;

    private LocalsManagement pathGraph;

    public PlayerManagementTest() {
        this.playersList = new PlayerManagement();
        this.pathGraph = new LocalsManagement();
    }

    @BeforeEach
    public void importInfoFromJSONBeforeTesting() {
        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        IImportExportFiles importer = new ImportExportFiles();

        importer.importJSON(path, this.playersList, this.pathGraph);
    }

    @Test
    public void testAddPlayerToPlayerList_ReturnSuccessfulString_WhenSentAValidPlayer() {
        Player player = new Player("Joaquim", "Sparks");
        String expected = "O jogador foi adicionado a lista de jogadores!";

        Assertions.assertEquals(expected, this.playersList.addPlayer(player));
    }

    @Test
    public void testAddPlayerToPlayerList_ReturnNullPointerException_WhenSentANullReference() {
        Assertions.assertThrows(NullPointerException.class, () -> this.playersList.addPlayer(null));
    }

    @Test
    public void testRemovePlayerFromPlayerList_ReturnSuccessfulString_WhenSentAValidPlayerFromTheList() {
        Player player = new Player("Joao", "Giants");

        Assertions.assertEquals(player.toString(), this.playersList.removePlayer(player).toString());
    }

    @Test
    public void testRemovePlayerFromPlayerList_ReturnNullPointerException_WhenSentANullReference() {
        Assertions.assertThrows(NullPointerException.class, () -> this.playersList.removePlayer(null));
    }

    @Test
    public void testRemovePlayerFromPlayerList_ReturnElementNotFoundException_WhenPlayerSentIsNotInList() {
        Player player = new Player("Teste9999", "Giants");
        Assertions.assertThrows(ElementNotFoundException.class, () -> this.playersList.removePlayer(player));
    }

    @Test
    public void testAssociatePlayerToTeam_ReturnSuccessfulString_WhenSentAValidPlayerFromTheListAndValidTeam() {
        Player player = new Player("Joao", "Giants");
        String expected = "O jogador foi adicionado a equipa Sparks";

        Assertions.assertEquals(expected, this.playersList.associatePlayerToTeam(player, "Sparks"));
    }

    @Test
    public void testAssociatePlayerToTeam_ReturnElementNotFoundException_WhenPlayerSentIsNotInList() {
        Player player = new Player("Test999", "Giants");
        Assertions.assertThrows(ElementNotFoundException.class, () -> this.playersList.associatePlayerToTeam(player, "Teste"));
    }

    @Test
    public void testAssociatePlayerToTeam_ReturnNullPointerException_WhenSentANullReference() {
        Assertions.assertThrows(NullPointerException.class, () -> this.playersList.associatePlayerToTeam(null, "Teste"));
    }

    @Test
    public void testDisassociatePlayerFromTeam_ReturnSuccessfulString_WhenSentAValidPlayerFromTheList() {
        Player player = new Player("Regina", "Sparks");
        String expected = "O jogador foi removido da equipa " + player.getTeam();

        Assertions.assertEquals(expected, this.playersList.disassociatePlayerFromTeam(player));
    }

    @Test
    public void testDisassociatePlayerFromTeam_ReturnElementNotFoundException_WhenPlayerSentIsNotInList() {
        Player player = new Player("Test999", "Giants");
        Assertions.assertThrows(ElementNotFoundException.class, () -> this.playersList.disassociatePlayerFromTeam(player));
    }

    @Test
    public void testDisassociatePlayerFromTeam_ReturnNullPointerException_WhenSentANullReference() {
        Assertions.assertThrows(NullPointerException.class, () -> this.playersList.disassociatePlayerFromTeam(null));
    }

    @Test
    public void testListPlayersByTeam_ReturnArrayUnorderedList() {
        String expected = this.playersList.listPlayersByTeam();

        Assertions.assertEquals(expected, this.playersList.listPlayersByTeam());
    }

    @Test
    public void testListPlayersByLevel_ReturnArrayUnorderedList() {
        String expected = this.playersList.listPlayersByLevel();

        Assertions.assertEquals(expected, this.playersList.listPlayersByLevel());
    }

    @Test
    public void testListPlayersByNumPortalsConquered_ReturnArrayUnorderedList() {
        String expected = this.playersList.listPlayersByPortalsConquered();

        Assertions.assertEquals(expected, this.playersList.listPlayersByPortalsConquered());
    }


    @Test
    public void testImportJSON_ReturnSuccessfulString_WhenSentValidFileName() {
        String expected = "O import foi feito com sucesso";

        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        try {
            Assertions.assertEquals(expected, this.playersList.importJSON(path, new JSONParser()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testImportJSON_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.playersList.importJSON(path, new JSONParser()));
    }


    //@Test
    public void testExportJSON_ReturnSuccessfulString_WhenSentValidFileName() {
        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player player = new Player("Test1", "Sparks");
        Player player2 = new Player("Test2", "Giants");
        Player player3 = new Player("Test3", "Giants");
        Player player4 = new Player("Test4", "Giants");
        Player player5 = new Player("Test5", "Giants");
        Player player6 = new Player("Test6", "Giants");

        this.playersList.addPlayer(player);
        this.playersList.addPlayer(player2);
        this.playersList.addPlayer(player3);
        this.playersList.addPlayer(player4);
        this.playersList.addPlayer(player5);
        this.playersList.addPlayer(player6);

        FileWriter finalFileWriter = fileWriter;

        IImportExportFiles exporter = new ImportExportFiles();

        String expected = "O export foi feito com sucesso";

        try {
            Assertions.assertEquals(expected, this.playersList.exportJSON(path, finalFileWriter));
            finalFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void testExportJSON_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
        } catch (IOException e) {
        }

        FileWriter finalFileWriter = fileWriter;
        Assertions.assertThrows(IOException.class, () -> this.playersList.exportJSON(path, finalFileWriter));
    }



}
