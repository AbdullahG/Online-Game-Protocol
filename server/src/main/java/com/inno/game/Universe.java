package com.inno.game;

import java.util.concurrent.ConcurrentHashMap;

import com.inno.models.User;

public class Universe {
	private static ConcurrentHashMap<String, User> onlineUsers;
	
	static {
		setOnlineUsers(new ConcurrentHashMap<String, User>());
	}

	public static ConcurrentHashMap<String, User> getOnlineUsers() {
		return onlineUsers;
	}

	public static void setOnlineUsers(ConcurrentHashMap<String, User> onlineUsers) {
		synchronized (onlineUsers) {
			Universe.onlineUsers = onlineUsers;
		}
	}
}
