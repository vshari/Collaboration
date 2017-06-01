package com.niit.collaboration.backend.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.collaboration.backend.dao.FriendDAO;
import com.niit.collaboration.backend.model.Friend;

@Transactional
@Repository("frienddao")
public class FriendDAOImpl implements FriendDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	// get all friends
	public List<Friend> getAllFriends(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where (to_id=? or from_id=?) and status=?");
		query.setString(0, username);
		query.setString(1, username);
		query.setCharacter(2, 'A');
		List friends = query.list();
		session.close();
		return friends;
	}

	public void sendFriendRequest(String from, String to) {
		Session session = sessionFactory.openSession();
		Friend friend = new Friend();
		friend.setFromId(from);
		friend.setToId(to);
		friend.setStatus('P');
		session.save(friend);
		session.flush();
		session.close();
		
	}

	public List<Friend> getPendingRequest(String username) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Friend where toId=? and status=?");
		query.setString(0, username);
		query.setCharacter(1, 'P');
		List<Friend> pendingRequest = query.list();
		session.close();
		return pendingRequest;
	}

	public void updatePendingRequest(char friendStatus, String fromId, String toId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("update Friend set status=? where fromId=? and toId=?");
		query.setCharacter(0, friendStatus);
		query.setString(1, fromId);
		query.setString(2, toId);
		int count = query.executeUpdate();
		System.out.println("Number of records updated " + count);
		session.flush();
		session.close();
		
	}

}
