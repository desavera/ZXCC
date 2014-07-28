package zirix.zxcc.server.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class AnexoPedidoDAO extends GenericDAO<AnexoPedidoDAO> {

	public AnexoPedidoDAO(PkList pkList) {
		super(TABLENAME,pkList);
    }

	public AnexoPedidoDAO() {
		super(TABLENAME);
    }

	public static PkList createKey(String name,int value) {
		PkList key = new PkList();
		key.put(name, new Integer(value));

		return key;
	}

    public void loadAttsFromResultSet(ResultSet res) throws SQLException {
    	setAttValueFor("COD_PEDIDO",res.getInt("COD_PEDIDO"));
    	setAttValueFor("COD_CLIENTE",res.getInt("COD_CLIENTE"));
    	setAttValueFor("ANEXO",res.getString("ANEXO"));
    }

    public Set<String> getPkNamesSet() {
    	return AnexoPedidoDAO.createKey("COD_ANEXO", GenericDAO.AUTO_INCREMENT_PK_VALUE).keySet();
    }

    public final static String TABLENAME = "ZX_CC_DEV.dbo.ANEXO_PEDIDO";
}