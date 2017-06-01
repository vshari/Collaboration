package com.niit.collaboration.backend.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.niit.collaboration.backend.dao.UserDAO;
import com.niit.collaboration.backend.model.User;


@Transactional
@Repository
@Component
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public User registerUser(User user) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(user);
		session.getTransaction().commit();
		session.flush();
		session.close();

		return user;

	}

	public List<User> list() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<User> users = session.createQuery("from User").list();
		tx.commit();
		session.flush();
		session.close();
		return users;
	}
		
	public User getUser(int id) {

		try {
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			User user = (User) session.get(User.class, id);
			tx.commit();
			session.flush();
			session.close();
			return user;

		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean update(User user) {
		try {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(user);
		tx.commit();
		session.flush();
		session.close();
		return true;
	}

	catch (Exception e) {
		e.printStackTrace();
		return false;
	}
	}

	public boolean delete(int id) {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.clear();
			session.delete(getUser(id));
			session.getTransaction().commit();
			session.flush();
			session.close();
			return true;
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public User validate(User user) {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from User where username=? and password=?");
		query.setString(0, user.getUsername());
		query.setString(1, user.getPassword());
		User validUser=(User)query.uniqueResult();
		return validUser;
		
	}

	
	
}