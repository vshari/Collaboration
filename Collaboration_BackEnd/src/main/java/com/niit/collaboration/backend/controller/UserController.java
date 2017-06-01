package com.niit.collaboration.backend.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.backend.dao.FileUploadDAO;
import com.niit.collaboration.backend.dao.UserDAO;
import com.niit.collaboration.backend.model.UploadFile;
import com.niit.collaboration.backend.model.User;

@RestController
public class UserController {
	@Autowired
	private User user;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private FileUploadDAO fileUploadDAO;

	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public ResponseEntity <List<User>> getAllUser() {
		List<User> userList = userDAO.list();
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}

	
	@GetMapping("/user/{id}")
	public ResponseEntity<User>getUserByID(@PathVariable(value="id")int id)
	{
		user=userDAO.getUser(id);
		if(user==null)
		{
			user=new User();
			user.setErrorCode("404");
			user.setErrorMessage("User doesnot exist with the id:"+id);
		}
		else
		{
			user.setErrorCode("200");
			user.setErrorMessage("Success");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}
	
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user) {

		User savedUser = userDAO.registerUser(user);
		if (savedUser.getId() == 0) {
			Error error = new Error("couldn't create user details");
			return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
		} else {
			System.out.println("user=" + savedUser.getId() + "\tregistered successfully********");
			return new ResponseEntity<User>(savedUser, HttpStatus.OK);
		}

	}
	//@CrossOrigin(origins="http://localhost:8096")
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session){
		
		User validUser=userDAO.validate(user);
		if(validUser==null){
			user.setErrorCode("404");
			user.setErrorMessage("Invalid Credentials.  Please enter valid credentials");
			return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);
		}
		else{
			user.setErrorCode("200");
			user.setErrorMessage("You have successfully logged in.");
			session.setAttribute("user", validUser);
			validUser.setOnline(true);
			userDAO.update(validUser);
			
			// getting the profile pic from database for the logged in user
						UploadFile getUploadFile = fileUploadDAO.getFile(user.getUsername());
						if (getUploadFile != null) {
							String name = getUploadFile.getFileName();
							System.out.println(getUploadFile.getData());
							byte[] imagefiles = getUploadFile.getData();
							try {
								String path = "E:/Collaboration_Project2/Collaboration_BackEnd/src/main/webapp/WEB-INF/resources/images/"
										+ user.getUsername();
								File file = new File(path);
								FileOutputStream fos = new FileOutputStream(file);
								fos.write(imagefiles);
								fos.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
			
			
						
		}
	}
	
	
	// logout
		@RequestMapping(value = "/logout", method = RequestMethod.PUT)
		public ResponseEntity<?> logout(HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				user.setOnline(false);
				userDAO.update(user);
				try {
					String path = "E:/Collaboration_Project2/Collaboration_BackEnd/src/main/webapp/WEB-INF/resources/images/"
							+ user.getUsername();
					File file = new File(path);
					System.out.println(file.delete());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			session.removeAttribute("user");
			session.invalidate();
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	

}