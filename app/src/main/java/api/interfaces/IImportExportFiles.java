package api.interfaces;

import api.implementation.LocalsManagement;
import api.implementation.PlayerManagement;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;

/**
 * Interface for import and export of JSON files
 */
public interface IImportExportFiles {

    /**
     * Calls the import method of all instances passed through reference
     *
     * @param fileName   of file to import from
     * @param playerList list of all players
     * @param pathGraph  list of all locations
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importJSON(String fileName, PlayerManagement playerList, LocalsManagement pathGraph);

    /**
     * Calls the export method of all instances passed through reference
     *
     * @param fileName   of file to export into
     * @param playerList list of all players
     * @param pathGraph  list of all locations
     * @param fileWriter file writer to write to file
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public  String exportJSON(String fileName, PlayerManagement playerList, LocalsManagement pathGraph, FileWriter fileWriter);
}
