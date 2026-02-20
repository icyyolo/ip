package zhongli.storage;

import java.io.File;
import java.io.IOException;

import zhongli.ui.Ui;

/**
 * Base class for managing persistent file storage operations. Provides functionality to read and
 * create storage files, with error handling for IO and security exceptions.
 *
 */
public class Storage {

    protected final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The file path where data will be read from or written to.
     */
    public Storage(String filePath) {
        assert filePath != null : "filePath is null";
        this.filePath = filePath;
    }

    /**
     * Reads the file at the specified file path, creating a new file if it does not exist.
     * Handles IO and security exceptions by displaying error messages through the provided UI.
     *
     * @param filePath The path to the file to read or create.
     * @param ui The UI object used to display error messages to the user.
     * @return The File object representing the file at the specified path.
     */
    protected File readFile(String filePath, Ui ui) {
        File file = new File(filePath);
        assert ui != null : "ui is null";
        if (!file.exists()) {
            try {
                boolean isSuccessful = file.createNewFile();
            } catch (IOException e) {
                ui.displayExceptionMessage("Unable to find file");
            } catch (SecurityException e) {
                ui.displayExceptionMessage(e.getMessage());
            }
        }
        return file;
    }
}
