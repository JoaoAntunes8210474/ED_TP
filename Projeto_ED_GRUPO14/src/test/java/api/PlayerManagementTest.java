package api;

import api.implementation.ImportExportFiles;
import api.implementation.Player;
import api.implementation.PlayerManagement;
import api.interfaces.IImportExportFiles;
import api.interfaces.IPlayerManagement;
import collections.implementation.ArrayUnorderedList;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

public class PlayerManagementTest {
    private IPlayerManagement playersList;
    public PlayerManagementTest() {
        this.playersList = new PlayerManagement();
    }

    @Before
    public void importInfoFromJSONBeforeTesting() {
        File file = new File("files/GameRules.json");
        String path = file.getAbsolutePath();

        IImportExportFiles importer = new ImportExportFiles();

        importer.importJSON(path, (PlayerManagement) this.playersList);
    }

    @Test
    public void testAddPlayerToPlayerList_ReturnSuccessfulString_WhenSentAValidPlayer() {
        
    }

    @Test
    public void testAddPlayerToPlayerList_ReturnIllegalArgumentException_WhenNotSentAPlayer() {

    }

    @Test
    public void testAddPlayerToPlayerList_ReturnNullPointerException_WhenSentANullReference() {

    }

    @Test
    public void testUpdatePlayerFromPlayerList_ReturnSuccessfulString_WhenSentAValidPlayerFromTheList() {

    }

    @Test
    public void testUpdatePlayerFromPlayerList_ReturnIllegalArgumentException_WhenNotSentAPlayer() {

    }

    @Test
    public void testUpdatePlayerFromPlayerList_ReturnNullPointerException_WhenSentANullReference() {

    }

    @Test
    public void testUpdatePlayerFromPlayerList_ReturnElementNotFoundException_WhenPlaySentIsNotInList() {

    }

    @Test
    public void testRemovePlayerFromPlayerList_ReturnSuccessfulString_WhenSentAValidPlayerFromTheList() {

    }

    @Test
    public void testRemovePlayerFromPlayerList_ReturnIllegalArgumentException_WhenNotSentAPlayer() {

    }

    @Test
    public void testRemovePlayerFromPlayerList_ReturnNullPointerException_WhenSentANullReference() {

    }

    @Test
    public void testRemovePlayerFromPlayerList_ReturnElementNotFoundException_WhenPlaySentIsNotInList() {

    }

    @Test
    public void testImportJSON_ReturnSuccessfulString_WhenSentValidFileName() {

    }

    @Test
    public void testImportJSON_ReturnIOException_WhenSentInvalidFileName() {

    }

    @Test
    public void testExportJSON_ReturnSuccessfulString_WhenSentValidFileName() {

    }

    @Test
    public void testExportJSON_ReturnIOException_WhenSentInvalidFileName() {

    }
}
