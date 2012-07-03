/*
 * Created on 12/11/2004
 *
 */
package com.master.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.master.ed.Modelo_PneuED;
import com.master.util.BancoUtil;
import com.master.util.Excecoes;
import com.master.util.FormataData;
import com.master.util.bd.ExecutaSQL;
import com.master.util.Data;

/**
 * @author Régis
 * @serial Modelos de pneus
 * @serialData 06/2007
 */
public class Modelo_PneuBD  extends BancoUtil {
	
  private ExecutaSQL executaSQL;
  
  String sql = null;
  
  public Modelo_PneuBD (ExecutaSQL executaSQL) {
	  super(executaSQL);  
	  this.executaSQL = executaSQL;
  }

    public Modelo_PneuED inclui (Modelo_PneuED ed) throws Excecoes {
	  try {
		  ed.setDt_stamp(Data.getDataDMY());
		  sql = "INSERT INTO Modelos_Pneus (" +
		  "oid_Empresa " +
		  ",cd_Modelo_Pneu " +
		  ",nm_Modelo_Pneu " +
		  ",oid_Fabricante_Pneu "+
		  ",dm_Tipo_Pneu "+
		  ",dm_Stamp" +
		  ",dt_Stamp" +
		  ",usuario_Stamp"+
		  ") " +
		  "values (" + 
		  ed.getOid_Empresa () +
		  " ,'" + (doValida(ed.getCD_Modelo_Pneu()) ? ed.getCD_Modelo_Pneu() : "") + 
		  "','" + ed.getNM_Modelo_Pneu () +
		  "'," + ed.getOid_Fabricante_Pneu() +
		  ",'" + ed.getDm_Tipo_Pneu() +
		  "','I'" +
		  ",'" + ed.getDt_stamp() + "'" +
		  ",'" + ed.getUsuario_Stamp() + "'" +
		  ")";
		  executaSQL.executarUpdate (sql);
		  ed.setOid_Modelo_Pneu(getSeq("Modelos_Pneus_oid_Modelo_Pneu_seq"));
		  return ed;
	  }
	  catch (SQLException e) {
		  throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "inclui (Modelo_PneuED ed)");
	  }
  }

  public void altera (Modelo_PneuED ed) throws Excecoes {
	  try {
		  sql = "UPDATE Modelos_Pneus SET " ;
		  sql+= "cd_modelo_pneu = " + ed.getCD_Modelo_Pneu () ;
		  sql+= ",nm_modelo_pneu = '" + ed.getNM_Modelo_Pneu () + "' " ;
		  sql+= ",dm_Tipo_Pneu = '" + ed.getDm_Tipo_Pneu() + "' " ;
		  sql+= ",dm_Stamp = 'A'" +
	  	   		",dt_Stamp = '" + Data.getDataDMY() + "' " +
	  	   		",usuario_Stamp = '"+ed.getUsuario_Stamp() + "' ";
		  sql+= ",oid_Fabricante_Pneu = " + ed.getOid_Fabricante_Pneu() +" "  ;
		  sql+= "where " ;
		  sql+= "oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu ();
		  
		  executaSQL.executarUpdate (sql);
	  }
	  catch (SQLException e) {
		  throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "altera (Modelo_PneuED ed)");
	  }
  }

  public void delete (Modelo_PneuED ed) throws Excecoes {
	  try {
		  sql = "DELETE FROM Modelos_Pneus " +
		  "WHERE " +
		  "oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu ();
		  executaSQL.executarUpdate (sql);
	  }
	  catch (SQLException e) {
		  throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "delete (Modelo_PneuED ed)");
	  }
  }

  public ArrayList lista (Modelo_PneuED ed) throws Excecoes {

	  ArrayList list = new ArrayList ();
	  try {
		  sql = "SELECT * " +
		  		",m.usuario_Stamp as usu_Stmp " +
		  		",m.dt_Stamp as dt_Stmp " +
		  		",m.dm_Stamp as dm_Stmp " +
		  "FROM " +
		  "Modelos_Pneus as m " +
		  "left join Fabricantes_Pneus as f on f.oid_Fabricante_Pneu = m.oid_Fabricante_Pneu " +
		  "WHERE 1=1 " ;
		  
		  if (ed.getOid_Modelo_Pneu () > 0) 
			  sql += " and m.oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu () + " ";
		  else{
			  if (ed.getOid_Fabricante_Pneu() > 0)
				  sql += " and m.oid_Fabricante_Pneu = " + ed.getOid_Fabricante_Pneu() + " ";
			  if (doValida(ed.getCD_Modelo_Pneu())) 
				  sql += " and m.cd_Modelo_Pneu = '" + ed.getCD_Modelo_Pneu () + "' ";
			  if (doValida(ed.getNM_Modelo_Pneu())) 
				  sql += " and m.nm_Modelo_Pneu like '%" + ed.getNM_Modelo_Pneu () + "%' ";
		  	}
		  sql+= " ORDER BY " +
		  "f.nm_Fabricante_Pneu,m.nm_Modelo_Pneu";
		  ResultSet rs = executaSQL.executarConsulta (sql);
		  while (rs.next ()) {
			  list.add (populaRegistro(rs));
		  }
	  }
	  catch (SQLException e) {
		  throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "lista (Modelo_PneuED ed)");
	  }
	  return list;
  }

  public ArrayList listaLookUp (Modelo_PneuED ed) throws Excecoes {

	  ArrayList list = new ArrayList ();
	  try {
		  sql = "SELECT * " +
		  		",m.usuario_Stamp as usu_Stmp " +
		  		",m.dt_Stamp as dt_Stmp " +
		  		",m.dm_Stamp as dm_Stmp " +
		  "FROM " +
		  "Modelos_Pneus as m " +
		  "left join Fabricantes_Pneus as f on f.oid_Fabricante_Pneu = m.oid_Fabricante_Pneu " +
		  "WHERE 1=1 " ;
		  if (ed.getOid_Modelo_Pneu() > 0)
			  sql += " and m.oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu() + " ";
		  else {
			  if (ed.getOid_Fabricante_Pneu() > 0)
				  sql += " and m.oid_Fabricante_Pneu = " + ed.getOid_Fabricante_Pneu() + " ";
			  if (doValida(ed.getCD_Modelo_Pneu())) 
				  sql += " and m.nm_Modelo_Pneu like '%" + ed.getCD_Modelo_Pneu () + "%' ";
			  if (doValida(ed.getNM_Modelo_Pneu())) 
				  sql += " and m.nm_Modelo_Pneu like '%" + ed.getNM_Modelo_Pneu () + "%' ";
		  }	  
		  sql+= " ORDER BY " +
		  "f.nm_Fabricante_Pneu,m.nm_Modelo_Pneu";
		  ResultSet rs = executaSQL.executarConsulta (sql);
		  while (rs.next ()) {
			  list.add (populaRegistro(rs));
		  }
	  }
	  catch (SQLException e) {
		  throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "lista (Modelo_PneuED ed)");
	  }
	  return list;
  }

  public Modelo_PneuED getByRecord (Modelo_PneuED ed , int oid) throws Excecoes {
    ArrayList lista = new ArrayList();
    Iterator iterator = lista.iterator ();
    if (iterator.hasNext ()) {
      return (Modelo_PneuED) iterator.next ();
    }
    else {
      return new Modelo_PneuED ();
    }
  }
  
  public Modelo_PneuED getByRecord (Modelo_PneuED ed ) throws Excecoes {
	  Modelo_PneuED pnED = new Modelo_PneuED();
	  try {
		  sql="SELECT m.*," +
	  		  " f.nm_Fabricante_Pneu" +
	  		  ",m.usuario_Stamp as usu_Stmp " +
	  		  ",m.dt_Stamp as dt_Stmp " +
	  		  ",m.dm_Stamp as dm_Stmp " +
	  		  "FROM " +
	  		  "Modelos_Pneus as m " +
	  		  "left join Fabricantes_Pneus as f on m.oid_Fabricante_Pneu = f.oid_Fabricante_Pneu " +
	  		  " WHERE 1=1 " ;
		  
		  if (ed.getOid_Modelo_Pneu () > 0) 
			  sql += " and m.oid_Modelo_Pneu = " + ed.getOid_Modelo_Pneu () + " ";
		  else{
			  if (ed.getOid_Fabricante_Pneu() > 0)
				  sql += " and m.oid_Fabricante_Pneu = " + ed.getOid_Fabricante_Pneu() + " ";
			  if (doValida(ed.getCD_Modelo_Pneu())) 
				  sql += " and m.cd_Modelo_Pneu = '" + ed.getCD_Modelo_Pneu () + "' ";
			  if (doValida(ed.getNM_Modelo_Pneu())) 
				  sql += " and m.nm_Modelo_Pneu = '" + ed.getNM_Modelo_Pneu () + "' ";
		  	}
		  sql+= " ORDER BY " +
		  "m.nm_Modelo_Pneu";
		  ResultSet rs = executaSQL.executarConsulta (sql);
		  while (rs.next ()) {
			  pnED = populaRegistro(rs);
		  }
	  }
	  catch (SQLException e) {
		  throw new Excecoes (e.getMessage () , e , this.getClass ().getName () , "lista (Modelo_PneuED ed)");
	  }
	  return pnED;
  }

  
  
  public Modelo_PneuED getByRecord (int oid) throws Excecoes {
	    ArrayList lista = new ArrayList();
	    Iterator iterator = lista.iterator ();
	    if (iterator.hasNext ()) {
	      return (Modelo_PneuED) iterator.next ();
	    }
	    else {
	      return new Modelo_PneuED ();
	    }
	  }
  public Modelo_PneuED getByCodigo (String codigo) throws Excecoes {
    ArrayList lista = new ArrayList();
    Iterator iterator = lista.iterator ();
    if (iterator.hasNext ()) {
      return (Modelo_PneuED) iterator.next ();
    }
    else {
      return new Modelo_PneuED ();
    }
  }
  
  public Modelo_PneuED populaRegistro(ResultSet res) throws SQLException {
	  Modelo_PneuED ed = new Modelo_PneuED();
	  ed.setOid_Modelo_Pneu(res.getInt("oid_Modelo_Pneu"));
	  ed.setOid_Empresa(res.getInt("oid_Empresa"));
	  ed.setCD_Modelo_Pneu(res.getString("cd_Modelo_Pneu"));
	  ed.setNM_Modelo_Pneu(res.getString("nm_Modelo_Pneu"));
	  ed.setDm_Tipo_Pneu(res.getString("dm_Tipo_Pneu"));
	  ed.setOid_Fabricante_Pneu(res.getInt("oid_Fabricante_Pneu"));
	  ed.setNm_Fabricante_Pneu(res.getString("nm_Fabricante_Pneu"));
	  ed.setUsuario_Stamp(res.getString("usu_Stmp"));
	  ed.setMsg_Stamp(("I".equals(res.getString("dm_Stmp"))? "Incluído":"Alterado") + " por " + res.getString("usu_Stmp")+ " em " + FormataData.formataDataBT(res.getString("dt_Stmp")));
	  return ed;	  
  }
  
}