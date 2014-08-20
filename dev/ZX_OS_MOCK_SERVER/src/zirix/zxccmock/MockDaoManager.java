package zirix.zxccmock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class MockDaoManager {

	
	public static final int CONNECTION_CHECK_TIMEOUT_IN_SECS = 10;	
	
		
	public static MockDaoManager getInstance() {
        return MockConnectionManagerSingleton.INSTANCE.get();
    }  
	
    public Connection getConnection() throws SQLException {
        try
        {
            return src.getConnection();
        }
        catch(SQLException e) { throw e; }
    }
    
    public Connection getLocalConnection() throws SQLException {
	
    	String url="jdbc:sqlserver://localhost:1433;integratedSecurity=true";    	
    	Connection conn = null;
    	
    	try {
    		
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	    conn = DriverManager.getConnection(url);
    	    
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		
    	} finally {
    		
    		return conn;
    	}
    	
    }
    
 
    public void closeConnection(Connection con) throws SQLException {
        try
        {
            if(con!=null && con.isValid(CONNECTION_CHECK_TIMEOUT_IN_SECS))
                con.close();
        }
        catch(SQLException e) { throw e; }
    }
    
    //Private
    private DataSource src;
  

    private MockDaoManager() throws Exception {
        try
        {
        	// Look up the JNDI data source only once at init time
  	      Context envCtx = (Context) new InitialContext().lookup("java:comp/env"); 	      
  	      src = (DataSource) envCtx.lookup("jdbc/poolConn");
  	      
        } catch(Exception e) { throw e; }
    }

    private static class MockConnectionManagerSingleton {

        public static final ThreadLocal<MockDaoManager> INSTANCE;
        static
        {
            ThreadLocal<MockDaoManager> dm;
            try
            {
                dm = new ThreadLocal<MockDaoManager>(){
                	
                    @Override
                    protected MockDaoManager initialValue() {
                        try
                        {
                            return new MockDaoManager();
                        }
                        catch(Exception e)
                        {
                            return null;
                        }
                    }
                };
            }
            
            catch(Exception e) {
            	dm = null;
            }
                            
            INSTANCE = dm;
        }        
    }
}