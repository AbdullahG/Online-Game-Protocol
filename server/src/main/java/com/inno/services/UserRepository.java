package com.inno.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.inno.models.User;

public class UserRepository {

	EntityManagerFactory emfactory = null;
	EntityManager entitymanager = null;

	public UserRepository() {
		emfactory = Persistence.createEntityManagerFactory("GAME_JPA");
		entitymanager = emfactory.createEntityManager();
	}

	public void create(User user) {
		entitymanager.getTransaction().begin();
		entitymanager.persist(user);
		entitymanager.getTransaction().commit();
	}

	public void update(User user) {
		entitymanager.getTransaction().begin();
		User u = entitymanager.find(User.class, user.getUserId());
		u.setUsername(user.getUsername());
		u.setHealthPoint(user.getHealthPoint());
		u.setX(user.getX());
		u.setY(user.getY());
		entitymanager.getTransaction().commit();
	}

	public User findById(Integer id) {
		User user = entitymanager.find(User.class, id);
		return user;
	}
	
	public User findByUsername(String username){
		Query query = entitymanager.createQuery("SELECT u FROM User u WHERE u.username = :username");
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<User> userList = query.getResultList();
		
		if(userList != null && userList.size()>0)
			return userList.get(0);
		else
			return null;
	}

	public void delete(Integer id) {
		entitymanager.getTransaction( ).begin( );
		User user = entitymanager.find(User.class, id);
		entitymanager.remove(user);
		entitymanager.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAll(){
		List<User> userList;
		Query query = entitymanager.createQuery("SELECT u FROM User u");
		userList = query.getResultList();
		return userList;
	}
}
