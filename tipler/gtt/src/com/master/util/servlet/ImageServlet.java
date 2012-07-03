 package com.master.util.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.master.ed.BandaED;
import com.master.ed.Stas_Motivo_SucataED;
import com.master.rn.BandaRN;
import com.master.rn.Stas_Motivo_SucataRN;
import com.master.rn.Stif_Veiculo_InspecaoRN;
import com.master.util.Excecoes;
import com.master.util.Utilitaria;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 5170618369183607602L;

	public ImageServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	public void init() throws ServletException {
		// Put your code here
	}

	private void processa (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Excecoes {
		
		if (Utilitaria.doValida(request.getParameter("oid_Banda"))) {
			processaBanda(request, response);
		} else 
		if (Utilitaria.doValida(request.getParameter("oid_Motivo_Sucata"))) {
			processaMotivoSucata(request, response);
		} else 
		if (Utilitaria.doValida(request.getParameter("oid_Veiculo_Inspecao"))) {
			processaVeiculoInspecao(request, response);
		}
	}

	private void processaBanda(HttpServletRequest request, HttpServletResponse response) throws IOException, Excecoes {
		BandaED bndED = new BandaED();
		long oid_Banda = 0;
		oid_Banda = Long.parseLong(request.getParameter("oid_Banda"));
		bndED.setOid_Banda(oid_Banda);
		response.setContentType("image/jpeg"); 
		ServletOutputStream out = response.getOutputStream();
		byte [] array = new BandaRN().getImagem(bndED);
		out.write(array);
		out.flush();
    	out.close();
	}

	private void processaMotivoSucata(HttpServletRequest request, HttpServletResponse response) throws IOException, Excecoes {
		Stas_Motivo_SucataED smsED = new Stas_Motivo_SucataED();
		long oid_Motivo_Sucata = 0;
		oid_Motivo_Sucata = Long.parseLong(request.getParameter("oid_Motivo_Sucata"));
		smsED.setOid_Motivo_Sucata(oid_Motivo_Sucata);
		response.setContentType("image/jpeg"); 
		ServletOutputStream out = response.getOutputStream();
		byte [] array = new Stas_Motivo_SucataRN().getImagem(smsED);
		out.write(array);
		out.flush();
    	out.close();
	}
	
	private void processaVeiculoInspecao(HttpServletRequest request, HttpServletResponse response) throws IOException, Excecoes {
		response.setContentType("image/jpeg"); 
		ServletOutputStream out = response.getOutputStream();
		byte [] array = new Stif_Veiculo_InspecaoRN().getImagem(Long.parseLong(request.getParameter("oid_Veiculo_Inspecao")));
		out.write(array);
		out.flush();
    	out.close();
	}
}