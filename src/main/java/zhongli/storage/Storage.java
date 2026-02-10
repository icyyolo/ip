package zhongli.storage;

import java.io.File;
import java.io.IOException;

import zhongli.ui.Ui;

/**
 * Represents a storage class to handle input from terminal
 * and writing to text file operations
 *
 */
public class Storage {

    protected final String filePath;

    /**
     * Represents a storage class to read / write file based on the file path
     *
     */
    public Storage(String filePath) {
        assert filePath != null : "filePath is null";
        this.filePath = filePath;
    }

    /**
     * Read the file from the filePath.
     * If the file does not exist, it will create a new file.
     *
     * @param filePath - the path to the file.
     * @param ui - UI object to display error messages to the user.
     * @return File specified by the filePath String.
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
