package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DAOManager {

	
	public static final int CONNECTION_CHECK_TIMEOUT_IN_SECS = 10;	
	
		
	public static DAOManager getInstance() {
        return DAOManagerSingleton.INSTANCE.get();
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
    
    public void executeUpdate(String query) throws SQLException {
    	
    	
    }
    
    public ArrayList<Object[]> executeQuery(String query) throws SQLException {
    	    	
        PreparedStatement stmt = null;
        ResultSet res = null;
        Connection con = getConnection();
                
        try
        {                       	        	
	       stmt = con.prepareStatement(query);	        	       	    			       
	       res = stmt.executeQuery();
	        
	        ArrayList<Object[]> values = new ArrayList<Object[]>();	    	       
	        
	        int colCount = res.getMetaData().getColumnCount();               	      
	        
	        while (res.next()) {
	        	
	        	Object[] rowValues = new Object[colCount];
	        	for (int i=1;i <= colCount;i++)	        	
	        		rowValues[i-1] = res.getObject(res.getMetaData().getColumnName(i));
	        		
	        	
	        	values.add(rowValues);
	        	
	        }

	        return values;	        			        		        
	        
        } catch(SQLException e){ 
	        
        	            
            throw e; 
        }
        
        finally {

        	con.commit();
        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	closeConnection(con);
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
  

    private DAOManager() throws Exception {
        try
        {
        	// Look up the JNDI data source only once at init time
  	      Context envCtx = (Context) new InitialContext().lookup("java:comp/env"); 	      
  	      src = (DataSource) envCtx.lookup("jdbc/poolConn");
  	      
        } catch(Exception e) { throw e; }
    }

    private static class DAOManagerSingleton {

        public static final ThreadLocal<DAOManager> INSTANCE;
        static
        {
            ThreadLocal<DAOManager> dm;
            try
            {
                dm = new ThreadLocal<DAOManager>(){
                	
                    @Override
                    protected DAOManager initialValue() {
                        try
                        {
                            return new DAOManager();
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