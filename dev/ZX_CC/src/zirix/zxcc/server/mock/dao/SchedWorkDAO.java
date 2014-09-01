package zirix.zxcc.server.mock.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import zirix.zxcc.server.*;
import zirix.zxcc.server.dao.*;

public class SchedWorkDAO extends GenericDAO<SchedWorkDAO> {


	public SchedWorkDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public SchedWorkDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("WORK_NAME",res.getString("WORK_NAME"));
    	setAttValueFor("PROCESS_ID",res.getInt("PROCESS_ID"));
    	setAttValueFor("RESTRICTION_ID",res.getInt("RESTRICTION_ID"));
    	setAttValueFor("PROCESS_STATE_ID",res.getInt("PROCESS_STATE_ID"));
    	setAttValueFor("DEPENDENCY_WORK_ID",res.getInt("DEPENDENCY_WORK_ID"));
    	setAttValueFor("WORK_ALERT_ID",res.getInt("WORK_ALERT_ID"));
    	setAttValueFor("SCHED_TIMESTAMP",res.getString("SCHED_TIMESTAMP"));
    	setAttValueFor("START_TIMESTAMP",res.getTimestamp("START_TIMESTAMP"));
    	setAttValueFor("END_TIMESTAMP",res.getTimestamp("END_TIMESTAMP"));
    	setAttValueFor("WORK_GROUP_ID",res.getInt("WORK_GROUP_ID"));
    }

    public Set<String> getPkNamesSet() {
    	return SchedWorkDAO.createKey("WORK_ID", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = ZXMain.DB_NAME_ + "SCHED_WORK";
}