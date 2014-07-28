/*ZIRIX CONTROL CENTER - MAIN PAGE
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES & M�RIO DE S� VERA
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import zirix.zxcc.server.dao.ClienteDAO;
import zirix.zxcc.server.dao.DAOManager;
import zirix.zxcc.server.dao.PkList;

public class ClienteServiceBean {

	private ClienteDAO dao_ = null;
	private Integer COD_CLIENTE_ = null;

	public ClienteServiceBean(String[] pkVal) {
		setPk(pkVal);
	}

	public ClienteServiceBean() {
	}

	public void setPk(Object[] pkVal) {

		COD_CLIENTE_ = new Integer((String)pkVal[0]);
		PkList pkList = new PkList();
	    pkList.put("COD_CLIENTE",COD_CLIENTE_);
	    dao_ = new ClienteDAO(pkList);
	    try {
	    	dao_.read();
	    } catch (SQLException ex) {
	    	ex.printStackTrace();
	    } finally {
	    }
	}

	public Integer getCodVendedor(){

		Integer codVendedor = (Integer)dao_.getAttValueFor("COD_VENDEDOR");
		return codVendedor;
	}

	public Integer getTipo(){

		Integer tipo = (Integer)dao_.getAttValueFor("TIPO");
		return tipo;
	}

	public String getNome() {

    	String nome = (String)dao_.getAttValueFor("NOME");
    	return nome;
	}

	public String getNomeFantasia() {

    	String nomeFantasia = (String)dao_.getAttValueFor("NOME_FANTASIA");
    	return nomeFantasia;
	}

	public String getDtNascimento() {

		String dtNascimento = dao_.getAttValueFor("DATA_NASCIMENTO").toString();
    	return dtNascimento;
	}

	public String getSite() {

    	String site = (String)dao_.getAttValueFor("SITE");
    	return site;
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getEnd() {

	    Vector<String[]> endClienteList = new Vector<String[]>();
	    try {
		    ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT ENDERECO, BAIRRO, CIDADE, UF, COD_PAIS, COMPLEMENTO, CEP, COD_ENDERECO "
		    		+ "							   FROM ZX_CC_DEV.dbo.ENDERECO_CLIENTE "
		    		+ "							  WHERE COD_CLIENTE = " + COD_CLIENTE_);

		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[8]; // pois eu sei que sao 8 atributos de fato !
			    attList[0] = (String) values.get(i)[0];
			    attList[1] = (String) values.get(i)[1];
			    attList[2] = (String) values.get(i)[2];
			    attList[3] = values.get(i)[3].toString();
			    attList[4] = values.get(i)[4].toString();
			    attList[5] = (String) values.get(i)[5];
			    attList[6] = (String) values.get(i)[6];
			    attList[7] = values.get(i)[7].toString();
			    endClienteList.add(attList);
		    }
	    } catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return endClienteList;
	    }
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getContato(){

		Vector<String[]> contatoClienteList = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT ZX_CC_DEV.dbo.TIPO_CONTATO.NOME_TIPO "
					+ "                                                              , ZX_CC_DEV.dbo.CONTATO_CLIENTE.DDD "
					+ "                                                              , ZX_CC_DEV.dbo.CONTATO_CLIENTE.NUMERO "
					+ "                                                              , ZX_CC_DEV.dbo.CONTATO_CLIENTE.COD_PAIS "
					+ "                                                              , ZX_CC_DEV.dbo.CONTATO_CLIENTE.NOME "
					+ "                                                              , ZX_CC_DEV.dbo.INFO_CONTATO.NOME_GRAU "
		    		+ "							   FROM ZX_CC_DEV.dbo.CONTATO_CLIENTE "
					+ "                               , ZX_CC_DEV.dbo.INFO_CONTATO "
					+ "                               , ZX_CC_DEV.dbo.TIPO_CONTATO "
		    		+ "							  WHERE ZX_CC_DEV.dbo.INFO_CONTATO.COD_GRAU = ZX_CC_DEV.dbo.CONTATO_CLIENTE.COD_GRAU "
		    		+ "                             AND ZX_CC_DEV.dbo.TIPO_CONTATO.COD_CONTATO = ZX_CC_DEV.dbo.CONTATO_CLIENTE.COD_CONTATO "
		    		+ "                             AND ZX_CC_DEV.dbo.CONTATO_CLIENTE.COD_CLIENTE = " + COD_CLIENTE_);

		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[6]; // pois eu sei que sao 6 atributos de fato !
			    attList[0] = (String) values.get(i)[0];
			    attList[1] = (String) values.get(i)[1];
			    attList[2] = (String) values.get(i)[2];
			    attList[3] = values.get(i)[3].toString();
			    attList[4] = (String) values.get(i)[4];
			    attList[5] = (String) values.get(i)[5];
			    contatoClienteList.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return contatoClienteList;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getDocumento(){

		Vector<String[]> documentoClienteList = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT ZX_CC_DEV.dbo.DOCUMENTO_CLIENTE.NUMERO "
					+ ",							    ZX_CC_DEV.dbo.TIPO_DOCUMENTO.NOME "
					+ ",                                ZX_CC_DEV.dbo.DOCUMENTO_CLIENTE.DATA_EMISSAO "
					+ ",                                ZX_CC_DEV.dbo.DOCUMENTO_CLIENTE.ORGAO_EMISSOR "
		    		+ "							   FROM ZX_CC_DEV.dbo.DOCUMENTO_CLIENTE "
		    		+ "							      , ZX_CC_DEV.dbo.TIPO_DOCUMENTO "
		    		+ "							  WHERE ZX_CC_DEV.dbo.TIPO_DOCUMENTO.COD_DOCUMENTO = ZX_CC_DEV.dbo.DOCUMENTO_CLIENTE.COD_DOCUMENTO "
		    		+ "                             AND ZX_CC_DEV.dbo.DOCUMENTO_CLIENTE.COD_CLIENTE = " + COD_CLIENTE_);

		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[4]; // pois eu sei que sao 4 atributos de fato !
			    attList[0] = (String) values.get(i)[0];
			    attList[1] = (String) values.get(i)[1];
		    	attList[2] = ((java.sql.Date) values.get(i)[2]).toLocalDate().toString();
			    attList[3] = (String) values.get(i)[3];
			    documentoClienteList.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return documentoClienteList;
		}
	}

	@SuppressWarnings("finally")
	public Vector<String[]> getEmail(){

		Vector<String[]> emailClienteList = new Vector<String[]>();
		try {
			ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT EMAIL "
		    		+ "							   FROM ZX_CC_DEV.dbo.EMAIL_CLI_VEN "
		    		+ "							  WHERE TIPO_CLI_VEN = 0 "
		    		+ "                             AND COD_CLI_VEN = " + COD_CLIENTE_);

		    for (int i=0;i < values.size();i++) {
			    String[] attList = new String[1]; // pois eu sei que e 1 atributo de fato !
			    attList[0] = (String) values.get(i)[0];
			    emailClienteList.add(attList);
		    }
		}catch (SQLException ex) {
    		ex.printStackTrace();
		}  finally {
			return emailClienteList;
		}
	}
}