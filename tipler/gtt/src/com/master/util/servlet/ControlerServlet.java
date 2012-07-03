 package com.master.util.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.master.util.Excecoes;
import com.master.util.OLUtil;

public class ControlerServlet extends HttpServlet {

	/**
	 * serialVersionUID id para serialização
	 */
	private static final long serialVersionUID = -9193607220486941502L;
	
	/**
	 * Constructor of the object.
	 */
	public ControlerServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			processa(request,response);
		} catch (Excecoes e) {
			e.printStackTrace();
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 *
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			this.processa(request,response);
		} catch (Excecoes e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
		
	}

	private void processa (HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException, Excecoes {

		OLUtil OLUtil = new OLUtil();
		
		//Pega os parâmetros do form
		String tela = request.getParameter("tela");
		String acao = request.getParameter("acao");
		String ed = request.getParameter("ed");
		String rn = request.getParameter("rn");
		String ecs = request.getParameter("escapa_Controle_Session");
		
		if (rn == null){
			response.setContentType("text/html");
	    	PrintWriter out = response.getWriter();
	    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
	    	out.println("<cad>");
	    	out.println("</cad>");
	    	out.flush();
	    	out.close();
		} else {
			//Cola os parametros no bean
			Object Obj = OLUtil.requestToBean(request,ed);
			
			// Se estiver incluindo uma empresa stgp, testa a session do usuário porque não tem
			if ("S".equals(ecs)) {
				//Executa o método processaOL na classe rn
				OLUtil.processaOL(request, response, acao, rn, Obj);
			} else 
			if (Obj == null) { // Identificamos que não há registro do usuário na session. Necessita de logon ...
		    	PrintWriter out = response.getWriter();
		    	out.println("<?xml version='1.0' encoding='ISO-8859-1'?>");
		    	out.println("<ret><item oknok='LOGON'/></ret>");
		    	out.flush();
		    	out.close();
			} else {
				//Executa o método processaOL na classe rn
				OLUtil.processaOL(request, response, acao, rn, Obj);
			}	
		}
	}

}
