<!-- Este jsp redireciona o chamdo de uma tela laszlo para a geracao do relatorio solicitado 
	chama o processo requestoBean para preencher o ed
	e executa o metodo processaRL que vai redirecionar para o RN correto chamando o relatorio 
	solicitado no parametro rel
-->
<jsp:directive.page import="com.master.util.OLUtil"/>
<% 
//Pega os parâmetros do form
String acao = request.getParameter("acao");
String ed = request.getParameter("ed");
String rn = request.getParameter("rn");

int he = Integer.parseInt(request.getParameter("he"));
int wi = Integer.parseInt(request.getParameter("wi"));
String tp = request.getParameter("tp");
//
OLUtil OLUtil = new OLUtil();
//Cola os parametros no bean
Object Obj = OLUtil.requestToBean(request,ed);
//Executa o método processaOL na classe rn
String src = OLUtil.processaGR(request, response, acao, rn, Obj);
String ti = src.substring(0, src.indexOf("<graph")); // Pega o título

%>

<HTML>
<HEAD>
	<TITLE>Gráfico</TITLE>	
	<style type="text/css">
	<!--
	body {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
	}
	-->
	</style>
	<SCRIPT LANGUAGE="Javascript" SRC="FusionCharts.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		var strXML = "<%=src%>";
		/*
		 * updateChart method is called, when user clicks the button
		 * Here, we generate the XML data again and build the chart.		  
		 *	@param	domId	domId of the Chart
		*/
		function updateChart(domId){
			//using updateChartXML method defined in FusionCharts.js
			updateChartXML(domId,strXML);
			//Disable the button
			this.document.frmUpdate.btnUpdate.disabled = true;
		}
	</SCRIPT>
</HEAD>
<BODY  bgcolor="#EAEAEA">
	<CENTER>
		<h2><%=ti%></h2>
		
		<div id="chart1div">
			FusionCharts
		</div>
		<script language="JavaScript">					
			var chart1 = new FusionCharts("<%=tp%>", "chart1Id", "<%=wi%>", "<%=he%>", "0", "1");	   			
			chart1.setDataXML(strXML );
			chart1.render("chart1div");
		</script>
		<BR />
	</CENTER>
</BODY>
</HTML>


