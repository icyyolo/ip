package zhongli.storage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import zhongli.alias.Alias;
import zhongli.alias.AliasList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

/**
 * Manages persistent storage of command aliases. Handles reading aliases from and writing
 * aliases to a text file, with error handling for malformed alias entries.
 *
 */
public class AliasStorage extends Storage {

    public AliasStorage(String filePath) {
        super(filePath);
    }

    /**
     * Reads aliases from the storage file and parses them into Alias objects.
     * Skips any lines that fail to parse and logs the error with the corresponding line number.
     *
     * @param ui The UI object used to display error messages.
     * @return An ArrayList of successfully parsed Alias objects.
     * @throws FileNotFoundException If the storage file cannot be found or accessed.
     */
    private ArrayList<Alias> getAliasesFromFile(Ui ui) throws FileNotFoundException {
        assert ui != null : "ui is null";
        File file = super.readFile(filePath, ui);
        Scanner s = new Scanner(file);

        ArrayList<Alias> aliases = new ArrayList<>();
        int lineNum = 0;

        while (s.hasNext()) {
            lineNum++;
            String curr = s.nextLine();

            try {
                Alias alias = Alias.parseAliasFromTextFile(curr);
                aliases.add(alias);
            } catch (ZhongliException e) {
                System.out.println("Line Number " + lineNum + " has error: " + e.getMessage());
            }
        }

        return aliases;
    }

    /**
     * Reads aliases from the storage file and returns them as an AliasList object.
     * If the file cannot be found, an empty AliasList is returned and an error message is displayed.
     *
     * @param ui The UI object used to display error messages.
     * @return An AliasList containing all successfully loaded aliases.
     */
    public AliasList initializeAliasList(Ui ui) {
        ArrayList<Alias> aliases = new ArrayList<>();

        try {
            aliases = this.getAliasesFromFile(ui);
        } catch (FileNotFoundException e) {
            ui.displayExceptionMessage(e.getMessage());
        }

        return new AliasList(aliases);
    }

    /**
     * Writes all aliases from the provided AliasList to the storage file.
     * Overwrites the existing file content. Skips any aliases that fail to convert to text format.
     *
     * @param aliasList The AliasList containing aliases to be persisted.
     * @throws IOException If an IO error occurs while writing to the file.
     */
    public void writeAliasListToFile(AliasList aliasList) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, false);

        for (int i = 0; i < aliasList.getSize(); i++) {
            try {
                Alias alias = aliasList.getAlias(i);
                assert alias != null : "Task when writing to file is null";
                fileWriter.write(alias.convertToText());
            } catch (ZhongliException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }

        fileWriter.close();
    }
}
