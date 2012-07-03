package com.master.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MobDelivery_Socket {

    private final Socket socket;

    public MobDelivery_Socket(Socket socket) {
        this.socket = socket;
    }

    public void processa() throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        
        String stringao = in.readUTF();
        System.out.println(stringao);
        MobDelivery_Conexao conexao = new MobDelivery_Conexao();
        String abc = "";
        try {
			abc  = conexao.execute("http://127.0.0.1:8080/mobdelivery/CelularServlet?"+stringao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.writeUTF(abc);
		System.out.println(abc);
        in.close();
        out.close();
        
    }

}