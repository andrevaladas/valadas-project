package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.master.ed.Stif_ProblemaED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial STIF Problemas 
 * @since 06/2012
 */
public class Stif_ProblemaBD extends BancoUtil {

	private ExecutaSQL executaSQL;
	String sql=null;

	public Stif_ProblemaBD (ExecutaSQL executaSQL) {
		super(executaSQL);
		this.executaSQL = executaSQL;
	}

	public Stif_ProblemaED inclui(Stif_ProblemaED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Stif_Problemas (" +
			"oid_empresa " +
			",cd_problema " +
			",nm_problema " +
			",pc_perda " +
			",dm_tipo" +
			",dm_Stamp" +
		  	",dt_Stamp" +
		  	",usuario_Stamp"+
			") values ( " +
			ed.getOid_Empresa() +
			",'" + ed.getCd_problema() + "'" +
			",'" + ed.getNm_problema () + "' " +
			"," + ed.getPc_perda() +
			",'" + ed.getDm_tipo()+ "'" +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" ;
			sql+=")";
			executaSQL.executarUpdate (sql);
			return ed;
		} catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "inclui (Stif_ProblemaED ed)");
		}
	}

	public void altera(Stif_ProblemaED ed) throws Excecoes {
		try {
			sql = "UPDATE Stif_Problemas SET " +
			"cd_problema = '" + ed.getCd_problema() + "'" +
			",nm_problema = '" + ed.getNm_problema() + "' " +
			",pc_perda = " + ed.getPc_perda() +
			",dm_tipo = '" + ed.getDm_tipo() + "' " +
			",dm_Stamp = 'A'" +
  	   		",dt_Stamp = '" + Data.getDataDMY() + "' " +
  	   		",usuario_Stamp = '"+ed.getUsuario_Stamp() + "' " +
			"WHERE oid_stif_problema = " + ed.getOid_stif_problema();
			executaSQL.executarUpdate (sql);
		} catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "altera (Stif_ProblemaED ed)");
		}
	}

	public void delete (Stif_ProblemaED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stif_Problemas " +
				  "WHERE oid_stif_problema = " + ed.getOid_stif_problema();
			executaSQL.executarUpdate (sql);
		} catch (SQLException e) {
			throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "delete (Stif_ProblemaED ed)");
		}
	}

	public List<Stif_ProblemaED> lista (Stif_ProblemaED ed) throws Excecoes {
		final List<Stif_ProblemaED> list = new ArrayList <Stif_ProblemaED>();
		try {
			/** Busca vinculo com PNEU INSPECAO */
			if (ed.getOid_Pneu_Inspecao() > 0) {
				sql = "SELECT sp.*," +
					  " CASE " +
					  "		WHEN spp.oid_stif_problema > 0 THEN 'true' "+ 
					  "		ELSE 'false' " +
					  " END AS checkItem " +
					  "FROM Stif_Problemas as sp " +
					  "		LEFT JOIN Stif_Problemas_Pneus as spp " + 			
					  "			ON (spp.oid_stif_problema = sp.oid_stif_problema and spp.oid_Pneu_Inspecao = "+ed.getOid_Pneu_Inspecao()+") " +
					  "WHERE 1=1 ";
			} else {
				/** Busca apenas os stif_problemas */
				sql = "SELECT sp.* " +
					  "FROM Stif_Problemas as sp " +
					  "WHERE 1=1 ";
			}
			if (ed.getOid_stif_problema() > 0) {
				sql += " and sp.oid_stif_problema = " + ed.getOid_stif_problema();
			} else {
				if (doValida(ed.getCd_problema())) {
					sql += " and sp.cd_problema like '%" + ed.getCd_problema () + "%'";
				}
				if (doValida(ed.getNm_problema())) { 
					sql += " and sp.nm_problema like '%" + ed.getNm_problema () + "%'";
				}
				if (doValida(ed.getDm_tipo())) { 
					sql += " and sp.dm_tipo = '" + ed.getDm_tipo() + "'";
				}
			}
			sql += " ORDER BY sp.nm_problema";
			final ResultSet rs = executaSQL.executarConsulta(sql);
			final AtomicInteger rowCount = new AtomicInteger(1);
			while (rs.next()) {
				final Stif_ProblemaED populaRegistro = populaRegistro(rs);
				populaRegistro.setOrdem(rowCount.getAndIncrement());
				list.add(populaRegistro);
			}
		} catch (SQLException e) {
			throw new Excecoes (e.getMessage(), e,this.getClass().getName() , "lista(Stif_ProblemaED ed)");
		}
		return list;
	}

	public Stif_ProblemaED getByRecord(Stif_ProblemaED ed) throws Excecoes {
		try {
			List<Stif_ProblemaED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Stif_ProblemaED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stif_ProblemaED ed)");
		}
		return new Stif_ProblemaED();
	}

	public Stif_ProblemaED populaRegistro(ResultSet res) throws SQLException {
		final Stif_ProblemaED ed = (Stif_ProblemaED) beanProcessor.toBean(res, Stif_ProblemaED.class);
	    ed.formataMsgStamp();
	    return ed;	  
	}
}