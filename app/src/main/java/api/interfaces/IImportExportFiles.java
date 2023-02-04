package api.interfaces;

import api.implementation.LocalsManagement;
import api.implementation.PlayerManagement;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;

public interface IImportExportFiles {

    /**
     * Calls the import method of all instances passed through reference
     *
     * @param fileName   of file to import from
     * @param playerList list of all players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importJSON(String fileName, PlayerManagement playerList, LocalsManagement pathGraph, JSONParser parser);

    /**
     * Calls the export method of all instances passed through reference
     *
     * @param fileName   of file to export into
     * @param playerList list of all players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public  String exportJSON(String fileName, PlayerManagement playerList, LocalsManagement pathGraph, FileWriter fileWriter);
}
