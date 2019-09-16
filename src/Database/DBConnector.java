/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Computebot
 */
public class DBConnector {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static String driver = "jdbc:ucanaccess://";
    private static String path = "";

    
    private DBConnector(){  
        String connectionString = driver + path;
            try {
                conn = DriverManager.getConnection(connectionString);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"The database no longer resides at that location!\nPlease choose another!","Denied!",JOptionPane.ERROR_MESSAGE); 
            }    
    }
    
    public static DBConnector getDBConnection(){
        return new DBConnector();
    }
    
    public Connection getConnection(){
        return conn;
    }

    
    public static Statement getStaticStatement(){
        DBConnector.getDBConnection();
            try{
              stmt = conn.createStatement();
            } catch(SQLException e){
                printSQLException(e);
                return stmt;
        }
        return stmt;
    }
    
    public static void setDBPath(String dbPath){
        path = dbPath;
    } 
    public static String getDBPath(){
        return path;
    } 
    
    
    private static void printSQLException(SQLException e){
        JOptionPane.showMessageDialog(null,"I could not find the database items you were looking for!\n Are you sure you are connected to the right Database?","Database Error!",JOptionPane.ERROR_MESSAGE);
        System.out.println("SQLException: "+e.getMessage());
        System.out.println("SQLState: "+e.getSQLState());
        System.out.println("VendorError: "+e.getErrorCode());
    }
}
