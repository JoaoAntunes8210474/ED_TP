
package api.implementation;
import api.interfaces.ILocal;
/**
 *
 * 
 */
public class Local implements ILocal {
    
    private int id;
    
    private String name;
    
    private String localType;
    
    private int amountEnergyItHas;
    
    private Coordinates coordinates;

    
    
    
    public Local(int id, String name, String localType, int amountEnergyItHas, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.localType = localType;
        this.amountEnergyItHas = amountEnergyItHas;
        this.coordinates = coordinates;
    }

    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLocalType() {
        return localType;
    }

    @Override
    public int getAmountEnergyItHas() {
        return amountEnergyItHas;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    
    
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setLocalType(String localType) {
        this.localType = localType;
    }

    @Override
    public void setAmountEnergyItHas(int amountEnergyItHas) {
        this.amountEnergyItHas = amountEnergyItHas;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    
    @Override
    public String toString() {
        return "Local{" + "id=" + id + ", name=" + name + ", localType=" + localType + ", amountEnergyItHas=" + amountEnergyItHas + ", coordinates=" + coordinates + '}';
    }
    
    
}
