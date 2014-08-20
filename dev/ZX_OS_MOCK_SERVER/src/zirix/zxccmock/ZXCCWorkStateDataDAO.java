package zirix.zxccmock;

import java.sql.ResultSet;
import java.sql.SQLException;

import zirix.zxcc.server.dao.GenericDAO;
import zirix.zxcc.server.dao.PkList;



public class ZXCCWorkStateDataDAO extends GenericDAO {
	
	
	protected ZXCCWorkStateDataDAO(String tableName, PkList pkList) {
		super(tableName, pkList);
		// TODO Auto-generated constructor stub
	}

	// a responsible
	public int respID() {
		return ((Integer) getAttValueFor("resp_id")).intValue();
	}

	// a status like COMPLETE,INCOMPLETE,ALERT
	public int statusID() {
		return ((Integer) getAttValueFor("work_status_id")).intValue();
	}

	// my URL
	public String URL() {
		return getAttValueFor("work_url").toString();
	}

	// my work ID
	public int workID() {
		return ((Integer) getAttValueFor("work_id")).intValue();
	}

	// next work ID
	public int nextWorkID() {
		return ((Integer) getAttValueFor("next_work_id")).intValue();
	}

	// the timestamp for the work START
	public long timestamp() {
		return ((Integer) getAttValueFor("work_id")).longValue();
	}

	// a title for the work state
	public String title() {
		return getAttValueFor("work_id").toString();
	}					

	@Override
	public void loadAttsFromResultSet(ResultSet res) throws SQLException {
		
		setAttValueFor("work_id",res.getInt("work_id"));
    	setAttValueFor("work_title",res.getString("work_title"));
    	setAttValueFor("resp_id",res.getInt("resp_id"));
    	setAttValueFor("timestamp",res.getInt("timestamp"));
    	setAttValueFor("next_work_id",res.getInt("next_work_id"));
    	setAttValueFor("work_title",res.getString("work_url"));
    	setAttValueFor("work_status_id",res.getInt("work_status_id"));
		
	}
	
	
	public final static String TABLENAME = "zxccmock.work_state_data";
	
}