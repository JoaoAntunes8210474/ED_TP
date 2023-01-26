package api.implementation;

import java.util.Iterator;

import api.exceptions.NotPlaceInstanceException;
import api.interfaces.ILocal;
import api.interfaces.IPathGameGraphADT;
import api.interfaces.IPortal;
import collections.implementation.MatrixGraph;
import collections.interfaces.ListADT;

public class PathGameGraph<T> extends MatrixGraph<T> implements IPathGameGraphADT <T>{
    
    /**
     * Gets the number of {@link Local locals} in graph.
     * @return the number of {@link Local locals} in graph.
     */
    @Override
    public int getNumberOfLocals(){

    }

    /**
     * Gets the number of {@link Connector connectores} in graph.
     * @return the number of {@link Connector connectores} in graph.
     */
    @Override
    public int getNumberOfConnectores(){

    }

    /**
     * Gets the number of {@link Portal portals} in graph.
     * @return the number of {@link Portal portals} in graph.
     */
    @Override
    public int getNumberOfPortals(){

    }

    /**
     * Gets the connectores on graph.
     * @return iterator of connectores.
     */
    @Override
    Iterator<IConnector> getConnectores(){

    }

    /**
     * Gets the Portals on graph.
     * @return iterator of portals.
     */
    @Override
    Iterator<IPortal> getPortals(){

    }

    /**
     * Gets the paths existences on the graph
     * @returnterator with those paths
     */
    @Override
    Iterator<ILocal> getRoutes(){

    }

    /**
     * Shortest path between two points.
     * @param source starting point, starting point
     * @param destiny Point of arrival, place where you want to go
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    Iterator<ILocal> shortestPathBetweenTwoPoints(T source, T destiny) throws NotPlaceInstanceException{

    }

    /**
     * Shortest path considering crossing only through portals.
     * @param source starting point, starting point
     * @param listOfPortals list of existing portals
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    Iterator<ILocal> shortestPathWithOnlyPortals(T source, ListADT<String> listOfPortals) throws NotPlaceInstanceException{

    }

    /**
     * Shortest path with crossing only by connectors.
     * @param source starting point, starting point
     * @param listOfConnectores list of existing connectors
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    Iterator<ILocal> shortestPathWithOnlyConnectors(T source, ListADT<String> listOfConnectores)throws NotPlaceInstanceException{

    }

}
