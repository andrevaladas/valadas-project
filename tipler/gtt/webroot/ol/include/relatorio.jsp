<jsp:directive.page import="com.master.util.OLUtil"/><%
// Este jsp redireciona o chamdo de uma tela laszlo para a geracao do relatorio solicitado 
// chama o processo requestoBean para preencher o ed
// e executa o metodo processaRL que vai redirecionar para o RN correto chamando o relatorio 
//  solicitado no parametro rel
//Pega os parâmetros do form
String acao = request.getParameter("acao");
String ed = request.getParameter("ed");
String rn = request.getParameter("rn");
String rel = request.getParameter("rel");
OLUtil OLUtil = new OLUtil();
//Cola os parametros no bean
Object Obj = OLUtil.requestToBean(request,ed);
//Executa o método processaOL na classe rn
OLUtil.processaRL(request, response, rel, rn, Obj);
%>