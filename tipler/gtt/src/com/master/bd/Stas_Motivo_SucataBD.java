package com.master.bd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.Stas_Motivo_SucataED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Stas - Itens a inspecionar
 * @serialData 05/2012
 */
public class Stas_Motivo_SucataBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public Stas_Motivo_SucataBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public Stas_Motivo_SucataED inclui(Stas_Motivo_SucataED ed) throws Excecoes {
		try {
			sql = "INSERT INTO Stas_Motivos_Sucatas (" +
			"cd_Motivo_Sucata" +
			",nm_Motivo_Sucata " +
			",tx_Motivo_Sucata " +
			",tx_Recomendacao " +
			",nm_Motivo_Sucata_E " +
			",tx_Motivo_Sucata_E " +
			",tx_Recomendacao_E " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES (" +
			" '" + ed.getCd_Motivo_Sucata() + "'"+	
			",'" + ed.getNm_Motivo_Sucata() + "'"+
			",'" + ed.getTx_Motivo_Sucata() + "'"+
			",'" + ed.getTx_Recomendacao() + "'"+
			",'" + ed.getNm_Motivo_Sucata_E() + "'"+
			",'" + ed.getTx_Motivo_Sucata_E() + "'"+
			",'" + ed.getTx_Recomendacao_E() + "'"+
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Motivo_Sucata(getSeq("Stas_Motivos_Sucatas_oid_Motivo_Sucata_seq"));
			
			if ( "S".equals(ed.getDm_Grava_Imagem()) ) {
				this.setImagem(ed.getOid_Motivo_Sucata(),ed.getOid_Usuario()+".jpg");
				//this.setImagemNova(ed.getOid_Banda(),ed.getOid_Usuario()+".jpg");
			}
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(Stas_Motivos_SucatasED ed)");
		}
	}

	public void altera(Stas_Motivo_SucataED ed) throws Excecoes {
		try {
			sql = "UPDATE Stas_Motivos_Sucatas SET " +
			" cd_Motivo_Sucata = '" + ed.getCd_Motivo_Sucata() + "' " +
			",nm_Motivo_Sucata = '" + ed.getNm_Motivo_Sucata() + "' " +
			",tx_Motivo_Sucata = '" + ed.getTx_Motivo_Sucata() + "' " +
			",tx_Recomendacao = '" + ed.getTx_Recomendacao() + "' " +
			",nm_Motivo_Sucata_E = '" + ed.getNm_Motivo_Sucata_E() + "' " +
			",tx_Motivo_Sucata_E = '" + ed.getTx_Motivo_Sucata_E() + "' " +
			",tx_Recomendacao_E = '" + ed.getTx_Recomendacao_E() + "' " +
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Motivo_Sucata = " + ed.getOid_Motivo_Sucata();
			executasql.executarUpdate(sql);
			if ( "S".equals(ed.getDm_Grava_Imagem()) ) {
				this.setImagem(ed.getOid_Motivo_Sucata(),ed.getOid_Usuario()+".jpg");
				//this.setImagemNova(ed.getOid_Banda(),ed.getOid_Usuario()+".jpg");
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(Stas_Motivos_SucatasED ed)");
		}
	}
	
	public byte[] getImagem(Stas_Motivo_SucataED ed) throws Excecoes {
		byte[] imgBytes = null;
		try {
			sql = "Select imagem from Stas_Motivos_Sucatas " +
			"where oid_Motivo_Sucata = "+ ed.getOid_Motivo_Sucata();
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				 imgBytes = res.getBytes("imagem");
 			}
			if (imgBytes == null ) { // se não achou imagem na base de dados, coloca uma padrão
				File img = new File(System.getProperty ("pathImgBanda") + "semfoto.jpg");
				imgBytes = new byte[(int)img.length()];
				FileInputStream fis = new FileInputStream(img);
				fis.read(imgBytes);	
				fis.close();
			}
			return imgBytes;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stas_Motivo_SucataED ed)");
		}
	}

	public void setImagem(long oid,String pArquivo) throws Excecoes {
		PreparedStatement ps = null;
		try {
			File img = new File(System.getProperty ("pathImgBanda") + pArquivo);
			byte []array = new byte[(int)img.length()];
			FileInputStream fis = new FileInputStream(img);
			fis.read(array);
			sql="Update Stas_Motivos_Sucatas set imagem = ? where oid_Motivo_Sucata = ?" ;
			Connection con = this.executasql.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql);
			ps.setBytes(1, array);
			ps.setLong(2, oid);
			ps.executeUpdate();
			ps.close();
			fis.close();
			img.delete();
		} catch (IOException io) {
			// Faz nada
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stas_Motivo_SucataED ed)");
		}
	}

	
	public void delete(Stas_Motivo_SucataED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Stas_Motivos_Sucatas " +
			"WHERE " +
			"oid_Motivo_Sucata = " + ed.getOid_Motivo_Sucata();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(Stas_Motivos_SucatasED ed)");
		}
	}

	public ArrayList<Stas_Motivo_SucataED> lista(Stas_Motivo_SucataED ed) throws Excecoes {
		ArrayList<Stas_Motivo_SucataED> list = new ArrayList<Stas_Motivo_SucataED>();
		try {
			sql = "SELECT * " +
			",usuario_Stamp as usu_Stmp " +
	  		",dt_Stamp as dt_Stmp " +
	  		",dm_Stamp as dm_Stmp " +
			"FROM Stas_Motivos_Sucatas as bd " +
			"WHERE " +
			"1 = 1 " ;
			if (ed.getOid_Motivo_Sucata()>0) 
				sql += " and oid_Motivo_Sucata = " + ed.getOid_Motivo_Sucata() + " ";
			else {
				if (doValida(ed.getCd_Motivo_Sucata()))
					sql += " and cd_Motivo_Sucata = '" + ed.getCd_Motivo_Sucata() + "' "   ;
				if (doValida(ed.getNm_Motivo_Sucata()))
					sql += " and nm_Motivo_Sucata like '%" + ed.getNm_Motivo_Sucata() + "%' "   ;
			}
			sql += "ORDER BY " +
			"cd_Motivo_Sucata ";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(Stas_Motivos_SucatasED ed)");
		}
	}

	public Stas_Motivo_SucataED getByRecord(Stas_Motivo_SucataED ed) throws Excecoes {
		try {
			ArrayList<Stas_Motivo_SucataED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (Stas_Motivo_SucataED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(Stas_Motivos_SucatasED ed)");
		}
		return new Stas_Motivo_SucataED();
	}
	
	private Stas_Motivo_SucataED populaRegistro(ResultSet res) throws SQLException {
		Stas_Motivo_SucataED ed = new Stas_Motivo_SucataED();
		ed.setOid_Motivo_Sucata(res.getInt("oid_Motivo_Sucata"));
		ed.setCd_Motivo_Sucata(res.getString("cd_Motivo_Sucata"));
		ed.setNm_Motivo_Sucata(res.getString("nm_Motivo_Sucata"));
		ed.setTx_Motivo_Sucata(res.getString("tx_Motivo_Sucata"));
		ed.setTx_Recomendacao(res.getString("tx_Recomendacao"));
		ed.setNm_Motivo_Sucata_E(res.getString("nm_Motivo_Sucata_E"));
		ed.setTx_Motivo_Sucata_E(res.getString("tx_Motivo_Sucata_E"));
		ed.setTx_Recomendacao_E(res.getString("tx_Recomendacao_E"));
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}

}
