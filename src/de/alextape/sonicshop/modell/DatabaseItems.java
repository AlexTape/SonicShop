package de.alextape.sonicshop.modell;

import java.sql.SQLException;

/**
 * The Class DatabaseItems.
 */
@Deprecated
public class DatabaseItems implements DatabaseInterface {

    /*
     * (non-Javadoc)
     * 
     * @see modell.DatabaseInterface#createEntity(modell.EntityFactory)
     */
    @Override
    public boolean createEntity(EntityFactory article) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see modell.DatabaseInterface#deleteEntity(modell.EntityFactory)
     */
    @Override
    public boolean deleteEntity(EntityFactory article) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see modell.DatabaseInterface#getEntity(java.lang.String)
     */
    @Override
    public boolean getEntity(String ID) throws SQLException {
        // TODO make query
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see modell.DatabaseInterface#getTable()
     */
    @Override
    public boolean getTable() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see modell.DatabaseInterface#updateDatabase()
     */
    @Override
    public boolean updateDatabase() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see modell.DatabaseInterface#updateEntity(modell.EntityFactory)
     */
    @Override
    public boolean updateEntity(EntityFactory article) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

}
