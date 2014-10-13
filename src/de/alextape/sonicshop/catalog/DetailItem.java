package de.alextape.sonicshop.catalog;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/*
 * the software class is able to hold all attributes of an software entity
 */
/**
 * The Class DetailItem.
 */
public class DetailItem extends CatalogItem {

    /** The detail map. */
    private Map<String, String> detailMap;

    /** The log. */
    protected Logger log = Logger.getLogger("WebshopLogger");

    /**
     * Instantiates a new detail item.
     *
     * @param collection
     *            the collection
     * @param artid
     *            the artid
     * @param artname
     *            the artname
     * @param preis
     *            the preis
     * @param beschreibung
     *            the beschreibung
     * @param menge
     *            the menge
     * @param angebot
     *            the angebot
     * @param rabatt
     *            the rabatt
     * @param kategoriename
     *            the kategoriename
     * @param unterkategorie
     *            the unterkategorie
     */
    public DetailItem(ItemCatalog collection, int artid, String artname,
            double preis, String beschreibung, int menge, boolean angebot,
            double rabatt, String kategoriename, String unterkategorie) {
        super(collection, artid, artname, preis, beschreibung, menge, angebot,
                rabatt, kategoriename, unterkategorie);
        detailMap = new HashMap<String, String>();
        log.debug("DetailItem new Item in collectionID " + collection.getId()
                + " with " + this.toString() + " and detailMap "
                + detailMap.toString());
    }

    /**
     * Gets the detail length.
     *
     * @return the detail length
     */
    public int getDetailLength() {
        return detailMap.size();
    }

    /**
     * Gets the detail map.
     *
     * @return the detail map
     */
    public Map<String, String> getDetailMap() {
        return detailMap;
    }

    /**
     * Sets the detail.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void setDetail(String key, String value) {
        detailMap.put(key, value);
    }

}
