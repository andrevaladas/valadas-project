package com.master.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MobDelivery_Server {

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(9999);
        
        System.out.println("MobDelivery Server running on 9999 ...");
        
        while(true){
        	final Socket socket = serverSocket.accept();
        	new Thread () {
        		public void run(){
                    try {
						new MobDelivery_Socket(socket).processa();
						System.out.println("processando...");
					} catch (IOException e) {
						e.printStackTrace();
					}
        		}
        	}.start();
        }
    }
}
