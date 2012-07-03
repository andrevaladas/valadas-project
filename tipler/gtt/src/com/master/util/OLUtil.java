/*
 * Created on 05/2007
 *
 */
package com.master.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.ed.UsuarioED;

/**
 * @author Regis Steigleder
 * Classes para utilizar na integração com requests do OL
 */

public class OLUtil {
	
	public OLUtil () {
		
	}
	/**
	 * requestToBean
	 * Este método popula um bean do pacote com.master.ed a partir dos parametros da request.
	 * @param req
	 * @param nomeClasse
	 * @return Object = Bean populado pelos values dos parametros da request.
	 * @throws ServletException
	 * @throws IOException
	 */ 
    public Object requestToBean(HttpServletRequest req, String nomeClasse) throws ServletException, IOException {
    	
    	String name, value, ecs="";
    	int achei = -1;
    	Object objComAClasse = null;
        try {
        	//Pega a classe pelo nome
        	Class c = Class.forName("com.master.ed." + nomeClasse);
        	//Instancia um objeto com a classe
        	objComAClasse = c.newInstance();
        	// Pega todos os campos declarados da classe
        	Field[] f = c.getDeclaredFields();
        	// Pega todos os parametros passados na request
	        Enumeration eNum = req.getParameterNames();
	        //Itera sobre os parametros da request
	        for (; eNum.hasMoreElements(); ) {
	            // Pega o nome e o valor do parametro
	            name = (String)eNum.nextElement();
	            value = req.getParameter(name);
	            //System.out.println(name+" - "+value);
	            // Se for solicitado para escapar do controle da session ( usado para cadastrar a empresa quando usuario aindaão logado ) pns099.lzx
	            if ("escapa_Controle_Session".equals(name) || "ecs".equals(name)) ecs=value;

	            if ("array".startsWith(name)) {	value = value.replaceAll("[@]","#"); } // Implementado por problema no envio da request do laszlo para relatorio.jsp	
	        	//Faz uma pesquisa procurando o campo, ao encontrar sai com a posição do índice do campo em 'achei'
	            achei = -1;
	            for (int i = 0 ; i < f.length ; i++) {
	            	if (name.equals(f[i].getName())) { achei = i; break; }	
	        	}
	            //Se achou o campo com o mesmo nome do parametro vai popular o campo pelo set
	            if ( achei > -1 && value.length() > 0)  {
	            	//Define o tipo do atributo e determina o valor do parametro
	            	String tipo = f[achei].getType().toString();
	            	Class typeType[] = new Class[1];
	            	Object arglist[] = new Object[1];
	            	if ("int".equals( tipo ) ) {
	            		typeType[0]=Integer.TYPE;
	            		arglist[0] = new Integer(Valores.converteStringToInt(value)); // verificar conversão americana
	            	} else 
            		if ("long".equals( tipo ) ) {
	            		typeType[0]=Long.TYPE;
	            		arglist[0] = new Long(Valores.converteStringToLong(value)); // verificar conversão americana
	            	} else 
            		if ("double".equals( tipo ) ) {
            			typeType[0]=Double.TYPE;
            			arglist[0] = new Double(Valores.converteStringToDouble(value)); // verificar conversão americana
	            	} else 
            		if ("boolean".equals( tipo ) ) {
            			typeType[0]=Boolean.TYPE;
            			arglist[0] = new Boolean(value);
	            	} else 
	            	if ("class java.lang.Integer".equals( tipo )  ) {
	            		typeType[0]=Integer.class;
	            		arglist[0] = new Integer(Valores.converteStringToInt(value)); // verificar conversão americana
	            	} else 
	            	if ("class java.lang.Long".equals( tipo )  ) {
	            		typeType[0]=Long.class;
	            		arglist[0] = new Long(Valores.converteStringToLong(value)); // verificar conversão americana
	            	} else 
	            	if ("class java.lang.Double".equals( tipo )  ) {
	            		typeType[0]=Double.class;
	            		arglist[0] = new Double(Valores.converteStringToDouble(value)); // verificar conversão americana
	            	} else 
            		if ("class java.lang.String".equals( tipo ) ) {
            			typeType[0]=String.class;
            			arglist[0] = value;
	            	}	
	            	//Pega o metodo set correspondente ao attributo 
	            	Method meth = c.getMethod("set" + name.substring(0,1).toUpperCase() + name.substring(1),typeType);
	            	//Invoca o método set correspondente populando o attributo
	            	Object retobj = meth.invoke(objComAClasse, arglist);
	            }
	        }
	        
	        // Faz o controle da session buscando a empresa do usuario registrado no logon
	        if (!"S".equals(ecs)) {
	            // Seta o oid_Empresa se houver no ed pegando da session	
	            // Pega o nome e o valor do parametro
	            name = "oid_Empresa";
	            //Pega o oid da empresa do usuario na session
	            UsuarioED usuario = (UsuarioED)req.getSession(true).getAttribute("usuario");
	            // Se usuario não está na session devolve objeto nulo porque o servidor caiu...
	            if (usuario == null) {
	            	//System.err.println(this.getClass().getName()+" >>> Usuário não informado na Sessão!");
	            	return null;
	            }
	            // Coloca a empres no oid_empresa do ED
	            value =  Long.toString(usuario.getOid_Empresa());
            
	        	//Faz uma pesquisa procurando o campo, ao encontrar sai com a posição do índice do campo em 'achei'
	            achei = -1;
	            for (int i = 0 ; i < f.length ; i++) {
	            	if (name.equals(f[i].getName())) { achei = i; break; }	
	        	}
	            //Se achou o campo com o mesmo nome do parametro vai popular o campo pelo set
	            if ( achei > -1 ) {
	            	//Define o tipo do atributo e determina o valor do parametro
	            	String tipo = f[achei].getType().toString();
	            	Class typeType[] = new Class[1];
	            	Object arglist[] = new Object[1];
	            	if ("int".equals( tipo ) ) {
	            		typeType[0]=Integer.TYPE;
	            		arglist[0] = new Integer(Valores.converteStringToInt(value)); // verificar conversão americana
	            	} else 
	        		if ("long".equals( tipo ) ) {
	            		typeType[0]=Long.TYPE;
	            		arglist[0] = new Long(Valores.converteStringToLong(value)); // verificar conversão americana
	            	} else 
	            	if ("class java.lang.Integer".equals( tipo )  ) {
	            		typeType[0]=Integer.class;
	            		arglist[0] = new Integer(Valores.converteStringToInt(value)); // verificar conversão americana
	            	} else 
	            	if ("class java.lang.Long".equals( tipo )  ) {
	            		typeType[0]=Long.class;
	            		arglist[0] = new Long(Valores.converteStringToLong(value)); // verificar conversão americana
	            	} 
	            	//Pega o metodo set correspondente ao attributo 
	            	Method meth = c.getMethod("set" + name.substring(0,1).toUpperCase() + name.substring(1),typeType);
	            	//Invoca o método set correspondente populando o attributo
	            	Object retobj = meth.invoke(objComAClasse, arglist);
	            }
	        }
        } catch (Throwable e) {
        	e.printStackTrace();
        }
		return objComAClasse;
    }
	
