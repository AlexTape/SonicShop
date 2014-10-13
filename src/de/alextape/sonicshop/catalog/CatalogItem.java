package de.alextape.sonicshop.catalog;

import org.apache.log4j.Logger;

/**
 * The Class CatalogItem. all CatalogItems have to register themselve at a
 * certain ItemCatalog for further use.
 */
public class CatalogItem {

    /** The item id. */
    private static int itemID = 0;

    /**
     * Gets the item id.
     *
     * @return the item id
     */
    public static int getItemID() {
        return itemID;
    }

    /**
     * Sets the item id.
     *
     * @param newItemID
     *            the new item id
     */
    public static void setItemID(final int newItemID) {
        CatalogItem.itemID = newItemID;
    }

    /** The angebot. */
    private boolean angebot;

    /** The artid. */
    private int artid;

    /** The artname. */
    private String artname;

    /** The beschreibung. */
    private String beschreibung;

    /** The kategoriename. */
    private String kategoriename;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /** The menge. */
    private int menge;

    /** The preis. */
    private double preis;

    /** The rabatt. */
    private double rabatt;

    /** The unterkategorie. */
    private String unterkategorie;

    /**
     * Instantiates a new catalog item.
     *
     * @param newCollection
     *            the collection
     * @param newArtid
     *            the artid
     * @param newArtname
     *            the artname
     * @param newPrice
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
    public CatalogItem(final ItemCatalog newCollection, final int newArtid,
            final String newArtname, final double newPrice,
            final String beschreibung, final int menge, final boolean angebot,
            final double rabatt, final String kategoriename,
            final String unterkategorie) {
        super();
        this.artid = newArtid;
        this.artname = newArtname;
        this.preis = newPrice;
        this.beschreibung = beschreibung;
        this.menge = menge;
        this.angebot = angebot;
        this.rabatt = rabatt;
        this.kategoriename = kategoriename;
        this.unterkategorie = unterkategorie;
        newCollection.addItem(this);
        log.debug("CatalogItemClass new Item in collectionID "
                + newCollection.getId() + " with " + this.toString());
    }

    /**
     * Gets the artid.
     *
     * @return the artid
     */
    public int getArtid() {
        return artid;
    }

    /**
     * Gets the artname.
     *
     * @return the artname
     */
    public String getArtname() {
        return artname;
    }

    /**
     * Gets the beschreibung.
     *
     * @return the beschreibung
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Gets the kategoriename.
     *
     * @return the kategoriename
     */
    public String getKategoriename() {
        return kategoriename;
    }

    /**
     * Gets the menge.
     *
     * @return the menge
     */
    public int getMenge() {
        return menge;
    }

    /**
     * Gets the preis.
     *
     * @return the preis
     */
    public double getPreis() {
        return preis;
    }

    /**
     * Gets the rabatt.
     *
     * @return the rabatt
     */
    public double getRabatt() {
        return rabatt;
    }

    /**
     * Gets the unterkategorie.
     *
     * @return the unterkategorie
     */
    public String getUnterkategorie() {
        return unterkategorie;
    }

    /**
     * Checks if is angebot.
     *
     * @return true, if is angebot
     */
    public boolean isAngebot() {
        return angebot;
    }

    /**
     * Sets the angebot.
     *
     * @param angebot
     *            the new angebot
     */
    public void setAngebot(boolean angebot) {
        this.angebot = angebot;
    }

    /**
     * Sets the artid.
     *
     * @param artid
     *            the new artid
     */
    public void setArtid(int artid) {
        this.artid = artid;
    }

    /**
     * Sets the artname.
     *
     * @param artname
     *            the new artname
     */
    public void setArtname(String artname) {
        this.artname = artname;
    }

    /**
     * Sets the beschreibung.
     *
     * @param beschreibung
     *            the new beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Sets the kategoriename.
     *
     * @param kategoriename
     *            the new kategoriename
     */
    public void setKategoriename(String kategoriename) {
        this.kategoriename = kategoriename;
    }

    /**
     * Sets the menge.
     *
     * @param menge
     *            the new menge
     */
    public void setMenge(int menge) {
        this.menge = menge;
    }

    /**
     * Sets the preis.
     *
     * @param preis
     *            the new preis
     */
    public void setPreis(double preis) {
        this.preis = preis;
    }

    /**
     * Sets the rabatt.
     *
     * @param rabatt
     *            the new rabatt
     */
    public void setRabatt(double rabatt) {
        this.rabatt = rabatt;
    }

    /**
     * Sets the unterkategorie.
     *
     * @param unterkategorie
     *            the new unterkategorie
     */
    public void setUnterkategorie(String unterkategorie) {
        this.unterkategorie = unterkategorie;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "CatalogItem [artid=" + artid + ", artname=" + artname
                + ", preis=" + preis + "]";
    }

}
