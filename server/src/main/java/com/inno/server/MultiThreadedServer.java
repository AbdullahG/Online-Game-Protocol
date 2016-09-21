package com.inno.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;


import com.inno.game.Command;
import com.inno.models.Response;
import com.inno.models.User;
import com.inno.services.HitRepository;
import com.inno.services.UserRepository;

public class MultiThreadedServer implements Runnable {

	private static ConcurrentHashMap<String, User> onlineUsers;
	private Socket clientSocket;
    private Command command;

	static {
		setOnlineUsers(new ConcurrentHashMap<String, User>());
	}

	public MultiThreadedServer(Socket socket) {
		this.setClientSocket(socket);
		this.setCommand(new Command());
	}

	public void run() {
		System.out.println("client thread started");

		try (BufferedReader bis = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintStream out = new PrintStream(clientSocket.getOutputStream())) {

			Response response;
			String commandText = null;
			
			trackHealthPoint(); // track hp and disconnect when it is 0, async
			
			while (true) {
				response = null;
				try{
					commandText = bis.readLine();
				}
				catch(Exception exc){
					// if there is no connection, break the loop
					break;
				}
				
				command.setCommand(commandText);
				response = command.execute();
				out.println(response);
				
				// if the response is quit, then break loop
				if(response != null && response.getMessage() != null
						&& response.getMessage().equals("You are disconnected from the server"))
					break;
			}
			if(!clientSocket.isClosed())
				clientSocket.close();
			System.out.println("Client connection is terminated");
		} catch (Exception e) {
		}
		System.out.println("client thread is terminated");
	}
	

	
	public void trackHealthPoint(){
		class HealtPointTracker implements Runnable{
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
						if(command.getActiveUser() != null && command.getActiveUser().getHealthPoint() <= 0){
							command.quit();
							clientSocket.close();
							break;
						}
					} catch (InterruptedException e) {
						System.out.println("A problem occured on sleep call of MoveCommand Runnable");
					} catch (Exception e) {
						System.out.println("A problem occured while closing socket due to health point");
					}
				}
			}
		}
		Thread t = new Thread(new HealtPointTracker());
		t.start();
	}


	public static ConcurrentHashMap<String, User> getOnlineUsers() {
		return onlineUsers;
	}

	public static void setOnlineUsers(ConcurrentHashMap<String, User> onlineUsers) {
		MultiThreadedServer.onlineUsers = onlineUsers;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}
}
