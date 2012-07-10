package com.master.util.rl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.master.ed.RelatorioBaseED;
import com.master.ed.UsuarioED;
import com.master.relatorio.InterfaceRelatorio;
import com.master.relatorio.Relatorio;
import com.master.util.Excecoes;
import com.master.util.JavaUtil;
import com.master.util.Mensagens;
import com.master.util.ed.Parametro_FixoED;

/**
 * @author Andr� Valadas Descri��o: Classe Padr�o para Relat�rios Jasper
 */
public class JasperRL implements Serializable {

	private static final long serialVersionUID = -5475922386114560428L;
	private RelatorioBaseED edRelatorio;

	// *** Relat�rioED tras todos o dados necess�rios para gerar o Relat�rio
	public JasperRL(final RelatorioBaseED edRelatorio) {
		this.edRelatorio = edRelatorio;
	}

	/***************************************************************************
	 * Relat�rios JASPER
	 **************************************************************************/
	@SuppressWarnings("unchecked")
	public void geraRelatorio() throws Excecoes {

		// *** Valida��es Finais
		if (edRelatorio == null) {
			throw new Excecoes("Dados do Relat�rio � nulo!", this.getClass().getName(), "geraRelatorio()");
		}
		if (!JavaUtil.doValida(edRelatorio.getNomeRelatorio())) {
			throw new Mensagens("Nome do Relat�rio n�o informado!");
		}
		if (edRelatorio.getLista() == null) {
			throw new Excecoes("Lista de Dados do Relat�rio n�o criada!", this.getClass().getName(), "geraRelatorio()");
		}
		if (edRelatorio.getResponse() == null) {
			throw new Excecoes("Response n�o Passado!", this.getClass().getName(), "geraRelatorio()");
		}

		InterfaceRelatorio relatorio;
		try {
			relatorio = Relatorio.getInstance(Relatorio.GERADOR_JASPER);
		} catch (final Exception e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorio()");
		}
		// *** Nome do relat�rio
		relatorio.setNomeArquivo(edRelatorio.getNomeRelatorio());
		relatorio.setPathReport(Parametro_FixoED.getInstancia().getPATH_RELATORIOS());
		relatorio.setPathImagem(Parametro_FixoED.getInstancia().getPATH_IMAGENS());
		// *** Se existe Arquivo
		if (!new File(relatorio.getPathReport() + relatorio.getNomeArquivo()+ ".jasper").exists()) {
			throw new Mensagens("Arquivo " + relatorio.getPathReport()
					+ relatorio.getNomeArquivo()
					+ ".jasper n�o encontrado para gerar Relat�rio!");
		}

		final HashMap parametros = new HashMap();
		parametros.put("RELATORIO", relatorio.getNomeArquivo());
		parametros.put("PATH_IMAGENS", relatorio.getPathImagem());
		parametros.put("PATH_RELATORIOS", relatorio.getPathReport());
		parametros.put("LOGO_IMAGE", "logo"+ Parametro_FixoED.getInstancia().getNM_Empresa().toLowerCase()+ ".jpg");
		if (JavaUtil.doValida(edRelatorio.getDescFiltro())) {
			parametros.put("DESC_FILTER", edRelatorio.getDescFiltro());
		} else {
			parametros.put("DESC_FILTER", "");
		}

		// Rotina (LASZLO)
		// Se for informado o request ent�o pega o usu�rio a empresa
		if (edRelatorio.getRequest() != null) {
			final HttpServletRequest req = edRelatorio.getRequest();
			final HttpSession sessao = req.getSession(true);
			final UsuarioED usuario = (UsuarioED) sessao.getAttribute("usuario");
			parametros.put("EMPRESA", usuario.getNm_Razao_Social());
			parametros.put("USUARIO", usuario.getNm_Usuario());
			parametros.put("BASE_PATH", req.getScheme() + "://"
					+ req.getServerName() + ":" + req.getServerPort()
					+ req.getContextPath() + "/");
		}

		// *** Agrega Map passado com os Fixos aqui
		if (edRelatorio.getHashMap() != null&& !edRelatorio.getHashMap().isEmpty()) {
			edRelatorio.getHashMap().putAll(parametros);
		} else {
			edRelatorio.setHashMap(parametros);
		}

		try {
			relatorio.listaRelatorioPdfParaByte(edRelatorio.getHashMap(),(ArrayList) edRelatorio.getLista());
		} catch (final Exception e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorio()");
		}
		final byte[] arquivo = relatorio.getRelatorioBytes();
		// Escreve o pdf no JSP
		ServletOutputStream retornoPDF;
		try {
			retornoPDF = edRelatorio.getResponse().getOutputStream();
		} catch (final IOException e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorio()");
		}
		edRelatorio.getResponse().setHeader("application/pdf", "Content-Type");
		edRelatorio.getResponse().setHeader("Content-Disposition:","inline; filename=" + relatorio.getNomeArquivo() + ".pdf");
		edRelatorio.getResponse().setContentType("application/pdf");
		edRelatorio.getResponse().setContentLength(arquivo.length);
		try {
			retornoPDF.write(arquivo);
			retornoPDF.flush();
			retornoPDF.close();
		} catch (final IOException e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorio()");
		}
	}

