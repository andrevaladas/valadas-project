package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Stib_Item_A_InspecionarED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial STIB - Itens a inspecionar
 * @serialData 03/2012
 */
public class Stib_Item_A_InspecionarBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Stib_Item_A_InspecionarBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Stib_Item_A_InspecionarED inclui(Stib_Item_A_InspecionarED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Stib_Itens_A_Inspecionar (" +
			"cd_Item_A_Inspecionar" +
			",nm_Item_A_Inspecionar " +
			",nm_Item_A_Inspecionar_E " +
			",nr_Ordem " +
			",dm_Tipo " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			" '" + ed.getCd_Item_A_Inspecionar() + "'"+	
			",'" + ed.getNm_Item_A_Inspecionar() + "'"+
			",'" + ed.getNm_Item_A_Inspecionar_E() + "'"+
			", " + ed.getNr_Ordem() +
			",'" + ed.getDm_Tipo() + "'"+
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Item_A_Inspecionar(getSeq("Stib_Itens_A_Inspecionar_oid_Item_A_Inspecionar_seq"));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stib_Itens_A_InspecionarED ed)");
		}
	}

	public void altera(Stib_Item_A_InspecionarED ed) throws Excecoes {
		try {
			sql = "UPDATE Stib_Itens_A_Inspecionar SET " +
			" cd_Item_A_Inspecionar = '" + ed.getCd_Item_A_Inspecionar() + "' " +
			",nm_Item_A_Inspecionar = '" + ed.getNm_Item_A_Inspecionar() + "' " +
			",nm_Item_A_Inspecionar_E = '" + ed.getNm_Item_A_Inspecionar_E() + "' " +
			",nr_Ordem = " + ed.getNr_Ordem() +
			",dm_Tipo = '" + ed.getDm_Tipo() + "' " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Item_A_Inspecionar = " + ed.getOid_Item_A_Inspecionar();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stib_Itens_A_InspecionarED ed)");
		}
	}
	
	public void delete(Stib_Item_A_InspecionarED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stib_Itens_A_Inspecionar " +
			"WHERE " +
			"oid_Item_A_Inspecionar = " + ed.getOid_Item_A_Inspecionar();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Stib_Itens_A_InspecionarED ed)");
		}
	}

	public ArrayList<Stib_Item_A_InspecionarED> lista(Stib_Item_A_InspecionarED ed) throws Excecoes {
		ArrayList<Stib_Item_A_InspecionarED> list = new ArrayList<Stib_Item_A_InspecionarED>();
		try {
			sql = "SELECT * " +
			",usuario_Stamp as usu_Stmp " +
	  		",dt_Stamp as dt_Stmp " +
	  		",dm_Stamp as dm_Stmp " +
			"FROM Stib_Itens_A_Inspecionar as bd " +
			"WHERE " +
			"1 = 1 " ;
			if (ed.getOid_Item_A_Inspecionar()>0) 
				sql += " and oid_Item_A_Inspecionar = " + ed.getOid_Item_A_Inspecionar() + " ";
			else {
				if (doValida(ed.getCd_Item_A_Inspecionar()))
					sql += " and cd_Item_A_Inspecionar = '" + ed.getCd_Item_A_Inspecionar() + "' "   ;
				if (doValida(ed.getNm_Item_A_Inspecionar()))
					sql += " and nm_Item_A_Inspecionar  like '%" + ed.getNm_Item_A_Inspecionar() + "%' "   ;
				if (doValida(ed.getDm_Tipo())) {
					if (!"T".equals(ed.getDm_Tipo()))
						sql += " and dm_Tipo = '" + ed.getDm_Tipo() + "' "   ;
				}
			}
			sql += "ORDER BY " +
			"nr_Ordem ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stib_Itens_A_InspecionarED ed)");
		}
	}

	public Stib_Item_A_InspecionarED getByRecord(Stib_Item_A_InspecionarED ed) throws Excecoes {
		try {
			ArrayList<Stib_Item_A_InspecionarED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Stib_Item_A_InspecionarED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stib_Itens_A_InspecionarED ed)");
		}
		return new Stib_Item_A_InspecionarED();
	}
	
	private Stib_Item_A_InspecionarED populaRegistro(ResultSet res) throws SQLException {
		Stib_Item_A_InspecionarED ed = new Stib_Item_A_InspecionarED();
		ed.setOid_Item_A_Inspecionar(res.getInt("oid_Item_A_Inspecionar"));
		ed.setCd_Item_A_Inspecionar(res.getString("cd_Item_A_Inspecionar"));
		ed.setNm_Item_A_Inspecionar(res.getString("nm_Item_A_Inspecionar"));
		ed.setNm_Item_A_Inspecionar_E(res.getString("nm_Item_A_Inspecionar_E"));
		ed.setNr_Ordem(res.getInt("nr_Ordem"));
		ed.setDm_Tipo(res.getString("dm_Tipo"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
