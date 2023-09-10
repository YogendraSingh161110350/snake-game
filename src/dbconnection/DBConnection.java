
package dbconnection;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author yogendra singh
 */
public class DBConnection
{
     Properties p = new Properties();
     public static Connection getConnection()
     {
         Connection con = null;
         try
         {
             FileReader fr = new FileReader("C:\\Users\\91740\\Documents\\Custom Office Templates\\snake2D\\src\\properties\\database.properties");
             Properties p = new Properties();
             p.load(fr);
             Class.forName(p.getProperty("class-name"));
             con = DriverManager.getConnection(p.getProperty("database-url"), p.getProperty("username"), p.getProperty("password"));
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
         return con;
     }
    
}
