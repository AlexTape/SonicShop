package de.alextape.sonicshop.databaseTags;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.catalog.CatalogItem;
import de.alextape.sonicshop.catalog.ItemCatalog;
import de.alextape.sonicshop.methods.Methods;
import de.alextape.sonicshop.modell.EntityFactory;

/*
 * the CategoryItems bodytag definition
 */
/**
 * The Class CategoryItems.
 */
public class CategoryItems extends BodyTagSupport {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7502185683578417458L;

    /** The category. */
    private String category;

    /** The item. */
    private String item;

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        int returnCode = EVAL_BODY_INCLUDE;
        log.debug("CategoryTag working");

        boolean skipper = false;

        // fist check input
        if (category.equals("")) {
            category = "Unkown Category Request";
            skipper = true;
        }
        if (item.equals("")) {
            item = null;
        }
        if (!skipper) {
            ItemCatalog categoryItems = new EntityFactory().getCategory(
                    category, item);
            if (categoryItems.getSize() == 0) {
                new CatalogItem(categoryItems, 0, "no data available", 0.0,
                        "Database Error", 0, false, 0.0, "", "");
            }
            ArrayList<CatalogItem> items = categoryItems.getItems();
            for (CatalogItem item : items) {
                try {
                    out.print("<div class=\"product\"><a href=\"/Webshop/ItemViewer?item="
                            + item.getArtid()
                            + "\" title=\""
                            + item.getArtname()
                            + "\"><img src=\"products/small/k-G_"
                            + item.getArtid()
                            + ".jpg\" alt=\""
                            + item.getArtname()
                            + "\" />"
                            + "<div class=\"price\">"
                            + "<div class=\"inner\">"
                            + "<a href=\"/Webshop/SaleCardHandler?item="
                            + item.getArtid()
                            + "\" title=\""
                            + item.getArtname()
                            + "\"><span class=\"title\">sonic</span><strong><span>&euro;</span>"
                            + Methods.getBeforeComma(item.getPreis())
                            + "<sup>."
                            + Methods.getAfterComma(item.getPreis())
                            + "</sup></strong></a></div></div>"
                            + "<div class=\"info\"><a href=\"/Webshop/ItemViewer?item="
                            + item.getArtid()
                            + "\"><p>"
                            + Methods.fitLength(item.getArtname())
                            + "<BR></p></a>"
                            + "<p class=\"number\">Artikelnummer "
                            + item.getArtid() + "</p></div></div>");
                } catch (IOException e) {
                    log.debug("CategoryTag error");
                }
            }
        }
        log.debug("CategoryTag finished");
        return returnCode;
    }

    /**
     * Sets the category.
     *
     * @param category
     *            the new category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the item.
     *
     * @param item
     *            the new item
     */
    public void setItem(String item) {
        this.item = item;
    }

}