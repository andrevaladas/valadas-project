package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Stas_Dimensao_SucateadoED;
import com.master.ed.Stas_Pneu_SucateadoED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial STAS - Dimensao Sucateado
 * @serialData 05/2012
 */
public class Stas_Dimensao_SucateadoBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Stas_Dimensao_SucateadoBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Stas_Dimensao_SucateadoED inclui(Stas_Dimensao_SucateadoED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Stas_Dimensoes_Sucateados (" +
			"oid_Analise" +
			",oid_Pneu_Dimensao " +
			",nr_Twi " +
			",vl_Pneu_Novo " +
			",vl_Recapagem " +
			",vl_Carcaca_R1 " +
			",vl_Carcaca_R2 " +
			",vl_Carcaca_R3 " +
			",vl_Carcaca_R4 " +
			",vl_Carcaca_R5 " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			"  " + ed.getOid_Analise() + 	
			", " + ed.getOid_Pneu_Dimensao() + 
			", " + ed.getNr_Twi() +
			", " + ed.getVl_Pneu_Novo() +
			", " + ed.getVl_Recapagem() +
			", " + ed.getVl_Carcaca_R1() +
			", " + ed.getVl_Carcaca_R2() +
			", " + ed.getVl_Carcaca_R3() +
			", " + ed.getVl_Carcaca_R4() +
			", " + ed.getVl_Carcaca_R5() +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Dimensao_Sucateado(getSeq("Stas_Dimensoes_Sucateados_Oid_Dimensao_Sucateado_seq"));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stas_Dimensao_SucateadoED ed)");
		}
	}

	public boolean verificaDimensaoNaAnalise(Stas_Pneu_SucateadoED ed) throws Excecoes {
		try {
			Stas_Dimensao_SucateadoED dsED = new Stas_Dimensao_SucateadoED();
			dsED.setOid_Analise(ed.getOid_Analise());
			dsED.setOid_Pneu_Dimensao(ed.getOid_Pneu_Dimensao());
			if (this.getByRecord(dsED).getOid_Dimensao_Sucateado()==0) {
				this.inclui(dsED);
				return true;
			} else{
				return false;
			}	
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"verificaDimensaoNaAnalise(Stas_Pneu_SucateadoED ed)");
		}
	}
	
	public void altera(Stas_Dimensao_SucateadoED ed) throws Excecoes {
		try {
			sql = "UPDATE Stas_Dimensoes_Sucateados SET " +
			" nr_Twi = " + ed.getNr_Twi() +
			",vl_Pneu_Novo = " + ed.getVl_Pneu_Novo() +
			",vl_Recapagem = " + ed.getVl_Recapagem() +
			",vl_Carcaca_R1 = " + ed.getVl_Carcaca_R1() +
			",vl_Carcaca_R2 = " + ed.getVl_Carcaca_R2() +
			",vl_Carcaca_R3 = " + ed.getVl_Carcaca_R3() +
			",vl_Carcaca_R4 = " + ed.getVl_Carcaca_R4() +
			",vl_Carcaca_R5 = " + ed.getVl_Carcaca_R5() +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Dimensao_Sucateado = " + ed.getOid_Dimensao_Sucateado();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stas_Dimensao_SucateadoED ed)");
		}
	}
	
	public void delete(Stas_Dimensao_SucateadoED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stas_Dimensoes_Sucateados " +
			"WHERE " +
			"oid_Dimensao_Sucateado = " + ed.getOid_Dimensao_Sucateado();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Stas_Dimensao_SucateadoED ed)");
		}
	}

	public ArrayList<Stas_Dimensao_SucateadoED> lista(Stas_Dimensao_SucateadoED ed) throws Excecoes {
		ArrayList<Stas_Dimensao_SucateadoED> list = new ArrayList<Stas_Dimensao_SucateadoED>();
		try {
			sql = "SELECT " +
			" * " +
			",pd.nm_Pneu_Dimensao " +
			",ds.usuario_Stamp as usu_Stmp " +
	  		",ds.dt_Stamp as dt_Stmp " +
	  		",ds.dm_Stamp as dm_Stmp " +
			"FROM " +
			"Stas_Dimensoes_Sucateados as ds " +
			"left join Pneus_Dimensoes as pd on (ds.oid_Pneu_Dimensao = pd.oid_Pneu_Dimensao) " +
			"WHERE " +
			"1=1 " ;
			if (ed.getOid_Dimensao_Sucateado()>0) 
				sql += " and ds.oid_Dimensao_Sucateado = " + ed.getOid_Dimensao_Sucateado() + " ";
			else {
				if (ed.getOid_Analise()>0) 
					sql += " and ds.oid_Analise = " + ed.getOid_Analise() + " ";
				if (ed.getOid_Pneu_Dimensao()>0) 
					sql += " and ds.oid_Pneu_Dimensao = " + ed.getOid_Pneu_Dimensao() + " ";
			}
			sql += "ORDER BY " ;
			sql += "pd.nm_Pneu_Dimensao ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stas_Dimensao_SucateadoED ed)");
		}
	}

	public Stas_Dimensao_SucateadoED getByRecord(Stas_Dimensao_SucateadoED ed) throws Excecoes {
		try {
			ArrayList<Stas_Dimensao_SucateadoED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Stas_Dimensao_SucateadoED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stas_Dimensao_SucateadoED ed)");
		}
		return new Stas_Dimensao_SucateadoED();
	}
	
	private Stas_Dimensao_SucateadoED populaRegistro(ResultSet res) throws SQLException {
		Stas_Dimensao_SucateadoED ed = new Stas_Dimensao_SucateadoED();
		ed.setOid_Dimensao_Sucateado(res.getLong("oid_Dimensao_Sucateado"));
		ed.setOid_Analise(res.getLong("oid_Analise"));
		ed.setOid_Pneu_Dimensao(res.getLong("oid_Pneu_Dimensao"));
		ed.setNr_Twi(res.getDouble("nr_Twi"));
		ed.setNm_Pneu_Dimensao(res.getString("nm_Pneu_Dimensao"));
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

