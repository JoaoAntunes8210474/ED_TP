package api.implementation;

import java.time.LocalTime;

/**
 * Class that represents the interaction of a specific player with a specific connector.
 */
public class ConnectorPlayerInteration {
    
    // Player who interacted with the connector.
    private Player player;
    
    //Date and time the player interacted with the connector
    private LocalTime horaInteracao;

    /**
     * Constructor method serves to instantiate objects of type ConnectorPlayerInteration.
     * @param player Player who interacted with the connector.
     * @param diaHoraInteracao Date and time the player interacted with the connector
     */
    public ConnectorPlayerInteration(Player player, LocalTime horaInteracao) {
        this.player = player;
        this.horaInteracao = horaInteracao;
    }

    /**
     * Get the player who interacted with the connector.
     * @return player Player who interacted with the connector.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Set or change player who interacted with the connector.
     * @param player Player who interacted with the connector.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get date and time when the player interacted with the connector.
     * @return diaHoraInteracao Date and time the player interacted with the connector
     */
    public LocalTime getHoraInteracao() {
        return this.horaInteracao;
    }

    /**
     * Set or change date and time when the player interacted with the connector.
     * @param diaHoraInteracao Date and time the player interacted with the connector
     */
    public void setHoraInteracao(LocalTime horaInteracao) {
        this.horaInteracao = horaInteracao;
    }
  
    /**
     * Get a string representing the interaction between the player and the connector.
     */
    @Override
    public String toString() {
        return "ConnectorPlayerInteration [player=" + player + ", diaHoraInteracao=" + horaInteracao + "]";
    }    
}
