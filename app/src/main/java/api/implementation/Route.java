package api.implementation;

import api.interfaces.IRoute;

/**
 * Class representing a route between two locations
 * Class that complies with the IRoute interface contract.
 */
public class Route implements IRoute {

    //integer number that represents the id of the location where the player is at the moment.
    private int from;

    //integer number that represents the id of the place where the player wants to go.
    private int to;

    /**
     * Constructor method used to instantiate objects of type route.
     *
     * @param from integer number that represents the id of the location where the player is at the moment.
     * @param to   integer number that represents the id of the place where the player wants to go.
     */
    public Route(int from, int to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Get id of the location where the player is currently.
     *
     * @return integer number that represents the id of the location where the player is at the moment.
     */
    @Override
    public int getFrom() {
        return from;
    }

    /**
     * Get the id of the location where the player wants to move.
     *
     * @return integer number that represents the id of the place where the player wants to go.
     */
    @Override
    public int getTo() {
        return to;
    }

    /**
     * Set or change the id of the location where the player is currently located.
     *
     * @param from integer number that represents the id of the location where the player is at the moment.
     */
    @Override
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * Set or change the id of the place where the player wants to move
     *
     * @param to integer number that represents the id of the place where the player wants to go.
     */
    @Override
    public void setTo(int to) {
        this.to = to;
    }

    /**
     * Route representative string.
     *
     * @return Route representative string.
     */
    @Override
    public String toString() {
        return "Route{" + "from=" + from + ", to=" + to + '}';
    }


}
