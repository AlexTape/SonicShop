package de.alextape.sonicshop.modell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import de.alextape.sonicshop.catalog.CatalogItem;
import de.alextape.sonicshop.catalog.DetailItem;
import de.alextape.sonicshop.catalog.ItemCatalog;
import de.alextape.sonicshop.connectivity.ClassPools;
import de.alextape.sonicshop.connectivity.ConnectionPool;
import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;

/* Implement the EntityFactory as Singleton */
/**
 * A factory for creating Entity objects.
 */
public class EntityFactory {

    /** The log. */
    private Logger log = Logger.getLogger("WebshopLogger");

    /** The pool. */
    private ConnectionPool pool = null;

    /**
     * Gets the best rated.
     *
     * @return the best rated
     */
    public ItemCatalog getBestRated() {
        // shell for connection
        Connection con = null;
        // get a bucket for the recieved entitys
        ItemCatalog getBestRated = new ItemCatalog();
        log.debug("EntityFactory getBestRated Request");
        log.debug("EntityFactory try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("EntityFactory initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("EntityFactory gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("EntityFactory could not initialize Connection Pool");
            }
        }
        log.debug("EntityFactory try to get connection");
        try {
            con = pool.getConnection();
            log.debug("EntityFactory db connection established");
            // enable transaction
            con.setAutoCommit(false);
            log.debug("EntityFactory transaction start: getBestRated()");
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM (ArticleTab INNER JOIN CategoryTab USING (CategoryID)) INNER JOIN SubcategoryTab USING (SubcategoryID) WHERE ArticleTab.liked > 300 LIMIT 20;";
            ResultSet resultSet = stmt.executeQuery(query);
            log.debug("EntityFactory doing: " + query);
            // fill it
            while (resultSet.next()) {
                new CatalogItem(getBestRated, resultSet.getInt("artid"),
                        resultSet.getString("artname"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getBoolean("offer"),
                        resultSet.getDouble("discount"),
                        resultSet.getString("category"),
                        resultSet.getString("subcategory"));

            }
            con.commit();
            log.debug("EntityFactory transaction complete");
        } catch (Exception e1) {
            // if error during transaction rollback()
            try {
                log.debug("EntityFactory transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("EntityFactory rollback error - transaction aborted with: "
                        + e2.toString());
                log.warn("EntityFactory create Error Item");
                new CatalogItem(getBestRated, 0, "Database Error", 0.0, "", 0,
                        false, 0.0, "", "");
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("EntityFactory db connection returned to pool");
            }
        }
        return getBestRated;
    }

    /**
     * Gets the catalog item.
     *
     * @param item
     *            the item
     * @return the catalog item
     */
    public DetailItem getCatalogItem(String item) {
        // shell for connection
        Connection con = null;
        // get a bucket for the recieved entitys
        ItemCatalog getCatalogItem = new ItemCatalog();
        DetailItem viewItem = new DetailItem(getCatalogItem, 0,
                "Database Error", 9999999.0, "Database Error", 0, false, 0.0,
                "", "");
        log.debug("EntityFactory getCatalogItem Request");
        log.debug("EntityFactory try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("EntityFactory initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("EntityFactory gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("EntityFactory could not initialize Connection Pool");
            }
        }
        log.debug("EntityFactory try to get connection");
        try {
            int articleID = Integer.parseInt(item);
            log.debug("EntityFactory try to get Item with ID: " + articleID);
            con = pool.getConnection();
            log.debug("EntityFactory db connection established");
            // enable transaction
            con.setAutoCommit(false);
            log.debug("EntityFactory transaction start: getCatalogItem()");
            String prprdQuery = "SELECT * FROM ((ArticleTab INNER JOIN SpecTab USING (ArtID)) INNER JOIN CategoryTab USING (CategoryID)) INNER JOIN SubcategoryTab USING (SubcategoryID) WHERE ArticleTab.artid = ?;";
            log.debug("EntityFactory doing: " + prprdQuery);
            PreparedStatement itemQuery;
            itemQuery = con.prepareStatement(prprdQuery);
            itemQuery.setInt(1, articleID);
            ResultSet resultSet = itemQuery.executeQuery();
            log.debug("EntityFactory doing: " + prprdQuery + " with "
                    + articleID);
            // fill it
            resultSet.next();
            // get standard item
            viewItem = new DetailItem(getCatalogItem,
                    resultSet.getInt("artid"), resultSet.getString("artname"),
                    resultSet.getDouble("price"),
                    resultSet.getString("description"),
                    resultSet.getInt("quantity"),
                    resultSet.getBoolean("offer"),
                    resultSet.getDouble("discount"),
                    resultSet.getString("category"),
                    resultSet.getString("subcategory"));
            // get attributes and keys to detailitem
            viewItem.setDetail(resultSet.getString("attribut1"),
                    resultSet.getString("value1"));
            viewItem.setDetail(resultSet.getString("attribut2"),
                    resultSet.getString("value2"));
            viewItem.setDetail(resultSet.getString("attribut3"),
                    resultSet.getString("value3"));
            viewItem.setDetail(resultSet.getString("attribut4"),
                    resultSet.getString("value4"));
            viewItem.setDetail(resultSet.getString("attribut5"),
                    resultSet.getString("value5"));
            viewItem.setDetail(resultSet.getString("attribut6"),
                    resultSet.getString("value6"));
            viewItem.setDetail(resultSet.getString("attribut7"),
                    resultSet.getString("value7"));
            con.commit();
            log.debug("EntityFactory transaction complete");
        } catch (Exception e1) {
            // if error during transaction rollback()
            log.debug(e1.toString());
            try {
                log.debug("EntityFactory transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("EntityFactory rollback error - transaction aborted with: "
                        + e2.toString());
                log.warn("EntityFactory create Error Item");
                new CatalogItem(getCatalogItem, 0, "Database Error", 0.0, "",
                        0, false, 0.0, "", "");
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("EntityFactory db connection returned to pool");
            }
        }
        return viewItem;
    }

    /**
     * Gets the category.
     *
     * @param category
     *            the category
     * @param item
     *            the item
     * @return the category
     */
    public ItemCatalog getCategory(String category, String item) {
        // shell for connection
        Connection con = null;
        // get a bucket for the recieved entitys
        ItemCatalog categoryItems = new ItemCatalog();
        log.debug("EntityFactory getCategory Request");
        log.debug("EntityFactory try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("EntityFactory initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("EntityFactory gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("EntityFactory could not initialize Connection Pool");
            }
        }
        log.debug("EntityFactory try to get connection");
        try {
            con = pool.getConnection();
            log.debug("EntityFactory db connection established");
            // enable transaction
            con.setAutoCommit(false);
            log.debug("EntityFactory transaction start: getCategory()");

            String prprdQuery;
            PreparedStatement categoryQuery;

            if (item.equals("...") == true) {
                item = null;
            }
            if (item == null) {
                prprdQuery = "SELECT * FROM (ArticleTab INNER JOIN CategoryTab USING (CategoryID)) INNER JOIN SubcategoryTab USING (SubcategoryID) WHERE LOWER(Category)=LOWER(?) LIMIT 25;";
                categoryQuery = con.prepareStatement(prprdQuery);
                categoryQuery.setString(1, category);
                log.debug("EntityFactory doing: " + prprdQuery);
            } else {
                prprdQuery = "SELECT * FROM (ArticleTab INNER JOIN CategoryTab USING (CategoryID)) INNER JOIN SubcategoryTab USING (SubcategoryID) WHERE LOWER(Category)=LOWER(?) AND LOWER(Subcategory)=LOWER(?) LIMIT 25;";
                categoryQuery = con.prepareStatement(prprdQuery);
                categoryQuery.setString(1, category);
                categoryQuery.setString(2, item);
                log.debug("EntityFactory doing: " + prprdQuery);
            }

            ResultSet resultSet = categoryQuery.executeQuery();
            resultSet.next();
            int categoryID = resultSet.getInt("CategoryID");
            int subcategoryID = resultSet.getInt("SubcategoryID");
            log.debug("EntityFactory Query: CategoryID: " + categoryID
                    + " SubcategoryID: " + subcategoryID);
            prprdQuery = "SELECT * FROM (ArticleTab INNER JOIN CategoryTab USING (CategoryID)) INNER JOIN SubcategoryTab USING (SubcategoryID) WHERE CategoryID = ?";

            if (item != null) {
                prprdQuery += " AND SubcategoryID = ?;";
            } else {
                prprdQuery += ";";
            }
            categoryQuery = con.prepareStatement(prprdQuery);
            categoryQuery.setInt(1, categoryID);
            if (item != null) {
                categoryQuery.setInt(2, subcategoryID);
            }
            log.debug("EntityFactory doing: " + prprdQuery);
            resultSet = categoryQuery.executeQuery();

            // fill it
            while (resultSet.next()) {
                new CatalogItem(categoryItems, resultSet.getInt("artid"),
                        resultSet.getString("artname"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getBoolean("offer"),
                        resultSet.getDouble("discount"),
                        resultSet.getString("category"),
                        resultSet.getString("subcategory"));
            }

            con.commit();
            log.debug("EntityFactory transaction complete");
        } catch (Exception e1) {
            // if error during transaction rollback()
            log.debug(e1.toString());
            try {
                log.debug("EntityFactory transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("EntityFactory rollback error - transaction aborted with: "
                        + e2.toString());
                log.warn("EntityFactory create Error Item");
                new CatalogItem(categoryItems, 0, "Database Error", 0.0, "", 0,
                        false, 0.0, "", "");
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("EntityFactory db connection returned to pool");
            }
        }
        return categoryItems;
    }

    /**
     * Gets the hot sales.
     *
     * @return the hot sales
     */
    public ItemCatalog getHotSales() {
        // shell for connection
        Connection con = null;
        // get a bucket for the recieved entitys
        ItemCatalog hotSaleItems = new ItemCatalog();
        log.debug("EntityFactory getHotSales Request");
        log.debug("EntityFactory try to reach relevant pool");
        if (pool == null) {
            try {
                log.debug("EntityFactory initialize ClassPools.PAGE");
                ClassPools.initialize(SecurityLevel.PAGE);
                log.debug("EntityFactory gets Pool of ClassPools.PAGE");
                pool = ClassPools.getPool(SecurityLevel.PAGE);
            } catch (Exception e) {
                log.warn("EntityFactory could not initialize Connection Pool");
            }
        }
        log.debug("EntityFactory try to get connection");
        try {
            con = pool.getConnection();
            log.debug("EntityFactory db connection established");
            // enable transaction
            con.setAutoCommit(false);
            log.debug("EntityFactory transaction start: getHotSales()");
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM (ArticleTab INNER JOIN CategoryTab USING (CategoryID)) INNER JOIN SubcategoryTab USING (SubcategoryID) WHERE ArticleTab.offer = 'true' LIMIT 20";
            ResultSet resultSet = stmt.executeQuery(query);
            log.debug("EntityFactory doing: " + query);
            // fill it
            while (resultSet.next()) {
                new CatalogItem(hotSaleItems, resultSet.getInt("artid"),
                        resultSet.getString("artname"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description"),
                        resultSet.getInt("quantity"),
                        resultSet.getBoolean("offer"),
                        resultSet.getDouble("discount"),
                        resultSet.getString("category"),
                        resultSet.getString("subcategory"));

            }
            con.commit();
            log.debug("EntityFactory transaction complete");
        } catch (Exception e1) {
            // if error during transaction rollback()
            try {
                log.debug("EntityFactory transaction error - try rollback");
                con.rollback();
            } catch (Exception e2) {
                log.warn("EntityFactory rollback error - transaction aborted with: "
                        + e2.toString());
                log.warn("EntityFactory create Error Item");
                new CatalogItem(hotSaleItems, 0, "Database Error", 0.0, "", 0,
                        false, 0.0, "", "");
            }
        } finally {
            if (con != null) {
                // return connection to pool
                pool.returnConnection(con);
                log.debug("EntityFactory db connection returned to pool");
            }
        }
        return hotSaleItems;
    }

}
