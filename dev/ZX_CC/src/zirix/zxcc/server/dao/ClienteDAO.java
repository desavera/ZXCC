package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.ZXCCConstants;

public class ClienteDAO extends GenericDAO<ClienteDAO> {

	private static ZXCCConstants AMBIENTE_ = new ZXCCConstants();

    public ClienteDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ClienteDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    /*
     * Temos de tratar a FK
     * @see zirix.zxcc_prod.server.dao.GenericDAO#delete()
     */
    /*public void delete() throws SQLException {
    	String query1 = "DELETE FROM CONTATO_CLIENTE WHERE COD_CLIENTE=\'" + getPkValueFor("COD_CLIENTE") + "\'";
    	String query2 = "DELETE FROM DOCUMENTO_CLIENTE WHERE COD_CLIENTE=\'" + getPkValueFor("COD_CLIENTE") + "\'";
    	String query3 = "DELETE FROM VEICULO WHERE COD_CLIENTE=\'" + getPkValueFor("COD_CLIENTE") + "\'";
    	String query4 = "DELETE FROM MODULO WHERE COD_CLIENTE=\'" + getPkValueFor("COD_CLIENTE") + "\'";

    	DAOManager.getInstance().executeQuery(query1);
    	DAOManager.getInstance().executeQuery(query2);
    	DAOManager.getInstance().executeQuery(query3);
    	DAOManager.getInstance().executeQuery(query4);

    }*/

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("NOME",res.getString("NOME"));
    	setAttValueFor("TIPO",res.getInt("TIPO"));
    	setAttValueFor("NOME_FANTASIA",res.getString("NOME_FANTASIA"));
    	setAttValueFor("SITE",res.getString("SITE"));
    	setAttValueFor("DATA_NASCIMENTO",res.getDate("DATA_NASCIMENTO"));
    	setAttValueFor("DATA_INGRESSO",res.getDate("DATA_INGRESSO"));
    	setAttValueFor("COD_VENDEDOR", res.getInt("COD_VENDEDOR"));  
    	setAttValueFor("DELETED",res.getInt("DELETED"));   	
    }

    public Set<String> getPkNamesSet() {
    	return ClienteDAO.createKey("COD_CLIENTE", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

	public final static String TABLENAME = AMBIENTE_.db_name + "CLIENTE";
}