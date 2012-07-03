package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Faturamento_ConcessionariaED;
import com.master.ed.IndenizacaoED;
import com.master.ed.Recapagem_GarantidaED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.FormataValor;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Recapagens Garantidas
 * @serialData 06/2009
 */
public class Recapagem_GarantidaBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Recapagem_GarantidaBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Recapagem_GarantidaED inclui(Recapagem_GarantidaED ed) throws Excecoes {
		try {
			//ed.setOid_Recapagem_Garantida(getAutoIncremento("oid_Recapagem_Garantida", "Recapagens_Garantidas"));
			//ed.setNr_Certificado(ed.getOid_Recapagem_Garantida());
			sql = "INSERT INTO Recapagens_Garantidas (" +
			//"oid_Recapagem_Garantida," +
			"oid_usuario, " +
			"oid_concessionaria, " +
			"oid_cliente, " +
			"oid_banda, " +
			"oid_banda_dimensao, " +
			"oid_modelo_pneu, " +
			"dt_registro, " +
			"hr_registro, " +
			"dt_emissao_certificado, " +
			"hr_emissao_certificado, " +			
			"nr_nota_fiscal, " +
			"vl_servico, " +
			"dm_reparo, " +
			"nr_quantidade_reparo, " +
			"nr_fogo, " +
			"dm_tipo_pneu, " +
			"nr_dot, " +
			"nr_indice_carga, " +
			"nr_vida, " +
			"dm_dano_acidente, " +
			"dm_excesso_carga, " +
			"dm_baixa_alta_pressao, " +
			"dm_fora_aplicacao_recomendada, " +
			"dm_montagem_desmontagem_inadequada, " +
			"dm_mau_estado, " +
			"dm_tubless_com_camara, " +
			"dm_substancia_quimica, " +
			"dt_validade_garantia, " +
			//"nr_certificado, " +
			"dm_substituida, " +
			"oid_Recapagem_Garantida_Substituta, " +
			"dm_Stamp," +
            "dt_Stamp," +
	  	    "usuario_Stamp"+
			") " +
			" VALUES " +
			"( " + //ed.getOid_Recapagem_Garantida() + ", " + 
			ed.getOid_Usuario() +  
			", " + ed.getOid_Concessionaria() +
			", " + ed.getOid_Cliente() +
			", " + ed.getOid_Banda() +
			", " + ed.getOid_Banda_Dimensao() +
			", " + ed.getOid_Modelo_Pneu() +
			",'" + ed.getDt_Registro() + "' " +
			",'" + ed.getHr_Registro() + "' " +
			",'" + ed.getDt_Emissao_Certificado() + "' " +
			",'" + ed.getHr_Emissao_Certificado() + "' " +
			", " + ed.getNr_Nota_Fiscal() +
			", " + ed.getVl_Servico() +
			",'" + ed.getDm_Reparo() + "' " +
	  		", " + ed.getNr_Quantidade_Reparo() +
	  		",'" + ed.getNr_Fogo()+ "' " +
	  		",'" + ed.getDm_Tipo_Pneu() + "' " +
	  		",'" + ed.getNr_Dot() + "' " +
	  		", " + ed.getNr_Indice_Carga() +
	  		", " + ed.getNr_Vida() +
	  		",'" + ed.getDm_Dano_Acidente() + "' " +
	  		",'" + ed.getDm_Excesso_Carga() + "' " +
	  		",'" + ed.getDm_Baixa_Alta_Pressao() + "' " +
	  		",'" + ed.getDm_Fora_Aplicacao_Recomendada() + "' " +
	  		",'" + ed.getDm_Montagem_Desmontagem_Inadequada() + "' " +
	  		",'" + ed.getDm_Mau_estado() + "' " +
	  		",'" + ed.getDm_Tubless_Com_Camara() + "' " +
	  		",'" + ed.getDm_Substancia_Quimica() + "' " +
	  		",'" + ed.getDt_Validade_Garantia() + "' " +
	  		//", " + ed.getNr_Certificado() +
	  		", " + "'N'" +
	  		", " + ed.getOid_Recapagem_Garantida_Substituta() +
	  		", " + "'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			// Pega o Oid
			ed.setOid_Recapagem_Garantida(getSeq("Recapagens_Garantidas_oid_Recapagem_Garantida_seq"));
			// Pega o nº certificado
			ed.setNr_Certificado(getSeq("Recapagens_Garantidas_nr_Certificado_seq"));
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Recapagem_GarantidaED ed)");
		}
	}

	public void altera(Recapagem_GarantidaED ed) throws Excecoes {
		try {
			sql = "UPDATE Recapagens_Garantidas SET " +
			"dm_Substituida = 'S' " + ", " +
			"oid_Recapagem_Garantida_Substituta = " + ed.getOid_Recapagem_Garantida_Substituta() + ", " +
			"dt_Stamp = '" + ed.getDt_stamp() + "', " +
			"usuario_Stamp = '" + ed.getUsuario_Stamp() + "', " + 
			"dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Recapagem_Garantida = " + ed.getOid_Recapagem_Garantida();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Recapagem_GarantidaED ed)");
		}
	}

	public void delete(Recapagem_GarantidaED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Recapagens_Garantidas " +
			"WHERE " +
			"oid_Recapagem_Garantida = " + ed.getOid_Recapagem_Garantida();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Recapagem_GarantidaED ed)");
		}
	}

	public ArrayList<Recapagem_GarantidaED> lista(Recapagem_GarantidaED ed) throws Excecoes {

		ArrayList<Recapagem_GarantidaED> list = new ArrayList<Recapagem_GarantidaED>();
		try {
			sql="SELECT " +
			 	"*, " +
			 	"us.nm_usuario as usnm_usuario," +
			 	"cl.nm_razao_social as clnm_razao_social," +
			 	"cl.nr_cnpj as clnr_cnpj," +
			 	"co.nm_razao_social as conm_razao_social," +
			 	"co.nm_cidade as conm_cidade," +
			 	"co.cd_estado as cocd_estado," +
			 	"ba.nm_banda as banm_banda," +
			 	"bd.nr_largura as banr_largura," +
			 	"bd.nr_profundidade as banr_profundidade," +
			 	"baAntes.nr_profundidade as baAntesnr_profundidade," +
			 	"fp.nm_fabricante_pneu as fpnm_fabricante_pneu," +
			 	"mp.nm_modelo_pneu as mpnm_modelo_pneu," +
			 	"id.dt_indenizacao as iddt_indenizacao," +
		  		"rg.usuario_Stamp as usu_Stmp, " +
		  		"rg.dt_Stamp as dt_Stmp, " +
		  		"rg.dm_Stamp as dm_Stmp " +
			 	"from " +
			 	"recapagens_garantidas as rg " +
			 	"left join usuarios as us on us.oid_usuario = rg.oid_usuario " +
			 	"left join empresas as cl on cl.oid_empresa = rg.oid_cliente " +
			 	"left join empresas as co on co.oid_empresa = rg.oid_concessionaria " +
			 	"left join bandas as ba on ba.oid_banda = rg.oid_banda " +
			 	"left join bandas_dimensoes as bd on bd.oid_banda_dimensao = rg.oid_banda_dimensao " +
			 	"left join modelos_pneus as mp on mp.oid_modelo_pneu = rg.oid_modelo_pneu " +
			 	"left join fabricantes_pneus as fp on fp.oid_fabricante_pneu = mp.oid_fabricante_pneu " +
			 	"left join indenizacoes as id on id.oid_recapagem_garantida = rg.oid_recapagem_garantida " +
			 	"left join bandas as baAntes on baAntes.oid_banda = rg.oid_banda_salva " +
			 	"WHERE 1=1 " ;
			if (ed.getOid_Recapagem_Garantida() > 0)
				sql += " and rg.oid_Recapagem_Garantida = " + ed.getOid_Recapagem_Garantida();
			else {
				if (ed.getOid_Cliente() > 0)
					sql += " and rg.oid_Cliente = " + ed.getOid_Cliente();
				if (ed.getOid_Concessionaria() > 0)
					sql += " and rg.oid_Concessionaria = " + ed.getOid_Concessionaria();
				if (ed.getOid_Banda() > 0)
					sql += " and rg.oid_Banda = " + ed.getOid_Banda();
				if (ed.getOid_Modelo_Pneu() > 0)
					sql += " and rg.oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu();
				if (ed.getNr_Nota_Fiscal() > 0)
					sql += " and rg.nr_Nota_Fiscal = " + ed.getNr_Nota_Fiscal();				
				if (ed.getNr_Certificado() > 0)
					sql += " and rg.nr_Certificado = " + ed.getNr_Certificado();				
				if (doValida(ed.getNr_Fogo()))
					sql += " and rg.nr_Fogo = '" + ed.getNr_Fogo() + "'";
				if (doValida(ed.getDt_Registro()))
					sql += " and rg.dt_Registro = '" + ed.getDt_Registro() + "'";
				if (doValida(ed.getDt_Registro_Inicial()))
					sql += " and rg.dt_Registro >= '" + ed.getDt_Registro_Inicial() + "'";
				if (doValida(ed.getDt_Registro_Final()))
					sql += " and rg.dt_Registro <= '" + ed.getDt_Registro_Final() + "'";
				if (doValida(ed.getDm_Substituida()))
					sql += " and rg.dm_Substituida = '" + ed.getDm_Substituida() + "'";
			}
			sql += " ORDER BY " +
			"rg.dt_Registro, " +
			"rg.hr_Registro";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Recapagem_GarantidaED ed)");
		}
	}
	

	/**
	 * Busca o resumo das recapagens garantidas no período e também o resumo das indenizacoes no período
	 * @param ed
	 * @return
	 * @throws Excecoes
	 */
	public ArrayList<Recapagem_GarantidaED> getResumoPeriodo(Recapagem_GarantidaED ed) throws Excecoes {

		ArrayList<Recapagem_GarantidaED> list = new ArrayList<Recapagem_GarantidaED>();
		try {
			ed.setDt_Registro_Inicial("01/"+ed.getDm_Mes_Ano());
			ed.setDt_Registro_Final(Data.getUltimoDiaDoMes(ed.getDt_Registro_Inicial()));
			sql="SELECT " +
			"rg.oid_concessionaria," +
			"r.nm_regional, " +
			"c.nm_razao_social," +
			"count(oid_recapagem_garantida) as cta " +
			"from " +
			"recapagens_garantidas as rg " +
			"left join empresas as c on c.oid_empresa = rg.oid_concessionaria " +
			"left join regionais as r on r.oid_regional = c.oid_regional " +
			"WHERE 1=1 " ;
			if (ed.getOid_Regional()>0)
				sql+= " and r.oid_Regional = " + ed.getOid_Regional();
			if (ed.getOid_Concessionaria()>0)
				sql+= " and rg.oid_concessionaria = " + ed.getOid_Concessionaria();
			if (doValida(ed.getDt_Registro_Inicial()))
				sql += " and rg.dt_Registro >= '" + ed.getDt_Registro_Inicial() + "'";
			if (doValida(ed.getDt_Registro_Final()))
				sql += " and rg.dt_Registro <= '" + ed.getDt_Registro_Final() + "'";
			sql += " GROUP BY " +
			"rg.oid_concessionaria," +
			"nm_regional," +
			"nm_razao_social " +
			"ORDER BY " +
			"r.nm_regional," +
			"c.nm_razao_social ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				Recapagem_GarantidaED rgED = new Recapagem_GarantidaED();
				rgED.setNm_Regional(res.getString("nm_Regional"));
				rgED.setNm_Razao_Social_Concessionaria(res.getString("nm_Razao_Social"));
				rgED.setNr_Total_Recapagens_Garantidas(res.getInt("cta"));
				// Busca a indenizacao do periodo 
				IndenizacaoED indED = new IndenizacaoED();
				indED.setOid_Empresa(res.getInt("oid_concessionaria"));
				indED.setDt_Indenizacao_Inicial(ed.getDt_Registro_Inicial());
				indED.setDt_Indenizacao_Final(ed.getDt_Registro_Final());
				indED = new IndenizacaoBD(this.executasql).getResumoPeriodo(indED);
				// coloca os dados de resumo das indenizacoes no ed de recapagem
				rgED.setNr_Total_Indenizacoes_Periodo(indED.getNr_Total_Indenizacoes_Periodo());
				rgED.setVl_Total_Indenizado_Periodo(indED.getVl_Total_Indenizado_Periodo());
				rgED.setNr_Total_Indenizacoes_Nao_Inspecionadas(indED.getNr_Total_Indenizacoes_Nao_Inspecionadas());
				rgED.setNr_Total_Indenizacoes_Inspecionadas(indED.getNr_Total_Indenizacoes_Inspecionadas());
				rgED.setNr_Total_Indenizacoes_Aprovadas(indED.getNr_Total_Indenizacoes_Aprovadas());
				rgED.setNr_Total_Indenizacoes_Reprovadas(indED.getNr_Total_Indenizacoes_Reprovadas());
				rgED.setNr_Total_Indenizacoes_Rejeitadas(indED.getNr_Total_Indenizacoes_Rejeitadas());
				// Busca o faturamento
				Faturamento_ConcessionariaED fatED = new Faturamento_ConcessionariaED();
				fatED.setOid_Concessionaria(res.getInt("oid_concessionaria"));
				fatED.setDm_Mes_Ano(ed.getDm_Mes_Ano());
				fatED = new Faturamento_ConcessionariaBD (this.executasql).getFaturamento(fatED);
				// Calcula o percentual de indenização em razao do último faturamento
				if (rgED.getNr_Total_Indenizacoes_Periodo()>0) {
					if (fatED.getVl_Faturamento()>0) {
						rgED.setNr_Perc_Faturamento((rgED.getVl_Total_Indenizado_Periodo()/fatED.getVl_Faturamento())* 100);
					}	
				}
				list.add(rgED);
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"listaResumo(Recapagem_GarantidaED ed)");
		}
	}
 
	public Recapagem_GarantidaED getByRecord(Recapagem_GarantidaED ed) throws Excecoes {
		try {
			ArrayList<Recapagem_GarantidaED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Recapagem_GarantidaED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Recapagem_GarantidaED ed)");
		}
		return new Recapagem_GarantidaED();
	}
	
	/**
	 * 
select 
	c.nm_Razao_Social,
	count(rg.oid_recapagem_garantida) as nr_cta_RG, 
	sum(case when rg.dm_substituida='S' then 1 else 0 end) as nr_cta_substituida 
from 
	recapagens_garantidas as rg
        left join empresas as c on c.oid_empresa = rg.oid_concessionaria 
where 
	rg.oid_concessionaria = 2 and 
	rg.dt_registro between '01/01/2009' and '31/01/2010'
group by
	c.nm_Razao_Social
	*/
	
	
	private Recapagem_GarantidaED populaRegistro(ResultSet res) throws SQLException {
		Recapagem_GarantidaED ed = new Recapagem_GarantidaED();
		ed.setOid_Recapagem_Garantida(res.getLong("oid_Recapagem_Garantida"));
		ed.setOid_Usuario(res.getLong("oid_Usuario"));
		ed.setOid_Concessionaria(res.getLong("oid_concessionaria"));
		ed.setOid_Cliente(res.getLong("oid_cliente"));
		ed.setOid_Banda(res.getLong("oid_banda"));
		ed.setOid_Modelo_Pneu(res.getLong("oid_modelo_pneu"));
		ed.setOid_Fabricante_Pneu(res.getLong("oid_fabricante_pneu"));
		ed.setDt_Registro(FormataData.formataDataBT(res.getString("dt_registro")));
		ed.setHr_Registro(res.getString("hr_registro"));
		ed.setDt_Emissao_Certificado(FormataData.formataDataBT(res.getString("dt_emissao_certificado")));
		ed.setHr_Emissao_Certificado(res.getString("hr_emissao_certificado"));
		ed.setNr_Nota_Fiscal(res.getLong("nr_nota_fiscal"));
		ed.setVl_Servico(res.getDouble("vl_servico"));
		ed.setDm_Reparo(res.getString("dm_reparo"));
		ed.setNr_Quantidade_Reparo(res.getLong("nr_quantidade_reparo"));
		ed.setNr_Fogo(res.getString("nr_fogo"));
		ed.setDm_Tipo_Pneu(res.getString("dm_tipo_pneu"));
		ed.setNr_Dot(res.getString("nr_dot"));
		ed.setNr_Indice_Carga(res.getLong("nr_indice_carga"));
		ed.setNr_Vida(res.getLong("nr_vida"));
		ed.setDm_Dano_Acidente(res.getString("dm_dano_acidente"));
		ed.setDm_Excesso_Carga(res.getString("dm_excesso_carga"));
		ed.setDm_Baixa_Alta_Pressao(res.getString("dm_baixa_alta_pressao"));
		ed.setDm_Fora_Aplicacao_Recomendada(res.getString("dm_fora_aplicacao_recomendada"));
		ed.setDm_Montagem_Desmontagem_Inadequada(res.getString("dm_montagem_desmontagem_inadequada"));
		ed.setDm_Mau_estado(res.getString("dm_mau_estado"));
		ed.setDm_Tubless_Com_Camara(res.getString("dm_tubless_com_camara"));
		ed.setDm_Substancia_Quimica(res.getString("dm_substancia_quimica"));
		ed.setOid_Recapagem_Garantida_Substituta(res.getLong("oid_Recapagem_Garantida_Substituta"));
		ed.setDm_Substituida(res.getString("dm_Substituida"));
		ed.setDt_Validade_Garantia(FormataData.formataDataBT(res.getString("dt_Validade_Garantia")));
		ed.setNr_Certificado(res.getLong("nr_certificado"));
	 	ed.setNm_Usuario(res.getString("usnm_usuario"));
	 	ed.setNm_Razao_Social_Cliente(res.getString("clnm_razao_social"));
	 	ed.setNr_Cnpj_Cliente(res.getString("clnr_Cnpj"));
	 	ed.setNm_Razao_Social_Concessionaria(res.getString("conm_razao_social"));
	 	ed.setNm_Banda(res.getString("banm_banda")); 
	 	
	 	if (res.getDouble("banr_Profundidade")>0) {
	 		ed.setNr_MM(res.getDouble("banr_Profundidade"));
	 	} else {
	 		ed.setNr_MM(res.getDouble("baAntesnr_profundidade"));
	 	}
	 	
	 	ed.setNm_Fabricante_Pneu(res.getString("fpnm_fabricante_pneu"));
	 	ed.setNm_Modelo_Pneu(res.getString("mpnm_modelo_pneu"));
	 	ed.setDm_Indenizada((doValida(res.getString("iddt_indenizacao"))?"SIM":"NÃO"));
	 	ed.setDt_Indenizacao(FormataData.formataDataBT(res.getString("iddt_indenizacao")));
	 	ed.setNm_Cidade(res.getString("conm_cidade") + ", "+ res.getString("cocd_estado"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
