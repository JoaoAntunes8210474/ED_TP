
package api.interfaces;

/**
 *
 * @author reginaneto
 */
public interface IPlayer {
    
    public String getName();

    public String getTeam();

    public int getLevel();

    public long getExperiencePoints();

    public int getCurrentEnergy();

    public int getTimeConnector();

    public int getNumPortals();

    public void setName(String name);

    public void setTeam(String team);

    public void setLevel(int level);

    public void setExperiencePoints(long experiencePoints);

    public void setCurrentEnergy(int currentEnergy);

    public void setTimeConnector(int timeConnector);

    public void setNumPortals(int numPortals);

    @Override
    public String toString();
    
}
