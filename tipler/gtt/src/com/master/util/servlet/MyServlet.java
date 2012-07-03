package com.master.util.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

public class MyServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3297460287651303207L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse reponse) throws ServletException, IOException {
		try {
			
			String usuario = request.getParameter("usuario");
			
			if ( FileUpload.isMultipartContent(request)) {
				DiskFileUpload upload = new DiskFileUpload();
				List items = upload.parseRequest(request);
				Iterator itr = items.iterator();
	            while(itr.hasNext()) {
	            	FileItem item = (FileItem) itr.next();
		              if (! item.isFormField() ) {
			                String path = getServletContext().getRealPath("/") + "Imagens/bandas/" + usuario+".jpg";
			                try {
			                	//File file = new File( path );
			                	item.write( new File( path ) );
			                } catch( IOException exc ) {
			                	System.out.println( "Erro gravando arquivo: " + exc.getMessage() );
			                }
		              }
	            }
			}    
		}
		catch (Exception e) { 
			e.printStackTrace(); 
			throw new ServletException(e); 
		}
	}
}