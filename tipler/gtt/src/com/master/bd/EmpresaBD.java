package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.EmpresaED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Regis
 * @serial Empresas
 * @serialData 06/2009
 */
public class EmpresaBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public EmpresaBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public EmpresaED inclui(EmpresaED ed) throws Excecoes {
		try {
			ed.setDt_stamp(Data.getDataDMY());
			ed.setOid_Empresa(getAutoIncremento("oid_Empresa", "Empresas"));
			sql = "INSERT INTO Empresas " +
			"(" +
			"oid_Empresa, " +
			"nm_Razao_Social," +
			"nm_Endereco," +
			"nm_Bairro," +
			"nm_Cidade," +
			"nr_Cep," +
			"nr_cnpj," +
			"nm_Inscricao_Estadual," +
			"nr_Telefone," +
			"nm_Email, " +
			"cd_Estado," +
			"cd_Pais," +
			"nr_Fax," +
			"nm_Contato, " +
			"dm_Tipo_Frota," +
			"dm_Tipo_Empresa," +
			"oid_Concessionaria, " +
			"oid_Regional, " +
			"dt_Stamp," +
			"dm_Stamp," +
			"usuario_Stamp" +
			") " +
			" VALUES " +
			"(" +
			ed.getOid_Empresa() +
			",'" + (doValida(ed.getNm_Razao_Social()) ? ed.getNm_Razao_Social() : "" )+"'" + 
			",'" + (doValida(ed.getNm_Endereco()) ? ed.getNm_Endereco() : "" )+"'" + 
			",'" + (doValida(ed.getNm_Bairro()) ? ed.getNm_Bairro() : "" )+"'" + 
			",'" + (doValida(ed.getNm_Cidade()) ? ed.getNm_Cidade() : "" )+"'" +
			",'" + (doValida(ed.getNr_Cep()) ? ed.getNr_Cep() : "" )+"'" +
			",'" + (doValida(ed.getNr_Cnpj()) ? ed.getNr_Cnpj() : "" )+"'" +
			",'" + (doValida(ed.getNm_Inscricao_Estadual())? ed.getNm_Inscricao_Estadual() : "" )+"'" + 
			",'" + (doValida(ed.getNr_Telefone())? ed.getNr_Telefone() : "" )+"'"+
			",'" + (doValida(ed.getNm_Email())? ed.getNm_Email() : "" )+"'"+
			",'" + (doValida(ed.getCd_Estado()) ? ed.getCd_Estado() : "" )+"'" +
			",'" + (doValida(ed.getCd_Pais()) ? ed.getCd_Pais() : "" )+"'" +
			",'" + (doValida(ed.getNr_Fax())? ed.getNr_Fax(): "" )+"'"+ 
			",'" + (doValida(ed.getNm_Contato())? ed.getNm_Contato(): "" )+"'"+
			",'" + (doValida(ed.getDm_Tipo_Frota())? ed.getDm_Tipo_Frota(): "" )+"'"+
			",'" + (doValida(ed.getDm_Tipo_Empresa())? ed.getDm_Tipo_Empresa(): "" )+"'"+
			"," + ed.getOid_Concessionaria()+ 
			"," + ed.getOid_Regional()+
			",'" + (doValida(ed.getDt_stamp())? ed.getDt_stamp(): "" )+"'"+
			",'I'"+
			",'" + (doValida(ed.getUsuario_Stamp())? ed.getUsuario_Stamp(): "" )+"'"+
			")";
			executasql.executarUpdate(sql);
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(EmpresaED ed)");
		}
	}

	public void altera(EmpresaED ed) throws Excecoes {
		try {
			sql = "UPDATE Empresas SET " +
			"nr_Cnpj= '" + ed.getNr_Cnpj()+ "' " +
			",nm_Razao_Social = '" + ed.getNm_Razao_Social() + "' " +
			",nm_Endereco = '" + ed.getNm_Endereco()  + "' " +
			",nm_Bairro = '" + ed.getNm_Bairro()  + "' " +
			",nm_Cidade = '" + ed.getNm_Cidade() + "' " +
			",cd_Estado = '" + ed.getCd_Estado()  + "' " +
			",cd_Pais = '" + ed.getCd_Pais()  + "' " +
			",nr_Cep = '" + ed.getNr_Cep() + "' " +
			",nm_Inscricao_Estadual = '" + ed.getNm_Inscricao_Estadual() + "' " +
			",nr_Telefone = '" + ed.getNr_Telefone() + "' " +
			",nr_Fax = '" +  ed.getNr_Fax() + "' " +
			",nm_eMail = '" +  ed.getNm_Email() + "' " +
			",oid_Regional = " +  ed.getOid_Regional() +  
			",nm_Contato = '" +  ed.getNm_Contato() + "' " ;
			if ("U".equals(ed.getDm_Tipo_Empresa())) 
				sql+=",dm_Tipo_Frota = '" + ed.getDm_Tipo_Frota() + "' " ;
			sql+=",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Empresa = " + ed.getOid_Empresa() ;
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(EmpresaED ed)");
		}
	}

	public void deleta(EmpresaED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Empresas " +
			"WHERE " +
			"oid_Empresa = '" + ed.getOid_Empresa() + "'";
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(EmpresaED ed)");
		}
	}

	public ArrayList<EmpresaED> lista(EmpresaED ed) throws Excecoes {

		ArrayList<EmpresaED> list = new ArrayList<EmpresaED>();
		try {
			sql = "SELECT " +
			"e.*" +
	  		",e.usuario_Stamp as usu_Stmp " +
	  		",e.dt_Stamp as dt_Stmp " +
	  		",e.dm_Stamp as dm_Stmp " +
			",c.nm_razao_social as nm_Razao_Social_Concessionaria "  +
			",r.nm_Regional "  +
			"FROM " +
			"Empresas as e " +
			"left join Empresas as c on c.oid_empresa = e.oid_concessionaria " +
			"left join Regionais as r on r.oid_Regional = e.oid_Regional " +
			"WHERE " + "1=1";
			// Seleciona conforme o tipo de empresa da tela : cad002C = C, cad001C = U, mnu004C = A 
			if (!"A".equals(ed.getDm_Tipo_Empresa()))
				sql += " and e.dm_Tipo_Empresa = '"  + ed.getDm_Tipo_Empresa() + "' " ;
			// Seleciona conforme a empresa logada se for concessionária ...
			if ("C".equals(ed.getDm_Tipo_Consulta()) ) 
				sql += " and e.oid_Concessionaria = " + ed.getOid_Empresa() ;
			if ("T".equals(ed.getDm_Tipo_Consulta()) )
				if (ed.getOid_Empresa_Gambiarra() > 0) 
					sql += " and e.oid_Concessionaria = " + ed.getOid_Empresa_Gambiarra() ;
			if (doValida(ed.getNr_Cnpj()))
				sql += " and e.nr_Cnpj LIKE '" + ed.getNr_Cnpj() + "%' ";
			if (doValida(ed.getNm_Razao_Social()))
				sql += " and e.nm_Razao_Social LIKE '" + ed.getNm_Razao_Social() + "%' ";
			if (ed.getOid_Regional()>0)
				sql +=" and e.oid_Regional = " + ed.getOid_Regional();
			sql += " ORDER BY " +
			"e.nm_Razao_Social ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}

	public ArrayList<EmpresaED> listaClientesModulo(EmpresaED ed) throws Excecoes {

		ArrayList<EmpresaED> list = new ArrayList<EmpresaED>();
		try {
			sql = "SELECT " +
			"e.*" +
	  		",e.usuario_Stamp as usu_Stmp " +
	  		",e.dt_Stamp as dt_Stmp " +
	  		",e.dm_Stamp as dm_Stmp " +
			",c.nm_razao_social as nm_Razao_Social_Concessionaria "  +
			",r.nm_Regional "  +
			"FROM " +
			"Empresas as e " +
			"left join Empresas as c on c.oid_empresa = e.oid_concessionaria " +
			"left join Regionais as r on r.oid_Regional = e.oid_Regional " +
			"WHERE " + 
			"1=1 ";
			if ("STIB".equals(ed.getDm_Modulo()) ) {
				// So pega clientes da conssecionária que tenham stibs concluidos
				sql+="and e.oid_Empresa in (" +
						"	SELECT distinct i.oid_Cliente " +
						"	FROM Stib_Inspecoes as i left join Empresas as e on (i.oid_Cliente=e.oid_Empresa) " +
						"	WHERE e.oid_Concessionaria = "+ed.getOid_Empresa()+" and i.dt_Fim is not null) ";
			} else
			if ("STAS".equals(ed.getDm_Modulo()) ) {
				// So pega clientes da conssecionária que tenham stibs concluidos
				sql+="and e.oid_Empresa in (" +
						"	SELECT distinct a.oid_Cliente " +
						"	FROM Stas_Analises as a left join Empresas as e on (a.oid_Cliente=e.oid_Empresa) " +
						"	WHERE e.oid_Concessionaria = "+ed.getOid_Empresa()+" and a.dt_Fim is not null) ";
			}	

			// Seleciona conforme o tipo de empresa da tela : cad002C = C, cad001C = U, mnu004C = A 
			if (!"A".equals(ed.getDm_Tipo_Empresa()))
				sql += " and e.dm_Tipo_Empresa = '"  + ed.getDm_Tipo_Empresa() + "' " ;
			// Seleciona conforme a empresa logada se for concessionária ...
			if ("C".equals(ed.getDm_Tipo_Consulta()) ) 
				sql += " and e.oid_Concessionaria = " + ed.getOid_Empresa() ;
			if ("T".equals(ed.getDm_Tipo_Consulta()) )
				if (ed.getOid_Empresa_Gambiarra() > 0) 
					sql += " and e.oid_Concessionaria = " + ed.getOid_Empresa_Gambiarra() ;
			if (doValida(ed.getNr_Cnpj()))
				sql += " and e.nr_Cnpj LIKE '" + ed.getNr_Cnpj() + "%' ";
			if (doValida(ed.getNm_Razao_Social()))
				sql += " and e.nm_Razao_Social LIKE '" + ed.getNm_Razao_Social() + "%' ";
			if (ed.getOid_Regional()>0)
				sql +=" and e.oid_Regional = " + ed.getOid_Regional();
			sql += " ORDER BY " +
			"e.nm_Razao_Social ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}

	public EmpresaED getByRecord(EmpresaED ed) throws Excecoes {

		EmpresaED edQBR = new EmpresaED();
		try {
			sql = "SELECT " +
			"e.*" +
	  		",e.usuario_Stamp as usu_Stmp " +
	  		",e.dt_Stamp as dt_Stmp " +
	  		",e.dm_Stamp as dm_Stmp " +
			",c.nm_razao_social as nm_Razao_Social_Concessionaria "  +
			",r.nm_Regional "  +
			"FROM " +
			"Empresas as e " +
			"left join Empresas as c on c.oid_empresa = e.oid_concessionaria " +
			"left join Regionais as r on r.oid_Regional = e.oid_Regional " +
			"WHERE " + "1=1";

			if (doValida(ed.getNr_Cnpj() ) ) { 
				sql+=" and e.nr_cnpj = '" + ed.getNr_Cnpj() + "' " ;
				if (ed.getOid_Concessionaria()>0) 
					sql+=" and e.oid_Concessionaria = " + ed.getOid_Concessionaria() ;
			} else {
				if (ed.getOid_Empresa()>0) 
					sql+=" and e.oid_empresa = " + ed.getOid_Empresa() ;
				if (ed.getOid_Concessionaria()>0) 
					sql+=" and e.oid_Concessionaria = " + ed.getOid_Concessionaria() ;
			}
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				edQBR = populaRegistro(res);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(EmpresaED ed)");
		}
		return edQBR;
	}

	private EmpresaED populaRegistro(ResultSet res) throws SQLException {
		EmpresaED ed = new EmpresaED();
		ed.setOid_Empresa(res.getLong("oid_Empresa"));
		ed.setNm_Razao_Social(res.getString("nm_Razao_Social"));
		ed.setNm_Endereco(res.getString("nm_Endereco"));
		ed.setNm_Bairro(res.getString("nm_Bairro"));
		ed.setNm_Cidade(res.getString("nm_Cidade"));
		ed.setCd_Estado(res.getString("cd_Estado"));
		ed.setCd_Pais(res.getString("cd_Pais"));
		ed.setNm_Inscricao_Estadual(res.getString("nm_Inscricao_Estadual"));
		ed.setNr_Cep(res.getString("nr_Cep"));
		ed.setNr_Telefone(res.getString("nr_Telefone"));
		ed.setNr_Fax(res.getString("nr_Fax"));
		ed.setNm_Email(res.getString("nm_Email"));
		ed.setNr_Cnpj(res.getString("nr_cnpj"));
		ed.setDm_Tipo_Frota(res.getString("dm_Tipo_Frota"));
		ed.setDm_Tipo_Empresa(res.getString("dm_Tipo_Empresa"));
		ed.setNm_Contato(res.getString("nm_Contato"));
		ed.setOid_Concessionaria(res.getLong("oid_Concessionaria"));
		ed.setOid_Regional(res.getLong("oid_Regional"));
		ed.setNm_Regional(res.getString("nm_Regional"));
		ed.setNm_Concessionaria(res.getString("nm_Razao_Social_Concessionaria"));
		
	    ed.setUsuario_Stamp(res.getString("usu_Stmp"));
	    ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));

		return ed;
	}
}
