package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Banda_DimensaoED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Bandas_Dimensoes
 * @serialData 01/2010
 */
public class Banda_DimensaoBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Banda_DimensaoBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Banda_DimensaoED inclui(Banda_DimensaoED ed) throws Excecoes {
		try {
			//ed.setOid_Banda(getAutoIncremento("oid_Banda", "Bandas"));
			sql = "INSERT INTO Bandas_Dimensoes (" +
			"oid_Banda" +
			",nr_Largura " +
			",nr_Profundidade " +
			",nr_Peso " +
			",nr_Peso_Metro " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			ed.getOid_Banda() +	
			", " + ed.getNr_Largura() +
			", " + ed.getNr_Profundidade() +
			", " + ed.getNr_Peso() +
			", " + ed.getNr_Peso_Metro() +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			
			ed.setOid_Banda_Dimensao(getSeq("Bandas_Dimensoes_oid_Banda_Dimensao_seq"));
			
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Banda_DimensaoED ed)");
		}
	}

	public void altera(Banda_DimensaoED ed) throws Excecoes {
		try {
			sql = "UPDATE Bandas_Dimensoes SET " +
			"oid_Banda = '" + ed.getOid_Banda() + "' " +
			",nr_Largura = " + ed.getNr_Largura() +
			",nr_Profundidade = " + ed.getNr_Profundidade() +
			",nr_Peso = " + ed.getNr_Peso() +
			",nr_Peso_Metro = " + ed.getNr_Peso_Metro() +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Banda_Dimensao = " + ed.getOid_Banda_Dimensao();
			executasql.executarUpdate(sql);
			
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Banda_DimensaoED ed)");
		}
	}
	
	
	public void converte(Banda_DimensaoED ed) throws Excecoes {
		try {
			sql="select " +
				"oid_banda, " +
				"oid_banda_substituta " +
				"from bandas " +
				"where " +
				"oid_fabricante_banda = 1 and " +
				"dm_substituir='true' " +
				"order by nm_banda ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				sql = "update " +
					  "recapagens_garantidas " +
					  "set " +
					  "oid_banda_salva=oid_banda, " +
					  "oid_banda = " + res.getInt("oid_banda_substituta") + " " +
					  "where " +
					  "oid_banda = " +res.getInt("oid_banda");
				System.out.println(sql);
				executasql.executarUpdate(sql);
			}
			//sql = "delete from bandas where oid_banda_substituta is not null and oid_banda_substituta > 0 ";
			//executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"converte(Banda_DimensaoED ed)");
		}
	}
	
	public void converteII(Banda_DimensaoED ed) throws Excecoes {
		try {
			sql="select " +
				"rg.oid_recapagem_garantida as rgoid_Recapagem_Garantida," +
				"rg.oid_banda," +
				"rg.oid_banda_dimensao," +
				"rg.oid_banda_Salva," +
				"b.nr_profundidade," +
				"b.nr_largura," +
				"bd.oid_banda_dimensao as bdoid_banda_dimensao " +
				"from " +
				"recapagens_garantidas as rg " +
				"left join bandas as b on b.oid_banda = rg.oid_banda_Salva " +
				"left join bandas_dimensoes as bd  on (bd.oid_banda=rg.oid_banda and bd.nr_largura = b.nr_largura and bd.nr_profundidade = b.nr_profundidade )  " +
				"where " +
				"rg.oid_banda_Salva > 0 " +
				"and bd.oid_banda_dimensao is not null ";
			
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				sql = "update " +
					  "recapagens_garantidas " +
					  "set " +
					  "oid_banda_dimensao = " + res.getInt("bdoid_banda_dimensao") + " " +
					  "where " +
					  "oid_recapagem_garantida = " +res.getInt("rgoid_Recapagem_Garantida");
				System.out.println(sql);
				executasql.executarUpdate(sql);
			}
			//sql = "delete from bandas where oid_banda_substituta is not null and oid_banda_substituta > 0 ";
			//executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"converte(Banda_DimensaoED ed)");
		}
	}
	

	public void delete(Banda_DimensaoED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Bandas_Dimensoes " +
			"WHERE " +
			"oid_Banda_Dimensao = " + ed.getOid_Banda_Dimensao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Banda_DimensaoED ed)");
		}
	}

	public ArrayList lista(Banda_DimensaoED ed) throws Excecoes {

		ArrayList list = new ArrayList();
		try {
			sql = "SELECT * " +
			",bd.nr_largura as bdNr_Largura" +
			",bd.nr_profundidade as bdNr_Profundidade" +
			",bd.nr_peso as bdNr_Peso" +
			",bd.nr_peso_metro as bdNr_Peso_Metro" +
	  		",bd.usuario_Stamp as usu_Stmp " +
	  		",bd.dt_Stamp as dt_Stmp " +
	  		",bd.dm_Stamp as dm_Stmp " +
			"FROM Bandas_Dimensoes as bd " +
			"left join Bandas as b on b.oid_Banda = bd.oid_Banda "+ 
			"left join Fabricantes_Bandas as f on f.oid_Fabricante_Banda = b.oid_Fabricante_Banda " +
			"WHERE " +
			"1 = 1 " ;
			if (ed.getOid_Banda_Dimensao()>0) 
				sql += "and bd.oid_Banda_Dimensao = " + ed.getOid_Banda_Dimensao();
			else {
				if (ed.getOid_Banda()>0)
					sql += " and bd.oid_Banda = " + ed.getOid_Banda()  ;
			}
			sql += "ORDER BY " +
			"b.nm_Banda, bd.nr_Largura, bd.nr_Profundidade";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Banda_DimensaoED ed)");
		}
	}

	public Banda_DimensaoED getByRecord(Banda_DimensaoED ed) throws Excecoes {
		try {
			ArrayList lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Banda_DimensaoED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Banda_DimensaoED ed)");
		}
		return new Banda_DimensaoED();
	}
	
	private Banda_DimensaoED populaRegistro(ResultSet res) throws SQLException {
		Banda_DimensaoED ed = new Banda_DimensaoED();
		ed.setOid_Banda_Dimensao(res.getInt("oid_Banda_Dimensao"));
		ed.setOid_Banda(res.getInt("oid_Banda"));
		ed.setNr_Largura(res.getDouble("bdNr_Largura"));
		ed.setNr_Profundidade(res.getDouble("bdNr_Profundidade"));
		ed.setNr_Peso(res.getDouble("bdNr_Peso"));
		ed.setNr_Peso_Metro(res.getDouble("bdNr_Peso_Metro"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}
}
