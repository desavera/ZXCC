/*ZIRIX CONTROL CENTER - TECNICO DAO SERVICE
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TecnicoDAOService {

	public static Vector<TecnicoDAO> loadAll() throws SQLException {

		String query = "SELECT * FROM " + TecnicoDAO.TABLENAME + " WHERE DELETED = 0 ORDER BY NOME_FANTASIA";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet res = null;
		Vector<TecnicoDAO> daoList = new Vector<TecnicoDAO>();

		try {
			con = DAOManager.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			res = stmt.executeQuery();

			while (res.next()) {

				int COD_TECNICO = res.getInt("COD_TECNICO");

				PkList pkList = new PkList();
				pkList.put("COD_TECNICO",COD_TECNICO);
				TecnicoDAO dao = new TecnicoDAO(pkList);

				dao.read();
				dao.loadAttsFromResultSet(res);
				daoList.add(dao);
			}
			return daoList;
		}catch(SQLException e){
			throw e;
		}finally{
        	if (res != null) res.close();
        	if (stmt != null) stmt.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}

	public static int count() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM "+ TecnicoDAO.TABLENAME;
        PreparedStatement counter = null;
        ResultSet res = null;
        Connection con = DAOManager.getInstance().getConnection();

        try{
	        counter = con.prepareStatement(query);
	        res = counter.executeQuery();
	        res.next();
	        return res.getInt("count");
        }

        catch(SQLException e){ throw e; }
        finally {
        	if (res != null) res.close();
        	if (counter != null) counter.close();
        	DAOManager.getInstance().closeConnection(con);
        }
	}
}
