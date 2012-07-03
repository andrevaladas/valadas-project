<!--=======================================================================-->
<!-- Laszlo Explorer index.jsp                                             -->
<!--                                                                       -->
<!-- * X_LZ_COPYRIGHT_BEGIN ***************************************************
* Copyright 2001-2006 Laszlo Systems, Inc.  All Rights Reserved.              *
* Use is subject to license terms.                                            *
* X_LZ_COPYRIGHT_END ****************************************************** -->
<!--=======================================================================-->
<%@ page import="java.util.*" %>

<%    
String userAgent = request.getHeader("User-Agent");
String qParams ="";
String bookmark = request.getParameter("bookmark");
if (bookmark == null ){ qParams = "tela=gestaoMenu";}

Enumeration e = request.getParameterNames();
  while (e.hasMoreElements()) {
    String name = (String)e.nextElement();
    String value = request.getParameter(name);

    qParams += "&" + name + "=" + value;
  }

if (userAgent.indexOf("MSIE") > 0 ){ // looks like MSIE
   qParams += "&browser=IE";
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<title>GTT - Gestão de menus</title>
<link href="../lps/includes/explore.css" rel="stylesheet" type="text/css">

</HEAD>
<frameset cols="200,*" border="0">
<frame name="nav" scrolling="no" src="explore-nav.lzx?<%= qParams %>&lzt=html&fb=1"/>
<frame name="content" scrolling="no" src="blank.html"/>
</frameset>
</HTML>

