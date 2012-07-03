package com.master.bd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.master.ed.BandaED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;

/**
 * @author Régis
 * @serial Bandas
 * @serialData 09/2007
 */
public class BandaBD extends BancoUtil {

	private ExecutaSQL executasql;

	String sql = null;

	public BandaBD(ExecutaSQL sql) {
		super(sql);
		this.executasql = sql;
	}

	public BandaED inclui(BandaED ed) throws Excecoes {
		try {
			//ed.setOid_Banda(getAutoIncremento("oid_Banda", "Bandas"));
			sql = "INSERT INTO Bandas (" +
			//"oid_Banda," +
			"cd_banda " +
			",nm_Banda " +
			",tx_Descricao " +
			",nr_Largura " +
			",nr_Profundidade " +
			",oid_Fabricante_Banda " +
			",dm_Aplicacao " +
			",dm_Tipo_Pneu " +
			",dm_Eixo " +
			",dm_Fora_Uso " +
			",dm_Stamp" +
            ",dt_Stamp" +
	  	    ",usuario_Stamp"+
			") " +
			" VALUES ('" +
			//ed.getOid_Banda() +	",'" + 
			ed.getCd_Banda() + "' " + 
			",'" + ed.getNm_Banda() + "' " +
			",'" + ed.getTx_Descricao() + "' " +
			", " + ed.getNr_Largura() +
			", " + ed.getNr_Profundidade() +
			", " + ed.getOid_Fabricante_Banda() +
			",'" + ed.getDm_Aplicacao() + "' " +
			",'" + ed.getDm_Tipo_Pneu() + "' " +
			",'" + ed.getDm_Eixo() + "' " +
			",'" + ed.getDm_Fora_Uso() + "' " +
			",'I'" +
	  		",'" + ed.getDt_stamp() + "'" +
	  		",'" + ed.getUsuario_Stamp() + "'" +
	  		")";
			executasql.executarUpdate(sql);
			ed.setOid_Banda(getSeq("Bandas_oid_Banda_seq"));
			
			// Altera a imagem
			if ( "S".equals(ed.getDm_Grava_Imagem()) ) {
				this.setImagem(ed.getOid_Banda(),ed.getOid_Usuario()+".jpg");
				//this.setImagemNova(ed.getOid_Banda(),ed.getOid_Usuario()+".jpg");
			}	
			
			return ed;
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"inclui(BandaED ed)");
		}
	}

	public void altera(BandaED ed) throws Excecoes {
		try {
			sql = "UPDATE Bandas SET " +
			"nm_Banda = '" + ed.getNm_Banda() + "' " +
			",cd_Banda = '" + ed.getCd_Banda() + "' " +
			",tx_Descricao = '" + ed.getTx_Descricao() + "' " +
			",nr_Largura = " + ed.getNr_Largura() +
			",nr_Profundidade = " + ed.getNr_Profundidade() +
			",dm_Aplicacao = '" + ed.getDm_Aplicacao() + "' " +
			",dm_Tipo_Pneu = '" + ed.getDm_Tipo_Pneu() + "' " +
			",dm_Eixo = '" + ed.getDm_Eixo() + "' " +
			",dm_Fora_Uso = '" + ed.getDm_Fora_Uso() + "' " +
			",dm_Substituir = '" + ed.getDm_Substituir() + "' " +
			",oid_Banda_Substituta = " + ("true".equals(ed.getDm_Substituir())? ed.getOid_Banda_Substituta():0) + 
			",dt_Stamp = '" + ed.getDt_stamp() + "' " +
			",usuario_Stamp = '" + ed.getUsuario_Stamp() + "' " + 
			",dm_Stamp = '" + ed.getDm_Stamp() + "' " +
			"WHERE " +
			"oid_Banda = " + ed.getOid_Banda();
			executasql.executarUpdate(sql);
			// Altera a imagem
			if ( "S".equals(ed.getDm_Grava_Imagem()) ) {
				this.setImagem(ed.getOid_Banda(),ed.getOid_Usuario()+".jpg");
				//this.setImagemNova(ed.getOid_Banda(),ed.getOid_Usuario()+".jpg");
			}

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"altera(BandaED ed)");
		}
	}

	public void delete(BandaED ed) throws Excecoes {
		try {
			sql = "DELETE FROM Bandas " +
			"WHERE " +
			"oid_Banda = " + ed.getOid_Banda();
			executasql.executarUpdate(sql);
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"delete(BandaED ed)");
		}
	}

	public ArrayList<BandaED> lista(BandaED ed) throws Excecoes {

		ArrayList<BandaED> list = new ArrayList<BandaED>();
		try {
			sql = "SELECT * "       +
	  		",b.usuario_Stamp as usu_Stmp " +
	  		",b.dt_Stamp as dt_Stmp " +
	  		",b.dm_Stamp as dm_Stmp " +
			"FROM Bandas as b " +
			"left join Fabricantes_Bandas as f on f.oid_Fabricante_Banda = b.oid_Fabricante_Banda " +
			"WHERE " +
			"1 = 1 " ;
			if (ed.getOid_Banda()>0) 
				sql += "and b.oid_Banda = " + ed.getOid_Banda();
			else {
				sql +=  "and f.oid_fabricante_banda is not null ";
				if (doValida(ed.getCd_Banda()))
					sql += " and b.cd_Banda = '" + ed.getCd_Banda() + "' ";
				if (doValida(ed.getNm_Banda()))
					sql += " and b.nm_Banda like '%" + ed.getNm_Banda() + "%' ";
				if (ed.getOid_Fabricante_Banda()>0) 
					sql += " and b.oid_Fabricante_Banda = " + ed.getOid_Fabricante_Banda();
				
				if (doValida(ed.getDm_Escapa_Substituida())) 
					sql += " and (b.dm_Substituir = 'false' or  b.dm_Substituir is null) " ;
				
				if (doValida(ed.getDm_Aplicacao()))
					sql += " and b.dm_Aplicacao = '" + ed.getDm_Aplicacao() + "' ";
				if (doValida(ed.getDm_Eixo()))
					sql += " and b.dm_Eixo = '" + ed.getDm_Eixo() + "' ";
				if (doValida(ed.getDm_Tipo_Pneu()))
					sql += " and b.dm_Tipo_Pneu = '" + ed.getDm_Tipo_Pneu() + "' ";
			}
			sql += "ORDER BY " +
			"b.nm_Banda";
			ResultSet res = this.executasql.executarConsulta(sql);
			while (res.next()) {
				list.add(populaRegistro(res));
			}
			return list;

		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(BandaED ed)");
		}
	}

	public byte[] getImagem(BandaED ed) throws Excecoes {
		byte[] imgBytes = null;
		try {
			sql = "Select imagem from Bandas " +
			"where oid_Banda = "+ ed.getOid_Banda();
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
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(BandaED ed)");
		}
	}

	public void setImagem(long oid,String pArquivo) throws Excecoes {
		PreparedStatement ps = null;
		try {
			File img = new File(System.getProperty ("pathImgBanda") + pArquivo);
			byte []array = new byte[(int)img.length()];
			FileInputStream fis = new FileInputStream(img);
			fis.read(array);
			sql="Update Bandas set imagem = ? where oid_Banda = ?" ;
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
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"lista(BandaED ed)");
		}
	}

	public BandaED getByRecord(BandaED ed) throws Excecoes {
		try {
			ArrayList<BandaED> lista = this.lista(ed);
			if(!lista.isEmpty()) {
				return (BandaED) lista.get(0);
			}
		} catch (Exception exc) {
			throw new Excecoes(exc.getMessage(), exc, this.getClass().getName(),"getByRecord(BandaED ed)");
		}
		return new BandaED();
	}
	
	private BandaED populaRegistro(ResultSet res) throws SQLException {
		BandaED ed = new BandaED();
		ed.setOid_Banda(res.getInt("oid_Banda"));
		ed.setCd_Banda(res.getString("cd_Banda"));
		ed.setNm_Banda(res.getString("nm_Banda"));
		ed.setNm_Fabricante_Banda(res.getString("nm_Fabricante_Banda"));
		ed.setTx_Descricao(doValida(res.getString("tx_Descricao"))?res.getString("tx_Descricao"):"");
		ed.setNr_Largura(res.getDouble("nr_Largura"));
		ed.setNr_Profundidade(res.getDouble("nr_Profundidade"));
		ed.setOid_Fabricante_Banda(res.getInt("oid_Fabricante_Banda"));
		
		ed.setDm_Aplicacao(res.getString("dm_Aplicacao"));
		ed.setNm_Aplicacao(getNmAplicacao(res.getString("dm_Aplicacao")));
		
		ed.setDm_Tipo_Pneu(res.getString("dm_Tipo_Pneu"));
		ed.setNm_Tipo_Pneu(getNmTipoPneu(res.getString("dm_Tipo_Pneu")));
		
		ed.setDm_Eixo(res.getString("dm_Eixo"));
		ed.setNm_Eixo(getNmEixo(res.getString("dm_Eixo")));
		
		ed.setDm_Fora_Uso(doValida(res.getString("dm_Fora_Uso"))?res.getString("dm_Fora_Uso"):"false");
		
		ed.setDm_Substituir(doValida(res.getString("dm_Substituir"))?res.getString("dm_Substituir"):"false");
		
		ed.setOid_Banda_Substituta(res.getInt("oid_Banda_Substituta"));
		
		ed.setUsuario_Stamp(res.getString("usu_Stmp"));
		ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
		return ed;
	}
	
	private String getNmEixo(String dm) {
		String nm=null;
		   if ("1".equals(dm)) 		{ nm="LIVRE"; } 	
		   else if ("2".equals(dm)) { nm="TRAÇÃO"; } 
		   else if ("3".equals(dm)) { nm="TRAÇÃO MODERADA"; }
		   else if ("4".equals(dm)) { nm="QUALQUER"; }
		   else 					{ nm="N/INF"; }
		return nm;
	}
	
	private String getNmTipoPneu(String dm) {
		String nm=null;
		   if ("D".equals(dm)) 		{ nm="DIAGONAL"; } 	
		   else if ("R".equals(dm)) { nm="RADIAL"; }
		   else if ("Q".equals(dm)) { nm="QUALQUER"; }
		   else 					{ nm="N/INF"; }
		return nm;
	}
	
	private String getNmAplicacao(String dm) {
		String nm=null;
		   if ("1".equals(dm)) 		{ nm="RODOVIÁRIO"; } 	
		   else if ("2".equals(dm)) { nm="URBANO"; } 
		   else if ("3".equals(dm)) { nm="MISTO"; }
		   else if ("4".equals(dm)) { nm="SEVERO"; }
		   else if ("5".equals(dm)) { nm="LEVES"; }
		   else if ("6".equals(dm)) { nm="QUALQUER"; }
		   else 					{ nm="N/INF"; }
		return nm;
	}
}
