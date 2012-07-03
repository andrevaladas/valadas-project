/*
 * Created on May 28, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package jFiles;
import java.util.Vector;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class UsuarioLogado extends Usuario {
    
	private Vector acessos;
	
	/**
	 * @return Returns the acessos.
	 */
	public final Vector getAcessos() {
		return acessos;
	}
	/**
	 * @param acessos The acessos to set.
	 */
	public final void setAcessos(Vector acessos) {
		this.acessos = acessos;
	}
	
	public final boolean validaAcesso(String nmAcesso){
		boolean retorno = false;
		
		for (int i = 0; acessos !=null && i < acessos.size(); i++){
			if (acessos.elementAt(i) != null && ((String)acessos.elementAt(i)).equals(nmAcesso)){
				retorno = true;
				break;
			}
		}
		
		return retorno;
	}
        public final String getStringAcessos(){
            String retorno = new String();
            for (int i=0; acessos != null && i < acessos.size(); i++){
                retorno += (String)acessos.elementAt(i) + "|";
            }
            return retorno;
        }

}