	@SuppressWarnings("unchecked")
	public String geraRelatorioToFile() throws Excecoes {

		// *** Valida��es Finais
		if (edRelatorio == null) {
			throw new Excecoes("Dados do Relat�rio � nulo!", this.getClass().getName(), "geraRelatorio()");
		}
		if (!JavaUtil.doValida(edRelatorio.getNomeRelatorio())) {
			throw new Mensagens("Nome do Relat�rio n�o informado!");
		}
		if (edRelatorio.getLista() == null) {
			throw new Excecoes("Lista de Dados do Relat�rio n�o criada!", this.getClass().getName(), "geraRelatorio()");
		}
		if (edRelatorio.getResponse() == null) {
			throw new Excecoes("Response n�o Passado!", this.getClass().getName(), "geraRelatorio()");
		}

		InterfaceRelatorio relatorio;
		try {
			relatorio = Relatorio.getInstance(Relatorio.GERADOR_JASPER);
		} catch (final Exception e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(), "geraRelatorioToFile()");
		}
		// *** Nome do relat�rio
		relatorio.setNomeArquivo(edRelatorio.getNomeRelatorio());
		relatorio.setPathReport(Parametro_FixoED.getInstancia().getPATH_RELATORIOS());
		relatorio.setPathImagem(Parametro_FixoED.getInstancia().getPATH_IMAGENS());
		// *** Se existe Arquivo
		if (!new File(relatorio.getPathReport() + relatorio.getNomeArquivo()+ ".jasper").exists()) {
			throw new Mensagens("Arquivo " + relatorio.getPathReport() + "/"
					+ relatorio.getNomeArquivo()
					+ ".jasper n�o encontrado para gerar Relat�rio!");
		}

		final HashMap parametros = new HashMap();
		parametros.put("RELATORIO", relatorio.getNomeArquivo());
		parametros.put("PATH_IMAGENS", relatorio.getPathImagem());
		parametros.put("PATH_RELATORIOS", relatorio.getPathReport());
		parametros.put("LOGO_IMAGE", "logo"+ Parametro_FixoED.getInstancia().getNM_Empresa().toLowerCase()+ ".jpg");
		if (JavaUtil.doValida(edRelatorio.getDescFiltro())) {
			parametros.put("DESC_FILTER", edRelatorio.getDescFiltro());
		} else {
			parametros.put("DESC_FILTER", "");
		}

		// Rotina (LASZLO)
		// Se for informado o request ent�o pega o usu�rio a empresa
		if (edRelatorio.getRequest() != null) {
			final HttpSession sessao = edRelatorio.getRequest().getSession(true);
			final UsuarioED usuario = (UsuarioED) sessao.getAttribute("usuario");
			parametros.put("EMPRESA", usuario.getNm_Razao_Social());
			parametros.put("USUARIO", usuario.getNm_Usuario());
		}

		// *** Agrega Map passado com os Fixos aqui
		if (edRelatorio.getHashMap() != null && !edRelatorio.getHashMap().isEmpty()) {
			edRelatorio.getHashMap().putAll(parametros);
		} else {
			edRelatorio.setHashMap(parametros);
		}

		try {
			relatorio.listaRelatorioPdfParaByte(edRelatorio.getHashMap(),(ArrayList) edRelatorio.getLista());
		} catch (final Exception e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorioToFile()");
		}
		final byte[] arquivo = relatorio.getRelatorioBytes();

