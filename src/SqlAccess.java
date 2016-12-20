/**
 * Sajay Shah, Mohnish Kadakia, Brandon Feist, Rahul Patel, Harkanwar Singh
 *
 * SqlQuery class allows for get and set of given MySQL table.
 */

import java.sql.*;
public class SqlAccess {

    private String mySQLURL;
    private String username;
    private String password;
    
    private Statement statement;
    private Connection connection;
    
    /**
     * Main constructor for SqlQuery.
     * 
     * @param url String URL pointing to the MySQL table.
     * @param username String username for MySQL table.
     * @param password String password for MySQL table.
     */
    public SqlAccess(String url, String username, String password) {
        this.mySQLURL = url;
        this.username = username;
        this.password = password;
        
        try {
			connection = DriverManager.getConnection(mySQLURL, username, password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    /**
     * Returns query results from SqlQuery's MySQL table.
     * 
     * @param query String MySQL query.
     * @return ResultSet created from given query string.
     */
    public ResultSet getQuery(String query) {
    	ResultSet resultSet = null;
    	
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return resultSet;
    }

    /**
     * setResultSet updates the SqlQuery's MySQL table.
     * 
     * @param query String query to setup the MySQL table.
     */
    public void setQuery(String query) {
        try {
            // Execute given query.
            statement.execute(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * createTable uses given query to create a table.
     * 
     * @param query String create table query.
     */
    public void createTable(String query) {
        try {
            //Create the table using given query.
            statement.executeUpdate(query);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
    }
    
    public void close() {
    	try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
