<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<jsp:useBean id="usuGtt" scope="request" class="com.master.ed.UsuarioED" />
	<jsp:setProperty name="usuGtt" property="*"/>
<jsp:useBean id="usuRN" scope="request" class="com.master.rn.UsuarioRN"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>SGTT - Sistemna de Garantia Total Tipler</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO">
		<script language="JavaScript" SRC="../Include/PRValidacao.js"></script>
	
		<c:if test="${! empty param.acao}">
			<c:choose>
				<c:when test="${param.acao == 'L'}">
			      	<% request.setAttribute("usuGtt",usuRN.getByRecord_Encrypt_Stgp(usuGtt)); %>
				</c:when>
			</c:choose>
			<c:if test="${! empty usuGtt.oid_Usuario}">
				<% session.setAttribute("usuario", request.getAttribute("usuGtt")); %>
				<script>parent.parent.window.location.assign("<%=basePath%>");</script>
			</c:if>	
		</c:if>
		<style>
			body {
				background-image: url("../Imagens/loginSGTT.JPG");
				background-repeat: no-repeat;
				background-position: center;
			}
		</style>
  	</head>
	
	<body  bgcolor="#EAEAEA" onload="document.forms[0].nr_Cnpj.focus();">
		<form name="gtt.jsp" method="post" action="gtt.jsp">
			<table width="100%" height="100%" border="0" align="center">
				<tr>
					<td align="center" valign="middle">
						<table  border="0" align="center" >
							<tr height="150">
								<td></td>
							</tr>
							<tr>
								<td width="90"></td>
								<td align="right"><font color="#000000">Cnpj&nbsp;:&nbsp;</font>
								<td align="left"><input type="text" name="nr_Cnpj" size="14" maxlength="14" value="${usuGtt.nr_Cnpj}">
							</tr>
							<tr>
								<td width="90"></td>
								<td align="right"><font color="#000000">Usuário&nbsp;:&nbsp;</font>
								<td align="left"><input type="text" name="nm_Usuario" size="14" maxlength="20" onkeyup="upperCaseCampo(this);" value="${usuGtt.nm_Usuario}">
							</tr>
							<tr>
								<td width="90"></td>
								<td align="right"><font color="#000000">Senha&nbsp;:&nbsp;</font>
								<td align="left"><input type="password" name="nm_Senha" size="9" maxlength="8" onkeyup="upperCaseCampo(this);"  onkeypress="upperCaseCampo(this);" >
							</tr>
							<tr>
								<td width="130"></td>
								<td align="right"></td>
								<td align="left"><input type="submit" name="btn" size="8" value="Enviar" onclick="submit();"> 
							</tr>
						</table>			
				 </td>
	  			</tr>
			</table>
			<P>
  			<input type="hidden" name="acao" value="L">
  			<input type="hidden" name="DM_LASZLO" value="${param.DM_LASZLO}">
  			</P>
  		</form>
	</body>
	
	<!--  Dá msg de erro -->
	<c:if test="${! empty param.acao}">
		<c:if test="${empty usuGtt.oid_Usuario}">
			<% session.setAttribute("usuario", null); %>
			<script>alert("Usuário não localizado ! Acesso negado!!!");</script>
		</c:if>		
	</c:if>
  
</html>
