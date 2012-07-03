//Source file: C:\\eclipse\\workspace\\BugTracking\\src\\jFiles\\ManipulaTarefa.java

package jFiles;
import java.sql.*;
import java.util.*;
import com.master.util.*;

/**
 */
public class ManipulaTarefa extends DbTransacao 
{
   
   /**
    * Creates new ManipulaTarefa
    * @roseuid 40BE623603B5
    */
   public ManipulaTarefa() 
   {

   }
   
   
   /**
    * @param tarefa
    * @roseuid 40BE623603BF
    */
   public void incluirTarefasUsuario(Tarefas tarefa) 
   {
   		String nmSituacao = getNomeSituacaoTarefa(tarefa.getIdSituacaoTarefa().intValue());
        
        try {
        	iniciaTransacaoBD();
        	System.out.println("incluirTarefasUsuario 1");
        	//lockTable("tarefas_bug_tracking", "INCTAREFA");
        	System.out.println("incluirTarefasUsuario 2");
        	
        	String sqlAux = "select max(idtarefa) as result from tarefas_bug_tracking";
            ResultSet rsAux = executaSql(sqlAux);
        	System.out.println("incluirTarefasUsuario 3");
            long id = 0;
            if (rsAux != null && rsAux.next()){
            	id = rsAux.getLong("result");
            	id++;
            }
        	System.out.println("incluirTarefasUsuario 4");
            
            String sql = "INSERT INTO TAREFAS_BUG_TRACKING " +
            		"(idtarefa, TITULO, " +
            		"IDTIPOSOLICITACAO, " +
            		"IDCLASSIFICACAOUSUARIO, ";
            
               		
                        if (tarefa.getOperacaoImpedida() != null && !tarefa.getOperacaoImpedida().equals("")){
                            sql += "OPERACAOIMPEDIDA, ";
        		}
                        if (tarefa.getDescricao() != null && !tarefa.getDescricao().equals("")){
                            sql += "DESCRICAO, ";
        		}
                        if (tarefa.getAnexo() != null && !tarefa.getAnexo().equals("")){
                            sql += "ANEXO, ";
        		}
                        sql += "CAMINHOMENU, " + 
            		"IDUSUARIOSOLICITACAO, " +
            		"IDSITUACAOTAREFA, " +
            		"DTSOLICITADO, " +
            		"HRSOLICITADO" +
            		") " +
            		"VALUES " +
            		"((max(TAREFAS_BUG_TRACKING.idtarefa)+1), " +
            		"'" + tarefa.getTitulo() + "', " +
            		"" + tarefa.getIdTipoSolicitacao().longValue() + ", " +
            		"" + tarefa.getIdClassificacaoUsuario() + ", ";
            		if (tarefa.getOperacaoImpedida() != null && !tarefa.getOperacaoImpedida().equals("")){
                            sql += "'" + tarefa.getOperacaoImpedida() + "', ";
        		}
            		if (tarefa.getDescricao() != null && !tarefa.getDescricao().equals("")){
                            sql += "'" + tarefa.getDescricao() + "', ";
       			}
            		if (tarefa.getAnexo() != null && !tarefa.getAnexo().equals("")){
                            sql += "'" + tarefa.getAnexo() + "', ";
       			}

                        if (tarefa.getDataIni() != null && !tarefa.getDataIni().equals("")){
                            sql += "'" + tarefa.getDataIni() + "', ";
       			}
                        if (tarefa.getDataPrevista() != null && !tarefa.getDataPrevista().equals("")){
                            sql += "'" + tarefa.getDataPrevista() + "', ";
       			}
                        if (tarefa.getTempoCorrecao() != null && !tarefa.getTempoCorrecao().equals("")){
                            sql += "'" + tarefa.getTempoCorrecao() + "', ";
       			}
            		if (tarefa.getValor() != null && !tarefa.getValor().equals("")){
                            sql += "'" + tarefa.getValor() + "', ";
       			}
            		if (tarefa.getCentroCusto() != null && !tarefa.getCentroCusto().equals("")){
                            sql += "'" + tarefa.getCentroCusto() + "', ";
       			}
                        sql += "'" + tarefa.getCaminhoMenu() + "', " +
       				"" + tarefa.getIdUsuarioSolicitacao() + ", " +
            		"" + tarefa.getIdSituacaoTarefa() + ", " +
            		"'"+ tarefa.getDtSolicitacao() +"', " + 
            		"'" + tarefa.getHrSolicitacao() + "')";
 

            System.out.println("sql = " + sql);
            		
            executaUpdate(sql);
            //unlockTable("INCTAREFA");

            System.out.println("incluirTarefasUsuario 5");
            
            Email email = new Email();
			//System.out.println("vai mandar e-mail");
			try {
				ManipulaUsuario man = new ManipulaUsuario();
                                Usuario usuario = man.getUsuarioById(tarefa.getIdUsuarioSolicitacao().longValue());
                                String assunto = "InfoTracking - Nova Tarefa. Nro.:" + id;
				String para = "";
                                Vector admins = man.getAdministradores();
                                for (int i=0; i < admins.size(); i++){
                                    para += ((Usuario)admins.elementAt(i)).getEmail() + ";"; 
                                }
                para = "sistema@deltaguialogistica.com.br";//para.substring(0, para.length() - 1); 
                System.out.println("para = " + para);
                String de = "sistema@deltaguialogistica.com.br";
                String mensagem ="Dados da nova tarefa: \n\n";
                mensagem += "Nro: " + id + " \n";
                mensagem += "Titulo: " + tarefa.getTitulo() + " \n";
                mensagem += "Cadastrada por: "+ usuario.getNome()  + " \n";
                mensagem += "Descricao: " + tarefa.getDescricao() + " \n\n\n ";
                mensagem += "Administracao InfoTracking. \n Desenvolvimento de Sistema. \n Delta Guia Logistica.";
				email.sendMail(assunto, para, de, mensagem);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
            
        } catch (Exception ex) {

            ex.printStackTrace();
        
        } finally {
        	unlockTable("INCTAREFA");
        	finalizaTransacaoBD();
        }
   }
   


   public void alterarTarefasUsuario(Tarefas tarefa) 
   {
        Tarefas tarefaAtual = getTarefasById(tarefa.getIdTarefa());
        String nmSituacao = getNomeSituacaoTarefa(tarefa.getIdSituacaoTarefa().intValue());
        
        try {

            iniciaTransacaoBD();
            String sql = "UPDATE TAREFAS_BUG_TRACKING ";
            sql += "set titulo = '" + tarefa.getTitulo() +"', ";
            sql += " idtiposolicitacao = " + tarefa.getIdTipoSolicitacao().longValue() +", ";
            sql += " idclassificacaousuario = " + tarefa.getIdClassificacaoUsuario().longValue() +", ";
            //sql += " idclassificacaoadmin = " + tarefa.getIdClassificacaoAdmin().longValue() +", ";

            if (tarefa.getDataIni() != null && !tarefa.getDataIni().equals("")&& !tarefa.getDataIni().equals("null")){
                sql += "DTINICIO = '" + tarefa.getDataIni() + "', ";
            }
            if (tarefa.getDataPrevista() != null && !tarefa.getDataPrevista().equals("") && !tarefa.getDataPrevista().equals("null")){
                sql += "DTPREVISAOFIM = '" + tarefa.getDataPrevista() + "', ";
            }
            if (tarefa.getHoraEstimada() != null && !tarefa.getHoraEstimada().equals("") && !tarefa.getHoraEstimada().equals("null")){
                    sql += "TEMPOCORRECAO = '" + tarefa.getHoraEstimada() + "', ";
            }
            if (tarefa.getValor() != null && !tarefa.getValor().equals("") && !tarefa.getValor().equals("null")){
                sql += "VLSOLICITACAO = '" + tarefa.getValor() + "', ";
            }
            if (tarefa.getCentroCusto() != null && !tarefa.getCentroCusto().equals("") && !tarefa.getCentroCusto().equals("null")){
                sql += "CENTROCUSTO = '" + tarefa.getCentroCusto() + "', ";
            }

            sql += " operacaoimpedida = '" + tarefa.getOperacaoImpedida() +"', ";
            sql += " caminhomenu = '" + tarefa.getCaminhoMenu() +"', ";
            sql += " idsituacaotarefa = " + tarefa.getIdSituacaoTarefa().longValue() +", ";
            sql += " idusuariocorrecao = " + tarefa.getIdUsuarioCorrecao().longValue() +", ";
            sql += " dtocorrencia = '" + DateFormatter.calendarToString(Calendar.getInstance(), "dd/MM/yyyy") +"', ";
            sql += " descricao = '" + tarefa.getDescricao() + "'";
            sql += " where idtarefa = " + tarefa.getIdTarefa().longValue();


            System.out.println("sql update= " + sql);
            		
            executaUpdate(sql); 
            
            
            //VAI GRAVAR O HISTORICO -------------------------------------------------------------------------
            sql = "INSERT INTO historico_bug_tracking ";   
            sql += "(id_historico, " +
            "id_tarefa , " +
            "acao, " +
            "data, " + 
            "hora, ";
            if (tarefa.getNovaDescricao() != null && !tarefa.getNovaDescricao().equals(""))
                sql +="descricao,";
            
            sql += "nm_usuario) "+
            "VALUES ( "+
            "(max(historico_bug_tracking.id_historico)+1), " +
            "" + tarefa.getIdTarefa() + ", " +
            "'" + nmSituacao + "', " +
            "'" + DateFormatter.calendarToString(Calendar.getInstance(), "dd/MM/yyyy") + "', " +
            "'" + DateFormatter.getHoraHM() + "', ";
            if (tarefa.getNovaDescricao() != null && !tarefa.getNovaDescricao().equals(""))
                sql += "'" + tarefa.getNovaDescricao() + "', ";
            
            sql += "'"+tarefa.getNmUsuarioAlteracao()+"')";  
            
            System.out.println("sql historico= " + sql);
            executaUpdate(sql);
            //------------------------------------------------------------------------------------------------

            //System.out.println(tarefaAtual.getIdSituacaoTarefa().longValue() + " == " + tarefa.getIdSituacaoTarefa().longValue());
            
            Email email = new Email();
            if (tarefaAtual.getIdSituacaoTarefa().longValue() != tarefa.getIdSituacaoTarefa().longValue()){
                //System.out.println("vai mandar e-mail");
                try {
                    ManipulaUsuario man = new ManipulaUsuario();
                    Usuario usuario = man.getUsuarioById(tarefaAtual.getIdUsuarioSolicitacao().longValue());
                    Usuario usuarioResponsavel = new Usuario();
                    String assunto = "";
                    String para = "sistema@deltaguialogistica.com.br";
                    String de = "sistema@deltaguialogistica.com.br";
                    String mensagem ="Dados da tarefa: \n\n";
                    mensagem += "Nro: " + tarefa.getIdSituacaoTarefa().longValue() + " \n";
                    mensagem += "Titulo: " + tarefa.getTitulo() + " \n";
                    mensagem += "Cadastrada por: "+ usuario.getNome()  + " \n";
                    mensagem += "Descricao: " + tarefa.getDescricao() + " \n\n\n ";

                    Vector vHist = getHistorico(tarefa.getIdTarefa().longValue());
                    HistoricoTarefa historico = null;
                    if (vHist != null){
                    	mensagem += "\n\n----------- Histórico -----------\n\n";
                        for (int i = 0; i < vHist.size(); i++){
                        	historico = (HistoricoTarefa) vHist.elementAt(i);
                        	mensagem += "Data: " + historico.getData() + "\n";
                        	mensagem += "Usuário: " + historico.getNm_usuario() + "\n";
                        	mensagem += "Ação: " + historico.getAcao() + "\n";
                        	mensagem += "Descrição: " + historico.getDescricao() + "\n";
                        	mensagem += "---------------------------------\n";
                        }
                    }
                    
                    mensagem += "Administracao InfoTracking. \n Desenvolvimento de Sistema. \n Delta Guia Logistica.";

                    if (tarefa.getIdSituacaoTarefa().longValue() == 5 ){
                        assunto = "InfoTracking - Correcao da Tarefa. Nro.: " + tarefa.getIdTarefa().longValue();
                        para = usuario.getEmail();
                        email.sendMail(assunto, para, de, mensagem);
                    } else if (tarefa.getIdSituacaoTarefa().longValue() == 3 ){
                        assunto = "InfoTracking - A Tarefa. Nro.: " + tarefa.getIdTarefa().longValue() + " foi iniciada.";
                        para = usuario.getEmail();
                        email.sendMail(assunto, para, de, mensagem);
                    } else if (tarefa.getIdSituacaoTarefa().longValue() == 4 ){
                        assunto = "InfoTracking - A Tarefa. Nro.: " + tarefa.getIdTarefa().longValue() + " foi cancelada.";
                        para = usuario.getEmail();
                        if (tarefa.getIdUsuarioCorrecao() != null) {
                            usuarioResponsavel = new ManipulaUsuario().getUsuarioById(tarefa.getIdUsuarioCorrecao().longValue());
                            para += ";"+usuarioResponsavel.getEmail();
                        }
                        email.sendMail(assunto, para, de, mensagem);
                    } else if (tarefa.getIdSituacaoTarefa().longValue() == 2 ){
                    //System.out.println("vai mandar e-mail 2");
                        assunto = "InfoTracking - A Tarefa. Nro.: " + tarefa.getIdTarefa().longValue() + " foi atribuida.";
                        if (tarefa.getIdUsuarioCorrecao() != null) {
                            usuarioResponsavel = new ManipulaUsuario().getUsuarioById(tarefa.getIdUsuarioCorrecao().longValue());
                            para = usuario.getEmail();
                            para += ";" + usuarioResponsavel.getEmail();
                        }
                        email.sendMail(assunto, para, de, mensagem);
                    } else if (tarefa.getIdSituacaoTarefa().longValue() == 9 ){
                        assunto = "InfoTracking - A Tarefa. Nro.: " + tarefa.getIdTarefa().longValue() + " foi rejeitada.";
                        if (tarefa.getIdUsuarioCorrecao() != null) {
                            usuarioResponsavel = new ManipulaUsuario().getUsuarioById(tarefa.getIdUsuarioCorrecao().longValue());
                            para = usuario.getEmail();
                            para += ";" + usuarioResponsavel.getEmail();
                        }
                        email.sendMail(assunto, para, de, mensagem);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {

                try {
                    ManipulaUsuario man = new ManipulaUsuario();
                    Usuario usuario = man.getUsuarioById(tarefaAtual.getIdUsuarioSolicitacao().longValue());
                    Usuario usuarioResponsavel = new Usuario();
                    String de = "sistema@deltaguialogistica.com.br";
                    String para = usuario.getEmail();
                   	usuarioResponsavel = new ManipulaUsuario().getUsuarioById(tarefa.getIdUsuarioCorrecao().longValue());
                    para += ";"+usuarioResponsavel.getEmail();
                    String assunto = "InfoTracking - A Tarefa. Nro.: " + tarefa.getIdTarefa().longValue() + " foi alterada.";

                    String mensagem ="Dados da tarefa: \n\n";
                    mensagem += "Nro: " + tarefa.getIdSituacaoTarefa().longValue() + " \n";
                    mensagem += "Titulo: " + tarefa.getTitulo() + " \n";
                    mensagem += "Cadastrada por: "+ usuario.getNome()  + " \n";
                    mensagem += "Atribuida para: "+ usuarioResponsavel.getNome()  + " \n";
                    mensagem += "Descricao: " + tarefa.getDescricao() + " \n\n\n ";

                    Vector vHist = getHistorico(tarefa.getIdTarefa().longValue());
                    HistoricoTarefa historico = null;
                    if (vHist != null){
                    	mensagem += "\n----------- Historico -----------\n\n";
                        for (int i = 0; i < vHist.size(); i++){
                        	historico = (HistoricoTarefa) vHist.elementAt(i);
                        	mensagem += "Data: " + historico.getData() + "/" + historico.getHora() + "\n";
                        	mensagem += "Usuario: " + historico.getNm_usuario() + "\n";
                        	mensagem += "Acao: " + historico.getAcao() + "\n";
                        	mensagem += "Descricao: " + historico.getDescricao() + "\n";
                        	mensagem += "---------------------------------\n";
                        }
                    }
                    
                    mensagem += "Administracao InfoTracking. \n Desenvolvimento de Sistema. \n Delta Guia Logistica.";

                    email.sendMail(assunto, para, de, mensagem);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            	
            	
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        
        } finally {
        	finalizaTransacaoBD();
        }
   }




   /**
    * Teste
    * @return java.util.Vector
    * @roseuid 40BE62370013
    */
   public Vector getTarefas(Tarefas tarefa) 
   {
    
        Vector retorno = new Vector();
        
        try {
        	iniciaTransacaoBD();
            
            String sql = "SELECT * FROM Tarefas_BUG_TRACKING where 1=1" ;
            
            if (tarefa.getIdUsuarioCorrecao() != null){
            	sql += " and idUsuarioCorrecao = " + tarefa.getIdUsuarioCorrecao().longValue();
            }
            if (tarefa.getIdUsuarioSolicitacao() != null){
            	sql += " and idUsuarioSolicitacao = " + tarefa.getIdUsuarioSolicitacao().longValue();
            }
            if (tarefa.getTitulo() != null && !tarefa.getTitulo().equals("")){
            	sql += " and titulo = '" + tarefa.getTitulo() + "'";
            }
            if (tarefa.getIdSituacaoTarefa() != null){
            	sql += " and idsituacaotarefa = " + tarefa.getIdSituacaoTarefa();
            }
            if (tarefa.getIdTipoSolicitacao() != null){
            	sql += " and idTipoSolicitacao = " + tarefa.getIdTipoSolicitacao().longValue();
            }
            if (tarefa.getIdClassificacaoUsuario() != null){
            	sql += " and idClassificacaoUsuario = " + tarefa.getIdClassificacaoUsuario().longValue();
            }
            if (tarefa.getIdClassificacaoAdmin() != null){
            	sql += " and idClassificacaoAdmin = " + tarefa.getIdClassificacaoAdmin().longValue();
            }
            
            if (tarefa.getDataIni() != null && !tarefa.getDataIni().equals("")){
            	sql += " and dtSolicitado >= " + tarefa.getDataIni() + "";
            }
            if (tarefa.getDataFim() != null && !tarefa.getDataFim().equals("")){
            	sql += " and dtSolicitado <= " + tarefa.getDataFim() + "";
            }
            sql += " ORDER BY dtSolicitado ";
            //System.out.println("SQL PesquisaTarefa = " + sql);
            ResultSet rs = executaSql(sql);
            //System.out.println("Depois do resultset...");
            
            while(rs != null && rs.next()){
                
                Tarefas tarefaRetorno = new Tarefas();

                tarefaRetorno.setIdTarefa(new Long(rs.getLong("idTarefa")));
                tarefaRetorno.setTitulo(rs.getString("titulo"));
                tarefaRetorno.setCaminhoMenu(rs.getString("caminhoMenu"));
                tarefaRetorno.setDescricao(rs.getString("descricao"));
                
                if (rs.getString("dtOcorrencia") != null)
                	tarefaRetorno.setDtOcorrencia(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("dtOcorrencia"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                
                tarefaRetorno.setHrOcorrencia(rs.getString("hrOcorrencia"));
                tarefaRetorno.setHrSolicitacao(rs.getString("hrSolicitado"));
                
                tarefaRetorno.setDtSolicitacao(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("dtSolicitado"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                
                tarefaRetorno.setIdClassificacaoAdmin(new Long(rs.getLong("idClassificacaoAdmin")));
                tarefaRetorno.setIdClassificacaoUsuario(new Long(rs.getLong("idClassificacaoUsuario")));
                tarefaRetorno.setIdSituacaoTarefa(new Long(rs.getLong("idSituacaoTarefa")));
                tarefaRetorno.setIdTipoSolicitacao(new Long(rs.getLong("idTipoSolicitacao")));
                tarefaRetorno.setIdUsuarioCorrecao(new Long(rs.getLong("idUsuarioCorrecao")));
                tarefaRetorno.setIdUsuarioSolicitacao(new Long(rs.getLong("idUsuarioSolicitacao")));
                tarefaRetorno.setOperacaoImpedida(rs.getString("operacaoImpedida"));
                tarefaRetorno.setAnexo(rs.getString("anexo"));
                if (rs.getString("tempoCorrecao") != null)
                    tarefaRetorno.setTempoCorrecao(new Double(rs.getString("tempoCorrecao")));

                if (rs.getString("dtinicio")!=null)
                	tarefaRetorno.setDataIni(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("dtinicio"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                if (rs.getString("DTPREVISAOFIM")!=null)
                	tarefaRetorno.setDataPrevista(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("DTPREVISAOFIM"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                
                tarefaRetorno.setTempoCorrecao(new Double(rs.getDouble("TEMPOCORRECAO")));
                tarefaRetorno.setHoraEstimada(rs.getString("TEMPOCORRECAO"));
                tarefaRetorno.setValor(rs.getString("VLSOLICITACAO"));
                tarefaRetorno.setCentroCusto(rs.getString("CENTROCUSTO"));

                retorno.addElement(tarefaRetorno);

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
        	finalizaTransacaoBD();
        }
        
        return retorno;    
   }
   

   public Vector getHistorico(long id){
   
    
        Vector retorno = new Vector();
        Tarefas tarefaRetorno = new Tarefas();
        HistoricoTarefa hist = new HistoricoTarefa();
        try {
            iniciaTransacaoBD();
            
            String sql = "SELECT * FROM HISTORICO_BUG_TRACKING where id_Tarefa = " + id;
            ResultSet rs = executaSql(sql);
            
            //System.out.println("Depois do resultset...");
            
            while (rs != null && rs.next()){
            	hist = new HistoricoTarefa();
                hist.setIdHistorico(new Long(rs.getLong("id_historico")));
                hist.setIdTarefa(new Long(rs.getLong("id_tarefa")));
                hist.setAcao(rs.getString("acao"));
                hist.setHora(rs.getString("hora"));
                hist.setData(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("data"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                hist.setDescricao((rs.getString("descricao")!=null?rs.getString("descricao"):""));
                hist.setNm_usuario(rs.getString("nm_usuario"));
            
                retorno.addElement(hist);
            
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
        	finalizaTransacaoBD();
        }
        
        return retorno;    
   
   
   }
   
   
   public Tarefas getTarefasById(Long id) 
   {
    
        Vector retorno = new Vector();
        Tarefas tarefaRetorno = new Tarefas();
        
        try {
        	iniciaTransacaoBD();
            
            String sql = "SELECT * FROM Tarefas_BUG_TRACKING where idTarefa = " + id.longValue();
            ResultSet rs = executaSql(sql);
            
            //System.out.println("Depois do resultset...");
            
            if(rs != null && rs.next()){
                

                tarefaRetorno.setIdTarefa(new Long(rs.getLong("idTarefa")));
                tarefaRetorno.setTitulo(rs.getString("titulo"));
                tarefaRetorno.setCaminhoMenu(rs.getString("caminhoMenu"));
                tarefaRetorno.setDescricao(rs.getString("descricao"));
                
                if (rs.getString("dtOcorrencia") != null)
                	tarefaRetorno.setDtOcorrencia(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("dtOcorrencia"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                
                tarefaRetorno.setHrOcorrencia(rs.getString("hrOcorrencia"));
                tarefaRetorno.setHrSolicitacao(rs.getString("hrSolicitado"));
                
                tarefaRetorno.setDtSolicitacao(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("dtSolicitado"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                
                tarefaRetorno.setIdClassificacaoAdmin(new Long(rs.getLong("idClassificacaoAdmin")));
                tarefaRetorno.setIdClassificacaoUsuario(new Long(rs.getLong("idClassificacaoUsuario")));
                tarefaRetorno.setIdSituacaoTarefa(new Long(rs.getLong("idSituacaoTarefa")));
                tarefaRetorno.setIdTipoSolicitacao(new Long(rs.getLong("idTipoSolicitacao")));
                tarefaRetorno.setIdUsuarioCorrecao(new Long(rs.getLong("idUsuarioCorrecao")));
                tarefaRetorno.setIdUsuarioSolicitacao(new Long(rs.getLong("idUsuarioSolicitacao")));
                tarefaRetorno.setOperacaoImpedida(rs.getString("operacaoImpedida"));
                tarefaRetorno.setAnexo(rs.getString("anexo"));
                if (rs.getString("dtinicio")!=null)
                	tarefaRetorno.setDataIni(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("dtinicio"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                if (rs.getString("DTPREVISAOFIM")!=null)
                	tarefaRetorno.setDataPrevista(DateFormatter.calendarToString(DateFormatter.stringToCalendar(rs.getString("DTPREVISAOFIM"), "dd/MM/yyyy"), "dd/MM/yyyy"));
                tarefaRetorno.setTempoCorrecao(new Double(rs.getDouble("TEMPOCORRECAO")));
                tarefaRetorno.setValor(rs.getString("VLSOLICITACAO"));
                tarefaRetorno.setCentroCusto(rs.getString("CENTROCUSTO"));
                tarefaRetorno.setHoraEstimada(rs.getString("TEMPOCORRECAO"));

				if (rs.getString("tempoCorrecao") != null)
					tarefaRetorno.setTempoCorrecao(new Double(rs.getString("tempoCorrecao")));
                

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
        	finalizaTransacaoBD();
        }
        
        return tarefaRetorno;    
   }
   
   public Vector getClassificacaoTarefa(){
	    Vector retorno = new Vector();
	    try {
	    	iniciaTransacaoBD();
	        String sql = "SELECT * FROM CLASSIFICACAO_TAREFA_BUG_TRACKING";
	        ResultSet rs = executaSql(sql);
	        ClassificacaoTarefa cf = null;
	        while(rs.next()){
	        	cf = new ClassificacaoTarefa();
	        	cf.setId(new Long(rs.getLong("id")));
	            cf.setNmClassificacaoTarefa(rs.getString("nmClassificacao"));
	        	retorno.addElement(cf);
	        }
	
	    } catch (Exception ex) {
	
	        ex.printStackTrace();
	
	    } finally {
	    	finalizaTransacaoBD();
	    }
    
    return retorno;
   }
   
   public Vector getTipoSolicitacao(){
	    Vector retorno = new Vector();
	    try {
	    	iniciaTransacaoBD();
	        String sql = "SELECT * FROM TIPO_SOLICITACAO_BUG_TRACKING";
	        ResultSet rs = executaSql(sql);
	        TipoSolicitacao ts = null;
	        while(rs.next()){
	        	ts = new TipoSolicitacao();
	        	ts.setId(new Long(rs.getLong("id")));
	        	ts.setNmTipoSolicitacao(rs.getString("nmTipoSolicitacao"));
	        	retorno.addElement(ts);
	        }
	
	    } catch (Exception ex) {
	
	        ex.printStackTrace();
	
	    } finally {
	    	finalizaTransacaoBD();
	    }
	
	    return retorno;
   }

    public Vector getSituacaoTarefa(){
        Vector retorno = new Vector();
        try {
            iniciaTransacaoBD();
            String sql = "SELECT * FROM SITUACAO_TAREFA_BUG_TRACKING";
            ResultSet rs = executaSql(sql);
            SituacaoTarefa st = null;
            while(rs.next()){
                    st = new SituacaoTarefa();
                    st.setIdSituacao(new Long(rs.getLong("idSituacao")));
                    st.setNmSituacao(rs.getString("nmSituacao"));
                    retorno.addElement(st);
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
            finalizaTransacaoBD();
        }

        return retorno;
    }
    public String getNomeSituacaoTarefa(int id){
        String retorno = "";
        try {  
            iniciaTransacaoBD();
            String sql = "SELECT * FROM SITUACAO_TAREFA_BUG_TRACKING WHERE IDSITUACAO = " + id;
            ResultSet rs = executaSql(sql); 
            if (rs != null && rs.next())
            	retorno = rs.getString("nmSituacao"); 

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {
            finalizaTransacaoBD();
        }

        return retorno; 
    }
} 