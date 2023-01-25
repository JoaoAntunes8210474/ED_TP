
package api.implementation;
import api.interfaces.IConnector;
/**
 *
 * @author reginaneto
 */
public class Connector extends Local implements IConnector{
    
private int cooldown;
private int DEFAULT_MAX_CAPACITY = 1000;
private Player[] players;

    public Connector(int cooldown, Player[] players, int id, String name, String localType, int amountEnergyItHas, Coordinates coordinates) {
        super(id, name, localType, amountEnergyItHas, coordinates);
        this.cooldown = cooldown;
        this.players = new Player[DEFAULT_MAX_CAPACITY];
    }

    @Override
    public int getCooldown() {
        return cooldown;
    }


    public int getDEFAULT_MAX_CAPACITY() {
        return DEFAULT_MAX_CAPACITY;
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }


    public void setDEFAULT_MAX_CAPACITY(int DEFAULT_MAX_CAPACITY) {
        this.DEFAULT_MAX_CAPACITY = DEFAULT_MAX_CAPACITY;
    }

    @Override
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Connector{" + "cooldown=" + cooldown + ", DEFAULT_MAX_CAPACITY=" + DEFAULT_MAX_CAPACITY + ", players=" + players + '}';
    }


}