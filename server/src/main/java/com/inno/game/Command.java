package com.inno.game;

import java.util.Map;

import com.inno.models.Hit;
import com.inno.models.Response;
import com.inno.models.User;
import com.inno.services.HitRepository;
import com.inno.services.UserRepository;

public class Command {
	private String command;
	private UserRepository userRepository;
	private HitRepository hitRepository;
	private User activeUser;

	public Command() {
		this.setUserRepository(new UserRepository());
		this.setHitRepository(new HitRepository());
	}

	public Response execute() {
		Response response = badCommand(); // default value
		try {
			if (command.endsWith("<CR>")) {
				// remove <CR> from end
				if(command.equals("<CR>"))
					command = "";
				else if(command.length() > 4)
					command = command.substring(0, command.length() - 4);
				
				if(command.equals("")){ 
					// to inform client and to store active user's data on server, for security
					response = getActiveUsername();
				}
				else if (command.startsWith("HELLO ")) {
					if (activeUser != null)
						response = alreadyLoggedIn();
					else {
						String[] tmp = command.split(" ", 2);
						String temp = tmp[1];
						response = login(temp);
					}
				} else if (activeUser != null) {
					String username = command.substring(command.lastIndexOf(" by ")+4, command.length());
					command = command.substring(0, command.lastIndexOf(" by "));
					if(!activeUser.getUsername().equals(username)){
						response = authFailure(username);
					}
					else if (command.startsWith("QUIT")) {
						response = quit();
					} else if (command.startsWith("MOVE ")) {
						String[] tmp = command.split(" ");
						String temp = tmp[1];
						tmp = temp.split(",", 2);
						int x = Integer.parseInt(tmp[0]);
						int y = Integer.parseInt(tmp[1]);
						response = move(x, y);
					} else if (command.startsWith("FIRE ")) {
						String[] tmp = command.split(" ");
						String temp = tmp[1];
						tmp = temp.split(",", 2);
						int x = Integer.parseInt(tmp[0]);
						int y = Integer.parseInt(tmp[1]);
						response = fire(x, y);
					} else if (command.startsWith("STATUS")) {
						response = status();
					} else if (command.startsWith("LIST")) {
						response = list();
					}
				}
			}
		} catch (Exception e) {
			response = badCommand();
		}
		return response;
	}

	public Response quit() {
		Response response = new Response();
		Universe.getOnlineUsers().remove(activeUser.getUsername());
		response.setMessage("You are disconnected from the server");
		response.setUsername(activeUser.getUsername());
		userRepository.update(activeUser);
		setActiveUser(null);
		return response;
	}

	public Response login(String username) {
		Response response = new Response();
		User user = userRepository.findByUsername(username);
		if (user != null) { // the username already in use - registered before -
			if (user.getHealthPoint() <= 0) {
				response.setMessage("The username cannot be used anymore..");
				response.setUsername(username);
			} else {
				response.setMessage("OK " + user.getX() + "," + user.getY() + "," + user.getHealthPoint());
				response.setUsername(username);
			}
		} else { // create new user by username
			user = new User();
			user.setUsername(username);
			userRepository.create(user);
			response.setMessage("OK " + user.getX() + "," + user.getY() + "," + user.getHealthPoint());
			response.setUsername(username);
		}
		this.activeUser = user;
		Universe.getOnlineUsers().put(username, user);

		return response;
	}

	public Response list() {
		Response response = new Response();
		String msg = "";
		for (User user : Universe.getOnlineUsers().values()) {
			if (!msg.equals("")) // if there are more elements, split them
				msg = msg + " - ";
			msg = msg + user.getUsername() + " " + user.getX() + "," + user.getY() + "," + user.getHealthPoint();
		}
		response.setMessage(msg);
		response.setUsername(activeUser.getUsername());
		return response;
	}

