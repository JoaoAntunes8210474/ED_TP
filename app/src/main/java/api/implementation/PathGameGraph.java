package api.implementation;

import java.util.Iterator;

import api.exceptions.NotPlaceInstanceException;
import api.interfaces.IConnector;
import api.interfaces.ILocal;
import api.interfaces.IPathGameGraphADT;
import api.interfaces.IPortal;
import api.interfaces.IRoute;
import collections.exceptions.EmptyCollectionException;
import collections.exceptions.NullException;
import collections.implementation.ArrayUnorderedList;
import collections.implementation.LinkedQueue;
import collections.implementation.LinkedStack;
import collections.implementation.MatrixGraph;
import collections.interfaces.ListADT;
import collections.interfaces.UnorderedListADT;

public class PathGameGraph<T> extends MatrixGraph<T> implements IPathGameGraphADT <T>{

        private enum SearchType {
            CONNECTOR_REQUIRED,
            PORTAL_ONLY,
            CONNECTOR_ONLY,
        }

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
        private Iterator<Integer> iteratorShortestPathIndices(SearchType typeOfSearch, int startIndex, int targetIndex) throws EmptyCollectionException, NullException {
            int index = startIndex;
            int[] pathLength = new int[this.numVertices];
            int[] predecessor = new int[this.numVertices];
            LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
            ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();

            if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)
                    || (startIndex == targetIndex)) {
                return resultList.iterator();
            }

            boolean[] visited = new boolean[this.numVertices];
            for (int i = 0; i < this.numVertices; i++) {
                visited[i] = false;
            }

            traversalQueue.enqueue(startIndex);
            visited[startIndex] = true;
            pathLength[startIndex] = 0;
            predecessor[startIndex] = -1;

            if (typeOfSearch == SearchType.CONNECTOR_ONLY) {
                while (!traversalQueue.isEmpty() && (index != targetIndex)) {
                    index = (traversalQueue.dequeue());

                    // Update the pathLength for each unvisited vertex adjacent to the vertex at the current index
                    for (int i = 0; i < this.numVertices; i++) {
                        if (this.adjMatrix[index][i] && !visited[i] && this.vertices[i] instanceof IConnector) {
                            pathLength[i] = pathLength[index] + 1;
                            predecessor[i] = index;
                            traversalQueue.enqueue(i);
                            visited[i] = true;
                        }
                    }
                }
            } else if (typeOfSearch == SearchType.PORTAL_ONLY) {
                while (!traversalQueue.isEmpty() && (index != targetIndex)) {
                    index = (traversalQueue.dequeue());

                    // Update the pathLength for each unvisited vertex adjacent to the vertex at the current index
                    for (int i = 0; i < this.numVertices; i++) {
                        if (this.adjMatrix[index][i] && !visited[i] && this.vertices[i] instanceof IPortal) {
                            pathLength[i] = pathLength[index] + 1;
                            predecessor[i] = index;
                            traversalQueue.enqueue(i);
                            visited[i] = true;
                        }
                    }
                }
            } else if (typeOfSearch == SearchType.CONNECTOR_REQUIRED) {
                boolean visitedConnector = false;

                while (!traversalQueue.isEmpty() && (index != targetIndex)) {
                    index = (traversalQueue.dequeue());

                    // Update the pathLength for each unvisited vertex adjacent to the vertex at the current index
                    for (int i = 0; i < this.numVertices; i++) {
                        if (this.adjMatrix[index][i] && !visited[i]) {
                            pathLength[i] = pathLength[index] + 1;
                            predecessor[i] = index;
                            traversalQueue.enqueue(i);
                            visited[i] = true;

                            // if the vertex is a connector, mark it as visited
                            if (this.vertices[i] instanceof IConnector) {
                                visitedConnector = true;
                            }
                        }
                    }

                    // if the target index was reached and a connector was visited, break the loop
                    // otherwise, if a connector was not visited, continue the loop
                    if (index == targetIndex && visitedConnector) {
                        break;
                    }

                }
            }

            // no path must have been found
            if (index != targetIndex) {
                return resultList.iterator();
            }

            LinkedStack<Integer> stack = new LinkedStack<>();
            index = targetIndex;
            stack.push(index);
            do {
                index = predecessor[index];
                stack.push(index);
            } while (index != startIndex);

            while (!stack.isEmpty()) {
                resultList.addToRear(stack.pop());
            }

            return resultList.iterator();
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
            Iterator<ILocal> iterator = (Iterator<ILocal>) super.iteratorShortestPath(source, destiny);
            return iterator;
        }
    
        /**
         * Shortest path considering crossing only through portals.
         * @param source starting point, starting point
         * @param destiny Point of arrival, place where you want to go
         * @return iterator with the path.
         * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
         */
        @Override
        public Iterator<ILocal> shortestPathWithOnlyPortals(T source, T destiny) throws NotPlaceInstanceException{
            ArrayUnorderedList<ILocal> resultList = new ArrayUnorderedList<>();
            if (!indexIsValid(this.getIndex(source)) || !indexIsValid(this.getIndex(destiny))) {
                return resultList.iterator();
            }

            Iterator<Integer> it;
            try {
                it = iteratorShortestPathIndices(SearchType.PORTAL_ONLY, this.getIndex(source), this.getIndex(destiny));

                while (it.hasNext()) {
                    resultList.addToRear((ILocal) this.vertices[it.next()]);
                }

            } catch (EmptyCollectionException | NullException e) {
                e.printStackTrace();
            }

            return resultList.iterator();
        }
    
        /**
         * Shortest path with crossing only by connectors.
         * @param source starting point, starting point
         * @param destiny Point of arrival, place where you want to go
         * @return iterator with the path.
         * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
         */
        @Override
        public Iterator<ILocal> shortestPathWithOnlyConnectors(T source, T destiny) throws NotPlaceInstanceException{
            //TODO: Copy logic from shortestPathWithOnlyPortals
            Iterator<ILocal> iteratorConnectorsOnly = null;
            try {
                iteratorConnectorsOnly = (Iterator<ILocal>) this.iteratorShortestPathIndices(SearchType.CONNECTOR_ONLY, this.getIndex(source), this.getIndex(destiny));
            } catch (EmptyCollectionException | NullException e) {
                throw new RuntimeException(e);
            }

            return iteratorConnectorsOnly;
        }

        @Override
        public Iterator<ILocal> shortestPathAtleastOneConnector(T source, T destiny) throws NotPlaceInstanceException{
            //TODO: Copy logic from shortestPathWithOnlyPortals
            Iterator<ILocal> iteratorAtleastOneConnector = null;
            try {
                iteratorAtleastOneConnector = (Iterator<ILocal>) this.iteratorShortestPathIndices(SearchType.CONNECTOR_REQUIRED, this.getIndex(source), this.getIndex(destiny));
            } catch (EmptyCollectionException | NullException e) {
                throw new RuntimeException(e);
            }

            return iteratorAtleastOneConnector;
        }
    
    }