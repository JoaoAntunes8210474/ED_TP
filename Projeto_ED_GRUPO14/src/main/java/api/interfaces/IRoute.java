
package api.interfaces;

/**
 *
 * @author reginaneto
 */
public interface IRoute {
    
    public int getFrom();

    public int getTo();

    public void setFrom(int from);

    public void setTo(int to);

    @Override
    public String toString();
    
    
}
