package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Stib_Inspecao_ItemED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial STIB - Itens inspecionados
 * @serialData 04/2012
 */
public class Stib_Inspecao_ItemBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Stib_Inspecao_ItemBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Stib_Inspecao_ItemED inclui(Stib_Inspecao_ItemED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Stib_Inspecoes_Itens (" +
			"oid_Inspecao " +
			",nr_Ordem " +
			",dm_Tipo " +
			",nm_Item_Inspecionado " +
			",dm_Situacao " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			"  " + ed.getOid_Inspecao()+	
			", " + ed.getNr_Ordem() + 
			",'" + ed.getDm_Tipo() + "' "+
			",'" + ed.getNm_Item_Inspecionado()+"' "+
			", " + ed.isDm_Situacao() +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Inspecao_Item(getSeq("Stib_Inspecoes_Itens_oid_Inspecao_Item_seq"));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stib_Inspecao_ItemED ed)");
		}
	}

	public void altera(Stib_Inspecao_ItemED ed) throws Excecoes {
		try {
			sql = "UPDATE Stib_Inspecoes_Itens SET " +
			" dm_Situacao = " + ed.isDm_Situacao() +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Inspecao_Item = " + ed.getOid_Inspecao_Item();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stib_Inspecao_ItemED ed)");
		}
	}
	
	public void delete(Stib_Inspecao_ItemED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stib_Inspecoes_Itens " +
			"WHERE " +
			"oid_Inspecao = " + ed.getOid_Inspecao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Stib_Inspecao_ItemED ed)");
		}
	}

	public ArrayList<Stib_Inspecao_ItemED> lista(Stib_Inspecao_ItemED ed) throws Excecoes {
		ArrayList<Stib_Inspecao_ItemED> list = new ArrayList<Stib_Inspecao_ItemED>();
		try {
			sql = "SELECT * " +
			",usuario_Stamp as usu_Stmp " +
	  		",dt_Stamp as dt_Stmp " +
	  		",dm_Stamp as dm_Stmp " +
			"FROM Stib_Inspecoes_Itens as ii " +
			"WHERE " +
			"1 = 1 " ;
			if (ed.getOid_Inspecao()>0) 
				sql += " and ii.oid_Inspecao = " + ed.getOid_Inspecao() + " ";
			sql += "ORDER BY " +
			"ii.nr_Ordem ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res,ed.getDm_Lingua()));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stib_Inspecao_ItemED ed)");
		}
	}
	
	public Stib_Inspecao_ItemED getByRecord(Stib_Inspecao_ItemED ed) throws Excecoes {
		try {
			ArrayList<Stib_Inspecao_ItemED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Stib_Inspecao_ItemED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stib_Inspecao_ItemED ed)");
		}
		return new Stib_Inspecao_ItemED();
	}
	
	private Stib_Inspecao_ItemED populaRegistro(ResultSet res,String dmLingua) throws SQLException {
		Stib_Inspecao_ItemED ed = new Stib_Inspecao_ItemED();
		ed.setDm_Lingua(dmLingua);
		ed.setOid_Inspecao_Item(res.getLong("oid_Inspecao_Item"));
		ed.setOid_Inspecao(res.getLong("oid_Inspecao"));
		ed.setNr_Ordem(res.getInt("nr_Ordem"));
		ed.setNm_Item_Inspecionado(res.getString("nm_Item_Inspecionado"));
		ed.setDm_Tipo(res.getString("dm_Tipo"));
		ed.setDm_Situacao(res.getBoolean("dm_Situacao"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
