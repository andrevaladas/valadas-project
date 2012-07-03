/*
 * Created on 14/05/2012
 *
 */
package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Pneu_NovoED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.Utilitaria;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis Steigleder
 * @serial Dimensões de pneus 
 * @serialData 05/2012
 */
public class Pneu_NovoBD extends BancoUtil {

	private ExecutaSQL executaSQL;

	String sql=null;

	public Pneu_NovoBD (ExecutaSQL executaSQL) {
		super(executaSQL);
		this.executaSQL = executaSQL;
	}

	public Pneu_NovoED inclui (Pneu_NovoED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Pneus_Novos (" +
			" oid_Pneu_Dimensao " +
			",oid_Modelo_Pneu " +
			",nr_Mm_Profundidade " +
			",dm_Stamp" +
		  	",dt_Stamp" +
		  	",usuario_Stamp" ;
			sql+=") values ( " + 
			" " + ed.getOid_Pneu_Dimensao() +
			"," + ed.getOid_Modelo_Pneu() +
			"," + ed.getNr_Mm_Profundidade() ;
			
			sql+= ",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" ;
			sql+=")";
			executaSQL.executarUpdate (sql);
			// Pega o Oid
			ed.setOid_Pneu_Novo(getSeq("Pneus_Novos_oid_Pneu_Novo_seq"));
			return ed;
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "inclui (Pneu_NovoED ed)");
		}
	}

	public void altera (Pneu_NovoED ed) throws Excecoes {
		try {
			sql = "UPDATE Pneus_Novos SET " +
			" oid_Pneu_Dimensao = " + ed.getOid_Pneu_Dimensao() +
			",oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu() +
			",nr_Mm_Profundidade = " + ed.getNr_Mm_Profundidade() +
			",dm_Stamp = 'A'" +
  	   		",dt_Stamp = '" + Data.getDataDMY() + "' " +
  	   		",usuario_Stamp = '"+ed.getUsuario_Stamp() + "' " +
			"WHERE " +
			"oid_Pneu_Novo = " + ed.getOid_Pneu_Novo ();
			executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "altera (Pneu_NovoED ed)");
		}
	}

	public void delete (Pneu_NovoED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Pneus_Novos " +
			"WHERE " +
			"oid_Pneu_Novo = " + ed.getOid_Pneu_Novo ();
			executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "delete (Pneu_NovoED ed)");
		}
	}

	public ArrayList<Pneu_NovoED> lista (Pneu_NovoED ed) throws Excecoes {
		ArrayList<Pneu_NovoED> list = new ArrayList <Pneu_NovoED>();
		try {
			sql = "SELECT * " +
			",pn.oid_Pneu_Dimensao as pnoid_Pneu_Dimensao" +
			",pn.oid_Modelo_Pneu as pnoid_Modelo_Pneu" +
			",mp.oid_Fabricante_Pneu as mpoid_Fabricante_Pneu" +
	  		",pn.usuario_Stamp as usu_Stmp " +
	  		",pn.dt_Stamp as dt_Stmp " +
	  		",pn.dm_Stamp as dm_Stmp " +
			"FROM " +
			"Pneus_Novos as pn " +
			"left join Modelos_Pneus as mp on (pn.oid_Modelo_Pneu = mp.oid_Modelo_Pneu) " +
			"left join Fabricantes_Pneus as fp on (mp.oid_Fabricante_Pneu = fp.oid_Fabricante_Pneu) " +
			"left join Pneus_Dimensoes as pd on (pn.oid_Pneu_Dimensao = pd.oid_Pneu_Dimensao )" +
			"WHERE 1=1 ";
			if (ed.getOid_Pneu_Novo () > 0) 
				sql += " and pn.oid_Pneu_Novo = " + ed.getOid_Pneu_Novo ();
			else {
				if (Utilitaria.doValida(ed.getCd_Pneu_Novo())) {
					sql += " and pn.cd_Pneu_Novo = '" + ed.getCd_Pneu_Novo() + "' ";
				}
				if (ed.getOid_Pneu_Dimensao() > 0) 
					sql += " and pn.oid_Pneu_Dimensao = " + ed.getOid_Pneu_Dimensao ();
				if (ed.getOid_Modelo_Pneu() > 0) 
					sql += " and pn.oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu();
			}
			sql += " ORDER BY " +
			"mp.nm_Modelo_Pneu";
			ResultSet rs = executaSQL.executarConsulta (sql);
			while (rs.next ()) {
				list.add (populaRegistro(rs));
			}
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "lista (Pneu_NovoED ed)");
		}
		return list;
	}

	public Pneu_NovoED getByRecord(Pneu_NovoED ed) throws Excecoes {
		
		try {
			ArrayList<Pneu_NovoED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Pneu_NovoED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Pneu_NovoED ed)");
		}
		return new Pneu_NovoED();
	}

	public Pneu_NovoED populaRegistro(ResultSet res) throws SQLException {
		Pneu_NovoED ed = new Pneu_NovoED();
		ed.setOid_Pneu_Novo(res.getInt("oid_Pneu_Novo"));
		ed.setCd_Pneu_Novo(res.getString("cd_Pneu_Novo"));
		ed.setNr_Mm_Profundidade(res.getDouble("Nr_Mm_Profundidade"));
		ed.setOid_Pneu_Dimensao(res.getInt("pnoid_Pneu_Dimensao"));
		ed.setCd_Pneu_Dimensao(res.getString("cd_Pneu_Dimensao"));
		ed.setNm_Pneu_Dimensao(res.getString("nm_Pneu_Dimensao"));
		ed.setOid_Modelo_Pneu(res.getInt("pnoid_Modelo_Pneu"));
		ed.setCd_Modelo_Pneu(res.getString("cd_Modelo_Pneu"));
		ed.setNm_Modelo_Pneu(res.getString("nm_Modelo_Pneu"));
		ed.setOid_Fabricante_Pneu(res.getInt("mpoid_Fabricante_Pneu"));
		ed.setNm_Fabricante_Pneu(res.getString("nm_Fabricante_Pneu"));
	    ed.setUsuario_Stamp(res.getString("usu_Stmp"));
	    ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
	    return ed;	  
	}
}