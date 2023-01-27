package api.implementation;

import org.json.simple.JSONObject;

import api.interfaces.ILocal;
import api.interfaces.IRoute;

/**
 * Class representing a route between two locations
 * Class that complies with the IRoute interface contract.
 */
public class Route<T> implements IRoute <T>{
    
    //integer number that represents the id of the location where the player is at the moment.
    private T from;
    
    //integer number that represents the id of the place where the player wants to go.
    private T to;

    /**
     * Constructor method used to instantiate objects of type route.
     *
     * @param from integer number that represents the id of the location where the player is at the moment.
     * @param to   integer number that represents the id of the place where the player wants to go.
     */
    public Route(T from, T to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Get id of the location where the player is currently.
     *
     * @return integer number that represents the id of the location where the player is at the moment.
     */
    @Override
    public T getFrom() {
        return from;
    }

    /**
     * Get the id of the location where the player wants to move.
     *
     * @return integer number that represents the id of the place where the player wants to go.
     */
    @Override
    public T getTo() {
        return to;
    }

    /**
     * Set or change the id of the location where the player is currently located.
     *
     * @param from integer number that represents the id of the location where the player is at the moment.
     */
    @Override
    public void setFrom(T from) {
        this.from = from;
    }

    /**
     * Set or change the id of the place where the player wants to move
     *
     * @param to integer number that represents the id of the place where the player wants to go.
     */
    @Override
    public void setTo(T to) {
        this.to = to;
    }


     /**
     * Transform a path (between two locals of the graph) sent by parameter into a JSONObject
     * @return the JSONObject of that path
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject routeToJSONObject(IRoute<ILocal> route) {
        JSONObject pathObject = new JSONObject();
        pathObject.put("From", route.getFrom().getName());
        pathObject.put("To", route.getTo().getName());
        return pathObject;
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
