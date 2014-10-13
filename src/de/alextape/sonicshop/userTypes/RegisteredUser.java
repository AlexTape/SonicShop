package de.alextape.sonicshop.userTypes;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.catalog.ItemCatalog;
import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;

/*
 * first level user (e.g. registeredUser)
 */
/**
 * The Class RegisteredUser.
 */
public class RegisteredUser implements DefaultUser {

    /** The email. */
    protected String email;

    /** The lastname. */
    protected String lastname;

    /** The log. */
    protected Logger log = Logger.getLogger("WebshopLogger");

    /** The name. */
    protected String name;

    /** The role. */
    protected final SecurityLevel ROLE;

    /** The shopping items. */
    protected ItemCatalog shoppingItems;

    /** The shopping price. */
    protected double shoppingPrice;

    /** The userid. */
    protected final int USERID;

    /**
     * Instantiates a new registered user.
     *
     * @param userID
     *            the user id
     * @param role
     *            the role
     * @param name
     *            the name
     * @param lastname
     *            the lastname
     * @param email
     *            the email
     */
    public RegisteredUser(int userID, SecurityLevel role, String name,
            String lastname, String email) {
        this.USERID = userID;
        this.ROLE = role;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.shoppingPrice = 0.0;
        this.shoppingItems = new ItemCatalog();
    }

    // TODO some registeredUser specific methods can be added here!

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#getEmail()
     */
    @Override
    public String getEmail() {
        return email;
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#getLastname()
     */
    @Override
    public String getLastname() {
        return lastname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#getRole()
     */
    @Override
    public SecurityLevel getRole() {
        return ROLE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#getShoppingCard()
     */
    @Override
    public ItemCatalog getShoppingCard() {
        return shoppingItems;
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#getShoppingItemsCount()
     */
    @Override
    public int getShoppingItemsCount() {
        return shoppingItems.getSize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#getShoppingPrice()
     */
    @Override
    public double getShoppingPrice() {
        return shoppingItems.getPrice();
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#getUserID()
     */
    @Override
    public int getUserID() {
        return USERID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#isAdmin()
     */
    @Override
    public boolean isAdmin() {
        return (ROLE == SecurityLevel.ADMINISTRATOR);
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#isSupporter()
     */
    @Override
    public boolean isSupporter() {
        return (ROLE == SecurityLevel.SUPPORTER);
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#newShoppingCard()
     */
    @Override
    public void newShoppingCard() {
        this.shoppingPrice = 0.0;
        this.shoppingItems = new ItemCatalog();
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#setEmail(java.lang.String)
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#setLastname(java.lang.String)
     */
    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see userTypes.DefaultUser#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

}