	/**
	 * processaOL - Transações on-line direto à datasets laszlo
	 * Este método invoca o metodo homônimo na classe RN passada como parâmetro
	 * @param response
	 * @param acao
	 * @param rn
	 * @param Obj
	 */
	public void processaOL(HttpServletRequest request, HttpServletResponse response, String acao, String rn, Object Obj) throws Excecoes {
		Object objComAClasse = null;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); //Resolve o problema de acentuação 'back' no laszlo.
	    try {
	    	//Pega a classe pelo nome
	    	Class c = Class.forName("com.master.rn." + rn);
	    	//Instancia um objeto com a classe
	    	objComAClasse = c.newInstance();
	    	//Monta os parametros para o rn
	    	Class typeType[] = new Class[4];
        	Object arglist[] = new Object[4];
	    	typeType[0]=String.class;
			arglist[0] = acao;
			typeType[1]= Object.class;
			arglist[1] = Obj;
			typeType[2]= HttpServletRequest.class; 
			arglist[2] = request;
			typeType[3]= HttpServletResponse.class;
			arglist[3] 	= response;
        	//Pega o metodo processaOL no rn 
        	Method meth = c.getMethod("processaOL",typeType);
        	//Chama o metodo
        	meth.invoke(objComAClasse, arglist);
	    } catch (Throwable e) {
	    	e.printStackTrace();
        }
	}

	/**
	 * processaRL - Transações de relatório direto a PDF
	 * Este método invoca o metodo homônimo na classe RN passada como parâmetro
	 * @param response
	 * @param acao
	 * @param rn
	 * @param Obj
	 */
	public void processaRL(HttpServletRequest request, HttpServletResponse response, String rel, String rn, Object Obj) throws Excecoes {
		Object objComAClasse = null;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); //Resolve o problema de acentuação 'back' no laszlo.
	    try {
	    	//Pega a classe pelo nome
	    	Class c = Class.forName("com.master.rn." + rn);
	    	//Instancia um objeto com a classe
	    	objComAClasse = c.newInstance();
	    	//Monta os parametros para o rn
	    	Class typeType[] = new Class[4];
        	Object arglist[] = new Object[4];
	    	typeType[0]=String.class;
			arglist[0] = rel;
			typeType[1]= Object.class;
			arglist[1] = Obj;
			typeType[2]= HttpServletRequest.class; 
			arglist[2] = request;
			typeType[3]= HttpServletResponse.class;
			arglist[3] 	= response;
        	//Pega o metodo processaOL no rn 
        	Method meth = c.getMethod("processaRL",typeType);
        	//Chama o metodo
        	meth.invoke(objComAClasse, arglist);
	    } catch (Throwable e) {
	    	e.printStackTrace();
        }
	}

	/**
	 * processaGR - Transações gráfico - direto ao JSP wraper de gráficos.
	 * Este método invoca o metodo homônimo na classe RN passada como parâmetro
	 * @param response
	 * @param acao
	 * @param rn
	 * @param Obj
	 */
	public String processaGR(HttpServletRequest request, HttpServletResponse response, String acao, String rn, Object Obj) throws Excecoes {
		Object objComAClasse = null;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); //Resolve o problema de acentuação 'back' no laszlo.
		String string = null;
	    try {
	    	//Pega a classe pelo nome
	    	Class c = Class.forName("com.master.rn." + rn);
	    	//Instancia um objeto com a classe
	    	objComAClasse = c.newInstance();
	    	//Monta os parametros para o rn
	    	Class typeType[] = new Class[4];
        	Object arglist[] = new Object[4];
	    	typeType[0]=String.class;
			arglist[0] = acao;
			typeType[1]= Object.class;
			arglist[1] = Obj;
			typeType[2]= HttpServletRequest.class; 
			arglist[2] = request;
			typeType[3]= HttpServletResponse.class;
			arglist[3] 	= response;
        	//Pega o metodo processaOL no rn 
        	Method meth = c.getMethod("processaGR",typeType);
        	//Chama o metodo
        	string = (String)meth.invoke(objComAClasse, arglist);
	    } catch (Throwable e) {
	    	e.printStackTrace();
        }
	    return string;
	}

	
	/**
	 * processaCEL - Transações on-line direto à celulares
	 * Este método invoca o metodo homônimo na classe RN passada como parâmetro
	 * @param response
	 * @param acao
	 * @param rn
	 * @param Obj
	 */
	public void processaCEL(HttpServletRequest request, HttpServletResponse response, String acao, String rn, Object Obj) throws Excecoes {
		Object objComAClasse = null;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); //Resolve o problema de acentuação 'back' no laszlo.
	    try {
	    	//Pega a classe pelo nome
	    	Class c = Class.forName("com.master.rn." + rn);
	    	//Instancia um objeto com a classe
	    	objComAClasse = c.newInstance();
	    	//Monta os parametros para o rn
	    	Class typeType[] = new Class[4];
        	Object arglist[] = new Object[4];
	    	typeType[0]=String.class;
			arglist[0] = acao;
			typeType[1]= Object.class;
			arglist[1] = Obj;
			typeType[2]= HttpServletRequest.class; 
			arglist[2] = request;
			typeType[3]= HttpServletResponse.class;
			arglist[3] 	= response;
        	//Pega o metodo processaOL no rn 
        	Method meth = c.getMethod("processaCEL",typeType);
        	//Chama o metodo
        	meth.invoke(objComAClasse, arglist);
	    } catch (Throwable e) {
	    	e.printStackTrace();
        }
	}

	/**
	 * stringToBean
	 * Este método popula um bean do pacote com.master.ed apartir de uma string com 'cpo=valor#cpo=valor....'
	 * @param str
	 * @param nomeClasse
	 * @return Object = Bean populado pelos values dos parametros da request.
	 * @throws ServletException
	 * @throws IOException
	 */ 
    public Object stringToBean(String str, String nomeClasse) throws ServletException, IOException {
    	
    	String name="m" , value="1";
    	int achei = -1;
    	Object objComAClasse = null;
        try {
        	//Pega a classe pelo nome
        	Class c = Class.forName("com.master.ed." + nomeClasse);
        	//Instancia um objeto com a classe
        	objComAClasse = c.newInstance();
        	// Pega todos os campos declarados da classe
        	Field[] f = c.getDeclaredFields();
        	// Pega os campos declarados nos String ( vem sempre a dupla campo=valor#campo=valor#campo=valor# )
			String[] strB = str.split("#");
			for (int x=0;x<strB.length;x++) {
				String objString = strB[x];
				String[] strC = objString.split("=");
	            //Valida quando nao tiver valor informado
				if(strC.length < 2 ) {
	            	continue;
	            }
	            name = strC[0];
            	value = strC[1];
            	
				//Verifica se é um SUB-ARRAY
	            if(objString.startsWith("array")) {
	            	if(objString.indexOf("**") > -1) {
	            		value = objString.replaceAll("[*]","#"); // Substitui caracteres, convertendo para padrao da arquitetura
	            	} else if(objString.indexOf("§§") > -1) {
	            		value = objString.replaceAll("[§]","#"); // SUB ARRAY 2
	            	}
	            	value = value.substring(name.length()+1);
	            }

	        	//Faz uma pesquisa procurando o campo, ao encontrar sai com a posição do índice do campo em 'achei'
	            achei = -1;
	            for (int i = 0 ; i < f.length ; i++) {
	            	if (name.equals(f[i].getName())) { achei = i; break; }	
	        	}
	            //Se achou o campo com o mesmo nome do parametro vai popular o campo pelo set
	            if ( achei > -1 ) {
	            	//Define o tipo do atributo e determina o valor do parametro
	            	String tipo = f[achei].getType().toString();
	            	Class typeType[] = new Class[1];
	            	Object arglist[] = new Object[1];
	            	if ("int".equals( tipo ) ) {
	            		typeType[0]=Integer.TYPE;
	            		arglist[0] = new Integer(Valores.converteStringToInt(value)); // verificar conversão americana
	            	} else 
            		if ("long".equals( tipo ) ) {
	            		typeType[0]=Long.TYPE;
	            		arglist[0] = new Long(Valores.converteStringToLong(value)); // verificar conversão americana
	            	} else 
            		if ("double".equals( tipo ) ) {
            			typeType[0]=Double.TYPE;
            			arglist[0] = new Double(Valores.converteStringToDouble(value)); // verificar conversão americana
	            	} else 
            		if ("boolean".equals( tipo ) ) {
            			typeType[0]=Boolean.TYPE;
            			arglist[0] = new Boolean(value);
	            	} else 
            		if ("class java.lang.String".equals( tipo ) ) {
            			typeType[0]=String.class;
            			arglist[0] = value;
	            	}	
	            	//Pega o metodo set correspondente ao attributo 
	            	Method meth = c.getMethod("set" + name.substring(0,1).toUpperCase() + name.substring(1),typeType);
	            	//Invoca o método set correspondente populando o attributo
	            	Object retobj = meth.invoke(objComAClasse, arglist);
	            }
			}
        } catch (Throwable e) {
        	e.printStackTrace();
        }
		return objComAClasse;
    }

	/**
	 * pegaArraydaRequest
	 * Este método retorna uma lista de beans (EDs) populada por um array passado na request com 'cpo=valor#cpo=valor....'
	 * @param s = array de registros obtido da request 'cpo=valor#cpo=valor....'
	 * "array=cpo1=valor#cpo2=valor##cpo1=valor#cpo2=valor##cpo1=valor#cpo2=valor##"
	 * # separa atributo=valor, ## separa registros
	 * @throws ServletException
	 * @throws IOException
	 */ 
	public ArrayList pegaArraydaRequest(String s) {
		ArrayList lst = new ArrayList();
		if(JavaUtil.doValida(s)) {
			String[] strA = s.split("##");
			for (int i=1;i<strA.length;i++) {
				try {
					lst.add(this.stringToBean(strA[i],strA[0]));
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lst;
	}
}