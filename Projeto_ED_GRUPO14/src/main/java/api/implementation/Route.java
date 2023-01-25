
package api.implementation;

import api.interfaces.IRoute;


public class Route implements IRoute {
    
    private int from;
    
    private int to;

    public Route(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public int getFrom() {
        return from;
    }

    @Override
    public int getTo() {
        return to;
    }

    @Override
    public void setFrom(int from) {
        this.from = from;
    }
    
    @Override
    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Route{" + "from=" + from + ", to=" + to + '}';
    }
    
    
    
}
