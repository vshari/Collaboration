package com.niit.collaboration.backend.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.backend.dao.FriendDAO;
import com.niit.collaboration.backend.model.Friend;
import com.niit.collaboration.backend.model.User;

@RestController
public class FriendController {
	@Autowired
	private FriendDAO friendDao;
	@Autowired
	 HttpSession session;
	
	// setters + getters
	
	public FriendDAO getFriendDao() {
		return friendDao;
	}

	public void setFriendDao(FriendDAO friendDao) {
		this.friendDao = friendDao;
	}
	// get list of all friends
		@RequestMapping(value = "/getAllFriends", method = RequestMethod.GET)
		public ResponseEntity<?> getAllFriends(HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				List<Friend> friends = friendDao.getAllFriends(user.getUsername());
				return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
			} else
				user.setErrorMessage( "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);
		}
	
		// friend request
		@RequestMapping(value = "/friendRequest", method = RequestMethod.POST)
		public ResponseEntity<?> sendFriendRequest(@RequestBody String username, HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user == null)
			{
				user.setErrorMessage( "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);
			}
			else {
				friendDao.sendFriendRequest(user.getUsername(), username);
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		}
		// get pending friend request list
		@RequestMapping(value = "/pendingRequest", method = RequestMethod.GET)
		public ResponseEntity<?> getAllPendingRequest(HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user == null)
			{
				user.setErrorMessage( "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);
			}
			else {
				List<Friend> pendingRequest = friendDao.getPendingRequest(user.getUsername());
				return new ResponseEntity<List<Friend>>(pendingRequest, HttpStatus.OK);
			}
		}
		// update friend request
		@RequestMapping(value = "/updateFriendRequest/{friendStatus}/{fromId}", method = RequestMethod.PUT)
		public ResponseEntity<?> updatePendingRequest(@PathVariable(value = "friendStatus") char friendStatus,
				@PathVariable(value = "fromId") String fromId, HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user == null)
			{
				user.setErrorMessage( "Unauthorized user.. login using valid credentials");
			return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);
			}
			else {
				friendDao.updatePendingRequest(friendStatus, fromId, user.getUsername());
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		}
}
