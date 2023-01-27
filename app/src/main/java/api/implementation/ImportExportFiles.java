package api.implementation;

import api.interfaces.IImportExportFiles;

import java.io.FileWriter;
import java.io.IOException;

public class ImportExportFiles implements IImportExportFiles {

    /**
     * Calls the import method of all instances passed through reference
     *
     * @param fileName   of file to import from
     * @param playerList list of all players
     * @return A string indicating whether the operation was successful or something went wrong
     */
    public String importJSON(String fileName, PlayerManagement playerList) {
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
     *
     * @param fileName   of file to export into
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

    
  
}
