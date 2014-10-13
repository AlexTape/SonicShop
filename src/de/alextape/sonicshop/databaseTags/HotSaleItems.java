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
 * the HotSaleItems bodytag definition
 */
/**
 * The Class HotSaleItems.
 */
public class HotSaleItems extends BodyTagSupport {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1028932837217758945L;

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
        log.debug("CategoryItems working");
        ItemCatalog hotSaleItems = new EntityFactory().getHotSales();
        ArrayList<CatalogItem> hotSales = hotSaleItems.getItems();
        for (CatalogItem item : hotSales) {
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
                log.debug("CategoryItems error");
            }
        }
        log.debug("CategoryItems finished");
        return returnCode;
    }
}