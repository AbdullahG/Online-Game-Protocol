package com.inno.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	@Column(unique=true)
	private String username;
	private int healthPoint;
	private int x,y;
	
	@Transient
	private boolean isMoving=false;
	
	@Transient
	private boolean isShooting=false;
	
	public User(){
		setHealthPoint(10);
		setX(0);
		setY(0);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer id) {
		this.userId = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getHealthPoint() {
		return healthPoint;
	}
	public void setHealthPoint(int i) {
		synchronized (this) {
			this.healthPoint = i;
		}
	}
	public int getX() {
		return x;
	}
	public void setX(int i) {
		synchronized (this) {
			this.x = i;
		}
	}
	public int getY() {
		return y;
	}
	public void setY(int i) {
		synchronized (this) {
			this.y = i;
		}
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		synchronized (this) {
			this.isMoving = isMoving;
		}
	}
	
	public boolean isShooting() {
		return isShooting;
	}

	public void setShooting(boolean isShooting) {
		synchronized (this) {
			this.isShooting = isShooting;
		}
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", username=" + username + ", healthPoint=" + healthPoint + ", x=" + x + ", y=" + y
				+ "]";
	}
}
