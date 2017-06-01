package com.niit.collaboration.backend.dao;

import java.util.List;

import com.niit.collaboration.backend.model.User;

public interface UserDAO {
	User registerUser(User user);
	public List<User> list();
	public User getUser(int id);
	public boolean update(User user);
	public boolean delete(int id);
	
	public User validate(User user);
}
