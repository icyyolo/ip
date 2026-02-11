package zhongli.alias;

import java.util.ArrayList;
import java.util.HashSet;

import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a list of aliases
 *
 */
public class AliasList {
    private ArrayList<Alias> aliases;
    private HashSet<String> aliasHashSet;

    /**
     * Represents an empty alias list
     *
     */
    public AliasList() {
        this.aliases = new ArrayList<>();
        this.aliasHashSet = new HashSet<>();
    }

    /**
     * Populate the alias list with an Array List of alias
     *
     */
    public AliasList(ArrayList<Alias> aliases) {
        this.aliases = aliases;
        this.aliasHashSet = populateHashSetUsingAliases();
    }

    /**
     * Populate the hashset based on alias in aliases
     *
     */
    public HashSet<String> populateHashSetUsingAliases() {
        HashSet<String> hs = new HashSet<>();
        for (Alias alias : aliases) {
            hs.add(alias.getAlias());
        }
        return hs;
    }

    /**
     * Adds an alias to the alias list
     * Alias needs to be unique, if not will throw an exception
     *
     */
    public void addAlias(Alias alias) throws ZhongliException {
        if (aliasHashSet.contains(alias.getAlias())) {
            throw new ZhongliException(alias.getAlias() + " has been used.");
        }
        this.aliasHashSet.add(alias.getAlias());
        this.aliases.add(alias);
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

    public Alias getAlias(int index) throws ZhongliException {
        checkValidRange(index);
        assert index >= 0 : "Index should not be less than 0";
        return this.aliases.get(index);
    }

    public int getSize() {
        return this.aliases.size();
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
