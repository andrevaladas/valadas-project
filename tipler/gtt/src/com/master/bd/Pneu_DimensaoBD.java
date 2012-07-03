/*
 * Created on 14/05/2012
 *
 */
package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Pneu_DimensaoED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis Steigleder
 * @serial Dimnensoes de pneus 
 * @serialData 05/2012
 */
public class Pneu_DimensaoBD extends BancoUtil {

	private ExecutaSQL executaSQL;

	String sql=null;

	public Pneu_DimensaoBD (ExecutaSQL executaSQL) {
		super(executaSQL);
		this.executaSQL = executaSQL;
	}

	public Pneu_DimensaoED inclui (Pneu_DimensaoED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Pneus_Dimensoes (" +
			"cd_Pneu_Dimensao " +
			",nm_Pneu_Dimensao " +
			",nr_mm_Perimetro " +
			",dm_Stamp" +
		  	",dt_Stamp" +
		  	",usuario_Stamp" ;
			sql+=") values ( '" + 
			(doValida(ed.getCd_Pneu_Dimensao()) ? ed.getCd_Pneu_Dimensao(): "" ) + "'" +
			", '" + ed.getNm_Pneu_Dimensao () + "' " +
			",  " + ed.getNr_Mm_Perimetro() ;
			sql+= ",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" ;
			sql+=")";
			executaSQL.executarUpdate (sql);
			// Pega o Oid
			ed.setOid_Pneu_Dimensao(getSeq("Pneus_Dimensoes_oid_Pneu_Dimensao_seq"));
			return ed;
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "inclui (Pneu_DimensaoED ed)");
		}
	}

	public void altera (Pneu_DimensaoED ed) throws Excecoes {
		try {
			sql = "UPDATE Pneus_Dimensoes SET " +
			"cd_Pneu_Dimensao = '" + (doValida(ed.getCd_Pneu_Dimensao ()) ? ed.getCd_Pneu_Dimensao () : "" ) + "'" +
			",nm_Pneu_Dimensao = '" + ed.getNm_Pneu_Dimensao () + "' " +
			",nr_Mm_Perimetro = " + ed.getNr_Mm_Perimetro() +
			",dm_Stamp = 'A'" +
  	   		",dt_Stamp = '" + Data.getDataDMY() + "' " +
  	   		",usuario_Stamp = '"+ed.getUsuario_Stamp() + "' " +
			"WHERE " +
			"oid_Pneu_Dimensao = " + ed.getOid_Pneu_Dimensao ();
			executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "altera (Pneu_DimensaoED ed)");
		}
	}

	public void delete (Pneu_DimensaoED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Pneus_Dimensoes " +
			"WHERE " +
			"oid_Pneu_Dimensao = " + ed.getOid_Pneu_Dimensao ();
			executaSQL.executarUpdate (sql);
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "delete (Pneu_DimensaoED ed)");
		}
	}

	public ArrayList<Pneu_DimensaoED> lista (Pneu_DimensaoED ed) throws Excecoes {
		ArrayList<Pneu_DimensaoED> list = new ArrayList <Pneu_DimensaoED>();
		try {
			sql = "SELECT * " +
	  		",pd.usuario_Stamp as usu_Stmp " +
	  		",pd.dt_Stamp as dt_Stmp " +
	  		",pd.dm_Stamp as dm_Stmp " +
			"FROM " +
			"Pneus_Dimensoes as pd " +
			"WHERE 1=1 ";
			if (ed.getOid_Pneu_Dimensao () > 0) 
				sql += " and pd.oid_Pneu_Dimensao = " + ed.getOid_Pneu_Dimensao ();
			else {
				if (doValida(ed.getCd_Pneu_Dimensao())) 
					sql += " and pd.cd_Pneu_Dimensao like '%" + ed.getCd_Pneu_Dimensao () + "%'";
				if (doValida(ed.getNm_Pneu_Dimensao())) 
					sql += " and pd.nm_Pneu_Dimensao like '%" + ed.getNm_Pneu_Dimensao () + "%'";
			}
			sql += " ORDER BY " +
			"pd.nm_Pneu_Dimensao";
			ResultSet rs = executaSQL.executarConsulta (sql);
			while (rs.next ()) {
				list.add (populaRegistro(rs));
			}
		}
		catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "lista (Pneu_DimensaoED ed)");
		}
		return list;
	}

	public Pneu_DimensaoED getByRecord(Pneu_DimensaoED ed) throws Excecoes {
		
		try {
			ArrayList<Pneu_DimensaoED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Pneu_DimensaoED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Pneu_DimensaoED ed)");
		}
		return new Pneu_DimensaoED();
	}

	public Pneu_DimensaoED populaRegistro(ResultSet res) throws SQLException {
		Pneu_DimensaoED ed = new Pneu_DimensaoED();
		ed.setOid_Pneu_Dimensao(res.getInt("oid_Pneu_Dimensao"));
		ed.setCd_Pneu_Dimensao(res.getString("cd_Pneu_Dimensao"));
		ed.setNm_Pneu_Dimensao(res.getString("nm_Pneu_Dimensao"));
		ed.setNr_Mm_Perimetro(res.getDouble("nr_Mm_Perimetro"));
	    ed.setUsuario_Stamp(res.getString("usu_Stmp"));
	    ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
	    return ed;	  
	}
}