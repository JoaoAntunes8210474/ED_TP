
package api.interfaces;

import org.json.simple.JSONObject;

/**
 *
 *  Contract of a Route Class.
 */
public interface IRoute <T>{
    
    /**
     * Get id of the location where the player is currently.
     * @return integer number that represents the id of the location where the player is at the moment.
     */
    public T getFrom();

    /**
     * Get the id of the location where the player wants to move.
     * @return integer number that represents the id of the place where the player wants to go.
     */
    public T getTo();

    /**
     * Set or change the id of the location where the player is currently located.
     * @param from integer number that represents the id of the location where the player is at the moment.
     */
    public void setFrom(T from);

    /**
     * Set or change the id of the place where the player wants to move
     * @param to integer number that represents the id of the place where the player wants to go.
     */
    public void setTo(T to);

    /**
     * Transform a path (between two locals of the graph) sent by parameter into a JSONObject
     * @return the JSONObject of that path
     */
    private JSONObject routeToJSONObject(IRoute<ILocal> route);

    /**
     * Route representative string.
     * @return Route representative string.
     */
    @Override
    public String toString();
    
    
}
