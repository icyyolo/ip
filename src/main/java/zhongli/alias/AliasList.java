package zhongli.alias;

import java.util.ArrayList;

import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a list of aliases
 *
 */
public class AliasList {
    private final ArrayList<Alias> aliases;

    public AliasList() {
        this.aliases = new ArrayList<>();
    }

    public AliasList(ArrayList<Alias> aliases) {
        this.aliases = aliases;
    }

    public void addAlias(Alias alias) {
        this.aliases.add(alias);
    }

    public int getSize() {
        return this.aliases.size();
    }

    /**
     * Checks if the index given is within the array size.
     *
     * @param index - the index the user want to access.
     * @throws ZhongliException - if the index is not within the range, or there is no items in the array.
     *
     */
    public void checkValidRange(int index) throws ZhongliException {
        if (aliases.isEmpty()) {
            throw new ZhongliException("The list is empty, please add some tasks before deleting");
        } else if (index < 0 || index >= aliases.size()) {
            throw new ZhongliException("This index does not exist. The range should be between 1 and " + getSize());
        }
    }

    public Alias getAlias(int i) throws ZhongliException {
        checkValidRange(i);
        return this.aliases.get(i);
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
