package zhongli.alias;

import java.util.ArrayList;
import java.util.HashSet;

import zhongli.zhongliexception.ZhongliException;

/**
 * Manages a collection of command aliases. Maintains both an ArrayList for order preservation
 * and a HashSet for efficient duplicate detection. Provides functionality to add, retrieve,
 * and search for aliases with uniqueness validation.
 *
 */
public class AliasList {
    private ArrayList<Alias> aliases;
    private HashSet<String> aliasHashSet;

    /**
     * Constructs an empty AliasList with no aliases.
     *
     */
    public AliasList() {
        this.aliases = new ArrayList<>();
        this.aliasHashSet = new HashSet<>();
    }

    /**
     * Constructs an AliasList populated with the provided ArrayList of aliases.
     * Automatically populates the internal HashSet for efficient lookup and duplicate detection.
     *
     * @param aliases The ArrayList of Alias objects to initialise the list with.
     */
    public AliasList(ArrayList<Alias> aliases) {
        this.aliases = aliases;
        this.aliasHashSet = populateHashSetUsingAliases();
    }

    /**
     * Populates a HashSet with all alias names from the current aliases list.
     * Used for efficient duplicate detection during alias addition.
     *
     * @return A HashSet containing all alias names from the current list.
     */
    public HashSet<String> populateHashSetUsingAliases() {
        HashSet<String> hs = new HashSet<>();
        for (Alias alias : aliases) {
            hs.add(alias.getAlias());
        }
        return hs;
    }

    /**
     * Adds a new alias to the list if its name is unique.
     * The alias name must not already exist in the list.
     *
     * @param alias The Alias object to add.
     * @throws ZhongliException If an alias with the same name already exists.
     */
    public void addAlias(Alias alias) throws ZhongliException {
        if (aliasHashSet.contains(alias.getAlias())) {
            throw new ZhongliException(alias.getAlias() + " has been used.");
        }
        this.aliasHashSet.add(alias.getAlias());
        this.aliases.add(alias);
    }

    /**
     * Validates that the given index is within the valid range of the aliases list.
     * Throws an exception if the list is empty or the index is out of bounds.
     *
     * @param index The index to validate.
     * @throws ZhongliException If the list is empty or the index is outside the range [0, size).
     */
    public void checkValidRange(int index) throws ZhongliException {
        if (aliases.isEmpty()) {
            throw new ZhongliException("The list is empty, please add some tasks before deleting");
        } else if (index < 0 || index >= aliases.size()) {
            throw new ZhongliException("This index does not exist. The range should be between 1 and " + getSize());
        }
    }

    /**
     * Returns the alias at the specified index in the list.
     *
     * @param index The index of the alias to retrieve.
     * @return The Alias object at the specified index.
     * @throws ZhongliException If the index is invalid or out of range.
     */
    public Alias getAlias(int index) throws ZhongliException {
        checkValidRange(index);
        assert index >= 0 : "Index should not be less than 0";
        return this.aliases.get(index);
    }

    public int getSize() {
        return this.aliases.size();
    }

    /**
     * Searches for and returns the Alias object matching the provided alias name.
     * Performs a linear search through the aliases list.
     *
     * @param alias The alias name to search for.
     * @return The Alias object with the matching name.
     * @throws ZhongliException If no alias with the specified name is found.
     */
    public Alias findAlias(String alias) throws ZhongliException {
        for (Alias currAlias : this.aliases) {
            if (currAlias.checkAlias(alias)) {
                return currAlias;
            }
        }
        throw new ZhongliException("No Alias found");
    }

    /**
     * Returns the original command keyword associated with the provided alias name.
     * Returns an empty string if the alias is not found rather than throwing an exception.
     *
     * @param alias The alias name to look up.
     * @return The original command string, or an empty string if the alias does not exist.
     */
    public String getCommand(String alias) {
        try {
            Alias currAlias = findAlias(alias);
            return currAlias.getOriginalCommand();
        } catch (ZhongliException e) {
            return "";
        }
    }

}
