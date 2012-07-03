package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.master.ed.Stas_AnaliseED;
import com.master.ed.Stas_Motivo_SucataED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.FormataValor;
import com.master.util.JavaUtil;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial STAS - Analise sucata
 * @serialData 05/2012
 */
public class Stas_AnaliseBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Stas_AnaliseBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Stas_AnaliseED inclui(Stas_AnaliseED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Stas_Analises (" +
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
			ed.setOid_Analise(getSeq("Stas_Analises_Oid_Analise_seq"));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stas_AnaliseED ed)");
		}
	}

	public void altera(Stas_AnaliseED ed) throws Excecoes {
		try {
			sql = "UPDATE Stas_Analises SET " +
			" dt_Fim = '" + ed.getDt_Fim() + "' " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Analise = " + ed.getOid_Analise();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stas_AnaliseED ed)");
		}
	}
	
	public void reabrirAnalise(Stas_AnaliseED ed) throws Excecoes {
		try {
			sql = "UPDATE Stas_Analises SET " +
			" dt_Fim = null " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Analise = " + ed.getOid_Analise();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stas_AnaliseED ed)");
		}
	}
	
	public void alteraRelatorio(Stas_AnaliseED ed) throws Excecoes {
		try {
			sql = "UPDATE Stas_Analises SET " +
			" tx_Inicial = '" + ed.getTx_Inicial() + "' " +
			",nr_Mm_Calculo_Perda = '" + ed.getNr_Mm_Calculo_Perda() + "' " +
			",tx_Assinatura1 = '" + ed.getTx_Assinatura1() + "' " +
			",tx_Assinatura2 = '" + ed.getTx_Assinatura2() + "' " +
			",nm_Signatario = '" + ed.getNm_Signatario() + "' " +
			",nm_Tecnico = '" + ed.getNm_Tecnico() + "' " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = 'A' " + 
			"WHERE " +
			"oid_Analise = " + ed.getOid_Analise();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stas_AnaliseED ed)");
		}
	}
	
	public void encerra(Stas_AnaliseED ed) throws Excecoes {
		try {
			sql = "UPDATE Stas_Analises SET " +
			" dt_Fim = '" + ed.getDt_Fim() + "' " +
			",nr_Quantidade_Pneus = (SELECT count(*) from Stas_Pneus_Sucateados WHERE oid_Analise = " + ed.getOid_Analise()+") " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = 'A' " + 
			"WHERE " +
			"oid_Analise = " + ed.getOid_Analise();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stas_AnaliseED ed)");
		}
	}
	
	public void delete(Stas_AnaliseED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stas_Pneus_Sucateados " +
			"WHERE " +
			"oid_Analise = " + ed.getOid_Analise();
			executasql.executarUpdate(sql);
			//
			sql = "DELETE FROM Stas_Dimensoes_Sucateados " +
			"WHERE " +
			"oid_Analise = " + ed.getOid_Analise();
			executasql.executarUpdate(sql);
			//
			sql = "DELETE FROM Stas_Analises " +
			"WHERE " +
			"oid_Analise = " + ed.getOid_Analise();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Stas_AnaliseED ed)");
		}
	}

	public ArrayList<Stas_AnaliseED> lista(Stas_AnaliseED ed) throws Excecoes {
		ArrayList<Stas_AnaliseED> list = new ArrayList<Stas_AnaliseED>();
		try {
			sql = "SELECT " +
			" i.* " +
			",replace(tx_Inicial,    chr(13), '&#13;' ) as tx_Inicial_OL " +
			",replace(tx_Assinatura1,chr(13), '&#13;' ) as tx_Assinatura1_OL " +
			",replace(tx_Assinatura2,chr(13), '&#13;' ) as tx_Assinatura2_OL " +
			",e.nm_Razao_Social " +
			",e.nm_Cidade " +
			",e.cd_Estado " +
			",us.nm_Usuario " +
			",i.usuario_Stamp as usu_Stmp " +
	  		",i.dt_Stamp as dt_Stmp " +
	  		",i.dm_Stamp as dm_Stmp " +
			"FROM Stas_Analises as i " +
			"left join Empresas as e on (e.oid_Empresa=i.oid_Cliente) " +
			"left join Usuarios as us on (us.oid_Usuario=i.oid_Usuario) " +
			"WHERE " +
			"1 = 1 " ;
			if (ed.getOid_Analise()>0) 
				sql += " and i.oid_Analise = " + ed.getOid_Analise() + " ";
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
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stas_AnaliseED ed)");
		}
	}

	public Stas_AnaliseED getTotalAnalise(Stas_AnaliseED ed) throws Excecoes {
		Stas_AnaliseED edVolta = new Stas_AnaliseED();
		try {
			sql = "SELECT " +
			" count(*) as nr_Quantidade_Total " +
			",sum(ps.nr_Mm_Inicial - ps.nr_Mm_Medido) as nr_Mm_Perdido_Total" +
			",sum(ps.vl_Pneu) as vl_Perdido_Total" +
			
			",sum (case when ps.nr_Vida = 0 then 1 else 0 end ) as nr_Quantidade_Novo" +
			",sum (case when ps.nr_Vida = 0 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_Novo" +
			",sum (case when ps.nr_Vida = 0 then ps.vl_Pneu else 0 end ) as vl_Perdido_Novo" +
			
			",sum (case when ps.nr_Vida = 1 then 1 else 0 end ) as nr_Quantidade_R1" +
			",sum (case when ps.nr_Vida = 1 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R1" +
			",sum (case when ps.nr_Vida = 1 then ps.vl_Pneu else 0 end ) as vl_Perdido_R1" +
			
			",sum (case when ps.nr_Vida = 2 then 1 else 0 end ) as nr_Quantidade_R2" +
			",sum (case when ps.nr_Vida = 2 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R2" +
			",sum (case when ps.nr_Vida = 2 then ps.vl_Pneu else 0 end ) as vl_Perdido_R2" +
			
			",sum (case when ps.nr_Vida = 3 then 1 else 0 end ) as nr_Quantidade_R3" +
			",sum (case when ps.nr_Vida = 3 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R3" +
			",sum (case when ps.nr_Vida = 3 then ps.vl_Pneu else 0 end ) as vl_Perdido_R3" +
			
			",sum (case when ps.nr_Vida = 4 then 1 else 0 end ) as nr_Quantidade_R4" +
			",sum (case when ps.nr_Vida = 4 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R4" +
			",sum (case when ps.nr_Vida = 4 then ps.vl_Pneu else 0 end ) as vl_Perdido_R4" +
			
			",sum (case when ps.nr_Vida > 4 then 1 else 0 end ) as nr_Quantidade_R5" +
			",sum (case when ps.nr_Vida > 4 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R5" +
			",sum (case when ps.nr_Vida > 4 then ps.vl_Pneu else 0 end ) as vl_Perdido_R5 " +
			
			"FROM " +
			"Stas_Pneus_Sucateados as ps " +
			"WHERE " +
			"ps.oid_Analise in (" + ed.getOid_Analise_In() + ") ";

			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				edVolta.setNr_Quantidade_Total(res.getInt("nr_Quantidade_Total"));
				edVolta.setNr_Mm_Perdido_Total(res.getDouble("nr_Mm_Perdido_Total"));
				edVolta.setVl_Perdido_Total(res.getDouble("vl_Perdido_Total"));
				
				edVolta.setNr_Quantidade_Novo(res.getInt("nr_Quantidade_Novo"));
				edVolta.setNr_Mm_Perdido_Novo(res.getDouble("nr_Mm_Perdido_Novo"));
				edVolta.setVl_Perdido_Novo(res.getDouble("vl_Perdido_Novo"));
				
				edVolta.setNr_Quantidade_R1(res.getInt("nr_Quantidade_R1"));
				edVolta.setNr_Mm_Perdido_R1(res.getDouble("nr_Mm_Perdido_R1"));
				edVolta.setVl_Perdido_R1(res.getDouble("vl_Perdido_R1"));
				
				edVolta.setNr_Quantidade_R2(res.getInt("nr_Quantidade_R2"));
				edVolta.setNr_Mm_Perdido_R2(res.getDouble("nr_Mm_Perdido_R2"));
				edVolta.setVl_Perdido_R2(res.getDouble("vl_Perdido_R2"));
				
				edVolta.setNr_Quantidade_R3(res.getInt("nr_Quantidade_R3"));
				edVolta.setNr_Mm_Perdido_R3(res.getDouble("nr_Mm_Perdido_R3"));
				edVolta.setVl_Perdido_R3(res.getDouble("vl_Perdido_R3"));
				
				edVolta.setNr_Quantidade_R4(res.getInt("nr_Quantidade_R4"));
				edVolta.setNr_Mm_Perdido_R4(res.getDouble("nr_Mm_Perdido_R4"));
				edVolta.setVl_Perdido_R4(res.getDouble("vl_Perdido_R4"));
				
				edVolta.setNr_Quantidade_R5(res.getInt("nr_Quantidade_R5"));
				edVolta.setNr_Mm_Perdido_R5(res.getDouble("nr_Mm_Perdido_R5"));
				edVolta.setVl_Perdido_R5(res.getDouble("vl_Perdido_R5"));
			}
			return edVolta;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getTotalAnalise(Stas_AnaliseED ed)");
		}
	}

	public ArrayList<Stas_Motivo_SucataED> getMotivosAnalise(Stas_AnaliseED ed, Stas_AnaliseED pEdTot) throws Excecoes {
		ArrayList<Stas_Motivo_SucataED> list = new ArrayList<Stas_Motivo_SucataED>();
		
		try {
			sql = "SELECT " +
			"ms.oid_Motivo_Sucata" +
			",ms.nm_Motivo_Sucata" +
			",nm_Motivo_Sucata_E" +
			",tx_Motivo_Sucata" +
			",tx_Recomendacao" +
			",tx_Motivo_Sucata_E" +
			",tx_Recomendacao_E" +
			
			",count(*) as nr_Quantidade_Total " +
			",sum(ps.nr_Mm_Inicial - ps.nr_Mm_Medido) as nr_Mm_Perdido_Total" +
			",sum(ps.vl_Pneu) as vl_Perdido_Total" +
			
			",sum (case when ps.nr_Vida = 0 then 1 else 0 end ) as nr_Quantidade_Novo" +
			",sum (case when ps.nr_Vida = 0 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_Novo" +
			",sum (case when ps.nr_Vida = 0 then ps.vl_Pneu else 0 end ) as vl_Perdido_Novo" +
			
			",sum (case when ps.nr_Vida = 1 then 1 else 0 end ) as nr_Quantidade_R1" +
			",sum (case when ps.nr_Vida = 1 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R1" +
			",sum (case when ps.nr_Vida = 1 then ps.vl_Pneu else 0 end ) as vl_Perdido_R1" +
			
			",sum (case when ps.nr_Vida = 2 then 1 else 0 end ) as nr_Quantidade_R2" +
			",sum (case when ps.nr_Vida = 2 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R2" +
			",sum (case when ps.nr_Vida = 2 then ps.vl_Pneu else 0 end ) as vl_Perdido_R2" +
			
			",sum (case when ps.nr_Vida = 3 then 1 else 0 end ) as nr_Quantidade_R3" +
			",sum (case when ps.nr_Vida = 3 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R3" +
			",sum (case when ps.nr_Vida = 3 then ps.vl_Pneu else 0 end ) as vl_Perdido_R3" +
			
			",sum (case when ps.nr_Vida = 4 then 1 else 0 end ) as nr_Quantidade_R4" +
			",sum (case when ps.nr_Vida = 4 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R4" +
			",sum (case when ps.nr_Vida = 4 then ps.vl_Pneu else 0 end ) as vl_Perdido_R4" +
			
			",sum (case when ps.nr_Vida > 4 then 1 else 0 end ) as nr_Quantidade_R5" +
			",sum (case when ps.nr_Vida > 4 then (ps.nr_Mm_Inicial - ps.nr_Mm_Medido) else 0 end ) as nr_Mm_Perdido_R5" +
			",sum (case when ps.nr_Vida > 4 then ps.vl_Pneu else 0 end ) as vl_Perdido_R5 " +
			
			"FROM " +
			"Stas_Pneus_Sucateados as ps " +
			"left join Stas_Motivos_Sucatas as ms on (ps.oid_Motivo_Sucata = ms.oid_Motivo_Sucata) " +
			"WHERE " +
			"ps.oid_Analise in (" + ed.getOid_Analise_In() + ") " +
			
			"GROUP BY " +
			"ms.oid_Motivo_Sucata" +
			",ms.nm_Motivo_Sucata" +
			",nm_Motivo_Sucata_E" +
			",tx_Motivo_Sucata" +
			",tx_Recomendacao" +
			",tx_Motivo_Sucata_E" +
			",tx_Recomendacao_E";
			
			System.out.println(sql);
			
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				Stas_Motivo_SucataED edVolta = new Stas_Motivo_SucataED();
				edVolta.setOid_Motivo_Sucata(res.getLong("oid_Motivo_Sucata"));
				edVolta.setNm_Motivo_Sucata(res.getString("nm_Motivo_Sucata"));
				edVolta.setTx_Motivo_Sucata(res.getString("tx_Motivo_Sucata"));
				edVolta.setTx_Recomendacao(res.getString("tx_Recomendacao"));
				
				edVolta.setNr_Quantidade_Total(res.getInt("nr_Quantidade_Total"));
				edVolta.setPerc_Quantidade_Total((double)edVolta.getNr_Quantidade_Total()/pEdTot.getNr_Quantidade_Total()*100);
				edVolta.setNr_Mm_Perdido_Total(res.getDouble("nr_Mm_Perdido_Total"));
				edVolta.setVl_Perdido_Total(res.getDouble("vl_Perdido_Total"));
				
				edVolta.setNr_Quantidade_Novo(res.getInt("nr_Quantidade_Novo"));
				edVolta.setPerc_Quantidade_Novo((double)edVolta.getNr_Quantidade_Novo()/edVolta.getNr_Quantidade_Total()*100);
				edVolta.setNr_Mm_Perdido_Novo(res.getDouble("nr_Mm_Perdido_Novo"));
				edVolta.setVl_Perdido_Novo(res.getDouble("vl_Perdido_Novo"));
				
				edVolta.setNr_Quantidade_R1(res.getInt("nr_Quantidade_R1"));
				edVolta.setPerc_Quantidade_R1((double)edVolta.getNr_Quantidade_R1()/edVolta.getNr_Quantidade_Total()*100);
				edVolta.setNr_Mm_Perdido_R1(res.getDouble("nr_Mm_Perdido_R1"));
				edVolta.setVl_Perdido_R1(res.getDouble("vl_Perdido_R1"));
				
				edVolta.setNr_Quantidade_R2(res.getInt("nr_Quantidade_R2"));
				edVolta.setPerc_Quantidade_R2((double)edVolta.getNr_Quantidade_R2()/edVolta.getNr_Quantidade_Total()*100);
				edVolta.setNr_Mm_Perdido_R2(res.getDouble("nr_Mm_Perdido_R2"));
				edVolta.setVl_Perdido_R2(res.getDouble("vl_Perdido_R2"));
				
				edVolta.setNr_Quantidade_R3(res.getInt("nr_Quantidade_R3"));
				edVolta.setPerc_Quantidade_R3((double)edVolta.getNr_Quantidade_R3()/edVolta.getNr_Quantidade_Total()*100);
				edVolta.setNr_Mm_Perdido_R3(res.getDouble("nr_Mm_Perdido_R3"));
				edVolta.setVl_Perdido_R3(res.getDouble("vl_Perdido_R3"));
				
				edVolta.setNr_Quantidade_R4(res.getInt("nr_Quantidade_R4"));
				edVolta.setPerc_Quantidade_R4((double)edVolta.getNr_Quantidade_R4()/edVolta.getNr_Quantidade_Total()*100);
				edVolta.setNr_Mm_Perdido_R4(res.getDouble("nr_Mm_Perdido_R4"));
				edVolta.setVl_Perdido_R4(res.getDouble("vl_Perdido_R4"));
				
				edVolta.setNr_Quantidade_R5(res.getInt("nr_Quantidade_R5"));
				edVolta.setPerc_Quantidade_R5((double)edVolta.getNr_Quantidade_R5()/edVolta.getNr_Quantidade_Total()*100);
				edVolta.setNr_Mm_Perdido_R5(res.getDouble("nr_Mm_Perdido_R5"));
				edVolta.setVl_Perdido_R5(res.getDouble("vl_Perdido_R5"));
				
				list.add(edVolta);
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getTotalAnalise(Stas_AnaliseED ed)");
		}
	}

	public ArrayList<Stas_Motivo_SucataED> getMotivosMarca(Stas_AnaliseED ed, Stas_Motivo_SucataED pMsED) throws Excecoes {
		ArrayList<Stas_Motivo_SucataED> list = new ArrayList<Stas_Motivo_SucataED>();
		
		try {
			System.out.println("ed.getDm_Encerradas()="+ed.getDm_Encerradas());
			if ("1".equals(ed.getDm_Encerradas())) {
				sql = "SELECT " +
				" mp.nm_modelo_pneu as nm_Tabela_Marca " +
				",count(*) as nr_Quantidade " +
				"FROM " +
				"Stas_Pneus_Sucateados as ps " +
				"left join Modelos_Pneus as mp on ( mp.oid_Modelo_Pneu=ps.oid_Modelo_Pneu ) " +
				"WHERE " +
				"ps.oid_Analise in (" + ed.getOid_Analise_In() + ") and " +
				"ps.oid_Motivo_Sucata = " + pMsED.getOid_Motivo_Sucata() + " " +
				"GROUP BY " +
				"mp.nm_Modelo_Pneu ";
			} else {
				sql = "SELECT " +
				" b.nm_banda as nm_Tabela_Marca" +
				",count(*) as nr_Quantidade " +
				"FROM " +
				"Stas_Pneus_Sucateados as ps " +
				"left join Bandas as b on (b.oid_Banda = ps.oid_Banda) " +
				"WHERE " +
				"ps.oid_Analise in (" + ed.getOid_Analise_In() + ") and " +
				"ps.oid_Motivo_Sucata = " + pMsED.getOid_Motivo_Sucata() + " " +
				"GROUP BY " +
				"b.nm_Banda ";
			}
			System.out.println(sql);
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				Stas_Motivo_SucataED edVolta = new Stas_Motivo_SucataED();
				edVolta.setNm_Descricao_Tabela_Marca(res.getString("nm_Tabela_Marca"));
				edVolta.setNr_Quantidade_Total(res.getInt("nr_Quantidade"));
				edVolta.setPerc_Quantidade_Total((double)edVolta.getNr_Quantidade_Total()/pMsED.getNr_Quantidade_Total()*100);
				System.out.println("getMotivosMarca "+edVolta.getNm_Descricao_Tabela_Marca() + " " + edVolta.getNr_Quantidade_Total() + " " + edVolta.getPerc_Quantidade_Total());
				list.add(edVolta);
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getTotalAnalise(Stas_AnaliseED ed)");
		}
	}

	public Stas_AnaliseED getByRecord(Stas_AnaliseED ed) throws Excecoes {
		try {
			ArrayList<Stas_AnaliseED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Stas_AnaliseED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stas_AnaliseED ed)");
		}
		return new Stas_AnaliseED();
	}
	
	private Stas_AnaliseED populaRegistro(ResultSet res) throws SQLException {
		Stas_AnaliseED ed = new Stas_AnaliseED();
		ed.setOid_Analise(res.getLong("oid_Analise"));
		ed.setOid_Concessionaria(res.getLong("oid_Concessionaria"));
		ed.setOid_Cliente(res.getLong("oid_Cliente"));
		ed.setOid_Usuario(res.getLong("oid_Usuario"));
		ed.setDt_Inicio(FormataData.formataDataBT(res.getDate("dt_Inicio")));
		ed.setDt_Fim(FormataData.formataDataBT(res.getDate("dt_Fim")));
		ed.setTx_Inicial(JavaUtil.getSQLStringDefOL(res.getString("tx_Inicial"),""));
		ed.setNr_Mm_Calculo_Perda(res.getDouble("Nr_Mm_Calculo_Perda"));
		ed.setNr_Quantidade_Pneus(res.getInt("nr_Quantidade_Pneus"));
		ed.setTx_Assinatura1(JavaUtil.getSQLStringDefOL(res.getString("tx_Assinatura1"),""));
		ed.setTx_Assinatura2(JavaUtil.getSQLStringDefOL(res.getString("tx_Assinatura2"), ""));
		ed.setTx_Inicial_OL(JavaUtil.getSQLStringDefOL(res.getString("tx_Inicial_OL"),""));
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
