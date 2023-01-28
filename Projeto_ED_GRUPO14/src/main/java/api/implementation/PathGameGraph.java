package api.implementation;

import java.util.Iterator;

import api.exceptions.NotPlaceInstanceException;
import api.interfaces.*;
import collections.implementation.MatrixGraph;
import collections.interfaces.ListADT;
import collections.interfaces.UnorderedListADT;
import collections.implementation.ArrayUnorderedList;

public class PathGameGraph<T> extends MatrixGraph<T> implements IPathGameGraphADT <T>{

    /**
     * Constructor of PathGameGraph.
     */
    public PathGameGraph(){
        super();
    }


    /**
     * Counts the number of instances that exists of type.
     *
     * @param type object to be compared.
     * @param <T>  type of to be compared.
     * @return number of times.
     */
    private <T extends ILocal> int getNumberOf(T type) {
        int count = 0;
        for (int i = 0; i < super.numVertices; i++) {
            if (super.vertices[i].getClass().isInstance(type)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Gets the number of {@link Connector connectores} in graph.
     * @return the number of {@link Connector connectores} in graph.
     */
    @Override
    public int getNumberOfConnectores(){
        return this.getNumberOf(new Connector(3,1,"Igreja São Pedro","Connector",100,new Coordinates(90,45)));
    }

    /**
     * Gets the number of {@link Portal portals} in graph.
     * @return the number of {@link Portal portals} in graph.
     */
    @Override
    public int getNumberOfPortals(){
        return this.getNumberOf(new Connector(100,2,"Palácio da Pena","Portal",50,new Coordinates(97.55,60.79)));
    }
    
    /**
     * Gets the connectores on graph.
     * @return iterator of connectores.
     */
    @Override
    public Iterator<IConnector> getConnectores(){
        UnorderedListADT<IConnector> resultList = new ArrayUnorderedList<>();
        for (int i = 0; i < super.numVertices; i++) {
            if (super.vertices[i] instanceof IConnector) {
                resultList.addToRear((IConnector) super.vertices[i]);
            }
        }
        return resultList.iterator();
    }

    /**
     * Gets the Portals on graph.
     * @return iterator of portals.
     */
    @Override
    public Iterator<IPortal> getPortals(){
        UnorderedListADT<IPortal> resultList = new ArrayUnorderedList<>();
        for (int i = 0; i < super.numVertices; i++) {
            if (super.vertices[i] instanceof IPortal) {
                resultList.addToRear((IPortal) super.vertices[i]);
            }
        }
        return resultList.iterator();
    }

    /**
     * Gets the paths existences on the graph
     * @returnterator with those paths
     */
    @Override
    public Iterator<IRoute<ILocal>> getRoutes(){
        UnorderedListADT<IRoute<ILocal>> resultList = new ArrayUnorderedList<>();
        for (int i = 0; i < super.numVertices; i++) {
            for (int j = i; j < super.numVertices; j++) {
                if (super.adjMatrix[i][j]) {
                    Local tempVertix1 = (Local) super.vertices[i];
                    Local tempVertix2 = (Local) super.vertices[j];
                    Route<ILocal> path = new Route(tempVertix1.getId(),tempVertix2.getId());
                    resultList.addToRear(path);
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Shortest path between two points.
     * @param source starting point, starting point
     * @param destiny Point of arrival, place where you want to go
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    public Iterator<ILocal> shortestPathBetweenTwoPoints(T source, T destiny) throws NotPlaceInstanceException{

    }

    /**
     * Shortest path considering crossing only through portals.
     * @param source starting point, starting point
     * @param listOfPortals list of existing portals
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    public Iterator<ILocal> shortestPathWithOnlyPortals(T source, ListADT<String> listOfPortals) throws NotPlaceInstanceException{

    }

    /**
     * Shortest path with crossing only by connectors.
     * @param source starting point, starting point
     * @param listOfConnectores list of existing connectors
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    public Iterator<ILocal> shortestPathWithOnlyConnectors(T source, ListADT<String> listOfConnectores)throws NotPlaceInstanceException{

    }

}
