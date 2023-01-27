package api.interfaces;

/**
 * Contract of a Route Class.
 */
public interface IRoute {

    /**
     * Get id of the location where the player is currently.
     *
     * @return integer number that represents the id of the location where the player is at the moment.
     */
    public int getFrom();

    /**
     * Get the id of the location where the player wants to move.
     *
     * @return integer number that represents the id of the place where the player wants to go.
     */
    public int getTo();

    /**
     * Set or change the id of the location where the player is currently located.
     *
     * @param from integer number that represents the id of the location where the player is at the moment.
     */
    public void setFrom(int from);

    /**
     * Set or change the id of the place where the player wants to move
     *
     * @param to integer number that represents the id of the place where the player wants to go.
     */
    public void setTo(int to);

    /**
     * Route representative string.
     *
     * @return Route representative string.
     */
    @Override
    public String toString();


}