	public Response move(final int x, final int y) {
		Response response = new Response();
		if (x <= 100 && x >= 0 && y <= 100 && y >= 0 && !activeUser.isMoving() && !activeUser.isShooting()) {
			response.setMessage("OK");
			class MoveCommand implements Runnable {
				@Override
				public void run() {
					activeUser.setMoving(true);
					while (activeUser.getX() != x || activeUser.getY() != y) {
						try {
							Thread.sleep(998); // approximately with the
												// calculations
							if (x > activeUser.getX() && y > activeUser.getY()) {
								activeUser.setX(activeUser.getX() + 1);
								activeUser.setY(activeUser.getY() + 1);
							} else if (x < activeUser.getX() && y < activeUser.getY()) {
								activeUser.setX(activeUser.getX() - 1);
								activeUser.setY(activeUser.getY() - 1);
							} else if (x > activeUser.getX() && y < activeUser.getY()) {
								activeUser.setX(activeUser.getX() + 1);
								activeUser.setY(activeUser.getY() - 1);
							} else if (x < activeUser.getX() && y > activeUser.getY()) {
								activeUser.setX(activeUser.getX() - 1);
								activeUser.setY(activeUser.getY() + 1);
							} else if (x < activeUser.getX() && y == activeUser.getY()) {
								activeUser.setX(activeUser.getX() - 1);
							} else if (y < activeUser.getY() && x == activeUser.getX()) {
								activeUser.setY(activeUser.getY() - 1);
							} else if (x > activeUser.getX() && y == activeUser.getY()) {
								activeUser.setX(activeUser.getX() + 1);
							} else if (y > activeUser.getY() && x == activeUser.getX()) {
								activeUser.setY(activeUser.getY() + 1);
							}
						} catch (InterruptedException e) {
							System.out.println("A problem occured on sleep call of MoveCommand Runnable");
						}
						userRepository.update(activeUser);
					}
					activeUser.setMoving(false);
				}
			}
			Thread t = new Thread(new MoveCommand());
			t.start();
		} else {
			response.setMessage("NO");
		}

		response.setUsername(activeUser.getUsername());
		return response;
	}

	public Response fire(final int x, final int y) {
		Response response = new Response();
		if (activeUser.isShooting() || activeUser.isMoving())
			response.setMessage("NO");
		else {
			class FireCommand implements Runnable {

				@Override
				public void run() {
					activeUser.setShooting(true);
					double distance = Math.sqrt(Math.pow(Math.abs(x - activeUser.getX()), 2)
							+ Math.pow(Math.abs(y - activeUser.getY()), 2));
					distance = distance / 3;
					try {
						Thread.sleep((long) (distance * 1000));
					} catch (InterruptedException e) {
						System.out.println("A problem occured on sleep call of MoveCommand Runnable");
					}
					Hit hit = null;
					// Control the positions of users and shoot them if there
					// are someone
					for (User user : Universe.getOnlineUsers().values()) {
						if (user.getX() == x && user.getY() == y && user.getHealthPoint() > 0) {
							user.setHealthPoint(user.getHealthPoint() - 1);
							userRepository.update(user);
							hit = new Hit();
							hit.setSource(activeUser);
							hit.setTarget(user);
							hitRepository.create(hit);
						}
						else if(user.getHealthPoint() <= 0){
							
						}
					}
					activeUser.setShooting(false);
				}
			}
			Thread t = new Thread(new FireCommand());
			t.start();
			response.setMessage("OK");
		}
		return response;
	}

	public Response status() {
		Response response = new Response();
		String msg = "";
		Map<User, Integer> hitMap = hitRepository.getHitsOfUser(activeUser);
		for (User user : hitMap.keySet()) {
			if (!msg.equals("")) // split the user info if there is more
				msg = msg + " ";
			msg = msg + user.getUsername() + "," + hitMap.get(user);
		}
		msg = activeUser.getX() + "," + activeUser.getY() + "," + activeUser.getHealthPoint() + " - " + msg;
		response.setMessage(msg);
		response.setUsername(activeUser.getUsername());

		return response;
	}

	public Response alreadyLoggedIn() {
		Response response = new Response();
		response.setMessage("You are already logged in with " + activeUser.getUsername() + " username");
		response.setUsername(activeUser.getUsername());
		return response;
	}

	public Response badCommand() {
		Response response = new Response();
		response.setMessage("Bad Command");
		if(activeUser != null)
			response.setUsername(activeUser.getUsername());
		else
			response.setUsername("no_active_user");
		return response;
	}
	
	public Response getActiveUsername(){
		Response response = new Response();
		response.setMessage("#"+activeUser.getUsername()+"#");
		response.setUsername("");
		return response;
	}
	
	public Response authFailure(String username){
		Response response = new Response();
		response.setMessage("You are not logged in with '"+username+"' username");
		if(activeUser != null)
			response.setUsername(activeUser.getUsername());
		else
			response.setUsername("no_active_user");
		
		return response;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public User getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(User activeUser) {
		this.activeUser = activeUser;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public HitRepository getHitRepository() {
		return hitRepository;
	}

	public void setHitRepository(HitRepository hitRepository) {
		this.hitRepository = hitRepository;
	}

}
