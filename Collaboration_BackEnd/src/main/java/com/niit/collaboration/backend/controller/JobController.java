package com.niit.collaboration.backend.controller;

import java.util.Date;
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

import com.niit.collaboration.backend.dao.JobDAO;
import com.niit.collaboration.backend.dao.UserDAO;
import com.niit.collaboration.backend.model.Job;
import com.niit.collaboration.backend.model.User;

@RestController
public class JobController {
	@Autowired
	private Job job;
	@Autowired
	private JobDAO jobDAO;
	@RequestMapping(value = "/postJob", method = RequestMethod.POST)
	public ResponseEntity<?> postJob(@RequestBody Job job, HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			user.setErrorMessage("Unauthorized user...login using correct credentials");
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		} else {
			job.setPostedOn(new Date());
			jobDAO.postJob(job);
			return new ResponseEntity<User>(HttpStatus.OK);
		}
	}
	// to get all jobs
	@RequestMapping(value = "/getAllJobs", method = RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			
			user.setErrorMessage("Unauthorized user...login using correct credentials");
			return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);// 401
		}
		System.out.println("USER OBJECT " + user.getRole());
		List<Job> jobs = jobDAO.getAllJobs();
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK); // 200
	}
	// to get job detail
		@RequestMapping(value = "/getJobDetail/{jobId}", method = RequestMethod.GET)
		public ResponseEntity<?> getJobDetail(@PathVariable(value = "jobId") int jobId, HttpSession session) {
			User user = (User) session.getAttribute("user");
			if (user == null) {
				user.setErrorMessage( "Unauthorized user.. login using valid credentials");
				return new ResponseEntity<User>(user,HttpStatus.UNAUTHORIZED);// 401
			}
			Job jobDetail = jobDAO.getJobByID(jobId);
			
			return new ResponseEntity<Job>(jobDetail, HttpStatus.OK); // 200
		}
	}


