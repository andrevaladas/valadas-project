<jsp:directive.page import="com.master.ed.UsuarioED"/><!-- * X_LZ_COPYRIGHT_BEGIN ***************************************************
* Copyright 2001-2004 Laszlo Systems, Inc.  All Rights Reserved.              *
* Use is subject to license terms.                                            *
* X_LZ_COPYRIGHT_END ****************************************************** -->
<HTML>       
<%@ page import="java.util.*" %>
<%
      String src=request.getParameter("src");
      String qParams="";
      Enumeration<String> e = request.getParameterNames();
      while (e.hasMoreElements()) {
        String name = (String)e.nextElement();
        String value = request.getParameter(name);
        qParams += "&" + name + "=" + value;
      }
      //Manda sempre o usuario junto na request de chamada do lzx ...
	  UsuarioED usuario = (UsuarioED)session.getAttribute("usuario");
      qParams += "&oidUsuario=" + usuario.getOid_Usuario();
      // Faz um substring para pegar somente os parametros a serem passados
      if (qParams.length()>0) qParams=qParams.substring(qParams.indexOf("src")+src.length()+4,qParams.length());
      // Manda o parametro da lingua do usuário se src ven com parametro coloca o & se não comoca o ?
      String chamaTela = src + (src.indexOf("?")>0?"&":"?") + "lang=" + usuario.getDm_Lingua() + qParams;
%>

<head>
<script xmlns="" src="../lps/includes/embed.js" language="JavaScript" type="text/javascript"></script>
<link href="../lps/includes/explore.css" rel="stylesheet" type="text/css">

</head>
<BODY onload="location.href='<%= chamaTela %>';" bgcolor="#eaeaea">
<center>
<script>
//lzEmbed({url: 'loading.swf', bgcolor: '#eaeaea', width: '200', height: '500'});
</script>
</center>
<table width="500"><tr><td>&nbsp;</td></tr></table>
</BODY>
</HTML>