		try {
			final FileOutputStream fos = new FileOutputStream(relatorio.getPathReport()+ relatorio.getNomeArquivo() + ".pdf");
			fos.write(arquivo);
			fos.flush();
		} catch (final IOException e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorio()");
		}
		return String.valueOf(relatorio.getPathReport()+ relatorio.getNomeArquivo() + ".pdf");
	}

	@SuppressWarnings("unchecked")
	public void geraRelatorioToFile(final String filepath) throws Excecoes {

		// *** Valida��es Finais
		if (edRelatorio == null) {
			throw new Excecoes("Dados do Relat�rio � nulo!", this.getClass().getName(), "geraRelatorio()");
		}
		if (!JavaUtil.doValida(edRelatorio.getNomeRelatorio())) {
			throw new Mensagens("Nome do Relat�rio n�o informado!");
		}
		if (edRelatorio.getLista() == null) {
			throw new Excecoes("Lista de Dados do Relat�rio n�o criada!", this.getClass().getName(), "geraRelatorio()");
		}
		if (edRelatorio.getResponse() == null) {
			throw new Excecoes("Response n�o Passado!", this.getClass().getName(), "geraRelatorio()");
		}

		InterfaceRelatorio relatorio;
		try {
			relatorio = Relatorio.getInstance(Relatorio.GERADOR_JASPER);
		} catch (final Exception e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorioToFile()");
		}
		// *** Nome do relat�rio
		relatorio.setNomeArquivo(edRelatorio.getNomeRelatorio());
		relatorio.setPathReport(Parametro_FixoED.getInstancia().getPATH_RELATORIOS());
		relatorio.setPathImagem(Parametro_FixoED.getInstancia().getPATH_IMAGENS());
		// *** Se existe Arquivo
		if (!new File(relatorio.getPathReport() + relatorio.getNomeArquivo()+ ".jasper").exists()) {
			throw new Mensagens("Arquivo " + relatorio.getPathReport() + "/"
					+ relatorio.getNomeArquivo()
					+ ".jasper n�o encontrado para gerar Relat�rio!");
		}

		final HashMap parametros = new HashMap();
		parametros.put("RELATORIO", relatorio.getNomeArquivo());
		parametros.put("PATH_IMAGENS", relatorio.getPathImagem());
		parametros.put("PATH_RELATORIOS", relatorio.getPathReport());
		parametros.put("LOGO_IMAGE", "logo"+ Parametro_FixoED.getInstancia().getNM_Empresa().toLowerCase()+ ".jpg");
		if (JavaUtil.doValida(edRelatorio.getDescFiltro())) {
			parametros.put("DESC_FILTER", edRelatorio.getDescFiltro());
		} else {
			parametros.put("DESC_FILTER", "");
		}

		// Rotina (LASZLO)
		// Se for informado o request ent�o pega o usu�rio a empresa
		if (edRelatorio.getRequest() != null) {
			final HttpSession sessao = edRelatorio.getRequest().getSession(true);
			final UsuarioED usuario = (UsuarioED) sessao.getAttribute("usuario");
			parametros.put("EMPRESA", usuario.getNm_Razao_Social());
			parametros.put("USUARIO", usuario.getNm_Usuario());
		}

		// *** Agrega Map passado com os Fixos aqui
		if (edRelatorio.getHashMap() != null && !edRelatorio.getHashMap().isEmpty()) {
			edRelatorio.getHashMap().putAll(parametros);
		} else {
			edRelatorio.setHashMap(parametros);
		}

		try {
			relatorio.listaRelatorioPdfParaByte(edRelatorio.getHashMap(),(ArrayList) edRelatorio.getLista());
		} catch (final Exception e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorioToFile()");
		}
		// System.out.println("------------------------");
		final byte[] arquivo = relatorio.getRelatorioBytes();

		try {
			final FileOutputStream fos = new FileOutputStream(filepath);
			fos.write(arquivo);
			fos.flush();
		} catch (final IOException e) {
			throw new Excecoes(e.getMessage(), e, getClass().getName(),"geraRelatorio()");
		}
	}

	public void setEdRelatorio(final RelatorioBaseED edRelatorio) {
		this.edRelatorio = edRelatorio;
	}

}