package de.alextape.sonicshop.viewBeans;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.userTypes.DefaultUser;

/**
 * The Class IndexBean.
 */
public class IndexBean extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1699557507867458704L;

    /** The email. */
    private String email;

    /** The lastname. */
    private String lastname;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /** The logout. */
    private String logout;

    /** The name. */
    private String name;

    /** The role. */
    private String role;

    /** The shopping items. */
    private int shoppingItems;

    /** The shopping price. */
    private double shoppingPrice;

    /**
     * Instantiates a new index bean.
     */
    public IndexBean() {
        this.name = "Gast";
        this.lastname = null;
        this.email = null;
        this.role = null;
        this.shoppingPrice = 0.00;
        this.shoppingItems = 0;
        this.logout = "";
        log.debug("IndexBean is constructing a new GuestBean");
    }

    /**
     * Instantiates a new index bean.
     *
     * @param user
     *            the user
     */
    public IndexBean(DefaultUser user) {
        super();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
        this.shoppingPrice = user.getShoppingPrice();
        this.shoppingItems = user.getShoppingItemsCount();
        // a little bit dirty.. but it works well
        this.logout = " - <a href=\"/Webshop/LoginHandler?logout=true\">Logout</a>";
        log.debug("IndexBean is reconstructing " + this.email);
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the lastname.
     *
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Gets the logout.
     *
     * @return the logout
     */
    public String getLogout() {
        return logout;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets the shopping items.
     *
     * @return the shopping items
     */
    public int getShoppingItems() {
        return shoppingItems;
    }

    /**
     * Gets the shopping price.
     *
     * @return the shopping price
     */
    public double getShoppingPrice() {
        return shoppingPrice;
    }

}
