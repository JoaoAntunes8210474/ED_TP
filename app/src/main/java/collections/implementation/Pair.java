package collections.implementation;

/**
 * This class is used to store the previous vertex, the current vertex and the cost of the path
 * @param <T> The type of the vertex
 */
public class Pair<T> {

    /**
     * The previous vertex
     */
    protected Pair<T> previous;

    /**
     * The current vertex
     */
    protected T vertex;

    /**
     * The cost of the path
     */
    protected double cost;

    /**
     * Constructor
     * @param previous The previous vertex
     * @param vertex The current vertex
     * @param cost The cost of the path
     */
    public Pair(Pair<T> previous, T vertex, double cost) {
        this.previous = previous;
        this.vertex = vertex;
        this.cost = cost;
    }

    /**
     * Returns the previous vertex
     * @return The previous vertex
     */
    public Pair<T> getPrevious() {
        return previous;
    }

    /**
     * Sets the previous vertex
     * @param previous The previous vertex
     */
    public void setPrevious(Pair<T> previous) {
        this.previous = previous;
    }

    /**
     * Returns the current vertex
     * @return The current vertex
     */
    public T getVertex() {
        return vertex;
    }

    /**
     * Sets the current vertex
     * @param vertex The current vertex
     */
    public void setVertex(T vertex) {
        this.vertex = vertex;
    }

    /**
     * Returns the cost of the path
     * @return The cost of the path
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost of the path
     * @param cost The cost of the path
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
}
