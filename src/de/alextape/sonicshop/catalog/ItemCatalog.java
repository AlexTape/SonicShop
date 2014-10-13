package de.alextape.sonicshop.catalog;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/*
 * instances of this class can hold collections of entity items
 */
/**
 * The Class ItemCatalog.
 */
public class ItemCatalog {

    /** The id. */
    private static int id = 0;

    /** The collection. */
    private ArrayList<CatalogItem> collection = null;

    /** The log. */
    protected Logger log = Logger.getLogger("WebshopLogger");

    /**
     * Instantiates a new item catalog.
     */
    public ItemCatalog() {
        id = id++;
        collection = new ArrayList<CatalogItem>();
    }

    /**
     * Adds the.
     *
     * @param newItem
     *            the new item
     */
    public void add(CatalogItem newItem) {
        collection.add(newItem);
    }

    /**
     * Adds the item.
     *
     * @param item
     *            the item
     */
    protected void addItem(CatalogItem item) {
        collection.add(item);
        log.debug("CatalogItemClass stored new item in instance " + id);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the items.
     *
     * @return the items
     */
    public ArrayList<CatalogItem> getItems() {
        log.debug("CatalogItemClass " + id + " returned collection");
        return collection;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public double getPrice() {
        double priceForAllItems = 0.0;
        for (CatalogItem item : collection) {
            priceForAllItems += item.getPreis();
        }
        return priceForAllItems;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * Removes the.
     *
     * @param articleID
     *            the article id
     */
    public void remove(int articleID) {
        int indexOfElement = 0;
        boolean foundIt = false;
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getArtid() == articleID) {
                indexOfElement = i;
                foundIt = true;
            }
        }
        if (foundIt) {
            collection.remove(indexOfElement);
        }
    }
}
