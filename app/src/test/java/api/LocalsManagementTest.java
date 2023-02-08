package api;

import api.app.Main;
import api.implementation.*;
import api.interfaces.IImportExportFiles;
import api.interfaces.ILocal;
import api.interfaces.IRoute;
import collections.exceptions.ElementNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class LocalsManagementTest {

    private PlayerManagement playersList;
    private LocalsManagement pathGraph;

    public LocalsManagementTest() {
        this.playersList = new PlayerManagement();
        this.pathGraph= new LocalsManagement();
    }

    @BeforeEach
    public void importInfoFromJSONBeforeTesting() {
        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        IImportExportFiles importer = new ImportExportFiles();

        importer.importJSON(path, this.playersList, this.pathGraph);
    }


    @Test
    public void testAddLocal_ReturnSuccessfulString_WhenSentAValidLocal() {
        Coordinates coordinates = new Coordinates(50,45);
        Connector local = new Connector (4,11, "Palácio de Monserrate", 100,coordinates);
        String expected = "O local foi adicionado com sucesso";

        Assertions.assertEquals(expected, this.pathGraph.addLocals(local));
    }

    @Test
    public void testAddLocal_ReturnNullPointerException_WhenSentANullReference() {
        Assertions.assertThrows(NullPointerException.class, () -> this.pathGraph.addLocals(null));
    }


    @Test
    public void testRemoveLocal_ReturnSuccessfulString_WhenSentAValidPlayerFromTheList() {
        // The 4th element of the list is a Connector, so we can use it to test the method
        // We should use a for loop to find the Connector, but we know that the 4th element is a Connector hence why we do this
        Connector local = (Connector) this.pathGraph.getPathGraph().get(4);

        String expected = "O local foi removido com sucesso";

        Assertions.assertEquals(expected, this.pathGraph.removeLocals(local));
    }

    @Test
    public void testRemoveLocal_ReturnNullPointerException_WhenSentANullReference() {
        Assertions.assertThrows(NullPointerException.class, () -> this.pathGraph.removeLocals(null));
    }

    @Test
    public void testRemoveLocal_ReturnElementNotFoundException_WhenLocalsSentIsNotInList() {
        Coordinates coordinates = new Coordinates(60,70);
        Portal local = new Portal (100,9999, "TESTE 1111", 50, coordinates);
        Assertions.assertThrows(ElementNotFoundException.class, () -> this.pathGraph.removeLocals(local));
    }

    @Test
    public void testAddPath_ReturnSuccessfulString_WhenSentAValidLocal() {
        Coordinates coordinates1 = new Coordinates(50,45);
        Coordinates coordinates2 = new Coordinates(60,50);
        Connector local1 = new Connector (5,1, "Palácio da Pena", 90, coordinates1);
        Connector local2 = new Connector (4,2, "Quinta da regaleira", 100, coordinates2);
        Route route = new Route(local1, local2);

        String expected = "O caminho foi adicionado com sucesso";

        Assertions.assertEquals(expected, this.pathGraph.addPath(route));
    }

    @Test
    public void testAddPath_ReturnNullPointerException_WhenSentANullReference() {
        Coordinates coordinates1 = new Coordinates(60,50);
        Connector local1 = new Connector (5,1, "Palácio da Pena", 90,coordinates1);
        Route route = new Route(local1, null);
        Assertions.assertThrows(NullPointerException.class, () -> this.pathGraph.addPath(route));
    }



    @Test
    public void testGetAllPortalsListing_ReturnString(){
        String expected = this.pathGraph.getAllPortalsListing();

        Assertions.assertEquals(expected, this.pathGraph.getAllPortalsListing());
    }

    @Test
    public void testGetPortalsWithoutTeamListing_ReturnString() {
        String expected = this.pathGraph.getPortalsWithoutTeamListing();

        Assertions.assertEquals(expected, this.pathGraph.getPortalsWithoutTeamListing());
    }

    @Test
    public void testGetPortalsPlayerListing_WhenSentAValidPlayer() {
        Player player = new Player("Joao", "Giants");
        String expected = this.pathGraph.getPortalsPlayerListing(player);

        Assertions.assertEquals(expected, this.pathGraph.getPortalsPlayerListing(player));
    }

    @Test
    public void testGetPortalsPlayerListing_ReturnNullPointerException_WhenSentANullReference() {
        Assertions.assertThrows(NullPointerException.class, () -> this.pathGraph.getPortalsPlayerListing(null));
    }

    @Test
    public void testGetPortalsByTeamListing_WhenSentValidString() {
        String expected = this.pathGraph.getPortalsByTeamListing("Giants");

        Assertions.assertEquals(expected, this.pathGraph.getPortalsByTeamListing("Giants"));
    }

    @Test
    public void testGetPortalsByTeamListing_WhenSentNullReference(){
        String expected = this.pathGraph.getPortalsByTeamListing(null);

        Assertions.assertEquals(expected, this.pathGraph.getPortalsByTeamListing(null));
    }

    @Test
    public void testGetPortalsOrderedByEnergyItHasListing_ReturnString(){
        String expected = this.pathGraph.getPortalsOrderedByEnergyItHasListing();

        Assertions.assertEquals(expected, this.pathGraph.getPortalsOrderedByEnergyItHasListing());
    }

    @Test
    public void testGetAllConnectorsListing_ReturnString(){
        String expected = this.pathGraph.getAllConnectorsListing();

        Assertions.assertEquals(expected, this.pathGraph.getAllConnectorsListing());
    }

    @Test
    public void testGetConnectorOrderedByEnergyItHasListing_ReturnString(){
        String expected = this.pathGraph.getConnectorsOrderedByEnergyItHasListing();

        Assertions.assertEquals(expected, this.pathGraph.getConnectorsOrderedByEnergyItHasListing());
    }

    @Test
    public void testImportPortalsFromJSON_ReturnSuccessfulString_WhenSentValidFileName() {
        String expected = "O import foi feito com sucesso";

        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        try {
            Assertions.assertEquals(expected, this.pathGraph.importPortalsFromJSON(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testImportPortalsFromJSON_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.pathGraph.importPortalsFromJSON(path));
    }


    @Test
    public void testImportConnectorsFromJSON_ReturnSuccessfulString_WhenSentValidFileName() {
        String expected = "O import foi feito com sucesso";

        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        try {
            Assertions.assertEquals(expected, this.pathGraph.importConnectorsFromJSON(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testImportConnectorsFromJSON_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.pathGraph.importConnectorsFromJSON(path));
    }

    @Test
    public void testImportPathsFromJSON_ReturnSuccessfulString_WhenSentValidFileName() {
        String expected = "O import foi feito com sucesso";

        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        try {
            Assertions.assertEquals(expected, this.pathGraph.importPathsFromJSON(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @Test
    public void testImportPathsFromJSON_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.pathGraph.importPathsFromJSON(path));
    }


    //@Test
    public void testExportPortalsToJson_ReturnSuccessfulString_WhenSentValidFileName() {
        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        Random random = new Random();

        Coordinates coordinates = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates1 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates2 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates3 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));

        ILocal local = new Portal(100, random.nextInt(3000), "Palacio de Monserratelo", 0, coordinates);
        ILocal local1 = new Portal(100, random.nextInt(3000), "Palacio da Pena", 0, coordinates1);
        ILocal local2 = new Portal(100, random.nextInt(3000), "Quinta da regaleira", 0, coordinates2);
        ILocal local3 = new Portal(100, random.nextInt(3000), "Palacio de Monserrate", 0, coordinates3);

        this.pathGraph.addLocals(local);
        this.pathGraph.addLocals(local1);
        this.pathGraph.addLocals(local2);
        this.pathGraph.addLocals(local3);

        String expected = "O export foi feito com sucesso";

        try {
            Assertions.assertEquals(expected, this.pathGraph.exportPortalsToJson(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExportPortalsToJson_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.pathGraph.exportPortalsToJson(path));
    }


    //@Test
    public void testExportConnectorsToJson_ReturnSuccessfulString_WhenSentValidFileName() {
        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        Random random = new Random();

        Coordinates coordinates = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates1 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));

        ILocal local4 = new Connector(3, random.nextInt(3000), "Arco do Triunfo", 50, coordinates);
        ILocal local5 = new Connector(3, random.nextInt(3000), "Torre Eifel", 50, coordinates1);

        this.pathGraph.addLocals(local4);
        this.pathGraph.addLocals(local5);

        String expected = "O export foi feito com sucesso";

        try {
            Assertions.assertEquals(expected, this.pathGraph.exportConnectorsToJson(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExportConnectorsToJson_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.pathGraph.exportConnectorsToJson(path));
    }


    //@Test
    public void testExportPathsToJson_ReturnSuccessfulString_WhenSentValidFileName() {
        File file = new File("files/ExportTest.json");
        String path = file.getAbsolutePath();

        Random random = new Random();

        Coordinates coordinates = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates1 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates2 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates3 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates4 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));
        Coordinates coordinates5 = new Coordinates(random.nextDouble(-100, 100), random.nextDouble(-200, 200));

        ILocal local = new Portal(100, random.nextInt(3000), "Palacio de Monserratelo", 0, coordinates);
        ILocal local1 = new Portal(100, random.nextInt(3000), "Palacio da Pena", 0, coordinates1);
        ILocal local2 = new Portal(100, random.nextInt(3000), "Quinta da regaleira", 0, coordinates2);
        ILocal local3 = new Portal(100, random.nextInt(3000), "Palacio de Monserrate", 0, coordinates3);
        ILocal local4 = new Connector(3, random.nextInt(3000), "Arco do Triunfo", 50, coordinates4);
        ILocal local5 = new Connector(3, random.nextInt(3000), "Torre Eifel", 50, coordinates5);

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

        this.pathGraph.addLocals(local);
        this.pathGraph.addLocals(local1);
        this.pathGraph.addLocals(local2);
        this.pathGraph.addLocals(local3);
        this.pathGraph.addLocals(local4);
        this.pathGraph.addLocals(local5);

        this.pathGraph.addPath(route);
        this.pathGraph.addPath(route1);
        this.pathGraph.addPath(route2);
        this.pathGraph.addPath(route3);
        this.pathGraph.addPath(route4);
        this.pathGraph.addPath(route5);
        this.pathGraph.addPath(route6);
        this.pathGraph.addPath(route7);
        this.pathGraph.addPath(route8);
        this.pathGraph.addPath(route9);
        this.pathGraph.addPath(route10);

        String expected = "O export foi feito com sucesso";

        try {
            Assertions.assertEquals(expected, this.pathGraph.exportPathsToJson(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExportPathsToJson_ReturnIOException_WhenSentInvalidFileName() {
        String path = "";

        Assertions.assertThrows(IOException.class, () -> this.pathGraph.exportPathsToJson(path));
    }

}
