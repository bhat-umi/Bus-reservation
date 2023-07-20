import java.sql.*;
public class connect {
    private static final String Database="jdbc:mysql://localhost:3306/Bus_reservation";
    private static final String userName ="root";
    private static final String passwd="";
    Statement  sql;
    Connection con;
    connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Trying to connect");  
             con=DriverManager.getConnection(Database,userName,passwd);
            System.out.println("Connected");  
            sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);  
        }
        catch(Exception e)
        {
            System.out.println("Unable to connect"+e);
            //System.exit(1);
        }
    }


    
}
