package de.alextape.sonicshop.catalog;

/*
 * needed methods for the catalog items
 * 
 * not enough time to do it the right/dynamic way...
 */
/**
 * The Interface CatalogItemInterface.
 */
@Deprecated
public interface CatalogItemInterface {

    /* delete this item */
    /**
     * Delete item.
     *
     * @return true, if successful
     */
    public boolean deleteItem();

    /**
     * Delete value.
     *
     * @param parameter
     *            the parameter
     * @return true, if successful
     */
    public boolean deleteValue(String parameter);

    /**
     * Gets the boolean.
     *
     * @param parameter
     *            the parameter
     * @return the boolean
     */
    public boolean getBoolean(String parameter);

    /**
     * Gets the double.
     *
     * @param parameter
     *            the parameter
     * @return the double
     */
    public double getDouble(String parameter);

    /**
     * Gets the int.
     *
     * @param parameter
     *            the parameter
     * @return the int
     */
    public int getInt(String parameter);

    /* get data of this item */
    /**
     * Gets the string.
     *
     * @param parameter
     *            the parameter
     * @return the string
     */
    public String getString(String parameter);

    /**
     * Sets the value.
     *
     * @param parameter
     *            the parameter
     * @param value
     *            the value
     * @return true, if successful
     */
    public boolean setValue(String parameter, boolean value);

    /**
     * Sets the value.
     *
     * @param parameter
     *            the parameter
     * @param value
     *            the value
     * @return true, if successful
     */
    public boolean setValue(String parameter, double value);

    /**
     * Sets the value.
     *
     * @param parameter
     *            the parameter
     * @param value
     *            the value
     * @return true, if successful
     */
    public boolean setValue(String parameter, int value);

    /* update this Item */
    /**
     * Sets the value.
     *
     * @param parameter
     *            the parameter
     * @param value
     *            the value
     * @return true, if successful
     */
    public boolean setValue(String parameter, String value);

}