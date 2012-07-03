<%@ page import = "com.master.util.DateFormatter" %>
<%@ page import = "java.util.*" %>
<%@ page import="com.master.util.ed.Parametro_FixoED" %>
<%@ page import="com.master.ed.UsuarioED" %>
<html>

<head>
	<script>
		function verificaErro()	{
			<%
			String  SHORT_DATE = "dd/MM/yyyy";
			//instancia objeto de formatacao de data
			DateFormatter dateFormatter = new DateFormatter();
	        Parametro_FixoED Parametro_FixoED = new Parametro_FixoED();
			//instancia objeto de data
			Calendar calendar = Calendar.getInstance();
			HttpSession sessao = request.getSession(true);
			UsuarioED usuario = (UsuarioED)session.getAttribute("usuario");
			%>
		}
	</script>
	<meta http-equiv="Content-Type"	content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#eaeaea" >
	<table border="0" width="100%" bgcolor="#eaeaea"  >
	  	<tr>
		    <td width="70%" height="20" valign="top" align="right" nowrap>
				<div align="center">
		        	<font color="#000000" face="Arial, Helvetica, sans-serif" size="1">
		        		<b>Empresa: <%=usuario.getNm_Razao_Social()%></b>
				  		<b>     .-:-.      Usuário: <%=usuario.getNm_Usuario()%></b>
				  	</font> 
			  	</div>
			</td>
		    <td width="20%" height="20" align="center" nowrap valign="top">
		    	<div align="center">
					<font color="#000000" face="Arial, Helvetica, sans-serif" size="1">
						<b>GTT v0.0.1</b>
				  </font> 
				</div>
			</td>
		    <td width="10%" height="20" align="center" nowrap valign="top">
				<div align="center">
			         <font color="#000000" face="Arial, Helvetica, sans-serif" size="1">
				  		<b><%=dateFormatter.calendarToString(calendar, SHORT_DATE)%></b>
				  	</font> 
				</div>
			</td>
	  	</tr>
	</table>
</body>
</html>
