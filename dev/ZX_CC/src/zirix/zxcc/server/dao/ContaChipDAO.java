package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.ZXCCConstants;

public class ContaChipDAO extends GenericDAO<ChipDAO> {

	private static ZXCCConstants AMBIENTE_ = new ZXCCConstants();

    public ContaChipDAO(PkList pkList) {
        super(TABLENAME,pkList);
    }

    public ContaChipDAO(){
    	super(TABLENAME);
    }

    public static PkList createKey(String name,int value) {

		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("NUMERO_CONTA",res.getString("NUMERO_CONTA"));
    	setAttValueFor("DELETED",res.getInt("DELETED"));
    }

    public Set<String> getPkNamesSet() {
    	return ContaChipDAO.createKey("COD_CONTA", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

	public final static String TABLENAME = AMBIENTE_.db_name + "CONTA_CHIP";
}