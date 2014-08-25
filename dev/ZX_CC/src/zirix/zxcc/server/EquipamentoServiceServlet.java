/*ZIRIX CONTROL CENTER
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: JAVA*/

package zirix.zxcc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zirix.zxcc.server.dao.EstoqueDAO;
import zirix.zxcc.server.dao.InstalacaoDAO;
import zirix.zxcc.server.dao.ModuloDAO;
import zirix.zxcc.server.dao.DAOManager;
import zirix.zxcc.server.dao.PkList;
import zirix.zxcc.server.ZXCCConstantsServlet;

/**
 * Servlet implementation class ClienteService
 */
@WebServlet( name="EquipamentoService", urlPatterns = {"/services/equipamento"}, loadOnStartup=1)
public class EquipamentoServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EquipamentoServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Set response content type
		   response.setContentType("text/html");

		   PrintWriter out = response.getWriter();

		   String OP_CODE = request.getParameter("OP_CODE");

		   try {
			   ModuloDAO daoModulo = new ModuloDAO();
			   PkList pkList;

			   if ((OP_CODE.compareTo("UPDATE") == 0) || (OP_CODE.compareTo("CREATE") == 0)) {

				   String NUMERO_MODULO = request.getParameter("NUM_MODULO").trim();
				   daoModulo.setAttValueFor("NUMERO_MODULO", NUMERO_MODULO);

				   String Aux = request.getParameter("INSTALADO").trim();
				   int INSTALADO = Integer.parseInt(Aux);

				   if(INSTALADO != 1){
					   String COD_CLIENTE = request.getParameter("COD_CLIENTE").trim();
					   daoModulo.setAttValueFor("COD_CLIENTE", Integer.parseInt(COD_CLIENTE));
					   //TODO switch de acordo com a unidade. Hoje somente ve�culo
					   /*Aux = request.getParameter("COD_VEICULO").trim();
					   int COD_VEICULO = Integer.parseInt(Aux);*/
				   }else{
					   daoModulo.setAttValueFor("COD_CLIENTE", 290); //Atualmente, o cod_cliente da ZIRIX � 290, futuramente ser� 1.
					   												 //Deve acontecer para caso o modulo esteja em estoque.
					   												 //Futuramente pode-se controlar mais de um estoque, como por exemplo WGP (Canela)!
				   }

				   String COD_MODELO = request.getParameter("COD_MODELO").trim();
				   daoModulo.setAttValueFor("COD_MODELO", Integer.parseInt(COD_MODELO));

				   String COD_CHIP = request.getParameter("COD_CHIP").trim();
				   daoModulo.setAttValueFor("COD_CHIP", Integer.parseInt(COD_CHIP));

				   String NFE = request.getParameter("NFE").trim();
				   daoModulo.setAttValueFor("NFE", NFE);

				   daoModulo.setAttValueFor("COD_ESTADO", 1);

				   //REALIZAR CREATE INSTALACAO TEMP
				   int pkInstalacao = 0;
				   int count = 0;

				   Vector<String[]> CodInstalacao_ = new Vector<String[]>();
				   try {
					   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COUNT(*) "
							   + " 											                 FROM " + ZXCCConstantsServlet.DB_NAME_ + "INSTALACAO "
							   + "                                                          WHERE COD_MODULO = 0 "
							   + "                                                            AND COD_UNIDADE = " + INSTALADO
							   + "                                                            AND DELETED = 0 ");
					   for (int i=0;i < values.size();i++) {
						   String[] attList = new String[1];
						   attList[0] = values.get(i)[0].toString();
						   CodInstalacao_.add(attList);
					   }
				   }catch (SQLException ex) {
					   ex.printStackTrace();
				   }  finally {
					   count = Integer.parseInt(CodInstalacao_.elementAt(0)[0].trim());
				   }

				   if(count == 0){
					   InstalacaoDAO daoInstalacao = new InstalacaoDAO();
					   daoInstalacao.setAttValueFor("COD_MODULO", 0);
					   daoInstalacao.setAttValueFor("COD_UNIDADE", INSTALADO);
					   daoInstalacao.setAttValueFor("DELETED", 0);
					   daoInstalacao.Create();
				   }

				   try {
					   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_INSTALACAO "
							   + " 											                 FROM " + ZXCCConstantsServlet.DB_NAME_ + "INSTALACAO "
							   + "                                                          WHERE COD_MODULO = 0 "
							   + "                                                            AND COD_UNIDADE = " + INSTALADO
							   + "                                                            AND DELETED = 0 ");
					   for (int i=0;i < values.size();i++) {
						   String[] attList = new String[1];
						   attList[0] = values.get(i)[0].toString();
						   CodInstalacao_.add(attList);
					   }
				   }catch (SQLException ex) {
					   ex.printStackTrace();
				   }  finally {
					   pkInstalacao = Integer.parseInt(CodInstalacao_.elementAt(0)[0].trim());
				   }
				   //FIM DA INSTALACAO TEMP

				   daoModulo.setAttValueFor("COD_INSTALACAO", pkInstalacao);

				   String SN = request.getParameter("SN").trim();
				   daoModulo.setAttValueFor("SN_MODULO", SN);

				   if (OP_CODE.compareTo("UPDATE") == 0){

					   String COD_MODULO = request.getParameter("COD_MODULO");
					   pkList = ModuloDAO.createKey("COD_MODULO", Integer.parseInt(COD_MODULO));

					   daoModulo.setPkList(pkList);
					   daoModulo.update();
				   }
				   else
				   {
					   daoModulo.Create();
					   int pkModulo = 0;
					   Vector<String[]> CodModulo_ = new Vector<String[]>();

					   try {
						   ArrayList<Object[]> values = DAOManager.getInstance().executeQuery("SELECT COD_MODULO "
								   + " 											                 FROM " + ZXCCConstantsServlet.DB_NAME_ + "MODULO "
								   + "                                                          WHERE NUMERO_MODULO = " + NUMERO_MODULO );
						   for (int i=0;i < values.size();i++) {
							   String[] attList = new String[1];
							   attList[0] = values.get(i)[0].toString();
							   CodModulo_.add(attList);
						   }
					   }catch (SQLException ex) {
						   ex.printStackTrace();
					   }  finally {
						   pkModulo = Integer.parseInt(CodModulo_.elementAt(0)[0].trim());
					   }

					   if(pkModulo != 0){
						   try {
							   DAOManager.getInstance().executeQuery("UPDATE " + ZXCCConstantsServlet.DB_NAME_ + "INSTALACAO SET COD_MODULO = " + pkModulo + " WHERE COD_INSTALACAO = " + pkInstalacao);
						   }catch (SQLException ex) {
							   ex.printStackTrace();
						   }finally{}

						   //Esse switch cresce de acordo com os tipos de unidades que forem sendo criados!
						   switch(INSTALADO){
						   case 1: //Estoque
							   EstoqueDAO daoEstoque = new EstoqueDAO();
							   daoEstoque.setAttValueFor("COD_INSTALACAO", pkInstalacao);
							   daoEstoque.setAttValueFor("DELETED", 0);
							   daoEstoque.Create();
							   break;
						   case 2: //Ve�culo
							   //Cadastrar M�dulo diretamente no cliente indica que deve ser feito o cadastro do mesmo no estoque
							   //e a� sim realizar a altera��o para o cliente
							   //TODO 'UPDATE VEICULO SET COD_MODULO = ' + pkModulo + ' WHERE COD_VEICULO = ' + COD_VEICULO;
							   break;
						   }

						   try {
							   DAOManager.getInstance().executeQuery("UPDATE " + ZXCCConstantsServlet.DB_NAME_ + "CHIP SET COD_MODULO = " + pkModulo + " WHERE COD_CHIP = " + COD_CHIP);
						   }catch (SQLException ex) {
							   ex.printStackTrace();
						   }finally{}
					   }
				   }
				   // TODO CRIAR P�GINA DE REDIRECIONAMENTO OU ALERT DE INGRESSO REALIZADO
				   String COD_USUARIO = request.getParameter("COD_USUARIO").trim();
				   response.sendRedirect(ZXCCConstantsServlet.URL_ADRESS_ + "zx_cc.jsp?COD_USUARIO=" + COD_USUARIO);
			   }

			   else if (OP_CODE.compareTo("DELETE") == 0){

				   String COD_MODULO = request.getParameter("COD_MODULO");
				   pkList = ModuloDAO.createKey("COD_MODULO", Integer.parseInt(COD_MODULO));

				   daoModulo.setPkList(pkList);
				   daoModulo.delete();
			   }
		   } catch (Exception e) {
				   out.println("Error on EquipamentoServiceServlet... " + ' ' + e.getMessage());
		   }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}

