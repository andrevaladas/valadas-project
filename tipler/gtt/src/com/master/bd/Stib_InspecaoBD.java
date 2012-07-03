package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.master.ed.Stib_InspecaoED;
import com.master.ed.Stib_Inspecao_ItemED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.JavaUtil;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial STIB - Inspeção
 * @serialData 04/2012
 */
public class Stib_InspecaoBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Stib_InspecaoBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Stib_InspecaoED inclui(Stib_InspecaoED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Stib_Inspecoes (" +
			"dt_inicio" +
			",oid_Concessionaria " +
			",oid_Usuario " +
			",oid_Cliente " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			" '" + ed.getDt_Inicio() + "'"+	
			", " + ed.getOid_Empresa() + 
			", " + ed.getOid_Usuario() +
			", " + ed.getOid_Cliente() +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Inspecao(getSeq("Stib_Inspecoes_Oid_Inspecao_seq"));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stib_InspecaoED ed)");
		}
	}

	public void altera(Stib_InspecaoED ed) throws Excecoes {
		try {
			sql = "UPDATE Stib_Inspecoes SET " +
			" dt_Fim = '" + ed.getDt_Fim() + "' " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Inspecao = " + ed.getOid_Inspecao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stib_InspecaoED ed)");
		}
	}
	
	public void reabrirInspecao(Stib_InspecaoED ed) throws Excecoes {
		try {
			sql = "UPDATE Stib_Inspecoes SET " +
			" dt_Fim = null " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Inspecao = " + ed.getOid_Inspecao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stib_InspecaoED ed)");
		}
	}
	
	public void alteraRelatorio(Stib_InspecaoED ed) throws Excecoes {
		try {
			sql = "UPDATE Stib_Inspecoes SET " +
			" tx_Inicial = '" + ed.getTx_Inicial() + "' " +
			",tx_Final = '" + ed.getTx_Final() + "' " +
			",tx_Assinatura1 = '" + ed.getTx_Assinatura1() + "' " +
			",tx_Assinatura2 = '" + ed.getTx_Assinatura2() + "' " +
			",nm_Signatario = '" + ed.getNm_Signatario() + "' " +
			",nm_Tecnico = '" + ed.getNm_Tecnico() + "' " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = 'A' " + 
			"WHERE " +
			"oid_Inspecao = " + ed.getOid_Inspecao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stib_InspecaoED ed)");
		}
	}
	
	public void delete(Stib_InspecaoED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stib_Inspecoes_Itens " +
			"WHERE " +
			"oid_Inspecao = " + ed.getOid_Inspecao();
			executasql.executarUpdate(sql);
			//
			sql = "DELETE FROM Stib_Inspecoes " +
			"WHERE " +
			"oid_Inspecao = " + ed.getOid_Inspecao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Stib_InspecaoED ed)");
		}
	}

	public ArrayList<Stib_InspecaoED> lista(Stib_InspecaoED ed) throws Excecoes {
		ArrayList<Stib_InspecaoED> list = new ArrayList<Stib_InspecaoED>();
		try {
			sql = "SELECT " +
			" i.* " +
			",replace(tx_Inicial,    chr(13), '&#13;' ) as tx_Inicial_OL " +
			",replace(tx_Final,      chr(13), '&#13;' ) as tx_Final_OL " +
			",replace(tx_Assinatura1,chr(13), '&#13;' ) as tx_Assinatura1_OL " +
			",replace(tx_Assinatura2,chr(13), '&#13;' ) as tx_Assinatura2_OL " +
			",e.nm_Razao_Social " +
			",e.nm_Cidade " +
			",e.cd_Estado " +
			",us.nm_Usuario " +
			",i.usuario_Stamp as usu_Stmp " +
	  		",i.dt_Stamp as dt_Stmp " +
	  		",i.dm_Stamp as dm_Stmp " +
			"FROM Stib_Inspecoes as i " +
			"left join Empresas as e on (e.oid_Empresa=i.oid_Cliente) " +
			"left join Usuarios as us on (us.oid_Usuario=i.oid_Usuario) " +
			"WHERE " +
			"1 = 1 " ;
			if (ed.getOid_Inspecao()>0) 
				sql += " and i.oid_Inspecao = " + ed.getOid_Inspecao() + " ";
			else {
				if (ed.getOid_Empresa()>0)
					sql += " and i.oid_Concessionaria = " + ed.getOid_Empresa() + " "   ;
				if (ed.getOid_Cliente()>0)
					sql += " and i.oid_Cliente = " + ed.getOid_Cliente()+ " "  ;
				if (ed.getOid_Usuario()>0)
					sql += " and i.oid_Usuario = " + ed.getOid_Usuario()+ " "  ;
				//if (Utilitaria.doValida(ed.getDt_Inicio())) {
				//	sql += " and i.dt_Inicio = '" +ed.getDt_Inicio()+"' "  ;
				//}
				if (Utilitaria.doValida(ed.getDm_Encerradas())) {
					if ("S".equals(ed.getDm_Encerradas())) {
						sql += " and i.dt_Fim is not null "  ;
					}
				} else {
					sql += " and i.dt_Fim is null "  ;
				}
			}
			sql += "ORDER BY " ;
			if (Utilitaria.doValida(ed.getDm_Ordenar())) {
				if ("DTINICIODESC".equals(ed.getDm_Ordenar())) {
					sql += "i.dt_Inicio desc ";
				} else {
					sql += "i.dt_Inicio ";
				}
			} else {
				sql += "i.dt_Inicio ";
			}	
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stib_InspecaoED ed)");
		}
	}

	public Stib_InspecaoED getByRecord(Stib_InspecaoED ed) throws Excecoes {
		try {
			ArrayList<Stib_InspecaoED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Stib_InspecaoED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stib_InspecaoED ed)");
		}
		return new Stib_InspecaoED();
	}
	
	// Retorna uma lista com o resumo de cada uma das inspeções
	public ArrayList<Stib_InspecaoED> listaResumo(Stib_InspecaoED ed) throws Excecoes {
		ArrayList<Stib_InspecaoED> list = new ArrayList<Stib_InspecaoED>();
		try {
			sql="SELECT * from Stib_Parametros ";
			ResultSet resP = this.executasql.executarConsulta(sql);
			resP.next();
			
			sql="SELECT " +
			"i.dt_inicio, " +
			"SUM(case when ii.dm_tipo = 'E' and ii.dm_Situacao = true  then 1 else 0 end) as Equipamento_OK, " +
			"SUM(case when ii.dm_tipo = 'P' and ii.dm_Situacao = true  then 1 else 0 end) as Pessoal_OK, " +
			"SUM(case when ii.dm_tipo = 'E' then 1 else 0 end) as Equipamento_Avaliados, " +
			"SUM(case when ii.dm_tipo = 'P' then 1 else 0 end) as Pessoal_Avaliados " +
			"FROM " +
			"Stib_Inspecoes as i " +
			"left join Stib_Inspecoes_itens as ii on (i.oid_Inspecao = ii.oid_Inspecao)" +
			"WHERE " ;
			if (ed.getOid_Inspecao()>0) 
				sql += " i.oid_Inspecao = " + ed.getOid_Inspecao() + " ";
			else {
				sql += " i.oid_Cliente = " + ed.getOid_Cliente() + " ";
				sql += "and i.dt_Inicio <= '" + ed.getDt_Inicio() + "' ";
			}
			sql+="GROUP BY " +
			"i.dt_Inicio ";
			sql+="ORDER BY " +
			"i.dt_Inicio desc";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				Stib_InspecaoED edVolta = new Stib_InspecaoED();
				edVolta.setDt_Inicio(FormataData.formataDataBT(res.getDate("dt_Inicio")));
				edVolta.setNr_Percentual_Equipamento(res.getLong("Equipamento_OK")*100/res.getLong("Equipamento_Avaliados"));
				edVolta.setNr_Percentual_Pessoal(res.getLong("Pessoal_OK")*100/res.getLong("Pessoal_Avaliados"));
				edVolta.setNr_Percentual_Geral((res.getLong("Equipamento_OK")+res.getLong("Pessoal_OK"))*100/(res.getLong("Equipamento_Avaliados")+res.getLong("Pessoal_Avaliados")));
				edVolta.setNm_Nota_Equipamento(this.getNota(edVolta.getNr_Percentual_Equipamento(), resP , ed.getDm_Lingua()));
				edVolta.setNm_Nota_Pessoal(this.getNota(edVolta.getNr_Percentual_Pessoal(), resP , ed.getDm_Lingua()));
				edVolta.setNm_Nota_Geral(this.getNota(edVolta.getNr_Percentual_Geral(), resP , ed.getDm_Lingua()));
				list.add(edVolta);
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stib_Inspecao_ItemED ed)");
		}
	}
	
	// Retorna o texto com a descrição da nota da inspeção
	private String getNota (long p, ResultSet resP,String dmLingua ) throws SQLException {
		String texto=null;
		if (p >=  resP.getLong("nr_Percentual_Faixa1_de")  && p <= resP.getLong("nr_Percentual_Faixa1_ate") ) {
				texto="E".equals(dmLingua)?"Necessita mejorar":"Necessita melhorar";
		} else 
		if (p >=  resP.getLong("nr_Percentual_Faixa2_de")  &&  p <= resP.getLong("nr_Percentual_Faixa2_ate") ) {
			texto="E".equals(dmLingua)?"Bueno":"Bom";
		} else
		if (p >=  resP.getLong("nr_Percentual_Faixa3_de")  &&  p <= resP.getLong("nr_Percentual_Faixa3_ate") ) {
			texto="E".equals(dmLingua)?"Muy bueno":"Muito bom";
		}
		return texto;
	}
	
	private Stib_InspecaoED populaRegistro(ResultSet res) throws SQLException {
		Stib_InspecaoED ed = new Stib_InspecaoED();
		ed.setOid_Inspecao(res.getLong("oid_Inspecao"));
		ed.setOid_Concessionaria(res.getLong("oid_Concessionaria"));
		ed.setOid_Cliente(res.getLong("oid_Cliente"));
		ed.setOid_Usuario(res.getLong("oid_Usuario"));
		ed.setDt_Inicio(FormataData.formataDataBT(res.getDate("dt_Inicio")));
		ed.setDt_Fim(FormataData.formataDataBT(res.getDate("dt_Fim")));
		ed.setTx_Inicial(JavaUtil.getSQLStringDefOL(res.getString("tx_Inicial"),""));
		ed.setTx_Final(JavaUtil.getSQLStringDefOL(res.getString("tx_Final"),""));
		
		ed.setTx_Assinatura1(JavaUtil.getSQLStringDefOL(res.getString("tx_Assinatura1"),""));
		ed.setTx_Assinatura2(JavaUtil.getSQLStringDefOL(res.getString("tx_Assinatura2"), ""));
		ed.setTx_Inicial_OL(JavaUtil.getSQLStringDefOL(res.getString("tx_Inicial_OL"),""));
		ed.setTx_Final_OL(JavaUtil.getSQLStringDefOL(res.getString("tx_Final_OL"),""));
		ed.setTx_Assinatura1_OL(JavaUtil.getSQLStringDefOL(res.getString("tx_Assinatura1_OL"),""));
		ed.setTx_Assinatura2_OL(JavaUtil.getSQLStringDefOL(res.getString("tx_Assinatura2_OL"), ""));
		
		ed.setNm_Local(res.getString("nm_Cidade")+ " - " + res.getString("cd_Estado"));
		
		DateFormat dfmt = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
		ed.setNm_Local_Data(res.getString("nm_Cidade")+", "+dfmt.format(new Date()));
		ed.setNm_Razao_Social(res.getString("nm_Razao_Social"));
		
		ed.setNm_Signatario(JavaUtil.getSQLStringDefOL(res.getString("nm_Signatario"),""));
		// Ajusta tratamento
		if (Utilitaria.doValida(ed.getNm_Signatario()))
			ed.setTx_Tratamento(ed.getNm_Signatario().toUpperCase().startsWith("SRA.", 0) ? "Prezada ":"Prezado ");
		else 
			ed.setTx_Tratamento("");
		ed.setNm_Tecnico(JavaUtil.getSQLStringDefOL(res.getString("nm_Tecnico"),""));
		ed.setNm_Usuario(res.getString("nm_Usuario"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
