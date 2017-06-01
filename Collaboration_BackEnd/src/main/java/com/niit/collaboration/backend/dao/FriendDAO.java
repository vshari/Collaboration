package com.niit.collaboration.backend.dao;

import java.util.List;

import com.niit.collaboration.backend.model.Friend;

public interface FriendDAO {

	List<Friend> getAllFriends(String username);

	void sendFriendRequest(String from, String to);

	List<Friend> getPendingRequest(String username);

	void updatePendingRequest(char friendStatus, String fromId, String toId);
}