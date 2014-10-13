package de.alextape.sonicshop.userTypes;

import de.alextape.sonicshop.catalog.ItemCatalog;
import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;

/**
 * The Interface DefaultUser.
 */
public interface DefaultUser {

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail();

    /**
     * Gets the lastname.
     *
     * @return the lastname
     */
    public String getLastname();

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName();

    /**
     * Gets the role.
     *
     * @return the role
     */
    public SecurityLevel getRole();

    /**
     * Gets the shopping card.
     *
     * @return the shopping card
     */
    public ItemCatalog getShoppingCard();

    /**
     * Gets the shopping items count.
     *
     * @return the shopping items count
     */
    public int getShoppingItemsCount();

    /**
     * Gets the shopping price.
     *
     * @return the shopping price
     */
    public double getShoppingPrice();

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserID();

    /**
     * Checks if is admin.
     *
     * @return true, if is admin
     */
    public boolean isAdmin();

    /**
     * Checks if is supporter.
     *
     * @return true, if is supporter
     */
    public boolean isSupporter();

    /**
     * New shopping card.
     */
    public void newShoppingCard();

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public void setEmail(String email);

    /**
     * Sets the lastname.
     *
     * @param lastname
     *            the new lastname
     */
    public void setLastname(String lastname);

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name);

}
