<!DOCTYPE html>
<!--
ZIRIX CONTROL CENTER - INDEX PAGE
DESENVOLVIDO POR ZIRIX SOLU��ES EM RASTREAMENTO LTDA.

DESENVOLVEDOR: RAPHAEL B. MARQUES
TECNOLOGIAS UTILIZADAS: HTML5, JAVASCRIPT E JSP
-->
<%@ page import="zirix.zxcc.server.*,zirix.zxcc.server.dao.*,java.sql.SQLException,java.util.Vector" %>

<%
	String log_failed = request.getParameter("LOGIN_FAILED");
%>

<html lang="pt-br">  
    <head>
        <title>ZX_CC</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta charset="utf-8">    
        <link rel="stylesheet" href="css/Bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/sizes.css">
    </head>  
    <body>
        <div class="container_login">        
            <form class="login_form" action="<%=ZXMain.URL_ADRESS_%>services/login">
                <img src="imagens/zx_cc_login.jpg" border="0">
                <div class="fields_form">
                    Login:&nbsp;&nbsp;<input type="text" name="login_login" style="color: #000;" autofocus="autofocus">
                   <br>
                   <br>
                   Senha: <input type="password" name="senha_login" style="color: #000;">
                   <br><%if(log_failed.compareTo("FAIL") == 0) {%>Login e/ou Senha inv�lidos. Tente novamente!<%}%>
                   <br>
                   <button type="submit" style="color: #000;" id="entrar_login">Entrar</button>
                </div>
            </form>
        </div>
    </body>
</html>