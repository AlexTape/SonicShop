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
 * the BestRatedItems bodytag definition
 */
/**
 * The Class BestRatedItems.
 */
public class BestRatedItems extends BodyTagSupport {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7870104699356291226L;

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
        log.debug("BestRatedTag working");
        ItemCatalog bestRatedItems = new EntityFactory().getBestRated();
        ArrayList<CatalogItem> bestRated = bestRatedItems.getItems();
        for (CatalogItem item : bestRated) {
            try {
                // TODO hard width/heigth for divs because artikelname is too
                // long...
                out.print("<li><a href=\"/Webshop/ItemViewer?item="
                        + item.getArtid()
                        + "\" title=\""
                        + item.getArtname()
                        + "\">"
                        + "<img src=\"products/small/k-G_"
                        + item.getArtid()
                        + ".jpg\" alt=\""
                        + item.getArtname()
                        + "\" />"
                        + "<div class=\"info\"><h4>"
                        + Methods.fitLength(item.getArtname())
                        + "</h4></a>"
                        + "<a href=\"/Webshop/SaleCardHandler?item="
                        + item.getArtid()
                        + "\"><span class=\"number\">for only"
                        + "</span><span class=\"price\"><span>&euro;</span>"
                        + Methods.getBeforeComma(item.getPreis())
                        + "<sup>."
                        + Methods.getAfterComma(item.getPreis())
                        + "</sup></span></a><div class=\"clearFloat\">&nbsp;</div></div></li>");
            } catch (IOException e) {
                log.debug("BestRatedTag error");
            }
        }
        log.debug("BestRatedTag finished");
        return returnCode;
    }
}