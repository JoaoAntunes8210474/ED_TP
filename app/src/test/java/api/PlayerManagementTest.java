package api;

import api.implementation.ImportExportFiles;
import api.implementation.LocalsManagement;
import api.implementation.Player;
import api.implementation.PlayerManagement;
import api.interfaces.IImportExportFiles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import collections.exceptions.ElementNotFoundException;
import java.io.File;
import java.io.IOException;

public class PlayerManagementTest {
    private PlayerManagement playersList;

    private LocalsManagement pathGraph;

    public PlayerManagementTest() {
        this.playersList = new PlayerManagement();
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
        Player player = new Player("Test1", "Sparks");

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
        Player player = new Player("Test1", "Sparks");
        String expected = "O jogador foi adicionado a equipa Giants";

        Assertions.assertEquals(expected, this.playersList.associatePlayerToTeam(player, "Giants"));
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
        Player player = new Player("Joaquim", "Giants");
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
            Assertions.assertEquals(expected, this.playersList.importJSON(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testImportJSON_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.playersList.importJSON(path));
    }


    @Test
    public void testExportJSON_ReturnSuccessfulString_WhenSentValidFileName() {
        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        IImportExportFiles exporter = new ImportExportFiles();

        String expected = "O export foi feito com sucesso";

        try {
            Assertions.assertEquals(expected, this.playersList.exportJSON(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExportJSON_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.playersList.exportJSON(path));
    }
}
