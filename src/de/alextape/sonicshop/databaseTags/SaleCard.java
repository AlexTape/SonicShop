package de.alextape.sonicshop.databaseTags;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.catalog.CatalogItem;
import de.alextape.sonicshop.catalog.ItemCatalog;
import de.alextape.sonicshop.userTypes.DefaultUser;

/*
 * the BestRatedItems bodytag definition
 */
/**
 * The Class SaleCard.
 */
public class SaleCard extends BodyTagSupport {

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
        log.debug("ItemDetail working");

        log.debug("ShoppingCard started");

        // get back the request object
        HttpServletRequest request = (HttpServletRequest) pageContext
                .getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext
                .getResponse();

        // get session
        HttpSession session = request.getSession();

        // check session is authed
        Object knowHim = session.getAttribute("login.object");

        // some redirects to get indexBean on other pages with sendRedirect out
        // of other classes
        String redirectTarget = request.getParameter("forward");
        if (redirectTarget == null) {
            redirectTarget = "indexView.jsp"; // default target
        }

        if (!(knowHim instanceof DefaultUser) | knowHim == null) {
            // not authed user
            log.debug("SaleCard not authed user detected - redirect to main");
            try {
                response.sendRedirect("/Main");
            } catch (IOException e) {
                log.debug("ShoppingCard not able to redirect");
                e.toString();
            }
        } else {
            // cast back the user object
            DefaultUser user = (DefaultUser) knowHim;
            log.info("Main " + user.getEmail() + " returned as "
                    + user.getRole().toString());
            // revalidate user
            request.isUserInRole(user.getRole().toString());

            boolean isEmptyBucket = false;

            ItemCatalog shoppingCardItems = user.getShoppingCard();
            if (shoppingCardItems.getSize() == 0) {
                isEmptyBucket = true;
            }
            try {
                out.print("<table width=\"100%\">"
                        + "<tr><td>&nbsp;</td><td><h3>Artikelname</h3></td><td><h3>Menge</h3></td><td><h3>Preis</h3></td><td><h3>W&auml;hlen</h3></td></tr>");
                if (!isEmptyBucket) {
                    ArrayList<CatalogItem> items = shoppingCardItems.getItems();
                    for (CatalogItem item : items) {
                        out.print("<tr>"
                                + "<td><img style=\"width:40px;height:40px;\" align=\"left\" src=\"products/small/k-G_"
                                + item.getArtid()
                                + ".jpg\" alt=\""
                                + item.getArtname()
                                + "\" /></td>"
                                + "<td><a href=\"/Webshop/ItemViewer?item="
                                + item.getArtid()
                                + "\">"
                                + item.getArtname()
                                + "</a><br>Art.Nr.: "
                                + item.getArtid()
                                + "</td>"
                                + "<td><input type=\"text\" name=\""
                                + item.getArtid()
                                + "\" value=\"1\"></td>"
                                + "<td>&euro;"
                                + item.getPreis()
                                + "</td>"
                                + "<td><a href=\"/Webshop/SaleCardHandler?remove="
                                + item.getArtid() + "\">Entfernen</a></td>"
                                + "</tr>");
                    }
                    out.print("<tr><td>&nbsp;</td><td>&nbsp;</td><td><h2>Gesamtpreis:</h2></td><td><h2>&euro;"
                            + shoppingCardItems.getPrice()
                            + "</h2></td><td><a href=\"/Webshop/OrderHandler\"><h1>bestellen</h1></a></td>");
                } else {
                    out.print("<tr colspan=\"4\"><td>&nbsp;</td><td>Warenkorb ist leer.</td></tr>");
                }
                out.print("</table>");
            } catch (IOException e) {
                log.debug("ShoppingCard error");
            }
        }
        log.debug("ShoppingCard finished");
        return returnCode;
    }
}