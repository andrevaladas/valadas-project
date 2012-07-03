package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Stas_Pneu_SucateadoED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial STAS - Pneu Sucateado
 * @serialData 05/2012
 */
public class Stas_Pneu_SucateadoBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Stas_Pneu_SucateadoBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Stas_Pneu_SucateadoED inclui(Stas_Pneu_SucateadoED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Stas_Pneus_Sucateados (" +
			"oid_Analise" +
			",oid_Pneu_Dimensao " +
			",oid_Modelo_Pneu " +
			",oid_Banda " +
			",oid_Banda_Dimensao " +
			",oid_Motivo_Sucata " +
			",nr_Fogo " +
			",nr_Vida " +
			",nr_Mm_Medido " +
			",nr_Mm_Inicial " +
			",vl_Pneu " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			"  " + ed.getOid_Analise() + 	
			", " + ed.getOid_Pneu_Dimensao() + 
			", " + ed.getOid_Modelo_Pneu() +
			", " + ed.getOid_Banda() +
			", " + ed.getOid_Banda_Dimensao() + 
			", " + ed.getOid_Motivo_Sucata() +
			",'" + ed.getNr_Fogo() +"' " +
			", " + ed.getNr_Vida() +
			", " + ed.getNr_Mm_Medido() +
			", " + ed.getNr_Mm_Inicial() +
			", " + ed.getVl_Pneu() +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Pneu_Sucateado(getSeq("Stas_Pneus_Sucateados_Oid_Pneu_Sucateado_seq"));
			// Procura ver se há em Stas_Dimensoes_Pneus essa dimensão já cadastrada
			ed.setDm_Gravou_Dimensao(new Stas_Dimensao_SucateadoBD(executasql).verificaDimensaoNaAnalise(ed));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stas_Pneu_SucateadoED ed)");
		}
	}

	public void altera(Stas_Pneu_SucateadoED ed) throws Excecoes {
		try {
			sql = "UPDATE Stas_Pneus_Sucateados SET " +
			" nr_Fogo = '" + ed.getNr_Fogo() + "' " +
			",oid_Pneu_Dimensao = " + ed.getOid_Pneu_Dimensao() +
			",oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu() +
			",oid_Banda = " + ed.getOid_Banda() +
			",oid_Banda_Dimensao = " + ed.getOid_Banda_Dimensao() +
			",oid_Motivo_Sucata = " + ed.getOid_Motivo_Sucata() +
			",nr_Vida = " + ed.getNr_Vida() +
			",nr_Mm_Medido = " + ed.getNr_Mm_Medido() +
			",nr_Mm_Inicial = " + ed.getNr_Mm_Inicial() +
			",vl_Pneu = " + ed.getVl_Pneu() +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Pneu_Sucateado = " + ed.getOid_Pneu_Sucateado();
			executasql.executarUpdate(sql);
			// Procura ver se há em Stas_Dimensoes_Pneus essa dimensão já cadastrada
			ed.setDm_Gravou_Dimensao(new Stas_Dimensao_SucateadoBD(executasql).verificaDimensaoNaAnalise(ed));
			// Deleta dimensao que nãoo tem mais correspondencia em pneu sucateado
			deleteDimensao(ed);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stas_Pneu_SucateadoED ed)");
		}
	}
	
	public void delete(Stas_Pneu_SucateadoED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stas_Pneus_Sucateados " +
			"WHERE " +
			"oid_Pneu_Sucateado = " + ed.getOid_Pneu_Sucateado();
			executasql.executarUpdate(sql);
			// Deleta dimensao que nãoo tem mais correspondencia em pneu sucateado
			deleteDimensao(ed);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Stas_Pneu_SucateadoED ed)");
		}
	}

	public void deleteDimensao(Stas_Pneu_SucateadoED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stas_Dimensoes_Sucateados " +
			"WHERE " +
			"oid_Analise = " + ed.getOid_Analise() + " and " +
			"oid_Pneu_Dimensao not in " +
			"(" +
			"	SELECT oid_Pneu_Dimensao " +
			"	FROM Stas_Pneus_Sucateados " +
			"	WHERE oid_analise=" + ed.getOid_Analise()  + 
			")";
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleteDimensao(Stas_Pneu_SucateadoED ed)");
		}
	}

	public ArrayList<Stas_Pneu_SucateadoED> lista(Stas_Pneu_SucateadoED ed) throws Excecoes {
		ArrayList<Stas_Pneu_SucateadoED> list = new ArrayList<Stas_Pneu_SucateadoED>();
		try {
			sql = "SELECT " +
			" ps.* " +
			",pd.nm_Pneu_Dimensao " +
			",mp.oid_Fabricante_Pneu " +
			",mp.nm_Modelo_Pneu " +
			",bd.oid_Fabricante_Banda " +
			",bd.nm_Banda" +
			",ms.nm_Motivo_Sucata" +
			",bdi.nr_Largura as nr_Largura_Banda " +
			",bdi.nr_Profundidade as nr_Profundidade_Banda " +
			",ds.nr_Twi " +
			",ds.vl_Pneu_Novo " +
			",ds.vl_Recapagem " +
			",ds.vl_Carcaca_R1 " +
			",ds.vl_Carcaca_R2 " +
			",ds.vl_Carcaca_R3 " +
			",ds.vl_Carcaca_R4 " +
			",ds.vl_Carcaca_R5 " +
			",ps.usuario_Stamp as usu_Stmp " +
	  		",ps.dt_Stamp as dt_Stmp " +
	  		",ps.dm_Stamp as dm_Stmp " +
			"FROM " +
			"Stas_Pneus_Sucateados as ps " +
			"left join Stas_Analises as an on (ps.oid_Analise = an.oid_Analise ) " +
			"left join Pneus_Dimensoes as pd on (ps.oid_Pneu_Dimensao = pd.oid_Pneu_Dimensao) " +
			"left join Bandas_Dimensoes as bdi on (ps.oid_Banda_Dimensao = bdi.oid_Banda_Dimensao) " +
			"left join Modelos_Pneus as mp on (ps.oid_Modelo_Pneu = mp.oid_Modelo_Pneu) " +
			"left join Bandas as bd on (ps.oid_Banda = bd.oid_Banda) " +
			"left join Pneus_Novos as pn on (ps.oid_Pneu_Dimensao = pn.oid_Pneu_Dimensao and ps.oid_Modelo_Pneu = pn.oid_Modelo_Pneu)" +
			"left join Stas_Motivos_Sucatas as ms on (ps.oid_Motivo_Sucata = ms.oid_Motivo_Sucata) " +
			"left join Stas_Dimensoes_Sucateados as ds on (ps.oid_Analise = ds.oid_Analise and ps.oid_Pneu_Dimensao = ds.oid_Pneu_Dimensao) " +
			"WHERE " +
			"1=1 " ;
			if (ed.getOid_Pneu_Sucateado()>0) 
				sql += " and ps.oid_Pneu_Sucateado = " + ed.getOid_Pneu_Sucateado() + " ";
			else {
				if (ed.getOid_Analise()>0) 
					sql += " and ps.oid_Analise = " + ed.getOid_Analise() + " ";
				if (Utilitaria.doValida(ed.getNr_Fogo())) 
					sql += " and ps.nr_Fogo = '" + ed.getNr_Fogo() + "' ";
			}
			//sql += "ORDER BY " ;
			//sql += "ps.nr_Fogo ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stas_Pneu_SucateadoED ed)");
		}
	}

	public Stas_Pneu_SucateadoED getByRecord(Stas_Pneu_SucateadoED ed) throws Excecoes {
		try {
			ArrayList<Stas_Pneu_SucateadoED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Stas_Pneu_SucateadoED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stas_Pneu_SucateadoED ed)");
		}
		return new Stas_Pneu_SucateadoED();
	}
	
	private Stas_Pneu_SucateadoED populaRegistro(ResultSet res) throws SQLException {
		Stas_Pneu_SucateadoED ed = new Stas_Pneu_SucateadoED();
		ed.setOid_Pneu_Sucateado(res.getLong("oid_Pneu_Sucateado"));
		ed.setOid_Analise(res.getLong("oid_Analise"));
		ed.setOid_Pneu_Dimensao(res.getLong("oid_Pneu_Dimensao"));
		ed.setOid_Fabricante_Pneu(res.getLong("oid_Fabricante_Pneu"));
		ed.setOid_Modelo_Pneu(res.getLong("oid_Modelo_Pneu"));
		ed.setOid_Fabricante_Banda(res.getLong("oid_Fabricante_Banda"));
		ed.setOid_Banda(res.getLong("oid_Banda"));
		ed.setOid_Banda_Dimensao(res.getLong("oid_Banda_Dimensao"));
		ed.setOid_Motivo_Sucata(res.getLong("oid_Motivo_Sucata"));
		ed.setNr_Fogo(res.getString("nr_Fogo"));
		ed.setNr_Vida(res.getInt("nr_Vida"));
		ed.setNr_Mm_Medido(res.getDouble("nr_Mm_Medido"));
		ed.setNr_Mm_Inicial(res.getDouble("nr_Mm_Inicial"));
		ed.setVl_Pneu(res.getDouble("vl_Pneu"));
		ed.setNm_Pneu_Dimensao(res.getString("nm_Pneu_Dimensao"));
		ed.setNm_Modelo_Pneu(res.getString("nm_Modelo_Pneu"));
		ed.setNm_Banda(getValueDef(res.getString("nm_Banda"), "ORIGINAL"));
		ed.setNm_Motivo_Sucata(res.getString("nm_Motivo_Sucata"));
		ed.setNr_Twi(res.getDouble("nr_Twi"));
		ed.setVl_Pneu_Novo(res.getDouble("vl_Pneu_Novo"));
		ed.setVl_Recapagem(res.getDouble("vl_Recapagem"));
		ed.setVl_Carcaca_R1(res.getDouble("vl_Carcaca_R1"));
		ed.setVl_Carcaca_R2(res.getDouble("vl_Carcaca_R2"));
		ed.setVl_Carcaca_R3(res.getDouble("vl_Carcaca_R3"));
		ed.setVl_Carcaca_R4(res.getDouble("vl_Carcaca_R4"));
		ed.setVl_Carcaca_R5(res.getDouble("vl_Carcaca_R5"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}

