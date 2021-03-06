/*ZIRIX CONTROL CENTER - DEFINED PROCESS DAO
DESENVOLVIDO POR ZIRIX SOLUÇÕES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server.mock.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class DefinedProcessDAO extends GenericDAO<DefinedProcessDAO> {


	public DefinedProcessDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public DefinedProcessDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("PROCESS_NAME",res.getString("PROCESS_NAME"));
    }

    public Set<String> getPkNamesSet() {
    	return DefinedProcessDAO.createKey("PROCESS_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "DEFINED_PROCESS";
}