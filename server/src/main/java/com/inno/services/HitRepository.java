package com.inno.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.inno.models.Hit;
import com.inno.models.User;

public class HitRepository {

	EntityManagerFactory emfactory = null;
	EntityManager entitymanager = null;

	public HitRepository() {
		emfactory = Persistence.createEntityManagerFactory("GAME_JPA");
		entitymanager = emfactory.createEntityManager();
	}

	public void create(Hit hit) {
		entitymanager.getTransaction().begin();
		entitymanager.persist(hit);
		entitymanager.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAll(){
		List<User> userList;
		Query query = entitymanager.createQuery("SELECT u FROM User u");
		userList = query.getResultList();
		return userList;
	}
	
	public Map<User,Integer> getHitsOfUser(User user){
		Map<User,Integer> hitListOfUser = new HashMap<User, Integer>();
		Query query = entitymanager.createQuery("SELECT h FROM Hit h WHERE h.target = :target_userId");
		query.setParameter("target_userId", user);
		@SuppressWarnings("unchecked")
		List<Hit> hitList = query.getResultList();
		for (Hit hit : hitList) {
			if(hitListOfUser.get(hit.getSource())==null)
				hitListOfUser.put(hit.getSource(), 1);
			else
				hitListOfUser.put(hit.getSource(), hitListOfUser.get(hit.getSource())+1);
		}
		return hitListOfUser;
	}
}
