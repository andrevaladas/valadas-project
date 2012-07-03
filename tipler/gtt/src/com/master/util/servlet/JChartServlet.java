package com.master.util.servlet;
 
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jCharts.encoders.ServletEncoderHelper;

import com.master.rn.Stas_AnaliseRN;
import com.master.util.Excecoes;
import com.master.util.Utilitaria;

public class JChartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 8831191624910953931L;
	
	public JChartServlet () {
		super();
	}

	public void init() throws ServletException {
		// Put your code here
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processa(request,response);
		} catch (Excecoes e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processa(request,response);
		} catch (Excecoes e) {
			e.printStackTrace();
		}
	}

	private void processa (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Excecoes {
		if (Utilitaria.doValida(request.getParameter("oid_Analise"))) {
			processaGraficoAnalise(request, response);
		} 
	}
	
	public void processaGraficoAnalise( HttpServletRequest req, HttpServletResponse httpServletResponse ) throws ServletException, IOException {
		try {
			ServletEncoderHelper.encodeJPEG13( new Stas_AnaliseRN().criaGraficoAnalise(), 1.0f, httpServletResponse );
		} catch( Throwable throwable ) {
			throwable.printStackTrace();
		}
	}
	
}
