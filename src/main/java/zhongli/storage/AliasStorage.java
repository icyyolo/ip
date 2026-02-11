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
 * Represents a storage class to handle alias objects.
 * It can read from file, and write to file.
 *
 */
public class AliasStorage extends Storage {

    public AliasStorage(String filePath) {
        super(filePath);
    }

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
     * Formats the file from the given filePath and transform it into an ALias List object
     *
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
     * Write the alias in aliasList to the file
     *
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
