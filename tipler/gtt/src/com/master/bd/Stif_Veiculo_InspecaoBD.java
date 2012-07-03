package com.master.bd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.master.ed.Stif_Veiculo_InspecaoED;
import com.master.ed.VeiculoED;
import com.master.util.BancoUtil;
import com.master.util.Data;
import com.master.util.Excecoes;
import com.master.util.bd.ExecutaSQL;

/**
 * @author André Valadas
 * @serial STIF- Veículo Inspeção
 * @since 06/2012
 */
public class Stif_Veiculo_InspecaoBD extends BancoUtil {

	public Stif_Veiculo_InspecaoBD(ExecutaSQL sql) {
		super(sql);
	}

	public Stif_Veiculo_InspecaoED inclui(Stif_Veiculo_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO Stif_Veiculos_Inspecoes (");
			insert.append("	 oid_Inspecao");
			insert.append("	,oid_Veiculo");
			insert.append("	,nr_Odometro");
			insert.append("	,tx_Observacao");
			insert.append("	,dm_Stamp");
			insert.append("	,dt_Stamp");
			insert.append("	,usuario_Stamp");
			insert.append(") VALUES (");
			insert.append(ed.getOid_Inspecao()); 
			insert.append("," + ed.getOid_Veiculo());
			insert.append("," + ed.getNr_Odometro());
			insert.append("," + getSQLString(ed.getTx_Observacao()));
			insert.append("," + getSQLString("I"));
			insert.append("," + getSQLString(Data.getDataDMY()));
			insert.append("," + getSQLString(ed.getUsuario_Stamp()));
			insert.append(")");
			sql.executarUpdate(insert.toString());
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stif_Veiculo_InspecaoED ed)");
		}
	}

	public void altera(Stif_Veiculo_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder update = new StringBuilder();
			update.append(" UPDATE Stif_Veiculos_Inspecoes SET");
			update.append("	 nr_Odometro = " + ed.getNr_Odometro());
			update.append("	,tx_Observacao = " + getSQLString(ed.getTx_Observacao()));
			update.append("	,dm_Stamp = " + getSQLString("A"));
			update.append("	,dt_Stamp = " + getSQLString(Data.getDataDMY()));
			update.append("	,usuario_Stamp = " + getSQLString(ed.getUsuario_Stamp()));
			update.append(" WHERE oid_Veiculo_Inspecao = " + ed.getOid_Veiculo_Inspecao());
			sql.executarUpdate(update.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stif_Veiculo_InspecaoED ed)");
		}
	}

	public void deleta(Stif_Veiculo_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder delete = new StringBuilder();
			delete.append(" DELETE FROM Stif_Veiculos_Inspecoes ");
			delete.append(" WHERE oid_Veiculo_Inspecao = " + ed.getOid_Veiculo_Inspecao()); 
			sql.executarUpdate(delete.toString());
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"deleta(Stif_Veiculo_InspecaoED ed)");
		}
	}

	public List<Stif_Veiculo_InspecaoED> lista(Stif_Veiculo_InspecaoED ed) throws Excecoes {
		try {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT * ");
			query.append(" FROM Stif_Veiculos_Inspecoes i");
			query.append(" INNER JOIN Veiculos v ON (v.oid_Veiculo = i.oid_Veiculo)");
			query.append(" WHERE i.oid_Inspecao = " + ed.getOid_Inspecao());
			if (ed.getOid_Veiculo() > 0) {
				query.append(" AND i.oid_Veiculo = "+ed.getOid_Veiculo());
			}
			query.append(" ORDER BY v.nr_Frota");

			final List<Stif_Veiculo_InspecaoED> list = new ArrayList<Stif_Veiculo_InspecaoED>();
			final ResultSet rs = sql.executarConsulta(query.toString());
			while (rs.next()) {
				list.add(populaRegistro(rs));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista()");
		}
	}
	
	public Stif_Veiculo_InspecaoED getByRecord(Stif_Veiculo_InspecaoED ed) throws Excecoes {
		try {
			final List<Stif_Veiculo_InspecaoED> lista = lista(ed);
			if (!lista.isEmpty()) {
				return lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stif_Veiculo_InspecaoED ed)");
		}
		return new Stif_Veiculo_InspecaoED();
	}

	public byte[] getImagem(long oidVeiculoInspcao) throws Excecoes {
		byte[] imgBytes = null;
		try {
			final ResultSet res = sql.executarConsulta("Select imagem from Stif_Veiculos_Inspecoes where oid_Veiculo_Inspecao = "+ oidVeiculoInspcao);
			while (res.next()) {
				 imgBytes = res.getBytes("imagem");
 			}
			if (imgBytes == null ) { // se não achou imagem na base de dados, coloca uma padrão
				final File img = new File(System.getProperty ("pathImgBanda") + "semfoto.jpg");
				imgBytes = new byte[(int)img.length()];
				final FileInputStream fis = new FileInputStream(img);
				fis.read(imgBytes);	
				fis.close();
			}
			return imgBytes;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getImagem(oidVeiculoInspcao long)");
		}
	}

	public void saveImagem(Stif_Veiculo_InspecaoED ed) throws Excecoes {
		try {
			try {
				final File img = new File(System.getProperty ("pathImgBanda") + ed.getOid_Veiculo_Inspecao()+".jpg");
				byte []array = new byte[(int)img.length()];
				final FileInputStream fis = new FileInputStream(img);
				fis.read(array);
				final Connection con = sql.getConnection();
				con.setAutoCommit(false);
				final PreparedStatement ps = con.prepareStatement("Update Stif_Veiculos_Inspecoes set imagem = ? where oid_Veiculo_Inspecao = ?");
				ps.setBytes(1, array);
				ps.setLong(2, ed.getOid_Veiculo_Inspecao());
				ps.executeUpdate();
				ps.close();
				fis.close();
				img.delete();
			} catch (IOException io) {
				//do nothing
			} finally {
				final Connection con = sql.getConnection();
				final PreparedStatement ps = con.prepareStatement("Update Stif_Veiculos_Inspecoes set tx_observacao_imagem = ? where oid_Veiculo_Inspecao = ?");
				ps.setString(1, ed.getTx_Observacao_Imagem());
				ps.setLong(2, ed.getOid_Veiculo_Inspecao());
				ps.executeUpdate();
				ps.close();
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"saveImagem()");
		}
	}

	private Stif_Veiculo_InspecaoED populaRegistro(ResultSet rs) throws SQLException {
		final Stif_Veiculo_InspecaoED ed = (Stif_Veiculo_InspecaoED) beanProcessor.toBean(rs, Stif_Veiculo_InspecaoED.class);
		/** fetch-join */
		ed.setVeiculoED((VeiculoED) beanProcessor.toBean(rs, VeiculoED.class));
		ed.formataMsgStamp();
		return ed;
	}
}
