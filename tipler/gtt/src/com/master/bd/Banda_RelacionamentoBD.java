package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Banda_RelacionamentoED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Bandas_Dimensoes
 * @serialData 01/2010
 */
public class Banda_RelacionamentoBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Banda_RelacionamentoBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Banda_RelacionamentoED inclui(Banda_RelacionamentoED ed) throws Excecoes {
		try {
			//ed.setOid_Banda(getAutoIncremento("oid_Banda", "Bandas"));
			sql = "INSERT INTO Bandas_Relacionamentos (" +
			"oid_Banda_1" +
			",oid_Banda_2 " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			ed.getOid_Banda_1() +	
			", " + ed.getOid_Banda_2() +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			
			ed.setOid_Banda_Relacionamento(getSeq("Bandas_Relacionamentos_oid_Banda_Relacionamento_seq"));
			
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Banda_RelacionamentoED ed)");
		}
	}


	public void delete(ArrayList lista) throws Excecoes {
		try {
			
			for (int i=0; i<lista.size(); i++){
				Banda_RelacionamentoED bndED = new Banda_RelacionamentoED();;
				bndED = (Banda_RelacionamentoED)lista.get(i);
				sql="DELETE " +
					"FROM " +
					"Bandas_Relacionamentos " +
					"WHERE " +
					"oid_Banda_Relacionamento = " + bndED.getOid_Banda_Relacionamento();
				executasql.executarUpdate(sql);
			}

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Banda_RelacionamentoED ed)");
		}
	}

	public ArrayList getRelacionamentos(Banda_RelacionamentoED ed) throws Excecoes {
		ArrayList list = new ArrayList();
		Banda_RelacionamentoED bdnRBD = new Banda_RelacionamentoED();
		try {
			sql="Select * " +
				"from " +
				"Bandas_Relacionamentos as rb " +
				"Where " +
				"(rb.oid_Banda_1 = "+ed.getOid_Banda_1()+ " and rb.oid_Banda_2 = "+ed.getOid_Banda_2() + ") or " +
				"(rb.oid_Banda_1 = "+ed.getOid_Banda_2()+ " and rb.oid_Banda_2 = "+ed.getOid_Banda_1() + ") " +
				"Order By " +
				"oid_Banda_Relacionamento " ;
			ResultSet res = this.executasql.executarConsulta(sql);
			if (res.next()) {
				bdnRBD.setOid_Banda_Relacionamento(res.getInt("oid_Banda_Relacionamento"));
				bdnRBD.setOid_Banda_1(res.getInt("oid_Banda_1"));
				bdnRBD.setOid_Banda_2(res.getInt("oid_Banda_2"));
				list.add(bdnRBD);
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Banda_RelacionamentoED ed)");
		}
	}

	public ArrayList lista(Banda_RelacionamentoED ed) throws Excecoes {

		ArrayList list = new ArrayList();
		try {
			sql="select distinct " +
				"b.oid_banda as oid_banda ," +
				"b.nm_banda  as nm_banda," +
				"fb.nm_fabricante_banda as nm_fabricante_banda " +
				"from " +
				"bandas_relacionamentos as rb " +
				"left join bandas as b on rb.oid_banda_2 = b.oid_banda " +
				"left join fabricantes_bandas as fb on fb.oid_fabricante_banda = b.oid_fabricante_banda " +
				"where " +
				"rb.oid_banda_1 = "+ed.getOid_Banda_1()+ 
				"union " +
				"select distinct " +
				"b.oid_banda as oid_banda , " +
				"b.nm_banda  as nm_banda, " +
				"fb.nm_fabricante_banda as nm_fabricante_banda " +
				"from " +
				"bandas_relacionamentos as rb left " +
				"join bandas as b on rb.oid_banda_1 = b.oid_banda " +
				"left join fabricantes_bandas as fb on fb.oid_fabricante_banda = b.oid_fabricante_banda " +
				"where " +
				"rb.oid_banda_2 = "+ed.getOid_Banda_1()+
				"order by " +
				"nm_fabricante_banda , nm_banda " ;
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro1(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Banda_RelacionamentoED ed)");
		}
	}

	private Banda_RelacionamentoED populaRegistro1(ResultSet res) throws SQLException {
		Banda_RelacionamentoED ed = new Banda_RelacionamentoED();
		//ed.setOid_Banda_Relacionamento(res.getInt("oid_Banda_Relacionamento"));
		//ed.setOid_Banda_1(res.getInt("oid_Banda_1"));
		ed.setOid_Banda_2(res.getInt("oid_Banda"));
		//ed.setNm_Banda_1(res.getString("nm_Banda_1"));
		ed.setNm_Banda_2(res.getString("nm_Banda"));
		//ed.setNm_Fabricante_Banda_1(res.getString("nm_Fabricante_Banda_1"));
		ed.setNm_Fabricante_Banda_2(res.getString("nm_Fabricante_Banda"));
		//ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		//ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
