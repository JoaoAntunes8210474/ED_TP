package api.implementation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import api.exceptions.NotPlaceInstanceException;
import api.interfaces.IConnector;
import api.interfaces.ILocal;
import api.interfaces.IPathGameGraphADT;
import api.interfaces.IPortal;
import api.interfaces.IRoute;
import collections.exceptions.EmptyCollectionException;
import collections.exceptions.NullException;
import collections.implementation.*;
import collections.implementation.ArrayUnorderedList;
import collections.interfaces.UnorderedListADT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * PathGameGraph class.
 *
 * @param <T> type of the graph.
 */
public class PathGameGraph<T> extends MatrixGraph<T> implements IPathGameGraphADT <T> {

    /**
     * Enum to represent the type of search.
     */
    private enum SearchType {
        CONNECTOR_REQUIRED,
        PORTAL_ONLY,
        CONNECTOR_ONLY,
    }

    /**
     * Constructor of PathGameGraph.
     */
    public PathGameGraph() {
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
     * Returns the iterator of the shortest path between two vertices determined by the type of search.
     * @param typeOfSearch type of search.
     * @param startIndex start index.
     * @param targetIndex target index.
     * @return iterator of the shortest path.
     * @throws EmptyCollectionException if the collection is empty.
     * @throws NullException if the object is null.
     */
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

        ArrayUnorderedList<Integer> visitedConnectors = new ArrayUnorderedList<>();

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
            int numberOfTimesStartIndexIsVisited = 0;
            while (!traversalQueue.isEmpty()) {
                index = (traversalQueue.dequeue());

                // Update the pathLength for each unvisited vertex adjacent to the vertex at the current index
                for (int i = 0; i < this.numVertices; i++) {
                    if (this.adjMatrix[index][i] && !visited[i]) {
                        pathLength[i] = pathLength[index] + 1;
                        predecessor[i] = index;
                        traversalQueue.enqueue(i);
                        visited[i] = true;

                        Local local = (Local) this.vertices[i];

                        // if the vertex is a connector, mark it as visited
                        if (local.getLocalType().equals("Connector")) {
                            visitedConnectors.addToRear(i);
                        }
                    }
                }

                // if the target index was reached and a connector was visited, break the loop
                // otherwise, if a connector was not visited, continue the loop
                if (index == targetIndex && !visitedConnectors.isEmpty()) {
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
     * Returns the local with the given index.
     *
     * @param index index of local.
     * @return local with the given index.
     */
    public ILocal get(int index) {
        return (ILocal) this.vertices[index];
    }

    /**
     * Gets the number of {@link Connector connectores} in graph.
     *
     * @return the number of {@link Connector connectores} in graph.
     */
    @Override
    public int getNumberOfConnectores() {
        return this.getNumberOf(new Connector(3, 1, "Igreja São Pedro", 100, new Coordinates(90, 45)));
    }

    /**
     * Gets the number of {@link Portal portals} in graph.
     *
     * @return the number of {@link Portal portals} in graph.
     */
    @Override
    public int getNumberOfPortals() {
        return this.getNumberOf(new Portal(100, 5, "Palácio da Pena", 50, new Coordinates(97.55, 60.79)));
    }

    /**
     * Gets the connectores on graph.
     *
     * @return iterator of connectores.
     */
    @Override
    public Iterator<IConnector> getConnectores() {
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
     *
     * @return iterator of portals.
     */
    @Override
    public Iterator<IPortal> getPortals() {
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
     *
     * @return iterator of paths.
     */
    @Override
    public Iterator<IRoute<ILocal>> getRoutes() {
        UnorderedListADT<IRoute<ILocal>> resultList = new ArrayUnorderedList<>();
        for (int i = 0; i < super.numVertices; i++) {
            for (int j = i; j < super.numVertices; j++) {
                if (super.adjMatrix[i][j]) {
                    Local tempVertix1 = (Local) super.vertices[i];
                    Local tempVertix2 = (Local) super.vertices[j];
                    Route<ILocal> path = new Route(tempVertix1.getId(), tempVertix2.getId());
                    resultList.addToRear(path);
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Shortest path between two points.
     *
     * @param source  starting point, starting point
     * @param destiny Point of arrival, place where you want to go
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    public Iterator<ILocal> shortestPathBetweenTwoPoints(T source, T destiny) throws NotPlaceInstanceException {
        if (!(source instanceof ILocal) || !(destiny instanceof ILocal)) {
            throw new NotPlaceInstanceException("Source or destiny is not a ILocal instance.");
        }

        Iterator<ILocal> iterator = (Iterator<ILocal>) super.iteratorShortestPath(source, destiny);
        return iterator;
    }

    /**
     * Shortest path considering crossing only through portals.
     *
     * @param source  starting point, starting point
     * @param destiny Point of arrival, place where you want to go
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    public Iterator<ILocal> shortestPathWithOnlyPortals(T source, T destiny) throws NotPlaceInstanceException {
        if (!(source instanceof ILocal) || !(destiny instanceof ILocal)) {
            throw new NotPlaceInstanceException("Source or destiny is not a ILocal instance.");
        }

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
     *
     * @param source  starting point, starting point
     * @param destiny Point of arrival, place where you want to go
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    public Iterator<ILocal> shortestPathWithOnlyConnectors(T source, T destiny) throws NotPlaceInstanceException {
        if (!(source instanceof ILocal) || !(destiny instanceof ILocal)) {
            throw new NotPlaceInstanceException("Source or destiny is not a ILocal instance.");
        }

        ArrayUnorderedList<ILocal> resultList = new ArrayUnorderedList<>();
        if (!indexIsValid(this.getIndex(source)) || !indexIsValid(this.getIndex(destiny))) {
            return resultList.iterator();
        }

        Iterator<Integer> it;
        try {
            it = iteratorShortestPathIndices(SearchType.CONNECTOR_ONLY, this.getIndex(source), this.getIndex(destiny));

            while (it.hasNext()) {
                resultList.addToRear((ILocal) this.vertices[it.next()]);
            }

        } catch (EmptyCollectionException | NullException e) {
            e.printStackTrace();
        }

        return resultList.iterator();
    }

    /**
     * Shortest path between two points, crossing through at least one connector.
     * @param source starting point, starting point
     * @param destiny Point of arrival, place where you want to go
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    @Override
    public Iterator<ILocal> shortestPathAtleastOneConnector(T source, T destiny) throws NotPlaceInstanceException {
        if (!(source instanceof ILocal) || !(destiny instanceof ILocal)) {
            throw new NotPlaceInstanceException("Source or destiny is not a ILocal instance.");
        }

        ArrayUnorderedList<ILocal> resultList = new ArrayUnorderedList<>();
        if (!indexIsValid(this.getIndex(source)) || !indexIsValid(this.getIndex(destiny))) {
            return resultList.iterator();
        }

        Iterator<Integer> it;
        try {
            it = iteratorShortestPathIndices(SearchType.CONNECTOR_REQUIRED, this.getIndex(source), this.getIndex(destiny));

            while (it.hasNext()) {
                resultList.addToRear((ILocal) this.vertices[it.next()]);
            }

        } catch (EmptyCollectionException | NullException e) {
            e.printStackTrace();
        }

        return resultList.iterator();
    }

    /**
     * Shortest path with crossing only by portals and connectors.
     *
     * @param vertex starting point, starting point
     * @return iterator with the path.
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    public ArrayUnorderedList<ILocal> getNeighbours(T vertex) throws NotPlaceInstanceException {
        if (!(vertex instanceof ILocal)) {
            throw new NotPlaceInstanceException("Vertex is not a IPortal instance");
        }

        ArrayUnorderedList<ILocal> neighours = new ArrayUnorderedList<>();
        int index = super.getIndex(vertex);
        int i;

        for (i = 0; i < super.numVertices; i++) {
            if (super.adjMatrix[i][index]) {
                neighours.addToRear((ILocal) super.vertices[i]);
            }
        }

        for (i = 0; i < super.numVertices; i++) {
            if (super.adjMatrix[index][i] && !neighours.contains((ILocal) super.vertices[i])) {
                neighours.addToRear((ILocal) super.vertices[i]);
            }
        }

        return neighours;
    }

    /**
     * Exports the shortest path between two points to a json file.
     * @param source starting point
     * @param destiny destiny point
     * @param fileName name of the file
     * @throws NotPlaceInstanceException if start point is not {@link ILocal local} instance.
     */
    public void exportShortestPathBetweenTwoPoints(T source, T destiny, String fileName) throws NotPlaceInstanceException {
        if (!(source instanceof ILocal) || !(destiny instanceof ILocal)) {
            throw new NotPlaceInstanceException("Source or destiny is not a ILocal instance.");
        }

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name is null or empty.");
        }

        if (!fileName.endsWith(".json")) {
            throw new IllegalArgumentException("File name must end with .json");
        }

        int counterIfInsideGraph = 0;

        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].toString().equals(source.toString()) || this.vertices[i].toString().equals(destiny.toString())) {
                counterIfInsideGraph++;
            }
        }

        if (counterIfInsideGraph != 2) {
            throw new IllegalArgumentException("Source or destiny is not inside the graph.");
        }

        Iterator<ILocal> iterator = shortestPathBetweenTwoPoints(source, destiny);
        JSONArray localsArray = new JSONArray();

        while (iterator.hasNext()) {
            ILocal local = iterator.next();
            if (local.getLocalType().equals("Portal")) {
                localsArray.add(((IPortal) local).portalToJSONObject());
            } else {
                localsArray.add(((IConnector) local).connectorToJSONObject());
            }
        }

        JSONObject jsonPath = new JSONObject();
        jsonPath.put("shortestPathBetweenTwoPoints", localsArray);

        Gson beautify = new GsonBuilder().setPrettyPrinting().create();
        String json = beautify.toJson(jsonPath);

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
        }
    }

    /**
     * Exports the shortest path between two points into a json file going only through portals.
     * @param source starting point
     * @param destiny destiny point
     * @param fileName name of the file
     * @throws NotPlaceInstanceException if source or destiny is not a {@link ILocal local} instance.
     */
    public void exportShortestPathWithOnlyPortals(T source, T destiny, String fileName) throws NotPlaceInstanceException {
        if (!(source instanceof ILocal) || !(destiny instanceof ILocal)) {
            throw new NotPlaceInstanceException("Source or destiny is not a ILocal instance.");
        }

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name is null or empty.");
        }

        if (!fileName.endsWith(".json")) {
            throw new IllegalArgumentException("File name must end with .json");
        }

        int counterIfInsideGraph = 0;

        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].toString().equals(source.toString()) || this.vertices[i].toString().equals(destiny.toString())) {
                counterIfInsideGraph++;
            }
        }

        if (counterIfInsideGraph != 2) {
            throw new IllegalArgumentException("Source or destiny is not inside the graph.");
        }

        Iterator<ILocal> iterator = this.shortestPathWithOnlyPortals(source, destiny);
        JSONArray localsArray = new JSONArray();

        while (iterator.hasNext()) {
            ILocal local = iterator.next();
            if (local.getLocalType().equals("Portal")) {
                localsArray.add(((IPortal) local).portalToJSONObject());
            } else {
                localsArray.add(((IConnector) local).connectorToJSONObject());
            }
        }

        JSONObject jsonPath = new JSONObject();
        jsonPath.put("shortestPathBetweenTwoPointsPortalsOnly", localsArray);

        Gson beautify = new GsonBuilder().setPrettyPrinting().create();
        String json = beautify.toJson(jsonPath);

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
        }
    }

    /**
     * Exports the shortest path between two points into a json file going only through connectors.
     * @param source starting point
     * @param destiny destiny point
     * @param fileName name of the file
     * @throws NotPlaceInstanceException if source or destiny is not a {@link ILocal local} instance.
     */
    public void exportShortestPathWithOnlyConnectors(T source, T destiny, String fileName) throws NotPlaceInstanceException {
        if (!(source instanceof ILocal) || !(destiny instanceof ILocal)) {
            throw new NotPlaceInstanceException("Source or destiny is not a ILocal instance.");
        }

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name is null or empty.");
        }

        if (!fileName.endsWith(".json")) {
            throw new IllegalArgumentException("File name must end with .json");
        }

        int counterIfInsideGraph = 0;

        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].toString().equals(source.toString()) || this.vertices[i].toString().equals(destiny.toString())) {
                counterIfInsideGraph++;
            }
        }

        if (counterIfInsideGraph != 2) {
            throw new IllegalArgumentException("Source or destiny is not inside the graph.");
        }

        Iterator<ILocal> iterator = this.shortestPathWithOnlyConnectors(source, destiny);
        JSONArray localsArray = new JSONArray();

        while (iterator.hasNext()) {
            ILocal local = iterator.next();
            if (local.getLocalType().equals("Portal")) {
                localsArray.add(((IPortal) local).portalToJSONObject());
            } else {
                localsArray.add(((IConnector) local).connectorToJSONObject());
            }
        }

        JSONObject jsonPath = new JSONObject();
        jsonPath.put("shortestPathBetweenTwoPointsConnectorsOnly", localsArray);

        Gson beautify = new GsonBuilder().setPrettyPrinting().create();
        String json = beautify.toJson(jsonPath);

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
        }
    }

    /**
     * Exports the shortest path between two points into a json file going through at least one connector.
     * @param source starting point
     * @param destiny destiny point
     * @param fileName name of the file
     * @throws NotPlaceInstanceException if source or destiny is not a {@link ILocal local} instance.
     */
    public void exportShortestPathAtleastOneConnector(T source, T destiny, String fileName) throws NotPlaceInstanceException {
        if (!(source instanceof ILocal) || !(destiny instanceof ILocal)) {
            throw new NotPlaceInstanceException("Source or destiny is not a ILocal instance.");
        }

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name is null or empty.");
        }

        if (!fileName.endsWith(".json")) {
            throw new IllegalArgumentException("File name must end with .json");
        }

        int counterIfInsideGraph = 0;

        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertices[i].toString().equals(source.toString()) || this.vertices[i].toString().equals(destiny.toString())) {
                counterIfInsideGraph++;
            }
        }

        if (counterIfInsideGraph != 2) {
            throw new IllegalArgumentException("Source or destiny is not inside the graph.");
        }

        Iterator<ILocal> iterator = this.shortestPathAtleastOneConnector(source, destiny);
        JSONArray localsArray = new JSONArray();

        while (iterator.hasNext()) {
            ILocal local = iterator.next();
            if (local.getLocalType().equals("Portal")) {
                localsArray.add(((IPortal) local).portalToJSONObject());
            } else {
                localsArray.add(((IConnector) local).connectorToJSONObject());
            }
        }

        JSONObject jsonPath = new JSONObject();
        jsonPath.put("shortestPathBetweenTwoPointsAtleastOneConnector", localsArray);

        Gson beautify = new GsonBuilder().setPrettyPrinting().create();
        String json = beautify.toJson(jsonPath);

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
        }
    }
}
