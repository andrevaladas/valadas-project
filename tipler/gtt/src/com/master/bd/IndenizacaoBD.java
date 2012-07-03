package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.IndenizacaoED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Indenização
 * @serialData 06/2009
 */
public class IndenizacaoBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public IndenizacaoBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public IndenizacaoED inclui(IndenizacaoED ed) throws Excecoes {
		try {
			//ed.setOid_Indenizacao(getAutoIncremento("oid_Indenizacao", "Indenizacoes"));
			sql = "INSERT INTO Indenizacoes (" +
			//"oid_Indenizacao" +
			"oid_empresa " +
			",oid_recapagem_garantida " +
			",dt_indenizacao " +
			",hr_indenizacao " +
			",dm_dano_acidente  " +
			",dm_excesso_carga " +
			",dm_baixa_alta_pressao " +
			",dm_fora_aplicacao_recomendada " +
			",dm_montagem_desmontagem_inadequada " +
			",dm_mau_estado " +
			",dm_tubless_com_camara " +
			",dm_substancia_quimica " +
			",dm_perda_total " +
			",nr_mm " +
			",nr_perc_perda_total" +
			",nr_perc_reforma " +
			",nr_perc_carcaca " +
			",vl_indenizacao " +
			",dm_aceita_por_parametro " +
			",tx_Laudo " +
			",dm_rejeicao " ;
			if("ET".equals(ed.getDm_Rejeicao()) ) {
				sql+=",dt_registro_recapagem " ;
				sql+=",nr_Dias_Perda_Total " ;
			}
			if("ED".equals(ed.getDm_Rejeicao()) )
				sql+=",nr_Dot " ;
			if("DE".equals(ed.getDm_Rejeicao()) )
				sql+=",nr_Perc_Desgaste " ;
			sql+=",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			//" " + ed.getOid_Indenizacao() +	"," + 
			ed.getOid_Empresa() +
			"," + ed.getOid_Recapagem_Garantida() +
			",'" + ed.getDt_Indenizacao() + "' " + 
			",'" + ed.getHr_Indenizacao() + "' " +
			",'" + ed.getDm_Dano_Acidente() + "' " +
			",'" + ed.getDm_Excesso_Carga() + "' " +
			",'" + ed.getDm_Baixa_Alta_Pressao() + "' " +
			",'" + ed.getDm_Fora_Aplicacao_Recomendada() + "' " +
			",'" + ed.getDm_Montagem_Desmontagem_Inadequada() + "' " +
			",'" + ed.getDm_Mau_Estado() + "' " +
			",'" + ed.getDm_Tubless_Com_Camara() + "' " +
			",'" + ed.getDm_Substancia_Quimica() + "' " +
			",'" + ed.getDm_Perda_Total() + "' " +
			", " + ed.getNr_MM()  +
			", " + ed.getNr_Perc_Perda_Total()  +
			", " + ed.getNr_Perc_Reforma()  +
			", " + ed.getNr_Perc_Carcaca()  +
			", " + ed.getVl_Indenizacao()  +
			",'" + ed.getDm_Aceita_Por_Parametro() + "' " +
			"," + (doValida(ed.getTx_Laudo()) ?  "'" +ed.getTx_Laudo() + "'" : null) +
			"," + (doValida(ed.getDm_Rejeicao()) ?  "'" +ed.getDm_Rejeicao() + "'" : null) ;
			if("ET".equals(ed.getDm_Rejeicao()) ) {
				sql+=",'" + ed.getDt_Registro_Recapagem() + "' " ;
				sql+=", " + ed.getNr_Dias_Perda_Total() ;
			}
			if("ED".equals(ed.getDm_Rejeicao()) )
				sql+=",'" + ed.getNr_Dot() + "' " ;
			if("DE".equals(ed.getDm_Rejeicao()) )
				sql+=", " + ed.getNr_Perc_Desgaste() ;
			sql+=",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Indenizacao(getSeq("Indenizacoes_oid_Indenizacao_seq"));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(IndenizacaoED ed)");
		}
	}

	public void altera(IndenizacaoED ed) throws Excecoes {
		try {
			sql = "UPDATE Indenizacoes SET " +
			"dm_aprovacao = '" + ed.getDm_Aprovacao() + "' " +
			",dt_aprovacao = '" + ed.getDt_Aprovacao() + "' " +
			",hr_aprovacao = '" + ed.getHr_Aprovacao() + "' " +
			",oid_usuario_tecnico = " + ed.getOid_Usuario_Tecnico() +
			",tx_aprovacao_motivo = " + (doValida(ed.getTx_Aprovacao_Motivo())?"'"+ed.getTx_Aprovacao_Motivo()+ "'":null)  +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Indenizacao = " + ed.getOid_Indenizacao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(IndenizacaoED ed)");
		}
	}

	public void delete(IndenizacaoED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Indenizacoes " +
			"WHERE " +
			"oid_Indenizacao = " + ed.getOid_Indenizacao();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(IndenizacaoED ed)");
		}
	}

	public ArrayList<IndenizacaoED> lista(IndenizacaoED ed) throws Excecoes {

		ArrayList<IndenizacaoED> list = new ArrayList<IndenizacaoED>();
		try {
			sql = "SELECT " +
			"ind.* "       +
			",rga.dt_registro " +
			",rga.dt_validade_Garantia " +
			",rga.nr_Fogo " +
			",rga.dm_tipo_pneu" +
			",rga.nr_nota_fiscal" +
			",rga.vl_servico" +
			",mop.nm_modelo_pneu" +
			",fap.nm_fabricante_pneu" +
			",bdn.nm_banda " +
			",bdn.nr_largura" +
			",bdn.nr_profundidade" +
			",emp.nm_razao_social as nm_Concessionaria"+
			",reg.nm_Regional as nm_Regional" +
			",usu.nm_Usuario as nm_Usuario_Tecnico"+
			",cli.nm_razao_social as nm_Cliente"+
	  		",ind.usuario_Stamp as usu_Stmp " +
	  		",ind.dt_Stamp as dt_Stmp " +
	  		",ind.dm_Stamp as dm_Stmp " +
			"FROM " +
			"Indenizacoes as ind " +
			"left join Recapagens_Garantidas as rga on rga.oid_Recapagem_Garantida = ind.oid_Recapagem_Garantida " +
			"left join Modelos_Pneus as mop on mop.oid_Modelo_Pneu = rga.oid_Modelo_Pneu " +
			"left join Bandas as bdn on bdn.oid_Banda = rga.oid_Banda " +
			"left join Fabricantes_Pneus as fap on fap.oid_Fabricante_Pneu = mop.oid_Fabricante_Pneu " +
			"left join Empresas as emp on ind.oid_Empresa = emp.oid_Empresa " +
			"left join Usuarios as usu on ind.oid_Usuario_Tecnico = usu.oid_Usuario " +
			"left join Empresas as cli on cli.oid_Empresa = rga.oid_cliente " +
			"left join Regionais as reg on reg.oid_Regional = emp.oid_Regional " +
			"WHERE " +
			"1 = 1 " ;
			if (ed.getOid_Indenizacao()>0) 
				sql+="and ind.oid_Indenizacao = " + ed.getOid_Indenizacao();
			else  {
				if (ed.getOid_Recapagem_Garantida()>0)
					sql+=" and ind.oid_Recapagem_Garantida = " + ed.getOid_Recapagem_Garantida()  ;
				else {
					if (ed.getOid_Empresa()>0)
						sql+=" and ind.oid_empresa = " + ed.getOid_Empresa() + " ";
					if (ed.getOid_Usuario_Tecnico()>0)
						sql+=" and ind.oid_Usuario_Tecnico = " + ed.getOid_Usuario_Tecnico();
					if (doValida(ed.getDm_Aprovacao()) ) {
						if ("I".equals(ed.getDm_Aprovacao())) { 
							sql+=" and ind.dm_aprovacao is null " +
								 " and ind.dm_rejeicao is null ";
						} else {
							if (!"T".equals(ed.getDm_Aprovacao())) {
								sql+=" and ind.dm_aprovacao = '" + ed.getDm_Aprovacao() + "' ";
							}	
						}
					}
					if (doValida(ed.getDt_Aprovacao_Inicial()))
						sql += " and ind.dt_Aprovacao >= '" + ed.getDt_Aprovacao_Inicial() + "'";
					if (doValida(ed.getDt_Aprovacao_Final()))
						sql += " and ind.dt_Aprovacao <= '" + ed.getDt_Aprovacao_Final() + "'";
					if (ed.getOid_Regional()>0)
						sql+=" and emp.Oid_Regional = " + ed.getOid_Regional() + " ";
					if (doValida(ed.getDt_Indenizacao_Inicial()))
						sql += " and ind.dt_Indenizacao >= '" + ed.getDt_Indenizacao_Inicial() + "'";
					if (doValida(ed.getDt_Indenizacao_Final()))
						sql += " and ind.dt_Indenizacao <= '" + ed.getDt_Indenizacao_Final() + "'";
				}
			}
			
			if (!doValida(ed.getDm_Ordenacao())) {
				sql +=" ORDER BY ind.dt_Indenizacao";
			} else {
				if ("RC".equals(ed.getDm_Ordenacao())){
					sql += " ORDER BY reg.Nm_Regional, emp.Nm_Razao_Social, ind.dt_Indenizacao " ;
				}
			}
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(IndenizacaoED ed)");
		}
	}

	public IndenizacaoED getByRecord(IndenizacaoED ed) throws Excecoes {
		try {
			ArrayList<IndenizacaoED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (IndenizacaoED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(IndenizacaoED ed)");
		}
		return new IndenizacaoED();
	}
	/**
	 * Resumo de Indenizações
	 * @param ed
	 * @return
	 * @throws Excecoes
	 */
	
	public IndenizacaoED getResumoPeriodo(IndenizacaoED ed) throws Excecoes {

		IndenizacaoED indED = new IndenizacaoED();
		try {
			//Total de indenizações no período  e não inspecionadas
			sql = "SELECT " +
			
			
			//"count(ind.oid_indenizacao) as nr_Total_Indenizacoes_Periodo," +
			"sum (case when ind.dm_rejeicao is null then 1 else 0 end) as nr_Total_Indenizacoes_Periodo," +
			"sum (case when ind.dm_aprovacao is null  and ind.dm_rejeicao is null then 1 else 0 end) as nr_Total_Indenizacoes_Nao_Inspecionadas, " +
			"sum (case when ind.dm_rejeicao is not null then 1 else 0 end) as nr_Total_Indenizacoes_Rejeitadas, " +
			" sum( case when ind.dm_aprovacao = 'N' then 0 else vl_indenizacao end ) as vl_Total_Indenizado_Periodo " +
			"FROM " +
			"Indenizacoes as ind " +
			"WHERE 1 = 1 " ;
			if (ed.getOid_Empresa()>0) {
				sql+=" and ind.oid_empresa = " + ed.getOid_Empresa() + " ";
			}
			if (doValida(ed.getDt_Indenizacao_Inicial()) ) {
				sql+=" and ind.dt_indenizacao >= '" + ed.getDt_Indenizacao_Inicial()+ "' ";
			}
			if (doValida(ed.getDt_Indenizacao_Inicial()) ) {
				sql+=" and ind.dt_indenizacao <= '" + ed.getDt_Indenizacao_Final()+ "' ";
			}
			ResultSet res0 = this.executasql.executarConsulta(sql);
			while (res0.next()) {
				indED.setNr_Total_Indenizacoes_Periodo(res0.getInt("nr_Total_Indenizacoes_Periodo"));
				indED.setNr_Total_Indenizacoes_Nao_Inspecionadas(res0.getInt("nr_Total_Indenizacoes_Nao_Inspecionadas"));
				indED.setNr_Total_Indenizacoes_Rejeitadas(res0.getInt("nr_Total_Indenizacoes_Rejeitadas"));
				indED.setVl_Total_Indenizado_Periodo(res0.getDouble("vl_Total_Indenizado_Periodo"));
			}
			//Total de indenizações Inspecionadas, aprovadas, rejeitadas no período 
			sql = "SELECT " +
			"count(ind.oid_indenizacao) as nr_Total_Indenizacoes_Inspecionadas, " +
			"sum(case when ind.dm_aprovacao = 'S' then 1 else 0 end) as nr_Total_Indenizacoes_Aprovadas," +
			"sum(case when ind.dm_aprovacao = 'N' then 1 else 0 end) as nr_Total_Indenizacoes_Reprovadas " +
			"FROM " +
			"Indenizacoes as ind " +
			"WHERE 1 = 1 " ;
			if (ed.getOid_Empresa()>0) {
				sql+=" and ind.oid_empresa = " + ed.getOid_Empresa() + " ";
			}
			if (doValida(ed.getDt_Indenizacao_Inicial()) ) {
				sql+=" and ind.dt_Aprovacao >= '" + ed.getDt_Indenizacao_Inicial()+ "' ";
			}
			if (doValida(ed.getDt_Indenizacao_Inicial()) ) {
				sql+=" and ind.dt_Aprovacao <= '" + ed.getDt_Indenizacao_Final()+ "' ";
			}
			ResultSet res2 = this.executasql.executarConsulta(sql);
			while (res2.next()) {
				indED.setNr_Total_Indenizacoes_Inspecionadas(res2.getInt("nr_Total_Indenizacoes_Inspecionadas"));
				indED.setNr_Total_Indenizacoes_Aprovadas(res2.getInt("nr_Total_Indenizacoes_Aprovadas"));
				indED.setNr_Total_Indenizacoes_Reprovadas(res2.getInt("nr_Total_Indenizacoes_Reprovadas"));
			}
			
			return indED;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(IndenizacaoED ed)");
		}
	}

	/**
	 * 

select 
	c.nm_Razao_Social,
	(count( i.oid_indenizacao ) - count( i.dm_rejeicao ))as nr_cta_concedidas ,
	sum(case when i.dm_aprovacao is null and i.dm_rejeicao is null then 1 else 0 end) as nr_cta_n_inspecionadas,
        sum(case when i.dm_aprovacao is not null  then 1 else 0 end) as nr_cta_inspecionadas,
        sum(case when i.dm_aprovacao = 'S'  then 1 else 0 end) as nr_cta_inspecionadas_aprovadas,
        sum(case when i.dm_aprovacao = 'N'  then 1 else 0 end) as nr_cta_inspecionadas_n_aprovadas,
	count( i.dm_rejeicao ) as nr_cta_rejeitadas,
	sum(case when rgi.oid_concessionaria != i.oid_empresa then 1 else 0 end) as nr_cta_de_outra_concessionaria
from 
	indenizacoes as i 
	left join recapagens_garantidas as rgi on rgi.oid_recapagem_garantida = i.oid_recapagem_garantida
	left join empresas as c on c.oid_empresa = i.oid_empresa 
where 
	 i.oid_empresa = 2 and i.dt_indenizacao between '01/01/2009' and '31/01/2010'
group by
	c.nm_Razao_Social
		 
	*/
	
	private IndenizacaoED populaRegistro(ResultSet res) throws SQLException {
		IndenizacaoED ed = new IndenizacaoED();
		ed.setOid_Indenizacao(res.getInt("oid_Indenizacao"));
		ed.setOid_Empresa(res.getInt("oid_Empresa"));
		ed.setOid_Recapagem_Garantida(res.getInt("oid_Recapagem_Garantida"));
		ed.setDt_Indenizacao(FormataData.formataDataBT(res.getString("dt_Indenizacao")));
		ed.setDm_Dano_Acidente(res.getString("dm_Dano_Acidente"));
		ed.setDm_Excesso_Carga(res.getString("dm_Excesso_Carga"));
		ed.setDm_Baixa_Alta_Pressao(res.getString("dm_Baixa_Alta_Pressao"));
		ed.setDm_Fora_Aplicacao_Recomendada(res.getString("dm_Fora_Aplicacao_Recomendada"));
		ed.setDm_Montagem_Desmontagem_Inadequada(res.getString("dm_Montagem_Desmontagem_Inadequada"));
		ed.setDm_Mau_Estado(res.getString("dm_Mau_estado"));
		ed.setDm_Tubless_Com_Camara(res.getString("dm_Tubless_Com_Camara"));
		ed.setDm_Substancia_Quimica(res.getString("dm_Substancia_Quimica"));
		ed.setDm_Perda_Total(res.getString("dm_Perda_Total"));
		ed.setNr_MM(res.getDouble("nr_MM"));
		ed.setNr_Perc_Perda_Total(res.getDouble("nr_Perc_Perda_Total"));
		ed.setNr_Perc_Reforma(res.getDouble("nr_Perc_Reforma"));
		ed.setNr_Perc_Carcaca(res.getDouble("nr_Perc_Carcaca"));
		ed.setVl_Indenizacao(res.getDouble("vl_Indenizacao"));
		ed.setDm_Aprovacao(res.getString("dm_Aprovacao"));
		ed.setDm_Aceita_Por_Parametro(res.getString("dm_Aceita_Por_Parametro"));
		ed.setDm_Rejeicao(res.getString("dm_Rejeicao"));
		ed.setDt_Aprovacao(FormataData.formataDataBT(res.getString("dt_Aprovacao")));
		ed.setTx_Laudo(res.getString("tx_Laudo"));
		ed.setOid_Usuario_Tecnico(res.getInt("oid_Usuario_Tecnico"));
		ed.setTx_Aprovacao_Motivo(res.getString("tx_Aprovacao_Motivo"));
		ed.setNr_Fogo(res.getString("nr_Fogo"));
		ed.setNr_Nota_Fiscal(res.getInt("nr_Nota_Fiscal"));
		ed.setVl_Servico(res.getDouble("vl_Servico"));
		ed.setDt_Registro(FormataData.formataDataBT(res.getString("dt_Registro")));
		ed.setDt_Validade_Garantia(FormataData.formataDataBT(res.getString("dt_Validade_Garantia")));
		ed.setDm_Tipo_Pneu(res.getString("dm_Tipo_Pneu"));
		ed.setNm_Modelo_Pneu(res.getString("nm_Modelo_Pneu"));
		ed.setNm_Fabricante_Pneu(res.getString("nm_Fabricante_Pneu"));
		ed.setNm_Banda(res.getString("nm_Banda") + " L: " + FormataValor.formataValorBT(res.getDouble("nr_Largura"), 1) + " P: " + FormataValor.formataValorBT(res.getDouble("nr_Profundidade"), 1));
		ed.setNm_Regional(res.getString("nm_Regional"));
		ed.setNm_Concessionaria(res.getString("nm_Concessionaria"));
		ed.setNm_Usuario_Tecnico(res.getString("nm_Usuario_Tecnico"));
		ed.setNm_Razao_Social(res.getString("nm_Cliente"));
		ed.setNm_Aprovacao(("S".equals(res.getString("dm_Aprovacao"))?"SIM":"NÃO"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}
}
