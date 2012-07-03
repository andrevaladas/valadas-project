package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.VeiculoED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial Cadastro de Veículos
 * @since 06/2012
 */
public class VeiculoBD extends BancoUtil {

	public VeiculoBD(ExecutaSQL sql) {
		super(sql);
	}

	public VeiculoED inclui(VeiculoED ed) throws Excecoes {
		try {
			final StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO Veiculos (");
			insert.append("	 oid_Empresa");
			insert.append("	,nr_Frota");
			insert.append("	,nr_Placa");
			insert.append("	,oid_Cliente");
			insert.append("	,dm_Tipo_Veiculo");
			insert.append("	,nm_Marca_Veiculo");
			insert.append("	,nm_Modelo_Veiculo");
			insert.append("	,nr_Ano_Fabricacao");
			insert.append("	,nr_Ano_Modelo");
			insert.append("	,dm_Tipo_Piso");
			insert.append("	,dm_Tipo_Severidade");
			insert.append("	,nm_Rota");
			insert.append("	,dm_Tipo_Chassis");
			insert.append("	,dm_Stamp");
			insert.append("	,dt_Stamp");
			insert.append("	,usuario_Stamp");
			insert.append(") VALUES (");
			insert.append(ed.getOid_Empresa()); 
			insert.append("," + getSQLString(ed.getNr_Frota())); 
			insert.append("," + getSQLString(ed.getNr_Placa()));
			insert.append("," + ed.getOid_Cliente());
			insert.append("," + ed.getDm_Tipo_Veiculo());
			insert.append("," + getSQLString(ed.getNm_Marca_Veiculo()));
			insert.append("," + getSQLString(ed.getNm_Modelo_Veiculo()));
			insert.append("," + ed.getNr_Ano_Fabricacao());
			insert.append("," + ed.getNr_Ano_Modelo());
			insert.append("," + ed.getDm_Tipo_Piso());
			insert.append("," + ed.getDm_Tipo_Severidade());
			insert.append("," + getSQLString(ed.getNm_Rota()));
			insert.append("," + ed.getDm_Tipo_Chassis());
			insert.append("," + getSQLString("I"));
			insert.append("," + getSQLString(Data.getDataDMY()));
			insert.append("," + getSQLString(ed.getUsuario_Stamp()));
			insert.append(")");
			sql.executarUpdate(insert.toString());
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(VeiculoED ed)");
		}
	}

	public void altera(VeiculoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Veiculos SET");
			update.append("	 nr_Frota = " + getSQLString(ed.getNr_Frota())); 
			update.append("	,nr_Placa = " + getSQLString(ed.getNr_Placa()));
			update.append("	,oid_Cliente = " + ed.getOid_Cliente());
			update.append("	,dm_Tipo_Veiculo = " + ed.getDm_Tipo_Veiculo());
			update.append("	,nm_Marca_Veiculo = " + getSQLString(ed.getNm_Marca_Veiculo()));
			update.append("	,nm_Modelo_Veiculo = " + getSQLString(ed.getNm_Modelo_Veiculo()));
			update.append("	,nr_Ano_Fabricacao = " + ed.getNr_Ano_Fabricacao());
			update.append("	,nr_Ano_Modelo = " + ed.getNr_Ano_Modelo());
			update.append("	,dm_Tipo_Piso = " + ed.getDm_Tipo_Piso());
			update.append("	,dm_Tipo_Severidade = " + ed.getDm_Tipo_Severidade());
			update.append("	,nm_Rota = " + getSQLString(ed.getNm_Rota()));
			update.append("	,dm_Tipo_Chassis = " + ed.getDm_Tipo_Chassis());
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Veiculo = " + ed.getOid_Veiculo());
			sql.executarUpdate(update.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(VeiculoED ed)");
		}
	}

	public void deleta(VeiculoED ed) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Veiculos ");
			delete.append(" WHERE oid_Veiculo = " + ed.getOid_Veiculo()); 
			sql.executarUpdate(delete.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(VeiculoED ed)");
		}
	}

	public List<VeiculoED> lista(VeiculoED ed) throws Excecoes {
		List<VeiculoED> list = new ArrayList<VeiculoED>();
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT v.* FROM Veiculos v");
			if (ed.getOid_Veiculo() > 0) {
				query.append(" WHERE v.oid_Veiculo = "+ed.getOid_Veiculo());
			} else {
				query.append(" WHERE v.oid_Empresa = " + ed.getOid_Empresa());
				if (ed.getOid_Cliente() > 0) {
					query.append(" AND v.oid_Cliente = "+ed.getOid_Cliente());
				}
				if (doValida(ed.getNr_Frota())) {
					query.append(" AND v.nr_frota = "+getSQLString(ed.getNr_Frota()));
				}
				if (doValida(ed.getNr_Placa())) {
					query.append(" AND v.nr_placa = "+getSQLString(ed.getNr_Placa()));
				}
				if (doValida(ed.getNm_Marca_Veiculo())) {
					query.append(" AND v.nm_marca_veiculo like "+getSQLStringLike(ed.getNm_Marca_Veiculo()));
				}
				if (doValida(ed.getNm_Modelo_Veiculo())) {
					query.append(" AND v.nm_modelo_veiculo like "+getSQLStringLike(ed.getNm_Modelo_Veiculo()));
				}
				if (ed.getDm_Tipo_Veiculo() > 0) {
					query.append(" AND v.dm_tipo_veiculo = "+ed.getDm_Tipo_Veiculo());
				}
				query.append(" ORDER BY v.nr_frota");
			}
			
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				list.add(populaRegistro(rs));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}
	
	public VeiculoED getByRecord(VeiculoED ed) throws Excecoes {
		try {
			final List<VeiculoED> lista = lista(ed);
			if (!lista.isEmpty()) {
				return lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(VeiculoED ed)");
		}
		return new VeiculoED();
	}

	private VeiculoED populaRegistro(ResultSet rs) throws SQLException {
		VeiculoED ed = (VeiculoED) beanProcessor.toBean(rs, VeiculoED.class);
		ed.formataMsgStamp();
		return ed;
	}
}
