package api.implementation;

import org.json.simple.JSONObject;


import api.interfaces.ILocal;

/**
 * Class that represents a location
 * Class that implements the contract of a ILocal.
 */
public abstract class Local implements ILocal, Comparable<Local>{
    
    //Integer representing the unique identifier of each location
    private int id;

    // Location name, the name will be the name of points of interest like statues, churches ...
    private String name;

    //Location type, can be portal or connector type.
    private String localType;

    //Amount of energy the site contains.
    private int amountEnergyItHas;

    //Location coordinates
    private Coordinates coordinates;


    /**
     * Constructor: instantiates objects of type local.
     *
     * @param id                Integer representing the unique identifier of each location
     * @param name              Location name, the name will be the name of points of interest like statues, churches ...
     * @param localType         Location type, can be portal or connector type.
     * @param amountEnergyItHas Amount of energy the site contains.
     * @param coordinates       Location coordinates
     */
    public Local(int id, String name, String localType, int amountEnergyItHas, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.localType = localType;
        this.amountEnergyItHas = amountEnergyItHas;
        this.coordinates = coordinates;
    }

    /**
     * Get unique location identification number.
     *
     * @return The integer representing the unique identifier of each location
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Get place name.
     *
     * @return The location name, the name will be the name of points of interest like statues, churches ...
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get location type
     *
     * @return The location type, can be portal or connector type.
     */
    @Override
    public String getLocalType() {
        return localType;
    }

    /**
     * Get amount of energy the location has.
     *
     * @return The amount of energy the site contains.
     */
    @Override
    public int getAmountEnergyItHas() {
        return amountEnergyItHas;
    }

    /**
     * Get location coordinates.
     *
     * @return The location coordinates
     */
    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Set or change a location's unique ID number
     *
     * @param id The integer representing the unique identifier of each location
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set or change the location name.
     *
     * @param name Location name, the name will be the name of points of interest like statues, churches ...
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set or change the location type.
     *
     * @param localType Location type, can be portal or connector type.
     */
    @Override
    public void setLocalType(String localType) {
        this.localType = localType;
    }

    /**
     * Set or change the amount of energy the location has.
     *
     * @param amountEnergyItHas Amount of energy the site contains.
     */
    @Override
    public void setAmountEnergyItHas(int amountEnergyItHas) {
        this.amountEnergyItHas = amountEnergyItHas;
    }

    /**
     * Set or change location coordinates.
     *
     * @param coordinates Location coordinates
     */
    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Aims to return a string representative of the location.
     *
     * @return string representative of the location.
     */
    @Override
    public String toString() {
        return "Local{" + "id=" + id + ", name=" + name + ", localType=" + localType + ", amountEnergyItHas=" + amountEnergyItHas + ", coordinates=" + coordinates + '}';
    }
    
    /**
     * Compares whether two objects are equal behind the name field
     * @return Returns true if the objects are equal, or false if the objects are not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ILocal that = (ILocal) o;
        return getName().equals(that.getName());
    }
    
    /**
     * 
     * @param local
     * @return
     */
    @Override
    public int compareTo(Local local) {
        if (this.getAmountEnergyItHas() > local.getAmountEnergyItHas()) {
            return 1;
        } else if (this.getAmountEnergyItHas() > local.getAmountEnergyItHas()) {
            return 0;
        } else {
            return -1;
        }
    }


    /**
    * Transforms the local into a JSONObject representation
    * @return The JSONObject with all the details of the local
    */
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject localToJSONObject() {
        JSONObject root = new JSONObject();
        root.put("Id", getId());
        root.put("Name", getName());
        root.put("Local Type", getLocalType());
        root.put("Amount Energy It Has", getAmountEnergyItHas());
        root.put("Cordinates", getCoordinates());
        return root;
    }
    
}
