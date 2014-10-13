package de.alextape.sonicshop.modell;

import java.sql.SQLException;

/**
 * The Interface DatabaseInterface.
 */
@Deprecated
public interface DatabaseInterface {

    /** The timestamp. */
    static int timestamp = 0;

    /* Set the Data of an Entity */
    /**
     * Creates the entity.
     *
     * @param article
     *            the article
     * @return true, if successful
     * @throws SQLException
     *             the SQL exception
     */
    public boolean createEntity(EntityFactory article) throws SQLException;

    /* Set the Data of an Entity */
    /**
     * Delete entity.
     *
     * @param article
     *            the article
     * @return true, if successful
     * @throws SQLException
     *             the SQL exception
     */
    public boolean deleteEntity(EntityFactory article) throws SQLException;

    /* Get the Value of an ID */
    /**
     * Gets the entity.
     *
     * @param ID
     *            the id
     * @return the entity
     * @throws SQLException
     *             the SQL exception
     */
    public boolean getEntity(String ID) throws SQLException;

    /* Get the whole Table */
    /**
     * Gets the table.
     *
     * @return the table
     * @throws SQLException
     *             the SQL exception
     */
    public boolean getTable() throws SQLException;

    /* Update the internal Memory */
    /**
     * Update database.
     *
     * @return true, if successful
     * @throws SQLException
     *             the SQL exception
     */
    public boolean updateDatabase() throws SQLException;

    /* Set the Data of an Entity */
    /**
     * Update entity.
     *
     * @param article
     *            the article
     * @return true, if successful
     * @throws SQLException
     *             the SQL exception
     */
    public boolean updateEntity(EntityFactory article) throws SQLException;

}