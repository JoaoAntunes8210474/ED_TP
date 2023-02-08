package collections.implementation;


import collections.exceptions.ElementNotFoundException;
import collections.exceptions.EmptyCollectionException;
import collections.exceptions.NonComparableElementException;
import collections.exceptions.NullException;
import collections.interfaces.GraphADT;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MatrixGraph implements the GraphADT interface using an adjacency matrix.
 * @param <T> the type of elements in this graph
 */
public class MatrixGraph<T> implements GraphADT<T> {

    /**
     * The default capacity of the graph
     */
    protected final int DEFAULT_CAPACITY = 10;

    /**
     * The number of vertices in the graph
     */
    protected int numVertices;

    /**
     * The adjacency matrix
     */
    protected boolean[][] adjMatrix;

    /**
     * The array of values of the vertices
     */
    protected T[] vertices;

    /**
     * Creates an empty graph.
     */
    @SuppressWarnings("unchecked")
    public MatrixGraph() {
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Returns a reference to the vertex array.
     * @return a reference to the vertex array
     */
    public T[] getVertices() {
        return this.vertices;
    }

    /**
     * Adds the specified vertex to this graph.
     * @param vertex the vertex to be added to this graph
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    /**
     * Expands the capacity of the graph by creating a larger array and copying the contents of the old array to the new one.
     * Copies the contents of the old adjacency matrix to the new one with the new capacity.
     */
    @SuppressWarnings("unchecked")
    private void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[vertices.length * 2]);
        boolean[][] largerAdjMatrix = new boolean[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                largerAdjMatrix[i][j] = adjMatrix[i][j];
            }
            largerVertices[i] = vertices[i];
        }

        vertices = largerVertices;
        adjMatrix = largerAdjMatrix;
    }

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param vertex the vertex to be removed from this graph
     */
    @Override
    public void removeVertex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex.toString().equals(vertices[i].toString())) {
                removeVertex(i);
                return;
            }
        }

        throw new ElementNotFoundException("Vertex not found");
    }

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param vertex the vertex to be removed from this graph
     */
    public void removeVertex(int vertex) {
        if (this.indexIsValid(vertex)) {
            this.numVertices--;

            for (int i = vertex; i < this.numVertices; i++) {
                this.vertices[i] = this.vertices[i + 1];
            }
            this.vertices[this.numVertices] = null;

            for (int i = vertex; i < this.numVertices; i++) {
                for (int j = 0; j < this.numVertices; j++) {
                    this.adjMatrix[i][j] = this.adjMatrix[i + 1][j];
                    this.adjMatrix[i + 1][j] = false;
                }
            }

            for (int i = 0; i < this.numVertices; i++) {
                for (int j = vertex; j < this.numVertices; j++) {
                    this.adjMatrix[i][j] = this.adjMatrix[i][j + 1];
                    this.adjMatrix[i][j + 1] = false;
                }
            }

            this.adjMatrix[this.numVertices][this.numVertices] = false;
        }
    }

    /**
     * Adds an edge between two vertices of this graph.
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Returns the index of the vertex in the vertices array.
     * @param vertex the vertex to be found
     * @return the index of the vertex in the vertices array
     */
    public int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].toString().equals(vertex.toString())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds an edge between two vertices of this graph.
     * @param index1 the index of the first vertex
     * @param index2 the index of the second vertex
     */
    private void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Checks if the index is valid. The index is valid if it is greater than or equal to 0 and less than the number of vertices.
     * @param index the index to be checked
     * @return true if the index is valid, false otherwise
     */
    public boolean indexIsValid(int index) {
        return ((index >= 0) && (index < numVertices));
    }

    /**
     * Removes an edge between two vertices of this graph.
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Removes an edge between two vertices of this graph.
     * @param index1 the index of the first vertex
     * @param index2 the index of the second vertex
     */
    public void removeEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }

    /**
     * Returns an iterator that performs a breadth-first traversal starting at the given index.
     * @param startVertex the starting vertex
     * @return an iterator that performs a breadth-first traversal
     */
    @Override
    public Iterator<T> iteratorBFS(T startVertex) {
        return iteratorBFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a breadth-first traversal starting at the given index.
     * @param startIndex the index of the starting vertex
     * @return an iterator that performs a breadth-first traversal
     */
    private Iterator<T> iteratorBFS(int startIndex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            try {
                x = traversalQueue.dequeue();
                resultList.addToRear(vertices[x]);

                // Find all vertices adjacent to x that have not been visited and queue them up
                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[x][i] && !visited[i]) {
                        traversalQueue.enqueue(i);
                        visited[i] = true;
                    }
                }
            } catch (EmptyCollectionException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that checks for the immediate neighbours of a vertex.
     * @param vertex the vertex to be checked
     * @return an iterator that checks for the immediate neighbours of a vertex
     * @throws EmptyCollectionException if the graph is empty
     * @throws NonComparableElementException if the vertex is not comparable
     */
    @SuppressWarnings("unchecked")
    public ArrayOrderedList<T> getNeighbours(Comparable<T> vertex) throws EmptyCollectionException, NonComparableElementException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Removing element in empty graph.");
        } else {
            int index = this.getIndex((T) vertex);
            ArrayOrderedList<T> list = new ArrayOrderedList<>();
            int j;

            for (j = 0; j < this.numVertices; ++j) {
                if (this.adjMatrix[j][index]) {
                    list.add(this.vertices[j]);
                }
            }

            for (j = 0; j < this.numVertices; ++j) {
                if (this.adjMatrix[index][j] && !list.contains(this.vertices[j])) {
                    list.add(this.vertices[j]);
                }
            }

            return list;
        }
    }

    /**
     * Returns an iterator that performs a depth-first traversal starting at the given index.
     * @param startVertex the index of the starting vertex
     * @return an iterator that performs a depth-first traversal
     */
    @Override
    public Iterator<T> iteratorDFS(T startVertex) {
        return iteratorDFS(getIndex(startVertex));
    }

    /**
     * Returns an iterator that performs a depth-first traversal starting at the given index.
     * @param startIndex the index of the starting vertex
     * @return an iterator that performs a depth-first traversal
     */
    private Iterator<T> iteratorDFS(int startIndex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        boolean[] visited = new boolean[numVertices];
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;
        while (!traversalStack.isEmpty()) {
            try {
                x = traversalStack.peek();

                found = false;

                // Find a vertex adjacent to x that has not been visited and push it on the stack
                for (int i = 0; (i < numVertices) && !found; i++) {
                    if (adjMatrix[x][i] && !visited[i]) {
                        traversalStack.push(i);
                        resultList.addToRear(vertices[i]);
                        visited[i] = true;
                        found = true;
                    }
                }
                if (!found && !traversalStack.isEmpty()) {
                    traversalStack.pop();
                }
            } catch (NullException ex) {
                Logger.getLogger(MatrixGraph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultList.iterator();

    }

    /**
     * Returns an iterator that returns the shortest path between two vertices.
     * @param startVertex  the starting vertex
     * @param targetVertex the ending vertex
     * @return an iterator that returns the shortest path between two vertices
     */
    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    /**
     * Returns an iterator that returns the shortest path between two vertices.
     * @param startIndex  the starting vertex
     * @param targetIndex the ending vertex
     * @return an iterator that returns the shortest path between two vertices
     */
    private Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        Iterator<Integer> it;
        try {
            it = iteratorShortestPathIndices(startIndex, targetIndex);

            while (it.hasNext()) {
                resultList.addToRear(vertices[it.next()]);
            }

        } catch (EmptyCollectionException | NullException e) {
            e.printStackTrace();
        }

        return resultList.iterator();
    }

    /**
     * Returns an iterator that returns the shortest path between two vertices as indices.
     * @param startIndex  the starting vertex
     * @param targetIndex the ending vertex
     * @return an iterator that returns the shortest path between two vertices as indices
     */
    private Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) throws EmptyCollectionException, NullException {
        int index = startIndex;
        int[] pathLength = new int[numVertices];
        int[] predecessor = new int[numVertices];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<Integer> resultList = new ArrayUnorderedList<>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)
                || (startIndex == targetIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (index != targetIndex)) {
            index = (traversalQueue.dequeue());

            // Update the pathLength for each unvisited vertex adjacent to the vertex at the current index
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i] && !visited[i]) {
                    pathLength[i] = pathLength[index] + 1;
                    predecessor[i] = index;
                    traversalQueue.enqueue(i);
                    visited[i] = true;
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
     * Checks if the graph is empty.
     * @return true if the graph is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return (this.numVertices == 0);
    }

    /**
     * Checks if the graph is connected.
     * @return true if the graph is connected, false otherwise
     */
    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }

        Iterator<T> it = iteratorBFS(0);
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return (count == numVertices);
    }

    /**
     * Returns the number of vertices in the graph.
     * @return the number of vertices in the graph
     */
    @Override
    public int size() {
        return this.numVertices;
    }

    /**
     * Returns a summary of the iteratorBFS method.
     * @return a summary of the iteratorBFS method
     */
    public String summaryBFS() {
        T startVertex = this.vertices[0];
        Iterator<T> itr = iteratorBFS(startVertex);
        String s = "Breadth-first traversal (Travessia em largura):";
        s += " | ";
        while (itr.hasNext()) {
            s += itr.next() + " | ";
        }
        return s;
    }

    /**
     * Returns a string representation of the graph.
     * @return a string representation of the graph
     */
    @Override
    public String toString() {
        if (numVertices == 0)
            return "Graph is empty";

        String result = "";

        result += "\n\t\tAdjacency Matrix\n";
        result += "\t\t-----------------------------------------\n";
        result += "\t\tindex\t";

        for (int i = 0; i < numVertices; i++) {
            result += " " + i;
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j])
                    result += "1 ";
                else
                    result += "0 ";
            }
            result += "\n";
        }

        result += "\nVertex Values";
        result += "\n-------------\n";
        result += "index\tvalue\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";
            result += vertices[i].toString() + "\n";
        }
        return result;
    }
}

