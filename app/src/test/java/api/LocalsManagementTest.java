package api;

import api.implementation.*;
import collections.exceptions.ElementNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocalsManagementTest {

    private LocalsManagement pathGraph;

    public LocalsManagementTest() {
        this.pathGraph= new LocalsManagement();
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
        Coordinates cordinates = new Coordinates(50,45);
        Connector local = new Connector(3,11, "Palácio de Monserrate", 100, cordinates);

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
        Player player = new Player("Joaquim", "Giants");
        String expected = this.pathGraph.getPortalsPlayerListing(player);

        Assertions.assertEquals(expected, this.pathGraph.getPortalsPlayerListing(player));
    }

    @Test
    public void testGetPortalsPlayerListing_WhenSentANullReference() {
        String expected = this.pathGraph.getPortalsPlayerListing(null);

        Assertions.assertEquals(expected, this.pathGraph.getPortalsPlayerListing(null));
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
    public void testGetConnectorsOrderedByEnergyItHasListing_ReturnString(){
        String expected = this.pathGraph.getConnectorsOrderedByEnergyItHasListing();

        Assertions.assertEquals(expected, this.pathGraph.getConnectorsOrderedByEnergyItHasListing());
    }


}
