package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Computebot
 */
public class StatementMaker {
    
    static Statement stmt = null;
    static ResultSet rs = null;
    static int rowsAffected = -1;
    public static ResultSet makeSelectStatement(String sqlStatement){
        try {
                stmt = DBConnector.getStaticStatement();
            rs =  stmt.executeQuery(sqlStatement);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"I could not find the database items you were looking for!\n Are you sure you are connected to the right Database?","Database Error!",JOptionPane.ERROR_MESSAGE);
            System.out.println("SQLException: "+ex.getMessage());
            System.out.println("SQLState: "+ex.getSQLState());
            System.out.println("VendorError: "+ex.getErrorCode());
        }
        return rs;
    }
    
    public static int makeUpdateDeleteOrInsertStatement(String sqlStatement){
        try {
                stmt = DBConnector.getStaticStatement();
            rowsAffected =  stmt.executeUpdate(sqlStatement);            
        } catch (SQLException ex) {
            if(ex.getMessage().contains("integrity constraint violation")){
                JOptionPane.showMessageDialog(null,"Delete action failed.  There are tables that still contain slabs with that species!","Database Error!",JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"Delete, Update, or Insert into database action Failed!","Database Error!",JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("SQLException: "+ex.getMessage());
            System.out.println("SQLState: "+ex.getSQLState());
            System.out.println("VendorError: "+ex.getErrorCode());
        }
        return rowsAffected;
    }

}

