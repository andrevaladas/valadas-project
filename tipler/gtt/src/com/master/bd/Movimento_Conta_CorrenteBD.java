package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Movimento_Conta_CorrenteED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Movimentos_Contas_Correntes
 * @serialData 07/2009
 */
/**
 * @author Regis
 *
 */
public class Movimento_Conta_CorrenteBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Movimento_Conta_CorrenteBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Movimento_Conta_CorrenteED inclui(Movimento_Conta_CorrenteED ed) throws Excecoes {
		try {
			//ed.setOid_Movimento_Conta_Corrente(getAutoIncremento("oid_Movimento_Conta_Corrente", "Movimentos_Contas_Correntes"));
			sql = "INSERT INTO Movimentos_Contas_Correntes (" +
			//"oid_Movimento_Conta_Corrente" +
			"oid_Indenizacao" +
			",oid_Concessionaria " +
			",oid_Usuario_Desbloqueio " +
			",dt_Movimento_Conta_Corrente " +
			",dt_Desbloqueio " +
			",dm_Debito_Credito  " +
			",dm_Tipo_Movimento  " +
			",dm_Bloqueado " +
			",tx_Bloqueio " +
			",tx_Descricao " +
			",nr_Fatura_Tipler" +
			",vl_Movimento_Conta_Corrente " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			//" " + ed.getOid_Movimento_Conta_Corrente() +", " +
			ed.getOid_Indenizacao() +
			"," + ed.getOid_Concessionaria() +
			"," + ed.getOid_Usuario_Desbloqueio() +
			",'" + ed.getDt_Movimento_Conta_Corrente() + "' " + 
			"," + (doValida(ed.getDt_Desbloqueio()) ?  "'" +ed.getDt_Desbloqueio() + "'" : null) +
			",'" + ed.getDm_Debito_Credito() + "' " +
			",'" + ed.getDm_Tipo_Movimento() + "' " +
			",'" + ed.getDm_Bloqueado() + "' " +
			"," + (doValida(ed.getTx_Bloqueio()) ?  "'" +ed.getTx_Bloqueio() + "'" : null) + 
			"," + (doValida(ed.getTx_Descricao()) ?  "'" +ed.getTx_Descricao() + "'" : null) +
			", " + ed.getNr_Fatura_Tipler()  +
			", " + ed.getVl_Movimento_Conta_Corrente()  +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Movimento_Conta_Corrente(getSeq("Movimentos_Contas_Correntes_oid_Movimento_Conta_Corrente_seq"));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Movimento_Conta_CorrenteED ed)");
		}
	}

	public void altera(Movimento_Conta_CorrenteED ed) throws Excecoes {
		try {
			sql = "UPDATE Movimentos_Contas_Correntes SET " +
			"dm_Bloqueado = 'N' " +
			",dt_Desbloqueio = " + (doValida(ed.getDt_Desbloqueio()) ?  "'" +ed.getDt_Desbloqueio() + "'" : null) +
			",oid_usuario_desbloqueio = " + ed.getOid_Usuario_Desbloqueio() +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Movimento_Conta_Corrente = " + ed.getOid_Movimento_Conta_Corrente();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Movimento_Conta_CorrenteED ed)");
		}
	}

	public void alteraOidDebito(Movimento_Conta_CorrenteED ed) throws Excecoes {
		try {
			sql = "UPDATE Movimentos_Contas_Correntes SET " +
			"oid_Movimento_Conta_Corrente_Debito = " + ed.getOid_Movimento_Conta_Corrente_Debito() + " " +
			"WHERE " +
			"oid_Movimento_Conta_Corrente = " + ed.getOid_Movimento_Conta_Corrente();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"alteraOidDebito(Movimento_Conta_CorrenteED ed)");
		}
	}
	
	public void descontaCreditos(Movimento_Conta_CorrenteED ed) throws Excecoes {
		try {
			sql = "UPDATE Movimentos_Contas_Correntes SET " +
			"oid_Movimento_Conta_Corrente_Debito = " + ed.getOid_Movimento_Conta_Corrente_Debito() + " " +
			",nr_Fatura_Tipler = " + ed.getNr_Fatura_Tipler() + " " +
			"WHERE " +
			"oid_Movimento_Conta_Corrente in (" + ed.getOids()+")";
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"alteraOidDebito(Movimento_Conta_CorrenteED ed)");
		}
	}
	public void delete(Movimento_Conta_CorrenteED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Movimentos_Contas_Correntes " +
			"WHERE " +
			"oid_Indenizacao = " + ed.getOid_Indenizacao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Movimento_Conta_CorrenteED ed)");
		}
	}

	public ArrayList<Movimento_Conta_CorrenteED> lista(Movimento_Conta_CorrenteED ed) throws Excecoes {
		ArrayList<Movimento_Conta_CorrenteED> list = new ArrayList<Movimento_Conta_CorrenteED>();
		try {
			sql = "SELECT " +
			"mcc.* " +
			",con.nm_Razao_Social as nm_Concessionaria"+
			",ind.dm_aprovacao as inddm_Aprovacao"+
			",ind.dt_aprovacao as inddt_Aprovacao"+ 
			",usu.nm_usuario   as ununm_usuario"+
	  		",mcc.usuario_Stamp as usu_Stmp " +
	  		",mcc.dt_Stamp as dt_Stmp " +
	  		",mcc.dm_Stamp as dm_Stmp " +
			"FROM " +
			"Movimentos_Contas_Correntes as mcc " +
			"left join Empresas as con on con.oid_empresa = mcc.oid_concessionaria " +
			"left join indenizacoes as ind  on ind.oid_indenizacao = mcc.oid_indenizacao " +
			"left join usuarios as usu on usu.oid_usuario = ind.oid_usuario_tecnico " +
			"WHERE " +	
			"1 = 1 " ;
			if (ed.getOid_Movimento_Conta_Corrente()>0) 
				sql += "and mcc.oid_Movimento_Conta_Corrente = " + ed.getOid_Movimento_Conta_Corrente();
			else { 
				if (ed.getOid_Indenizacao()>0) 
					sql += "and mcc.oid_Indenizacao = " + ed.getOid_Indenizacao();
				if (ed.getOid_Concessionaria()>0) 
					sql += " and mcc.oid_Concessionaria = " + ed.getOid_Concessionaria() + " ";
				if (doValida(ed.getDm_Debito_Credito())) 
					sql += " and mcc.dm_Debito_Credito = '" + ed.getDm_Debito_Credito() + "' ";
				if (doValida(ed.getDm_Tipo_Movimento())) 
					sql += " and mcc.dm_Tipo_Movimento = '" + ed.getDm_Tipo_Movimento() + "' ";
				if (doValida(ed.getDm_Bloqueado())) 
					sql += " and mcc.dm_Bloqueado = '" + ed.getDm_Bloqueado() + "' ";
				if (ed.getOid_Movimento_Conta_Corrente_Debito()>0) 
					sql += "and mcc.oid_movimento_conta_corrente_debito = " + ed.getOid_Movimento_Conta_Corrente_Debito();
				if (doValida(ed.getDm_Descontado())) {
					if ("S".equals(ed.getDm_Descontado())) 
						sql += " and mcc.oid_movimento_conta_corrente_debito is not null ";
					if ("N".equals(ed.getDm_Descontado())) 
						sql += " and mcc.oid_movimento_conta_corrente_debito is null ";
				}
				if (ed.getNr_Fatura_Tipler()>0) 
					sql += "and mcc.nr_fatura_tipler = " + ed.getNr_Fatura_Tipler();
				if (doValida(ed.getDt_Movimento_Conta_Corrente_Inicial()))
					sql += " and mcc.dt_Movimento_Conta_Corrente >= '" + ed.getDt_Movimento_Conta_Corrente_Inicial() + "'";
				if (doValida(ed.getDt_Movimento_Conta_Corrente_Final()))
					sql += " and mcc.dt_Movimento_Conta_Corrente <= '" + ed.getDt_Movimento_Conta_Corrente_Final() + "'";
				
			}
			sql += "ORDER BY " +
			"con.nm_Razao_Social, mcc.dt_Movimento_Conta_Corrente, mcc.dm_Debito_Credito ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Movimento_Conta_CorrenteED ed)");
		}
	}
	
	/**
	 * @param ed ed com as restrições
	 * @return valor do saldo do periodo informadol
	 * @throws Excecoes
	 */
	public double getSaldoHistorico(Movimento_Conta_CorrenteED ed) throws Excecoes {

		double vl_Saldo=0;
		try {
			sql="SELECT " +
				" sum ( case when dm_debito_credito='C' then vl_movimento_conta_corrente else 0 end ) - " +
				" sum ( case when dm_debito_credito='D' then vl_movimento_conta_corrente else 0 end ) as vl_Saldo " +
				"FROM " +
				"Movimentos_Contas_Correntes as mcc " +
				"WHERE " +	
				" mcc.oid_Concessionaria = " + ed.getOid_Concessionaria() ;
			if ( doValida(ed.getDm_Bloqueado()) )
				sql+=" and mcc.dm_Bloqueado <> 'S' ";
			if ( doValida(ed.getDt_Movimento_Conta_Corrente()))
				sql += "and dt_movimento_conta_corrente < '"+ed.getDt_Movimento_Conta_Corrente()+"' "; 
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				vl_Saldo=res.getDouble("vl_Saldo");
			}
			return vl_Saldo;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getSaldoHistorico(Movimento_Conta_CorrenteED ed)");
		}
	}

	public ArrayList<Movimento_Conta_CorrenteED> listaSaldoMes(Movimento_Conta_CorrenteED ed) throws Excecoes {
		ArrayList<Movimento_Conta_CorrenteED> list = new ArrayList<Movimento_Conta_CorrenteED>();
		try {
			sql = "SELECT " +
			"reg.nm_regional, " +
			"con.nm_razao_social, " +
			"sum ( case when dm_debito_credito='C' then vl_movimento_conta_corrente else 0 end ) -  " +
			"sum ( case when dm_debito_credito='D' then vl_movimento_conta_corrente else 0 end ) as vl_Saldo " +
			"FROM " +
			"Movimentos_Contas_Correntes as mcc " +
			"left join empresas as con on con.oid_empresa = mcc.oid_concessionaria " +
			"left join regionais as reg on reg.oid_regional = con.oid_regional " +
			"WHERE " ;
			if (ed.getOid_Regional()>0) {
				sql+=" reg.oid_regional = " + ed.getOid_Regional() + " and ";
			}
			sql+="mcc.dt_Movimento_Conta_Corrente <= '" + ed.getDt_Movimento_Conta_Corrente() + "'";
			sql += "GROUP BY " +
			"reg.nm_regional, " +
			"con.nm_Razao_Social";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				Movimento_Conta_CorrenteED mcED = new Movimento_Conta_CorrenteED();
				mcED.setNm_Regional(res.getString("nm_regional"));
				mcED.setNm_Concessionaria(res.getString("nm_razao_social"));
				mcED.setVl_Saldo(res.getDouble("vl_Saldo"));
				if (mcED.getVl_Saldo()>0)
					mcED.setDm_Debito_Credito("C");
				else
					mcED.setDm_Debito_Credito("D");	
				list.add(mcED);
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Movimento_Conta_CorrenteED ed)");
		}
	}

	public Movimento_Conta_CorrenteED getByRecord(Movimento_Conta_CorrenteED ed) throws Excecoes {
		try {
			ArrayList<Movimento_Conta_CorrenteED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Movimento_Conta_CorrenteED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Movimento_Conta_CorrenteED ed)");
		}
		return new Movimento_Conta_CorrenteED();
	}
	
	private Movimento_Conta_CorrenteED populaRegistro(ResultSet res) throws SQLException {
		Movimento_Conta_CorrenteED ed = new Movimento_Conta_CorrenteED();
		ed.setOid_Movimento_Conta_Corrente(res.getInt("oid_Movimento_Conta_Corrente"));
		ed.setOid_Indenizacao(res.getInt("oid_Indenizacao"));
		ed.setOid_Concessionaria(res.getInt("oid_Concessionaria"));
		ed.setOid_Usuario_Desbloqueio(res.getInt("oid_Usuario_Desbloqueio"));
		ed.setDt_Movimento_Conta_Corrente(FormataData.formataDataBT(res.getString("dt_Movimento_Conta_Corrente")));
		ed.setDm_Debito_Credito(res.getString("dm_Debito_Credito"));
		ed.setDm_Tipo_Movimento(res.getString("dm_Tipo_Movimento"));
		ed.setDm_Bloqueado(res.getString("dm_Bloqueado"));
		ed.setTx_Bloqueio(res.getString("tx_Bloqueio"));
		ed.setTx_Descricao(res.getString("tx_Descricao"));
		ed.setNr_Fatura_Tipler(res.getInt("nr_Fatura_Tipler"));
		ed.setVl_Movimento_Conta_Corrente(res.getDouble("vl_Movimento_Conta_Corrente"));
		ed.setOid_Movimento_Conta_Corrente_Debito(res.getInt("oid_Movimento_Conta_Corrente_Debito"));
		ed.setNm_Concessionaria(res.getString("nm_Concessionaria"));
		ed.setDt_Aprovacao(FormataData.formataDataBT(res.getString("inddt_Aprovacao")));
		ed.setDm_Aprovacao(doValida(res.getString("inddm_Aprovacao"))?res.getString("inddm_Aprovacao"):"");
		ed.setNm_Usuario_Tecnico(doValida(res.getString("ununm_usuario"))?res.getString("ununm_usuario"):"");
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}
}
