package api.implementation;

import api.interfaces.IImportExportFiles;

import java.io.FileWriter;
import java.io.IOException;

public class ImportExportFiles implements IImportExportFiles {

    /**
     * Calls the import method of all instances passed through reference
     * @param fileName of file to import from
     * @param playerList list of all players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importJSON(String fileName, PlayerManagement playerList){
        StringBuilder mensagemSucesso = new StringBuilder();

        try {
            mensagemSucesso.append(playerList.importJSON(fileName)).append("\n");
        } catch (IOException e) {
            return e.getMessage();
        }

        return mensagemSucesso.toString();
    }

    /**
     * Calls the export method of all instances passed through reference
     * @param fileName of file to export into
     * @param playerList list of all players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String exportJSON(String fileName, PlayerManagement playerList) {
        StringBuilder mensagemSucesso = new StringBuilder();

        try {
            mensagemSucesso.append(playerList.exportJSON(fileName)).append("\n");
        } catch (IOException e) {
            return e.getMessage();
        }

        return mensagemSucesso.toString();
    }

    
    /**
     * Directory that the JSON is gonna be saved
     */
    private static final String directory = "docs/outputs/json";

    /**
     * Exports a json file with the content of @param jsonFile,
     * with the name @param name
     *
     * @param stringJSON string representation of json file.
     * @param name       name of the file without the extension (.json).
     * @throws IOException if some error occurs when writing the file
     * @implNote Files are saved by default in "docs/outputs/json"
     */
    public static void exportJSON(String stringJSON, String name) throws IOException {
        if (stringJSON == null || stringJSON.equals("") || name == null || name.equals("")) {
            throw new IllegalArgumentException("Cannot send parameters null or empty!");
        }
        try (FileWriter file = new FileWriter(directory + "/" + name + ".json")) {
            file.write(stringJSON);
        } catch (IOException exception) {
            throw new IOException("Error trying to write the file!");
        }
    }
}
