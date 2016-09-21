package com.inno.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

	public static void main(String[] args) {
		System.out.println("server is started");
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(5000);
			while(true){
				System.out.println("Listening for a client connection");
				Socket socket = serverSocket.accept();
				System.out.println("Connected to a Client");
				new Thread(new MultiThreadedServer(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(serverSocket != null)
				try {
					serverSocket.close();
					System.out.println("Multi-threaded server terminated..");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
