package de.alextape.sonicshop.databaseTags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.catalog.DetailItem;
import de.alextape.sonicshop.methods.Methods;
import de.alextape.sonicshop.modell.EntityFactory;

/*
 * the BestRatedItems bodytag definition
 */
/**
 * The Class ItemDetail.
 */
public class ItemDetail extends BodyTagSupport {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7870104699356291226L;

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
        log.debug("ItemDetail working");
        DetailItem singleItem = new EntityFactory().getCatalogItem(item);
        try {
            String appendAttributes = "";
            if (singleItem.getDetailLength() != 0) {
                for (Map.Entry<String, String> entry : singleItem
                        .getDetailMap().entrySet()) {
                    appendAttributes += "<div id=\"attributes\">"
                            + "<div class=\"key\"><p>" + entry.getKey()
                            + "</p></div>"
                            + "<div class=\"value\"><p class=\"number\">"
                            + entry.getValue() + "</p></div>" + "</div>\n";
                }
            }
            out.print("<div id=\"products\">\n"
                    + "<div class=\"productdetail\">\n"
                    + "<img src=\"products/big/G_"
                    + singleItem.getArtid()
                    + ".jpg\" alt=\""
                    + singleItem.getArtname()
                    + "\" />\n"
                    + "<h2>"
                    + singleItem.getArtname()
                    + "</h2>\n"
                    + "<div class=\"price\">\n"
                    + "<div class=\"inner\">\n"
                    + "<span class=\"title\">Preis</span>\n"
                    + "<strong><span>&euro;</span>"
                    + Methods.getBeforeComma(singleItem.getPreis())
                    + "<sup>."
                    + Methods.getAfterComma(singleItem.getPreis())
                    + "</sup></strong>\n"
                    + "</div>\n"
                    + "</div>\n"
                    + "<div class=\"info\">\n"
                    + "<p>"
                    + singleItem.getBeschreibung()
                    + "</p>\n"
                    + "<br><br></p>\n"
                    + "<h2>Technische Details:</h2><br>\n"
                    + "</div>\n"
                    + "<a id=\"buyButton\" href=\"/Webshop/SaleCardHandler?item="
                    + singleItem.getArtid()
                    + "\"><h3>kaufen</h3></a>\n"
                    + appendAttributes.toString()
                    + "</div>\n"
                    + "<div class=\"cl\">&nbsp;</div>\n" + "</div>\n");
        } catch (IOException e) {
            log.debug("ItemDetail error");
        }

        log.debug("ItemDetail finished");
        return returnCode;
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