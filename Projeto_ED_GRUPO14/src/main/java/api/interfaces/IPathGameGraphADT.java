package api.interfaces;

import java.util.Iterator;

import api.exceptions.NotPlaceInstanceException;
import collections.interfaces.GraphADT;
import collections.interfaces.ListADT;

/**
 * Contract of a graph with paths of a game between portals and connectors.
 *
 * @param <T> type of locals in graph.
 */
public interface IPathGameGraphADT<T> extends GraphADT<T> {


    /**
     * Gets the number of connectors in graph.
     * @return the number of connectors in graph.
     */
    public int getNumberOfConnectores();

    /**
     * Gets the number of portals in graph.
     * @return the number of portals in graph.
     */
    public int getNumberOfPortals();


    /**
     * Gets the connectores on graph.
     * @return iterator of connectores.
     */
    Iterator<IConnector> getConnectores();

    /**
     * Gets the Portals on graph.
     * @return iterator of portals.
     */
    Iterator<IPortal> getPortals();

    /**
     * Gets the paths existences on the graph
     * @return iterator with those paths
     */
    Iterator<IRoute<ILocal>> getRoutes();

    /**
     * Shortest path between two points.
     * @param source starting point, starting point
     * @param destiny Point of arrival, place where you want to go
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    Iterator<ILocal> shortestPathBetweenTwoPoints(T source, T destiny) throws NotPlaceInstanceException;

    /**
     * Shortest path considering crossing only through portals.
     * @param source starting point, starting point
     * @param listOfPortals list of existing portals
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    Iterator<ILocal> shortestPathWithOnlyPortals(T source, ListADT<String> listOfPortals) throws NotPlaceInstanceException;

    /**
     * Shortest path with crossing only by connectors.
     * @param source starting point, starting point
     * @param listOfConnectores list of existing connectors
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    Iterator<ILocal> shortestPathWithOnlyConnectors(T source, ListADT<String> listOfConnectores)throws NotPlaceInstanceException;


}
