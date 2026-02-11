package zhongli.alias;

import java.util.ArrayList;

import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a list of aliases
 *
 */
public class AliasList {
    private ArrayList<Alias> aliases;

    public AliasList() {
        this.aliases = new ArrayList<>();
    }

    public AliasList(ArrayList<Alias> aliases) {
        this.aliases = aliases;
    }

    public void addAlias(Alias alias) {
        this.aliases.add(alias);
    }

    /**
     * Find the correct alias object, given the alias string
     * Throws an exception if the alias object is not found
     *
     */
    public Alias findAlias(String alias) throws ZhongliException {
        for (Alias currAlias : this.aliases) {
            if (currAlias.checkAlias(alias)) {
                return currAlias;
            }
        }
        throw new ZhongliException("No Alias found");
    }

    public String getCommand(String alias) {
        try {
            Alias currAlias = findAlias(alias);
            return currAlias.getOriginalCommand();
        } catch (ZhongliException e) {
            return "";
        }
    }

}
